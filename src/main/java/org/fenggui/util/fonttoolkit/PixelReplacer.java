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
 * Created on Jan 30, 2006
 * $Id: PixelReplacer.java 606 2009-03-13 14:56:05Z marcmenghin $
 */
package org.fenggui.util.fonttoolkit;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;

/**
 * 
 * 
 * @todo Comment this class... #
 * 
 * @author Johannes Schaback, last edited by $Author: marcmenghin $, $Date:
 *         2009-03-13 15:56:05 +0100 (Fr, 13 Mär 2009) $
 * @version $Revision: 606 $
 */
public class PixelReplacer extends RenderStage
{
    
    private final Paint p;
    private Color       matchColor = null;
    
    public PixelReplacer(Paint paint, Color matchColor)
    {
        p = paint;
        this.matchColor = matchColor;
    }
    
    
    public void renderChar(FontMetrics fontMetrics, BufferedImage image,
            char c, int safetyMargin)
    {
        final Graphics2D g = (Graphics2D) image.getGraphics();
        g.setPaint(p);
        for (int x = 0; x < image.getWidth(); x++)
        {
            for (int y = 0; y < image.getHeight(); y++)
            {
                if (INT_ARGBequals(image.getRGB(x, y), matchColor))
                {
                    g.fillRect(x, y, 1, 1);
                }
            }
        }
    }
    
    /**
     * Compares the RGB values of the two arguments. Note that it does not
     * compare the alpha values!
     * 
     * @param argb
     *            argb value from the image
     * @param color
     *            the color to compare it with
     * @return true if red, green and blue components are equal, false otherwise
     */
    public static boolean INT_ARGBequals(int argb, Color color)
    {
        final int blue = 0x000000FF & argb;
        final int green = (0x0000FF00 & argb) >> 8;
        final int red = (0x00FF0000 & argb) >> 16;
        // int alpha = (0xFF000000 & argb) >> 24;
        
        return color.getRed() == red && color.getBlue() == blue
                && color.getGreen() == green;
    }
    
    public static boolean INT_ARGBhasColor(int argb)
    {
        final int blue = 0x000000FF & argb;
        final int green = (0x0000FF00 & argb) >> 8;
        final int red = (0x00FF0000 & argb) >> 16;
        
        return red != 0 && green != 0 && blue != 0;
    }
}
