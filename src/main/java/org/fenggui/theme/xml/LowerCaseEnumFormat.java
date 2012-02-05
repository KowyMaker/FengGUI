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
 */
package org.fenggui.theme.xml;

/**
 * @author Esa Tanskanen
 * 
 */
public class LowerCaseEnumFormat<T extends Enum<?>> extends EnumFormat<T>
{
    /**
     * @param enumClass
     */
    public LowerCaseEnumFormat(Class<T> enumClass)
    {
        super(enumClass);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.fenggui.io.EnumFormat#encodeName(java.lang.String)
     */
    
    @Override
    protected String encodeName(String name) throws EncodingException
    {
        name = name.toLowerCase();
        if (!name.contains("-"))
        {
            name = name.replace('_', '-');
        }
        else if (!name.contains("_"))
        {
            throw new EncodingException("The encoded name '" + name
                    + "' of the enumeration " + _enumClass.getName()
                    + " would be ambiguous");
        }
        
        return name;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.fenggui.io.EnumFormat#decodeName(java.lang.String)
     */
    
    @Override
    protected String decodeName(String encodedName)
    {
        if (!encodedName.contains("_"))
        {
            encodedName = encodedName.replace('-', '_');
        }
        
        return encodedName;
    }
    
    @Override
    protected boolean equalityOperation(String enumName, String decodedName)
    {
        return enumName.equalsIgnoreCase(decodedName);
    }
}
