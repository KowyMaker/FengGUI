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
 * $Id: ComboBox.java 116 2006-12-12 22:46:21Z schabby $
 */
package org.fenggui.theme.xml;

import java.util.StringTokenizer;

/**
 * @author Esa Tanskanen
 * 
 */
public class XMLProcessPointer
{
    private static final String POSITION_MARKER = "--> ";
    
    public static String getParsingContext(String element,
            int elementStartLine, int pointerLine)
    {
        final StringBuffer withLineNumbers = new StringBuffer();
        final StringTokenizer lines = new StringTokenizer(element, "\n");
        
        final int maxLineNumber = elementStartLine + lines.countTokens();
        final int numRequiredNumbers = Integer.toString(maxLineNumber).length();
        
        for (int lineNum = 0; lines.hasMoreTokens(); lineNum++)
        {
            final String line = lines.nextToken();
            
            if (lineNum == pointerLine)
            {
                withLineNumbers.append("*");
            }
            else
            {
                withLineNumbers.append(" ");
            }
            
            final String lineNumberStr = Integer.toString(elementStartLine
                    + lineNum);
            
            withLineNumbers.append("[");
            withLineNumbers.append(lineNumberStr);
            withLineNumbers.append("] ");
            
            final int numTrailingZeros = numRequiredNumbers
                    - lineNumberStr.length();
            
            for (int i = 0; i < numTrailingZeros; i++)
            {
                withLineNumbers.append(' ');
            }
            withLineNumbers.append(line);
            withLineNumbers.append('\n');
        }
        
        return withLineNumbers.toString();
    }
    
    public static String getParsingContext(String element,
            String parentElement, String document)
    {
        final String activeElementStr = element.trim();
        
        if (parentElement == null)
        {
            return POSITION_MARKER + activeElementStr;
        }
        
        // Find out the position of the active element inside the parent
        // (There might be a better way, but this was the easiest way ;)
        
        final int activeElementPos = parentElement.indexOf(activeElementStr);
        
        final int parentPos = document.indexOf(parentElement);
        
        if (parentPos >= 0)
        {
            // A hackish way to find out where the printed line numbers start
            int lineNumberStart = 2;
            for (int i = 0; i < parentPos; i++)
            {
                if (document.charAt(i) == '\n')
                {
                    lineNumberStart++;
                }
            }
            
            final StringTokenizer lineTokens = new StringTokenizer(
                    parentElement, "\n");
            final StringBuffer withLineNumbers = new StringBuffer();
            
            final int maxLineNumber = lineNumberStart
                    + lineTokens.countTokens();
            final int numRequiredNumbers = Integer.toString(maxLineNumber)
                    .length();
            
            final String[] lines = new String[lineTokens.countTokens()];
            int index = 0;
            int activeLineNum = -1;
            int charCount = 0;
            
            while (lineTokens.hasMoreTokens())
            {
                final String line = lineTokens.nextToken();
                lines[index] = line;
                charCount += line.length() + 1;
                
                if (charCount > activeElementPos && activeLineNum < 0)
                {
                    activeLineNum = index;
                }
                
                index++;
            }
            
            if (index > 0)
            {
                lines[index - 1] = lines[index - 1].trim();
            }
            
            for (int lineNum = 0; lineNum < lines.length; lineNum++)
            {
                final String line = lines[lineNum];
                
                if (lineNum == activeLineNum)
                {
                    withLineNumbers.append("*");
                }
                else
                {
                    withLineNumbers.append(" ");
                }
                
                final String lineNumberStr = Integer.toString(lineNumberStart
                        + lineNum);
                
                withLineNumbers.append("[");
                withLineNumbers.append(lineNumberStr);
                withLineNumbers.append("] ");
                
                final int numTrailingZeros = numRequiredNumbers
                        - lineNumberStr.length();
                
                for (int i = 0; i < numTrailingZeros; i++)
                {
                    withLineNumbers.append(' ');
                }
                withLineNumbers.append(line);
                withLineNumbers.append('\n');
            }
            
            parentElement = withLineNumbers.toString();
        }
        else if (activeElementPos >= 0)
        {
            final String start = parentElement.substring(0, activeElementPos);
            parentElement = start + POSITION_MARKER
                    + parentElement.substring(activeElementPos);
        }
        
        return parentElement;
    }
}
