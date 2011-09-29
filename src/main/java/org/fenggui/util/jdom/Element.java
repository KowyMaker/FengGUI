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
 * Created on Dec 17, 2006
 * $Id: Element.java 606 2009-03-13 14:56:05Z marcmenghin $
 */
package org.fenggui.util.jdom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Element
{
    private int                       line       = -1;
    private String                    name       = null;
    private final List<Element>       kids       = new ArrayList<Element>();
    private final Map<String, String> attributes = new HashMap<String, String>();
    protected Element                 parent     = null;
    private static final String       OFFSET     = "   ";
    
    public Element(String name)
    {
        this(name, -1);
    }
    
    public Element(String name, int lineNumber)
    {
        this.name = name;
        line = lineNumber;
    }
    
    public Element getParent()
    {
        return parent;
    }
    
    public void setAttribute(String name, String value)
    {
        attributes.put(name, value);
    }
    
    public String getAttributeValue(String name)
    {
        return attributes.get(name);
    }
    
    public void add(Element e)
    {
        kids.add(e);
        e.parent = this;
    }
    
    public String getName()
    {
        return name;
    }
    
    public Iterable<Element> getChildren()
    {
        return kids;
    }
    
    public Element getChild(String name)
    {
        // boolean search = false;
        for (final Element el : kids)
        {
            if (el.getName().equals(name))
            {
                return el;
            }
        }
        
        return null;
    }
    
    public Iterable<String> getAttributeNames()
    {
        return attributes.keySet();
    }
    
    public void toXML(String offset, StringBuilder b)
    {
        b.append(offset);
        b.append('<');
        b.append(name);
        
        for (final String key : attributes.keySet())
        {
            b.append(' ');
            b.append(key);
            b.append("=\"");
            b.append(attributes.get(key));
            b.append('"');
        }
        
        if (kids.isEmpty())
        {
            b.append("/>\n");
            return;
        }
        else
        {
            b.append(">\n");
            
            for (final Element kid : kids)
            {
                kid.toXML(offset + OFFSET, b);
            }
            
            b.append(offset);
            b.append("</");
            b.append(name);
            b.append(">\n");
            
        }
    }
    
    public int getLineNumber()
    {
        return line;
    }
}
