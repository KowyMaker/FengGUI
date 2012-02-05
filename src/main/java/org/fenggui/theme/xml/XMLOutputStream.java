/*
 * FengGUI - Java GUIs in OpenGL (http://www.fenggui.org)
 * 
 * Copyright (c) 2005-2009 FengGUI Project
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details:
 * http://www.gnu.org/copyleft/lesser.html#TOC3
 * 
 * $Id: XMLOutputStream.java 613 2009-03-25 22:02:20Z marcmenghin $
 */
package org.fenggui.theme.xml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import org.fenggui.util.jdom.Document;
import org.fenggui.util.jdom.Element;

/**
 * An output stream which produces an XML presentation of an object
 * 
 * @author Esa Tanskanen
 * 
 */
public class XMLOutputStream extends OutputOnlyStream
{
    private final NumberFormat                                  doubleFormat;
    private final Document                                      document;
    private final PrintWriter                                   out;
    
    private Element                                             activeElement;
    private final Stack<Boolean>                                writtenAnythingStack = new Stack<Boolean>();
    private boolean                                             writtenAnythingNow   = false;
    
    private final Map<String, Element>                          namedElements        = new HashMap<String, Element>();
    private final Map<Class<? extends IXMLStreamable>, Integer> instanceCounts       = new HashMap<Class<? extends IXMLStreamable>, Integer>();
    
    public XMLOutputStream(OutputStream out, String rootElementName,
            GlobalContextHandler contextHandler)
    {
        this(out, rootElementName, contextHandler, false, 3);
    }
    
    public Document getDocument()
    {
        return document;
    }
    
    public XMLOutputStream(OutputStream out, String rootElementName,
            GlobalContextHandler contextHandler, boolean saveDefaultValues,
            int numSavedDecimals)
    {
        super(saveDefaultValues, contextHandler);
        this.out = new PrintWriter(out);
        
        doubleFormat = new DecimalFormat("0");
        doubleFormat.setMaximumFractionDigits(numSavedDecimals);
        
        document = new Document(rootElementName);
        
        activeElement = document;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.fenggui.io.InputOutputStream#process(java.lang.String, int)
     */
    
    @Override
    public int processAttribute(String name, int value) throws IOException
    {
        writtenAnythingNow = true;
        processAttribute(name, String.valueOf(value));
        return value;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.fenggui.io.InputOutputStream#process(java.lang.String,
     * java.lang.String)
     */
    
    @Override
    public String processAttribute(String name, String value)
            throws IOException
    {
        writtenAnythingNow = true;
        activeElement.setAttribute(name, value);
        return value;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.fenggui.io.InputOutputStream#process(java.lang.String, float)
     */
    
    @Override
    public double processAttribute(String name, double value)
            throws IOException
    {
        writtenAnythingNow = true;
        processAttribute(name, doubleFormat.format(value).replace(',', '.'));
        return value;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.fenggui.io.InputOutputStream#process(java.lang.String, boolean)
     */
    
    @Override
    public boolean processAttribute(String name, boolean value)
            throws IOException
    {
        writtenAnythingNow = true;
        processAttribute(name, String.valueOf(value));
        return value;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.fenggui.io.InputOnlyStream#getParsingContext()
     */
    
    @Override
    protected IParseContext getParsingContext()
    {
        final Element parent = activeElement.getParent();
        
        if (parent != null)
        {
            return new LazyParseContext(parent, parent.getLineNumber(),
                    activeElement.getLineNumber());
        }
        
        return new LazyParseContext(activeElement, 0, 0);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.fenggui.io.InputOutputStream#startSubcontext(java.lang.String)
     */
    
    @Override
    public boolean startSubcontext(String name)
    {
        super.startSubcontext(name);
        final Element el = new Element(name);
        activeElement.add(el);
        activeElement = el;
        return true;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.fenggui.io.InputOutputStream#endSubcontext()
     */
    
    @Override
    public void endSubcontext()
    {
        super.endSubcontext();
        activeElement = activeElement.getParent();
    }
    
    private <T extends IXMLStreamable> Element handleChild(T child,
            String elementName) throws IOException, IXMLStreamableException
    {
        writtenAnythingNow = true;
        startSubcontext(elementName);
        
        // Check if the object has already been saved and refer to it
        
        String idName = getQualifiedName(child);
        Element existingElement = null;
        
        if (idName != null)
        {
            existingElement = namedElements.get(idName);
        }
        
        if (existingElement != null)
        {
            // Construct a reference
            existingElement.setAttribute(XMLInputStream.ATTRIBUTE_NAME, idName);
            processAttribute(XMLInputStream.ATTRIBUTE_REFERENCE, idName);
        }
        else
        {
            // Save a new object
            
            writtenAnythingStack.push(writtenAnythingNow);
            writtenAnythingNow = false;
            child.process(this);
            final boolean wasWritten = writtenAnythingNow;
            writtenAnythingNow = writtenAnythingStack.pop();
            
            if (wasWritten)
            {
                final String setName = activeElement
                        .getAttributeValue(XMLInputStream.ATTRIBUTE_NAME);
                
                if (setName != null)
                {
                    idName = setName;
                }
                
                if (idName == null)
                {
                    idName = child.getUniqueName();
                }
                
                if (idName != null)
                {
                    if (idName.equals(IXMLStreamable.GENERATE_NAME))
                    {
                        // Generate a simple name for the object
                        Integer count = instanceCounts.get(child.getClass());
                        
                        if (count == null)
                        {
                            count = new Integer(0);
                        }
                        
                        count++;
                        
                        instanceCounts.put(child.getClass(), count);
                        
                        idName = child.getClass().getSimpleName() + " " + count;
                        
                        if (namedElements.containsKey(idName))
                        {
                            idName = child.getClass().getName() + " " + count;
                        }
                    }
                    
                    // Make it possible to refer to this object
                    putObject(idName, child);
                    namedElements.put(idName, activeElement);
                    
                    existingElement = activeElement;
                }
            }
        }
        
        endSubcontext();
        
        return existingElement;
    }
    
    @Override
    public <T extends IXMLStreamable> T processChild(String name, T value,
            Class<T> objectClass) throws IOException, IXMLStreamableException
    {
        if (value == null)
        {
            return null;
        }
        handleChild(value, name);
        return value;
    }
    
    @Override
    public <T extends IXMLStreamable> T processChild(T value,
            TypeRegister typeRegister) throws IOException,
            IXMLStreamableException
    {
        final String name = typeRegister.getName(value.getClass());
        handleChild(value, name);
        return value;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T extends IXMLStreamable> void processChildren(String childName,
            List children, Class<T> childClass) throws IOException,
            IXMLStreamableException
    {
        for (final Object ob : children)
        {
            handleChild((IXMLStreamable) ob, childName);
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.fenggui.theme.xml.InputOutputStream#processChildren(java.lang.String,
     * java.util.Map, java.lang.Class)
     */
    
    @Override
    public <T extends IXMLStreamable> void processChildren(String childName,
            Map<String, T> children, Class<T> childClass) throws IOException,
            IXMLStreamableException
    {
        for (final Entry<String, T> entry : children.entrySet())
        {
            final Element element = handleChild(
                    (IXMLStreamable) entry.getValue(), childName);
            element.setAttribute(InputOutputStream.KEY_MAPKEY, entry.getKey());
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.fenggui.theme.xml.InputOutputStream#processChildren(java.lang.String,
     * java.util.Map, java.lang.Class, java.lang.Enum)
     */
    
    @Override
    public <T extends Enum<T>> void processEnumChildren(String childName,
            Map<String, T> children, Class<T> enumClass) throws IOException,
            IXMLStreamableException
    {
        for (final Entry<String, T> entry : children.entrySet())
        {
            final Element element = new Element(childName);
            element.setAttribute(InputOutputStream.KEY_MAPKEY, entry.getKey());
            // Name attribute would be nice but is not as nice for the end user.
            // element.setAttribute(InputOutputStream.KEY_MAPKEY,
            // entry.getValue().name());
            element.setAttribute(InputOutputStream.KEY_MAPKEY, entry.getValue()
                    .toString());
        }
    }
    
    @Override
    public <T extends IXMLStreamable> void processChildren(List<T> children,
            TypeRegister typeRegister) throws IOException,
            IXMLStreamableException
    {
        for (final T ob : children)
        {
            final String name = typeRegister.getName(ob.getClass());
            
            if (name != null)
            {
                handleChild((IXMLStreamable) ob, name);
            }
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.fenggui.theme.xml.InputOutputStream#processChildren(java.util.Map,
     * org.fenggui.theme.xml.TypeRegister)
     */
    
    @Override
    public <T extends IXMLStreamable> void processChildren(
            Map<String, T> children, TypeRegister typeRegister)
            throws IOException, IXMLStreamableException
    {
        for (final Entry<String, T> entry : children.entrySet())
        {
            final String name = typeRegister.getName(entry.getValue()
                    .getClass());
            
            if (name != null)
            {
                final Element element = handleChild((IXMLStreamable) entry,
                        name);
                element.setAttribute(InputOutputStream.KEY_MAPKEY,
                        entry.getKey());
            }
        }
        
    }
    
    @Override
    public <T extends IXMLStreamable> void processInherentChild(String name,
            T value) throws IOException, IXMLStreamableException
    {
        handleChild(value, name);
    }
    
    @Override
    public void close()
    {
        out.write(document.toXML());
        out.close();
    }
    
}
