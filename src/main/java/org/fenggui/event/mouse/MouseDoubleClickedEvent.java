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
 * Created on 2005-4-10
 * $Id: MouseReleasedEvent.java 473 2008-03-12 11:25:07Z marcmenghin $
 */
package org.fenggui.event.mouse;

import java.util.Set;

import org.fenggui.IWidget;
import org.fenggui.event.key.Key;

/**
 * @author Johannes Schaback ($Author: marcmenghin $)
 * 
 */
public class MouseDoubleClickedEvent extends MouseEvent
{
    
    private final int         displayX, displayY, clickCount;
    private final MouseButton button;
    
    public MouseDoubleClickedEvent(IWidget source, int x, int y,
            MouseButton mouseButton, int clickCount, Set<Key> modifiers)
    {
        super(source, modifiers);
        button = mouseButton;
        displayX = x;
        displayY = y;
        this.clickCount = clickCount;
    }
    
    public MouseButton getButton()
    {
        return button;
    }
    
    @Override
    public int getDisplayX()
    {
        return displayX;
    }
    
    @Override
    public int getDisplayY()
    {
        return displayY;
    }
    
    public int getClickCount()
    {
        return clickCount;
    }
    
}