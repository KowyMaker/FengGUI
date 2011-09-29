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
package org.fenggui.binding.render.jogl;

import java.awt.Component;

import org.fenggui.binding.render.Cursor;

public class JOGLCursor extends Cursor
{
    private java.awt.Cursor cursor    = null;
    private Component       component = null;
    
    public JOGLCursor(java.awt.Cursor c, Component awtComponent)
    {
        cursor = c;
        component = awtComponent;
    }
    
    @Override
    public void show()
    {
        component.setCursor(cursor);
    }
}
