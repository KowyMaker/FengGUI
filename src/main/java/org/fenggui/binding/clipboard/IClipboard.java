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
 * Created on 26.09.2007
 * $Id$
 */
package org.fenggui.binding.clipboard;

/**
 * Represents a Clipboard implementation. It is possible to read and write a
 * String from and to the clipboard.
 * 
 * @author marcmenghin, last edited by $Author$, $Date$
 * @version $Revision$
 */
public interface IClipboard
{
    
    /**
     * Returns the text in the systems clipboard.
     * 
     * @return Text form the clipboard.
     */
    public abstract String getText();
    
    /**
     * Sets the given text to the systems clipboard.
     * 
     * @param text
     *            Text to set to the clipboard.
     */
    public abstract void setText(String text);
    
}
