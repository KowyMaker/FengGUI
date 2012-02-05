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
 * $Id: BinaryDilation.java 606 2009-03-13 14:56:05Z marcmenghin $
 */
package org.fenggui.util.fonttoolkit;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Performs a Binary Dilation on the character image. In non-technical terms,
 * the shape of the character will grow by the half of the mask size.<br/>
 * <br/>
 * The Binary Dilation algorithm places on every pixel that matches a certain
 * color a colored mask (centered). That means that each pixel that has the
 * matching color will grow by the half size of the mask if the mask is solid.<br/>
 * <br/>
 * This Binary Dilation implementation only supports rectangular kernels.
 * 
 * 
 * 
 * @todo Evaluate to implement Erosion as well #
 * @todo Evaulate idea to facilitate arbirtray kernels #
 * 
 * @author Johannes Schaback, last edited by $Author: marcmenghin $, $Date:
 *         2009-03-13 15:56:05 +0100 (Fr, 13 Mär 2009) $
 * @version $Revision: 606 $
 */
public class BinaryDilation extends RenderStage
{
    
    private Color maskColor = null;
    private int   maskSize  = 3;
    
    /**
     * Creates a new BinaryDilation font rendering pipeline stage.
     * 
     * @param maskColor
     *            the color of the kernel
     * @param maskSize
     *            the square root of size kernel (kernelSize x kernelSize)
     *            kernel on
     */
    public BinaryDilation(Color maskColor, int maskSize)
    {
        this.maskColor = maskColor;
        this.maskSize = maskSize;
    }
    
    /**
     * Performs binary dilation.
     */
    
    @Override
    public void renderChar(FontMetrics fontMetrics, BufferedImage image,
            char c, int safetyMargin)
    {
        final BufferedImage copy = new BufferedImage(image.getWidth(),
                image.getHeight(), image.getType());
        copy.getGraphics().drawImage(image, 0, 0, null);
        final Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(maskColor);
        
        for (int x = maskSize / 2; x < image.getWidth() - maskSize / 2; x++)
        {
            for (int y = maskSize / 2; y < image.getHeight() - maskSize / 2; y++)
            {
                
                if (PixelReplacer.INT_ARGBhasColor(copy.getRGB(x, y)))
                {
                    for (int j = 0; j < maskSize; j++)
                    {
                        for (int k = 0; k < maskSize; k++)
                        {
                            g.drawLine(x + j - maskSize / 2, y + k - maskSize
                                    / 2, x + j - maskSize / 2, y + k - maskSize
                                    / 2);
                        }
                    }
                }
                
            }
        }
        
    }
    
    public static void hasColor(int rgb)
    {
        
    }
    
}
