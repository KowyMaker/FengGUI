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
 * $Id: MouseButton.java 606 2009-03-13 14:56:05Z marcmenghin $
 */
package org.fenggui.event.mouse;

/**
 * Enum that represents the three mouse buttons.
 * 
 * @author Johannes Schaback aka Schabby, last edited by $Author: marcmenghin $,
 *         $Date: 2009-03-13 15:56:05 +0100 (Fr, 13 Mär 2009) $
 * @version $Revision: 606 $
 */
public enum MouseButton
{
    
    LEFT(0), MIDDLE(2), RIGHT(1), WHEEL(3);
    
    private final int code;
    
    private MouseButton(int code)
    {
        this.code = code;
    }
    
    public int getCode()
    {
        return code;
    }
    
    public static MouseButton valueOf(int value)
    {
        for (final MouseButton b : values())
        {
            if (b.code == value)
            {
                return b;
            }
        }
        
        return LEFT;
    }
}
