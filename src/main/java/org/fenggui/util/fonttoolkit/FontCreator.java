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
package org.fenggui.util.fonttoolkit;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import org.fenggui.util.Alphabet;

/**
 * Command line prototype for creating fonts that looks like from the stone age
 * when Rainers Font Creator is done :)
 * 
 * @author Johannes, last edited by $Author: bbeaulant $, $Date: 2006-10-25
 *         12:49:21 +0200 (Wed, 25 Oct 2006) $
 * @version $Revision: 94 $
 */
public class FontCreator
{
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            printUsage();
            return;
        }
        
        if (args[0].equals("-list"))
        {
            final GraphicsEnvironment ge = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();
            final String fontNames[] = ge.getAvailableFontFamilyNames();
            for (final String s : fontNames)
            {
                System.out.println(s);
            }
        }
        
        if (args[0].equals("-list"))
        {
            final GraphicsEnvironment ge = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();
            final String fontNames[] = ge.getAvailableFontFamilyNames();
            for (final String s : fontNames)
            {
                System.out.println(s);
            }
        }
        else if (args[0].equals("-create"))
        {
            if (args[0].length() < 6)
            {
                System.out.println("Too few arguments!");
                printUsage();
                return;
            }
            
            final String fontname = args[1];
            final int size = Integer.parseInt(args[2]);
            int style = 0;
            
            if (args[3].equals("plain"))
            {
                style = Font.PLAIN;
            }
            else if (args[3].equals("bold"))
            {
                style = Font.BOLD;
            }
            else if (args[3].equals("italic"))
            {
                style = Font.ITALIC;
            }
            
            final boolean antiAliasing = Boolean.parseBoolean(args[4]);
            final org.fenggui.binding.render.ImageFont f = FontFactory
                    .renderStandardFont(
                            new java.awt.Font(fontname, style, size),
                            antiAliasing, Alphabet.getDefaultAlphabet());
            /*
             * try { f.writeFontData(args[5]+".png",args[5]+".font"); } catch
             * (IOException e) { e.printStackTrace(); }
             */
            f.getHeight(); // fix for warning
        }
        
    }
    
    public static void printUsage()
    {
        System.out.println("FengGUI Command-Line Font Creator");
        System.out.println("Usage:");
        System.out
                .println("java org.fenggui.examples.FontCreator {-list | -create fontname size {plain|bold|italic} anti-aliasing name}");
        System.out.println("");
        System.out.println("-list:   lists all available AWT fonts");
        System.out.println("-create: creates a new FengGUI font");
        System.out.println("    fontame       - name of the AWT font");
        System.out
                .println("    size          - size of the FengGUI font (int)");
        System.out
                .println("    anti-aliasing - boolean whether font shall be rendered with anti-aliasing enabled");
        System.out
                .println("    name          - filename stem for name.png and name.font");
        System.out
                .println("Send questions and comments to johannes.schaback@gmail.com");
        
    }
    
}
