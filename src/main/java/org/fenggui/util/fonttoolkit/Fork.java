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
 * $Id: Fork.java 606 2009-03-13 14:56:05Z marcmenghin $
 */
package org.fenggui.util.fonttoolkit;

import java.awt.FontMetrics;
import java.awt.image.BufferedImage;

/**
 * @todo This class has never been tested or used :P #
 * 
 * @todo Comment this class... #
 * 
 * @author Johannes Schaback, last edited by $Author: marcmenghin $, $Date:
 *         2009-03-13 15:56:05 +0100 (Fr, 13 Mär 2009) $
 * @version $Revision: 606 $
 */
public class Fork extends RenderStage
{
    
    private AssemblyLine  line       = null;
    private BufferedImage forkResult = null;
    
    public Fork()
    {
        line = new AssemblyLine();
    }
    
    @Override
    public void renderChar(FontMetrics fontMetrics, BufferedImage image,
            char c, int safetyMargin)
    {
        
        forkResult = new BufferedImage(image.getWidth(), image.getHeight(),
                image.getType());
        
        line.execute(fontMetrics, forkResult, c, safetyMargin);
        
    }
    
    public BufferedImage getForkResult()
    {
        return forkResult;
    }
    
    public AssemblyLine getForkedAssemblyLine()
    {
        return line;
    }
    
}
