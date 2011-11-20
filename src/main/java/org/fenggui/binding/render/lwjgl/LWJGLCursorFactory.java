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
package org.fenggui.binding.render.lwjgl;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import org.fenggui.binding.render.CursorFactory;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

/**
 * Creates Cursors for LWJGL for FengGUI.
 * 
 * Note that cursors have to be greater than 16x16:
 * http://www.javagaming.org/forums/index.php?topic=11190.0
 * 
 * @author Johannes, last edited by $Author: marcmenghin $, $Date: 2009-03-13
 *         15:56:05 +0100 (Fr, 13 Mär 2009) $
 * @version $Revision: 606 $
 */
public class LWJGLCursorFactory extends CursorFactory
{
    private static final int    BLACK                    = 0xff000000;
    private static final int    WHITE                    = 0xffffffff;
    private static final int    OPAQUE                   = 0x0;
    
    /**
     * Cursor mask for the standard text cursor.
     */
    public static final int[][] TEXT_CURSOR              = new int[][] {
            { BLACK, BLACK, BLACK, OPAQUE, BLACK, BLACK, BLACK },
            { OPAQUE, OPAQUE, OPAQUE, BLACK, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, BLACK, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, BLACK, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, BLACK, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, BLACK, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, BLACK, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, BLACK, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, BLACK, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, BLACK, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, BLACK, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, BLACK, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, BLACK, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, BLACK, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, BLACK, OPAQUE, OPAQUE, OPAQUE },
            { BLACK, BLACK, BLACK, OPAQUE, BLACK, BLACK, BLACK } };
    
    public static final int[][] FORBIDDEN_CURSOR         = new int[][] {
            { OPAQUE, OPAQUE, BLACK, BLACK, BLACK, OPAQUE, OPAQUE },
            { OPAQUE, BLACK, OPAQUE, OPAQUE, OPAQUE, BLACK, OPAQUE },
            { BLACK, OPAQUE, OPAQUE, OPAQUE, BLACK, OPAQUE, BLACK },
            { BLACK, OPAQUE, OPAQUE, BLACK, OPAQUE, OPAQUE, BLACK },
            { BLACK, OPAQUE, BLACK, OPAQUE, OPAQUE, OPAQUE, BLACK },
            { OPAQUE, BLACK, OPAQUE, OPAQUE, OPAQUE, BLACK, OPAQUE },
            { OPAQUE, OPAQUE, BLACK, BLACK, BLACK, OPAQUE, OPAQUE }, };
    
    public static final int[][] VERTICAL_RESIZE_CURSOR   = new int[][] {
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE },
            { OPAQUE, OPAQUE, WHITE, BLACK, BLACK, BLACK, WHITE, OPAQUE, OPAQUE },
            { OPAQUE, WHITE, BLACK, BLACK, BLACK, BLACK, BLACK, WHITE, OPAQUE },
            { WHITE, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, WHITE },
            { WHITE, WHITE, WHITE, WHITE, BLACK, WHITE, WHITE, WHITE, WHITE },
            { OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE },
            { WHITE, WHITE, WHITE, WHITE, BLACK, WHITE, WHITE, WHITE, WHITE },
            { WHITE, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, WHITE },
            { OPAQUE, WHITE, BLACK, BLACK, BLACK, BLACK, BLACK, WHITE, OPAQUE },
            { OPAQUE, OPAQUE, WHITE, BLACK, BLACK, BLACK, WHITE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE }                                    };
    
    public static final int[][] HORIZONTAL_RESIZE_CURSOR = new int[][] {
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE, WHITE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE,
            WHITE, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE,
            BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, WHITE, BLACK, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE,
            BLACK, BLACK, WHITE, OPAQUE, OPAQUE },
            { OPAQUE, WHITE, BLACK, BLACK, BLACK, WHITE, WHITE, WHITE, WHITE,
            WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, BLACK, BLACK,
            BLACK, WHITE, OPAQUE },
            { WHITE, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK,
            BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK,
            BLACK, BLACK, WHITE },
            { OPAQUE, WHITE, BLACK, BLACK, BLACK, WHITE, WHITE, WHITE, WHITE,
            WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, BLACK, BLACK,
            BLACK, WHITE, OPAQUE },
            { OPAQUE, OPAQUE, WHITE, BLACK, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE,
            BLACK, BLACK, WHITE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE,
            BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE, WHITE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE,
            WHITE, OPAQUE, OPAQUE, OPAQUE, OPAQUE }     };
    
    public static final int[][] MOVE_CURSOR              = new int[][] {
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, WHITE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            WHITE, BLACK, BLACK, BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE,
            BLACK, BLACK, BLACK, BLACK, BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK,
            BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE, WHITE,
            WHITE, WHITE, BLACK, WHITE, WHITE, WHITE, WHITE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE, WHITE, OPAQUE, OPAQUE,
            OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE, WHITE, WHITE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK,
            WHITE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, WHITE, BLACK, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK,
            BLACK, WHITE, OPAQUE, OPAQUE },
            { OPAQUE, WHITE, BLACK, BLACK, BLACK, WHITE, WHITE, WHITE, WHITE,
            WHITE, BLACK, WHITE, WHITE, WHITE, WHITE, WHITE, BLACK, BLACK,
            BLACK, WHITE, OPAQUE },
            { WHITE, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK,
            BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK,
            BLACK, BLACK, WHITE },
            { OPAQUE, WHITE, BLACK, BLACK, BLACK, WHITE, WHITE, WHITE, WHITE,
            WHITE, BLACK, WHITE, WHITE, WHITE, WHITE, WHITE, BLACK, BLACK,
            BLACK, WHITE, OPAQUE },
            { OPAQUE, OPAQUE, WHITE, BLACK, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK,
            BLACK, WHITE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK,
            WHITE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE, WHITE, OPAQUE, OPAQUE,
            OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE, WHITE, WHITE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE, WHITE,
            WHITE, WHITE, BLACK, WHITE, WHITE, WHITE, WHITE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK,
            BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE,
            BLACK, BLACK, BLACK, BLACK, BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            WHITE, BLACK, BLACK, BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, WHITE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE }    };
    
    public static final int[][] NW_RESIZE_CURSOR         = new int[][] {
            { WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { WHITE, BLACK, BLACK, BLACK, BLACK, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { WHITE, BLACK, BLACK, BLACK, BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { WHITE, BLACK, BLACK, BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { WHITE, BLACK, BLACK, WHITE, BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { WHITE, BLACK, WHITE, OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { WHITE, WHITE, OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK, WHITE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK,
            WHITE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE,
            BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE, WHITE, WHITE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            WHITE, BLACK, WHITE, OPAQUE, WHITE, BLACK, WHITE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, WHITE, BLACK, WHITE, BLACK, BLACK, WHITE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, WHITE, BLACK, BLACK, BLACK, WHITE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, WHITE, BLACK, BLACK, BLACK, BLACK, WHITE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            WHITE, BLACK, BLACK, BLACK, BLACK, BLACK, WHITE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE } };
    
    public static final int[][] SW_RESIZE_CURSOR         = new int[][] {
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            WHITE, BLACK, BLACK, BLACK, BLACK, BLACK, WHITE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, WHITE, BLACK, BLACK, BLACK, BLACK, WHITE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, WHITE, BLACK, BLACK, BLACK, WHITE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, WHITE, BLACK, WHITE, BLACK, BLACK, WHITE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            WHITE, BLACK, WHITE, OPAQUE, WHITE, BLACK, WHITE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE,
            BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE, WHITE, WHITE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK,
            WHITE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { WHITE, WHITE, OPAQUE, OPAQUE, OPAQUE, WHITE, BLACK, WHITE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { WHITE, BLACK, WHITE, OPAQUE, WHITE, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { WHITE, BLACK, BLACK, WHITE, BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { WHITE, BLACK, BLACK, BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { WHITE, BLACK, BLACK, BLACK, BLACK, WHITE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { WHITE, BLACK, BLACK, BLACK, BLACK, BLACK, WHITE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, OPAQUE, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE } };
    
    public static final int[][] HAND_CURSOR              = new int[][] {
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, BLACK, BLACK, OPAQUE,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, BLACK, WHITE, WHITE, BLACK,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, BLACK, WHITE, WHITE, BLACK,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, BLACK, WHITE, WHITE, BLACK,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, BLACK, WHITE, WHITE, BLACK,
            OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, BLACK, WHITE, WHITE, BLACK,
            BLACK, BLACK, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE,
            OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, BLACK, WHITE, WHITE, BLACK,
            WHITE, WHITE, BLACK, BLACK, BLACK, OPAQUE, OPAQUE, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, BLACK, WHITE, WHITE, BLACK,
            WHITE, WHITE, BLACK, WHITE, WHITE, BLACK, BLACK, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, BLACK, WHITE, WHITE, BLACK,
            WHITE, WHITE, BLACK, WHITE, WHITE, BLACK, WHITE, BLACK, OPAQUE },
            { BLACK, BLACK, BLACK, OPAQUE, BLACK, WHITE, WHITE, BLACK, WHITE,
            WHITE, BLACK, WHITE, WHITE, BLACK, WHITE, WHITE, BLACK },
            { BLACK, WHITE, WHITE, BLACK, BLACK, WHITE, WHITE, WHITE, WHITE,
            WHITE, WHITE, WHITE, WHITE, BLACK, WHITE, WHITE, BLACK },
            { BLACK, WHITE, WHITE, WHITE, BLACK, WHITE, WHITE, WHITE, WHITE,
            WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, BLACK },
            { OPAQUE, BLACK, WHITE, WHITE, BLACK, WHITE, WHITE, WHITE, WHITE,
            WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, BLACK },
            { OPAQUE, OPAQUE, BLACK, WHITE, BLACK, WHITE, WHITE, WHITE, WHITE,
            WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, BLACK },
            { OPAQUE, OPAQUE, BLACK, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE,
            WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, BLACK },
            { OPAQUE, OPAQUE, OPAQUE, BLACK, WHITE, WHITE, WHITE, WHITE, WHITE,
            WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, BLACK },
            { OPAQUE, OPAQUE, OPAQUE, BLACK, WHITE, WHITE, WHITE, WHITE, WHITE,
            WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, BLACK, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, BLACK, WHITE, WHITE, WHITE,
            WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, BLACK, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, BLACK, WHITE, WHITE, WHITE,
            WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, BLACK, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, BLACK, WHITE, WHITE,
            WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, BLACK, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, BLACK, WHITE, WHITE,
            WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, BLACK, OPAQUE, OPAQUE },
            { OPAQUE, OPAQUE, OPAQUE, OPAQUE, OPAQUE, BLACK, BLACK, BLACK,
            BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, OPAQUE, OPAQUE } };
    
    public static int[][] enlargeMask(int[][] source, int targetWidth,
            int targetHeight)
    {
        final int[][] array = new int[targetHeight][targetWidth];
        
        for (int i = 0; i < source.length; i++)
        {
            for (int j = 0; j < source[0].length; j++)
            {
                array[i + targetHeight - source.length][j] = source[i][j];
            }
        }
        
        return array;
    }
    
    public static IntBuffer convertMask(int[][] cursorMask)
    {
        final ByteBuffer bb = ByteBuffer.allocateDirect(cursorMask.length
                * cursorMask[0].length * 4);
        bb.order(ByteOrder.nativeOrder());
        final IntBuffer ib = bb.asIntBuffer();
        
        for (int y = cursorMask.length - 1; y >= 0; y--)
        {
            for (int x = 0; x < cursorMask[0].length; x++)
            {
                ib.put(cursorMask[y][x]);
            }
        }
        
        ib.flip();
        
        return ib;
    }
    
    public LWJGLCursorFactory()
    {
        setDefaultCursor(new LWJGLCursor(Mouse.getNativeCursor()));
        
        try
        {
            IntBuffer ib = convertMask(enlargeMask(TEXT_CURSOR,
                    Cursor.getMaxCursorSize(), Cursor.getMaxCursorSize()));
            setTextCursor(new LWJGLCursor(new Cursor(Cursor.getMaxCursorSize(),
                    Cursor.getMaxCursorSize(), 3, TEXT_CURSOR.length - 7, 1,
                    ib, null)));
            
            ib = convertMask(enlargeMask(HORIZONTAL_RESIZE_CURSOR,
                    Cursor.getMaxCursorSize(), Cursor.getMaxCursorSize()));
            setHorizontalResizeCursor(new LWJGLCursor(new Cursor(
                    Cursor.getMaxCursorSize(), Cursor.getMaxCursorSize(), 9,
                    HORIZONTAL_RESIZE_CURSOR.length - 4, 1, ib, null)));
            
            ib = convertMask(enlargeMask(VERTICAL_RESIZE_CURSOR,
                    Cursor.getMaxCursorSize(), Cursor.getMaxCursorSize()));
            setVerticalResizeCursor(new LWJGLCursor(new Cursor(
                    Cursor.getMaxCursorSize(), Cursor.getMaxCursorSize(), 4,
                    VERTICAL_RESIZE_CURSOR.length - 11, 1, ib, null)));
            
            ib = convertMask(enlargeMask(NW_RESIZE_CURSOR,
                    Cursor.getMaxCursorSize(), Cursor.getMaxCursorSize()));
            setNWResizeCursor(new LWJGLCursor(new Cursor(
                    Cursor.getMaxCursorSize(), Cursor.getMaxCursorSize(), 7,
                    NW_RESIZE_CURSOR.length - 7, 1, ib, null)));
            
            ib = convertMask(enlargeMask(SW_RESIZE_CURSOR,
                    Cursor.getMaxCursorSize(), Cursor.getMaxCursorSize()));
            setSWResizeCursor(new LWJGLCursor(new Cursor(
                    Cursor.getMaxCursorSize(), Cursor.getMaxCursorSize(), 7,
                    SW_RESIZE_CURSOR.length - 7, 1, ib, null)));
            
            ib = convertMask(enlargeMask(HAND_CURSOR,
                    Cursor.getMaxCursorSize(), Cursor.getMaxCursorSize()));
            setHandCursor(new LWJGLCursor(new Cursor(Cursor.getMaxCursorSize(),
                    Cursor.getMaxCursorSize(), 5, HAND_CURSOR.length - 1, 1,
                    ib, null)));
            
            ib = convertMask(enlargeMask(MOVE_CURSOR,
                    Cursor.getMaxCursorSize(), Cursor.getMaxCursorSize()));
            setMoveCursor(new LWJGLCursor(new Cursor(Cursor.getMaxCursorSize(),
                    Cursor.getMaxCursorSize(), 10, MOVE_CURSOR.length - 10, 1,
                    ib, null)));
            
            ib = convertMask(enlargeMask(FORBIDDEN_CURSOR,
                    Cursor.getMaxCursorSize(), Cursor.getMaxCursorSize()));
            setMoveCursor(new LWJGLCursor(new Cursor(Cursor.getMaxCursorSize(),
                    Cursor.getMaxCursorSize(), 3, 3, 1, ib, null)));
        }
        catch (final LWJGLException e)
        {
            e.printStackTrace();
        }
    }
    
    
    public LWJGLCursor createCursor(int xHotspot, int yHotspot,
            BufferedImage image)
    {
        return new LWJGLCursor(xHotspot, yHotspot, image);
    }
    
}
