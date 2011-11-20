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
 * Created on 22.11.2007
 * $Id$
 */
package org.fenggui.text.content.part;

import org.fenggui.appearance.TextAppearance;
import org.fenggui.binding.render.Graphics;
import org.fenggui.binding.render.text.ITextRenderer;
import org.fenggui.text.TextUtil;
import org.fenggui.text.content.factory.simple.TextStyleEntry;
import org.fenggui.util.Dimension;

/**
 * 
 * @author marcmenghin, last edited by $Author$, $Date$
 * @version $Revision$
 */
public class TextPart extends AbstractContentPart
{
    private String text;
    private int    activeAtom = -1;
    
    /**
     * This constructor is used for every content that is not plaintext. A
     * selection needs to be mapped into the content therefore the indexStart
     * and indexAddedLength are used.
     * 
     * @param text
     * @param style
     * @param character
     * @param indexStart
     * @param indexAddedLength
     */
    public TextPart(String text, String styleKey, int beforeLength,
            int afterLength, TextAppearance appearance)
    {
        super(beforeLength, afterLength, styleKey);
        this.text = text;
        updateSize(appearance);
    }
    
    /**
     * This constructor is used to create a plaintext textpart. The index used
     * will be just like using the String index for the given text.
     * 
     * @param text
     * @param style
     * @param hideContent
     * @param character
     */
    public TextPart(String text, String styleKey, TextAppearance appearance)
    {
        this(text, styleKey, 0, 0, appearance);
    }
    
    
    public void getContent(StringBuilder result)
    {
        result.append(text);
    }
    
    private void updateSize(TextAppearance appearance)
    {
        final Dimension size = new Dimension(0, 0);
        final ITextRenderer renderer = appearance.getStyle(getStyleKey())
                .getTextStyleEntry(this.getStyleStateKey())
                .resolveRenderer(appearance);
        if (text.length() > 0)
        {
            size.setSize(renderer.getWidth(text), renderer.getLineHeight());
        }
        else
        {
            size.setSize(0, renderer.getLineHeight());
        }
        this.setSize(size);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.fenggui.binding.render.text.advanced.AbstractContentPart#splitAt(int,
     * boolean)
     */
    
    public TextPart splitAtWord(int width, boolean firstPart,
            TextAppearance appearance)
    {
        String part1, part2;
        final ITextRenderer renderer = appearance.getStyle(getStyleKey())
                .getTextStyleEntry(this.getStyleStateKey())
                .resolveRenderer(appearance);
        int h = TextUtil.findLineEnd(text, renderer, width);
        if (h < 0 || h > text.length())
        {
            final TextPart newPart = new TextPart("", getStyleKey(),
                    getBeforeLength(), getAfterLength(), appearance);
            newPart.setSelected(this.isSelected());
            return newPart;
        }
        else
        {
            if (h + 1 < text.length() && text.charAt(h) == ' ')
            {
                final int curWidth = renderer
                        .getWidth(text.substring(0, h + 1));
                if (width >= curWidth)
                {
                    h++;
                }
            }
            part1 = text.substring(0, h);
            part2 = text.substring(h, text.length());
            
            if (firstPart && renderer.getWidth(part1) > width)
            {
                // if width smaller then first word split on char level, don'
                // care about words
                h = TextUtil.findLineEndChar(text, renderer, width);
                if (h <= 0)
                {
                    h = 1; // take at least one character
                }
                
                part1 = text.substring(0, h);
                part2 = text.substring(h, text.length());
            }
            
            text = part1;
            updateSize(appearance);
            final TextPart newPart = new TextPart(part2, getStyleKey(),
                    getBeforeLength(), getAfterLength(), appearance);
            newPart.setSelected(this.isSelected());
            if (activeAtom > text.length())
            {
                newPart.activeAtom = activeAtom - text.length();
                activeAtom = -1;
            }
            return newPart;
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.fenggui.binding.render.text.advanced.AbstractContentPart#isBreakable
     * ()
     */
    
    public boolean isSplittable()
    {
        // return (text.indexOf(' ') >= 0);
        return text.length() > 1;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.fenggui.binding.render.text.advanced.AbstractContentPart#mergePart
     * (org.fenggui.binding.render.text.advanced.AbstractContentPart)
     */
    
    public void mergePart(AbstractContentPart part, TextAppearance appearance)
    {
        if (canMerge(part))
        {
            final TextPart tPart = (TextPart) part;
            if (tPart.activeAtom != -1)
            {
                activeAtom = text.length() + tPart.activeAtom;
            }
            text += tPart.text;
            updateSize(appearance);
        }
        else
        {
            throw new IllegalArgumentException(
                    "AbstractContentPart not compatible with this TextPart.");
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.fenggui.binding.render.text.advanced.AbstractContentPart#canMerge
     * (org.fenggui.binding.render.text.advanced.AbstractContentPart)
     */
    
    public boolean canMerge(AbstractContentPart part)
    {
        if (part instanceof TextPart)
        {
            if (this.getStyleKey() == ((TextPart) part).getStyleKey()
                    && this.isSelected() == ((TextPart) part).isSelected())
            {
                return true;
            }
        }
        
        return false;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.fenggui.binding.render.text.advanced.AbstractContentPart#render(int,
     * int, org.fenggui.binding.render.Graphics,
     * org.fenggui.binding.render.IOpenGL)
     */
    
    public void render(int x, int y, Graphics g, TextAppearance appearance)
    {
        final TextStyleEntry style = appearance.getStyle(getStyleKey())
                .getTextStyleEntry(this.getStyleStateKey());
        if (this.isSelected())
        {
            style.getSelectionBackground().paint(g, x,
                    y - getSize().getHeight(), getSize().getWidth(),
                    getSize().getHeight());
            
            style.resolveRenderer(appearance).render(x, y, text,
                    style.getSelectionColor(), g);
        }
        else
        {
            style.getBackground().paint(g, x, y - getSize().getHeight(),
                    getSize().getWidth(), getSize().getHeight());
            
            style.resolveRenderer(appearance).render(x, y, text,
                    style.getColor(), g);
        }
    }
    
    
    public int getAtomCount()
    {
        return text.length();
    }
    
    
    public int calculatePositionInAtoms(int x, TextAppearance appearance)
    {
        final ITextRenderer renderer = appearance.getStyle(getStyleKey())
                .getTextStyleEntry(this.getStyleStateKey())
                .resolveRenderer(appearance);
        final int maxCharWidth = renderer.getWidth('M');
        int curChar = x / maxCharWidth - 1;
        if (curChar >= text.length())
        {
            return -1;
        }
        else
        {
            if (curChar < 0)
            {
                curChar = 0;
            }
        }
        
        int length = renderer.getWidth(text.substring(0, curChar));
        while (length < x)
        {
            curChar++;
            if (curChar < text.length())
            {
                length = renderer.getWidth(text.substring(0, curChar));
            }
            else
            {
                curChar = text.length();
                break;
            }
        }
        
        curChar--;
        if (curChar < 0)
        {
            curChar = 0;
        }
        
        // move char one up if over half of char
        int charWidth = 0;
        if (text.length() > 0)
        {
            charWidth = renderer.getWidth(text.charAt(curChar));
        }
        length = renderer.getWidth(text.substring(0, curChar));
        if (x - length > charWidth / 2)
        {
            curChar++;
        }
        
        return curChar;
    }
    
    
    public int getAtomPosition(int atom, TextAppearance appearance)
    {
        if (atom < 0 || atom > text.length())
        {
            return -1;
        }
        final ITextRenderer renderer = appearance.getStyle(getStyleKey())
                .getTextStyleEntry(this.getStyleStateKey())
                .resolveRenderer(appearance);
        return renderer.getWidth(text.substring(0, atom));
    }
    
    
    public boolean addChar(char c, TextAppearance appearance)
    {
        if (activeAtom >= 0)
        {
            text = text.substring(0, activeAtom) + c
                    + text.substring(activeAtom);
            updateSize(appearance);
            activeAtom++;
            
            return true;
        }
        else
        {
            return false;
        }
    }
    
    
    public boolean addContent(String content, TextAppearance appearance)
    {
        if (activeAtom >= 0)
        {
            text = text.substring(0, activeAtom) + content
                    + text.substring(activeAtom);
            activeAtom += content.length();
            updateSize(appearance);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    
    public Character removeNextAtom(TextAppearance appearance)
    {
        int activeAtom = this.activeAtom;
        if (activeAtom <= 0)
        {
            activeAtom = 0;
            this.activeAtom = 0;
        }
        
        if (activeAtom < text.length())
        {
            final char removableChar = text.charAt(activeAtom);
            final String textBefore = text.substring(0, activeAtom);
            String textAfter = "";
            if (activeAtom + 1 < text.length())
            {
                textAfter = text.substring(activeAtom + 1, text.length());
            }
            
            text = textBefore + textAfter;
            updateSize(appearance);
            return removableChar;
        }
        return null;
    }
    
    
    public Character removePreviousAtom(TextAppearance appearance)
    {
        int activeAtom = this.activeAtom;
        if (activeAtom < 0)
        {
            activeAtom = text.length();
            this.activeAtom = activeAtom;
        }
        
        if (activeAtom >= 1)
        {
            final char removableChar = text.charAt(activeAtom - 1);
            final String textBefore = text.substring(0, activeAtom - 1);
            final String textAfter = text.substring(activeAtom, text.length());
            text = textBefore + textAfter;
            updateSize(appearance);
            this.activeAtom--;
            return removableChar;
        }
        return null;
    }
    
    
    public void setActiveAtom(int atom)
    {
        activeAtom = atom;
    }
    
    
    public int getActivePosition(TextAppearance appearance)
    {
        return getAtomPosition(activeAtom, appearance);
    }
    
    
    public boolean hasActiveAtom()
    {
        return activeAtom >= 0;
    }
    
    
    public boolean isEmpty()
    {
        if (text == null || text.length() <= 0)
        {
            return true;
        }
        return false;
    }
    
    
    public AbstractContentPart splitAtChar(int width, TextAppearance appearance)
    {
        final ITextRenderer renderer = appearance.getStyle(getStyleKey())
                .getTextStyleEntry(this.getStyleStateKey())
                .resolveRenderer(appearance);
        final int h = TextUtil.findLineEndChar(text, renderer, width);
        return splitAtAtom(h, appearance);
    }
    
    
    public AbstractContentPart splitAtAtom(int atom, TextAppearance appearance)
    {
        String part1, part2;
        
        part1 = text.substring(0, atom);
        part2 = text.substring(atom, text.length());
        
        text = part1;
        updateSize(appearance);
        
        final TextPart newPart = new TextPart(part2, getStyleKey(),
                getBeforeLength(), getAfterLength(), appearance);
        newPart.setSelected(this.isSelected());
        if (activeAtom > text.length())
        {
            newPart.activeAtom = activeAtom - text.length();
            activeAtom = -1;
        }
        return newPart;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.fenggui.text.content.part.AbstractContentPart#getActiveAtom()
     */
    
    public int getActiveAtom()
    {
        return activeAtom;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.fenggui.text.content.part.AbstractContentPart#splittAtActivePosition
     * (org.fenggui.appearance.TextAppearance)
     */
    
    public AbstractContentPart splittAtActivePosition(TextAppearance appearance)
    {
        if (activeAtom < 0 || activeAtom > text.length())
        {
            throw new IllegalStateException(
                    "No active position defined or active Position is wrong. (was "
                            + activeAtom + ")");
        }
        
        String part1, part2;
        
        part1 = text.substring(0, activeAtom);
        part2 = text.substring(activeAtom, text.length());
        
        text = part1;
        updateSize(appearance);
        
        final TextPart newPart = new TextPart(part2, getStyleKey(),
                getBeforeLength(), getAfterLength(), appearance);
        newPart.setSelected(this.isSelected());
        if (activeAtom > text.length())
        {
            newPart.activeAtom = activeAtom - text.length();
            activeAtom = -1;
        }
        return newPart;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.fenggui.text.content.part.AbstractContentPart#isValidCharacter(char,
     * org.fenggui.appearance.TextAppearance)
     */
    
    public boolean isValidCharacter(char character, TextAppearance appearance)
    {
        final TextStyleEntry style = appearance.getStyle(getStyleKey())
                .getTextStyleEntry(this.getStyleStateKey());
        
        if (this.isSelected())
        {
            return style.resolveRenderer(appearance).isValidChar(character);
        }
        else
        {
            return style.resolveRenderer(appearance).isValidChar(character);
        }
    }
    
}
