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
 * Created on 02.01.2008
 * $Id$
 */
package org.fenggui.text.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.fenggui.appearance.TextAppearance;
import org.fenggui.binding.render.Graphics;
import org.fenggui.text.ITextCursorRenderer;
import org.fenggui.text.content.part.AbstractContentPart;
import org.fenggui.util.Dimension;
import org.fenggui.util.Point;

/**
 * A UserLine is a line where the user wants a linebreak after it. This means
 * that in the content there will be a '\n' before and/or after this userline.
 * Within a UserLine there will never be a linebreak ('\n'). So in other words
 * this is for all the lines that are not auto-wordwarped. Furthermore this
 * object maybe represents multiple lines.
 * 
 * @author marcmenghin, last edited by $Author$, $Date$
 * @version $Revision$
 */
public class ContentUserLine implements IContentSelection,
        IContent<ContentUserLine>
{
    private Dimension         size;
    private List<ContentLine> content;
    private ContentLine       activeLine      = null;
    private int               activeLineY     = 0;
    private int               activeLineAtoms = 0;
    private int               atomCountCache;
    
    public ContentUserLine(AbstractContentPart emptypart)
    {
        content = new ArrayList<ContentLine>();
        content.add(new ContentLine(emptypart));
        updateSize();
    }
    
    public ContentUserLine(ContentLine... lines)
    {
        content = new ArrayList<ContentLine>(lines.length);
        for (final ContentLine line : lines)
        {
            content.add(line);
        }
        
        updateSize();
    }
    
    public List<ContentLine> getContentLines()
    {
        return content;
    }
    
    public int getAtomsTillActiveLine()
    {
        if (hasActiveLine())
        {
            return activeLineAtoms;
        }
        else
        {
            return 0;
        }
    }
    
    public int getAtomsTillActiveLineEnd()
    {
        if (hasActiveLine())
        {
            return activeLineAtoms + activeLine.getAtomCount();
        }
        else
        {
            return 0;
        }
    }
    
    public void setContentLines(List<ContentLine> content)
    {
        this.content = content;
        this.updateSize();
    }
    
    private void updateSize()
    {
        int y = 0;
        int x = 0;
        int atomCount = 0;
        for (final ContentLine line : content)
        {
            x = Math.max(x, line.getSize().getWidth());
            y += line.getSize().getHeight();
            atomCount += line.getAtomCount();
        }
        
        // Dimension oldSize = this.size;
        size = new Dimension(x, y);
        
        atomCountCache = atomCount;
    }
    
    public void add(ContentLine line)
    {
        content.add(line);
        
        // update size
        int y = size.getHeight();
        int x = size.getWidth();
        
        x = Math.max(x, line.getSize().getWidth());
        y += line.getSize().getHeight();
        
        size = new Dimension(x, y);
        
        // update atomcount cache
        atomCountCache += line.getAtomCount();
    }
    
    public int getContentLineCount()
    {
        return content.size();
    }
    
    public int getLinePartCount(int line)
    {
        if (line >= content.size() || line < 0)
        {
            throw new IllegalArgumentException("Line does not exist (line: "
                    + line + " - range: 0-" + (content.size() - 1) + ")");
        }
        
        return content.get(line).getPartCount();
    }
    
    public ContentLine getSelectedLine()
    {
        return activeLine;
    }
    
    public int getAtomCount()
    {
        return atomCountCache;
    }
    
    public long findAtomOnPosition(int x, int y, TextAppearance appearance)
    {
        // find line:
        int posY = 0;
        int atomCount = 0;
        ContentLine line = null;
        for (final ContentLine parts : content)
        {
            if (posY + parts.getSize().getHeight() > y)
            {
                line = parts;
                break;
            }
            posY += parts.getSize().getHeight();
            atomCount += parts.getAtomCount();
        }
        
        if (line == null)
        {
            // select last line
            line = content.get(content.size() - 1);
            atomCount -= line.getAtomCount();
        }
        
        atomCount += line.calculatePositionInAtoms(x, appearance);
        
        return atomCount;
    }
    
    /**
     * Splits the Content on the current active position. Throws an
     * AssertionError if no active position is defined.
     * 
     * @return Second part of the content.
     */
    public ContentUserLine splitActive(IContentFactory factory,
            TextAppearance appearance)
    {
        if (!hasActiveLine())
        {
            throw new AssertionError(
                    "Can not split line as there is no active position.");
        }
        
        // prepare ContentUserLine
        final ContentUserLine uline = new ContentUserLine(
                factory.getEmptyContentPart(appearance));
        final List<ContentLine> lines = uline.getContentLines();
        lines.clear();
        
        // add last part of selected line
        final ContentLine rest = activeLine.splitActive(factory, appearance);
        if (rest != null)
        {
            lines.add(rest);
        }
        
        // add all other lines
        boolean found = false;
        for (final ContentLine line : content)
        {
            if (line == activeLine)
            {
                found = true;
            }
            else
            {
                if (found == true)
                {
                    lines.add(line);
                }
            }
        }
        
        content.removeAll(lines);
        
        // updates size & contentCache
        uline.updateSize();
        this.updateSize();
        
        // update active lines
        uline.activeLine = rest;
        uline.activeLineY = 0;
        uline.activeLineAtoms = 0;
        
        activeLine = null;
        activeLineAtoms = 0;
        activeLineY = 0;
        
        return uline;
    }
    
    public void mergeContent(ContentUserLine line)
    {
        content.addAll(line.content);
        if (!hasActiveLine() && line.hasActiveLine())
        {
            activeLine = line.activeLine;
            activeLineAtoms = line.activeLineAtoms + atomCountCache;
            activeLineY = line.activeLineY + this.getSize().getHeight();
        }
        size.setHeight(line.size.getHeight() + size.getHeight());
        size.setWidth(Math.max(line.size.getWidth(), size.getWidth()));
        atomCountCache += line.atomCountCache;
    }
    
    public void optimizeContent(TextAppearance appearance)
    {
        ContentLine first = null;
        // collect content from all lines into first
        for (final ContentLine line : content)
        {
            if (first == null)
            {
                first = line;
            }
            else
            {
                first.mergeContent(line);
            }
        }
        
        if (first != null)
        {
            // optimize first content and only add that one
            first.optimizeContent(appearance);
            content.clear();
            content.add(first);
            if (first.hasActivePart())
            {
                activeLine = first;
                activeLineAtoms = 0;
                activeLineY = 0;
            }
            updateSize();
        }
    }
    
    public boolean hasActiveLine()
    {
        return activeLine != null && activeLine.hasActivePart();
    }
    
    /**
     * Updates the content by merging together the lines and splitting them
     * again with the given width.
     * 
     * @param width
     */
    public void UpdateContent(int width, boolean wordWarp,
            IContentFactory factory, TextAppearance appearance)
    {
        final List<ContentLine> result = new Vector<ContentLine>(content.size());
        
        ContentLine curLine = null;
        for (final ContentLine line : content)
        {
            if (curLine == null)
            {
                curLine = line;
            }
            else
            {
                if (activeLine == line)
                {
                    activeLine = curLine;
                    activeLineAtoms -= curLine.getAtomCount();
                    activeLineY -= line.getSize().getHeight();
                }
                curLine.mergeContent(line);
            }
            
            if (wordWarp && curLine.getSize().getWidth() > width)
            {
                final ContentLine newLine = curLine;
                newLine.optimizeContent(appearance);
                result.add(newLine);
                curLine = curLine.splitAt(width, factory, appearance);
                if (newLine == activeLine && !newLine.hasActivePart())
                {
                    activeLine = curLine;
                    activeLineY += newLine.getSize().getHeight();
                    activeLineAtoms += newLine.getAtomCount();
                }
            }
        }
        // add rest lines
        if (curLine != null)
        {
            while (wordWarp && curLine.getSize().getWidth() > width
                    && curLine.isSplittable())
            {
                final ContentLine newLine = curLine;
                newLine.optimizeContent(appearance);
                result.add(newLine);
                curLine = curLine.splitAt(width, factory, appearance);
            }
            if (curLine != null)
            {
                curLine.optimizeContent(appearance);
                result.add(curLine);
            }
        }
        content.clear();
        content.addAll(result);
        
        updateSize();
        UpdateActiveLine();
    }
    
    /**
     * Updates the data for the active Line. It will use the first active line
     * it finds.
     */
    private void UpdateActiveLine()
    {
        int lineY = 0;
        int atoms = 0;
        
        activeLine = null;
        activeLineY = 0;
        activeLineAtoms = 0;
        
        for (final ContentLine line : content)
        {
            if (line.hasActivePart())
            {
                activeLine = line;
                activeLineY = lineY;
                activeLineAtoms = atoms;
                break;
            }
            
            atoms += line.getAtomCount();
            lineY += line.getSize().getHeight();
        }
        
    }
    
    public void removeAll(IContentFactory factory, TextAppearance appearance)
    {
        final boolean activePart = this.hasActiveLine();
        
        for (final ContentLine line : content)
        {
            line.removeAll(factory, appearance);
        }
        
        content.clear();
        
        content.add(new ContentLine(factory.getEmptyContentPart(appearance)));
        this.updateSize();
        if (activePart)
        {
            this.setActiveAtom(0);
        }
        
    }
    
    public void getContent(StringBuilder result)
    {
        for (final ContentLine part : content)
        {
            part.getContent(result);
        }
    }
    
    public void getSelectedContent(StringBuilder result)
    {
        for (final ContentLine line : content)
        {
            if (line.hasSelection())
            {
                line.getSelectedContent(result);
            }
        }
    }
    
    public boolean addChar(char c, TextAppearance appearance)
    {
        if (this.hasActiveLine())
        {
            if (activeLine.addChar(c, appearance))
            {
                updateSize();
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    
    public void addContent(Object content, int width, boolean wordwarp,
            IContentFactory factory, TextAppearance appearance)
    {
        if (activeLine != null)
        {
            activeLine
                    .addContent(content, width, wordwarp, factory, appearance);
            UpdateContent(width, wordwarp, factory, appearance);
        }
        else
        {
            throw new IllegalStateException(
                    "Can't add new content if none is set to active.");
        }
    }
    
    /**
     * Removes the next character after the active position. Does not realign
     * any lines.
     * 
     * @return
     */
    public Character removeNextChar(TextAppearance appearance)
    {
        Character result = null;
        if (hasActiveLine())
        {
            result = activeLine.removeNextChar(appearance);
            
            if (result == null)
            {
                final int index = content.indexOf(activeLine) + 1;
                if (index < content.size())
                {
                    final ContentLine nextLine = content.get(index);
                    result = nextLine.removeNextChar(appearance);
                    
                    if (result != null && nextLine.isEmpty())
                    {
                        content.remove(nextLine);
                    }
                }
            }
            
            if (result != null)
            {
                updateSize();
            }
            return result;
        }
        else
        {
            if (content.size() >= 1)
            {
                final ContentLine nextLine = content.get(0);
                result = nextLine.removeNextChar(appearance);
                
                if (result != null && nextLine.isEmpty())
                {
                    content.remove(nextLine);
                }
                if (result != null)
                {
                    updateSize();
                }
            }
            return result;
        }
    }
    
    /**
     * Removes previous next character after the active position. Does not
     * realign any lines.
     * 
     * @return
     */
    public Character removePreviousChar(TextAppearance appearance)
    {
        if (hasActiveLine())
        {
            Character result = activeLine.removePreviousChar(appearance);
            if (result == null)
            {
                final int index = content.indexOf(activeLine) - 1;
                if (index >= 0)
                {
                    activeLine.setActiveAtom(-1);
                    activeLine = content.get(index);
                    activeLineY -= activeLine.getSize().getHeight();
                    activeLineAtoms -= activeLine.getAtomCount();
                    activeLine.setActiveAtom(activeLine.getAtomCount());
                    result = activeLine.removePreviousChar(appearance);
                }
            }
            
            if (result != null)
            {
                atomCountCache--;
                updateSize();
            }
            return result;
        }
        else
        {
            return null;
        }
    }
    
    public int getActiveAtom()
    {
        int atom = 0;
        for (final ContentLine line : content)
        {
            if (line.hasActivePart())
            {
                return atom + line.getActiveAtom();
            }
            
            atom += line.getAtomCount();
        }
        
        return atom;
    }
    
    public ContentLine setActiveAtom(int atomIndex)
    {
        long atomCount = 0;
        activeLineY = 0;
        
        if (hasActiveLine())
        {
            activeLine.setActiveAtom(-1);
            activeLine = null;
            activeLineAtoms = 0;
            activeLineY = 0;
        }
        
        if (atomIndex >= 0)
        {
            for (final ContentLine line : content)
            {
                atomCount += line.getAtomCount();
                if (atomCount >= atomIndex)
                {
                    activeLine = line;
                    activeLineAtoms = (int) (atomCount - line.getAtomCount());
                    activeLine.setActiveAtom(atomIndex - activeLineAtoms);
                    return line;
                }
                activeLineY += line.getSize().getHeight();
            }
        }
        activeLine = null;
        activeLineY = 0;
        activeLineAtoms = 0;
        return null;
    }
    
    public Dimension getSize()
    {
        return size;
    }
    
    public Point getActivePosition(TextAppearance appearance)
    {
        if (activeLine != null)
        {
            final Point result = activeLine.getActivePosition(appearance);
            if (result != null)
            {
                result.setY(result.getY() + activeLineY);
            }
            return result;
        }
        else
        {
            return null;
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.fenggui.binding.render.text.advanced.IContentSelection#hasSelection()
     */
    
    public boolean hasSelection()
    {
        for (final ContentLine line : content)
        {
            if (line.hasSelection())
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
     * org.fenggui.binding.render.text.advanced.IContentSelection#getSelectionStart
     * ()
     */
    
    public int getSelectionStart()
    {
        int result = 0;
        for (final ContentLine line : content)
        {
            if (line.hasSelection())
            {
                return result + line.getSelectionStart();
            }
            result += line.getAtomCount();
        }
        
        return -1; // no selection start found
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.fenggui.binding.render.text.advanced.IContentSelection#getSelectionEnd
     * ()
     */
    
    public int getSelectionEnd()
    {
        boolean inSelection = false;
        int endSelection = 0;
        ContentLine lastLine = null;
        
        for (final ContentLine line : content)
        {
            final boolean lineSelected = line.hasSelection();
            if (lineSelected)
            {
                inSelection = true;
            }
            
            if (inSelection == true)
            {
                if (lineSelected)
                {
                    lastLine = line;
                }
                else
                {
                    break;
                }
            }
            
            endSelection += line.getAtomCount();
        }
        
        if (inSelection)
        {
            if (lastLine != null)
            {
                endSelection -= lastLine.getAtomCount();
            }
            
            endSelection += lastLine.getSelectionEnd();
            return endSelection;
        }
        return -1;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.fenggui.binding.render.text.advanced.IContentSelection#removeSelection
     * ()
     */
    
    public void clearSelection(TextAppearance appearance)
    {
        for (final ContentLine line : content)
        {
            line.clearSelection(appearance);
        }
    }
    
    public boolean isEmpty()
    {
        if (content.size() > 1)
        {
            return false;
        }
        else
        {
            return content.get(0).isEmpty();
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.fenggui.binding.render.text.advanced.IContentSelection#
     * removeSelectionWithContent()
     */
    
    public void removeSelection(IContentFactory factory,
            TextAppearance appearance)
    {
        final List<ContentLine> removables = new ArrayList<ContentLine>();
        
        for (final ContentLine line : content)
        {
            line.removeSelection(factory, appearance);
            
            if (line.isEmpty() && line.hasSelection())
            {
                if (removables.contains(activeLine))
                {
                    final int index = content.indexOf(removables.get(0)) - 1;
                    
                    if (index >= 0)
                    {
                        activeLine = content.get(index);
                    }
                    else
                    {
                        activeLine = null;
                    }
                }
                // remove line
                removables.add(line);
            }
        }
        
        content.removeAll(removables);
        
        // take first part if none anymore
        if (activeLine == null && content.size() > 0)
        {
            activeLine = content.get(0);
            activeLine.setActiveAtom(0);
            activeLineAtoms = 0;
            activeLineY = 0;
        }
        
        if (content.size() <= 0)
        {
            // keep at least one line
            final ContentLine line = new ContentLine(
                    factory.getEmptyContentPart(appearance));
            content.add(line);
            activeLine = line;
            activeLineAtoms = line.getAtomCount();
            line.setSelection(0, 1, appearance);
            line.setActiveAtom(0);
            activeLineY = 0;
        }
        
        updateSize();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.fenggui.binding.render.text.advanced.IContentSelection#setSelection
     * (int, int)
     */
    
    public void setSelection(int start, int end, TextAppearance appearance)
    {
        int current = 0;
        boolean inSelection = false;
        
        for (final ContentLine line : content)
        {
            final long lineAtoms = line.getAtomCount();
            if (current + lineAtoms > start && current < end)
            {
                line.setSelection(start - current, end - current, appearance);
                inSelection = true;
            }
            
            if (inSelection && current > end)
            {
                break;
            }
            current += line.getAtomCount();
        }
    }
    
    public void render(int x, int y, Graphics g,
            ITextCursorRenderer cursorRenderer, boolean editMode,
            TextAppearance appearance)
    {
        int yCurrent = y;
        for (final ContentLine line : content)
        {
            line.render(x, yCurrent, g, cursorRenderer, editMode, appearance);
            yCurrent -= line.getSize().getHeight();
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.fenggui.text.content.IContent#addContentAtBegining(java.lang.Object,
     * org.fenggui.text.content.IContentFactory)
     */
    
    public boolean addContentAtBegining(Object content, int width,
            boolean wordwarp, IContentFactory factory, TextAppearance appearance)
    {
        final boolean result = this.content.get(0).addContentAtBegining(
                content, width, wordwarp, factory, appearance);
        this.UpdateContent(width, wordwarp, factory, appearance);
        return result;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.fenggui.text.content.IContent#addContentAtEnd(java.lang.Object,
     * org.fenggui.text.content.IContentFactory)
     */
    
    public boolean addContentAtEnd(Object content, int width, boolean wordwarp,
            IContentFactory factory, TextAppearance appearance)
    {
        final boolean result = this.content.get(this.content.size() - 1)
                .addContentAtEnd(content, width, wordwarp, factory, appearance);
        this.UpdateContent(width, wordwarp, factory, appearance);
        return result;
    }
    
    public boolean isValidCharacter(char character, TextAppearance appearance)
    {
        if (activeLine != null)
        {
            return activeLine.isValidCharacter(character, appearance);
        }
        else
        {
            return false;
        }
    }
}
