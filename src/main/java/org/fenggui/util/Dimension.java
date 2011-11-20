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
 * Created on May 17, 2005
 * $Id: Dimension.java 614 2009-03-28 13:13:57Z marcmenghin $
 */
package org.fenggui.util;

/**
 * Class for holding sizes. This Dimension is read-only.
 * 
 * 
 * @author Johannes Schaback
 * @see org.lwjgl.util.WritableDimension
 */
public class Dimension implements Cloneable
{
    
    int width = -1, height = -1;
    
    /**
     * Creates a new Dimension object.
     * 
     * @param w
     *            the width
     * @param h
     *            the height
     */
    public Dimension(int w, int h)
    {
        width = w;
        height = h;
    }
    
    /**
     * Copy constructor for creating an <code>Dimenion</code> object.
     * 
     * @param d
     *            the <code>Dimenion</code> object to copy
     */
    public Dimension(Dimension d)
    {
        width = d.getWidth();
        height = d.getHeight();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    
    public Dimension clone()
    {
        try
        {
            return (Dimension) super.clone();
        }
        catch (final CloneNotSupportedException e)
        {
            // can not happen but write out anyway
            Log.error("Couldn't clone Dimension", e);
            return null;
        }
    }
    
    /**
     * Returns if the given point lays within the dimension.
     * 
     * @param x
     *            the x coordinate of the point
     * @param y
     *            the y coordinat of the point
     * @return true if inside, false otherwise
     */
    public boolean contains(int x, int y)
    {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
    
    /**
     * Returns the height of the dimension.
     * 
     * @return height
     */
    public int getHeight()
    {
        return height;
    }
    
    /**
     * Returns the width of the dimension
     * 
     * @return width
     */
    public int getWidth()
    {
        return width;
    }
    
    /**
     * Returns a string with the width and height of this dimension.
     * 
     * @return string
     */
    
    public String toString()
    {
        return "(" + width + ", " + height + ")";
    }
    
    public void setWidth(int width)
    {
        this.width = width;
    }
    
    public void setSize(int width, int height)
    {
        this.width = width;
        this.height = height;
    }
    
    public void setSize(Dimension d)
    {
        width = d.getWidth();
        height = d.getHeight();
    }
    
    public void setHeight(int height)
    {
        this.height = height;
    }
    
    
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        
        final Dimension dimension = (Dimension) o;
        
        if (height != dimension.height)
        {
            return false;
        }
        if (width != dimension.width)
        {
            return false;
        }
        
        return true;
    }
    
    
    public int hashCode()
    {
        int result;
        result = width;
        result = 31 * result + height;
        return result;
    }
}
