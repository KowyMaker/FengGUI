package org.fenggui.theme.css.functions;

import java.awt.Font;
import java.util.List;

import org.fenggui.binding.render.IFont;
import org.fenggui.util.fonttoolkit.FontFactory;

import aurelienribon.ui.css.primitives.BaseFunction;

public class FontFunction extends BaseFunction
{
    
    public FontFunction()
    {
        super("font");
    }
    
    public Class<?>[][] getParams()
    {
        return new Class<?>[][] { { String.class },
                { String.class, String.class },
                { String.class, Integer.class },
                { String.class, String.class, Integer.class },
                { String.class, Integer.class, String.class } };
    }
    
    public String[][] getParamsNames()
    {
        return new String[][] { { "name" }, { "name", "weight" },
                { "name", "size" }, { "name", "weight", "size" },
                { "name", "size", "weight" } };
    }
    
    public Class<?> getReturn()
    {
        return IFont.class;
    }
    
    public Object process(List<Object> params)
    {
        IFont font = null;
        
        String name = (String) params.get(0);
        
        int style = Font.PLAIN;
        int size = 12;
        
        if (params.size() == 2)
        {
            if (params.get(1) instanceof String)
            {
                style = getStyle((String) params.get(1));
            }
            else
            {
                size = (Integer) params.get(1);
            }
        }
        else if (params.size() == 3)
        {
            if (params.get(1) instanceof String)
            {
                style = getStyle((String) params.get(1));
                size = (Integer) params.get(2);
            }
            else
            {
                style = getStyle((String) params.get(2));
                size = (Integer) params.get(1);
            }
        }
        
        Font awtFont = new Font(name, style, size);
        font = FontFactory.renderStandardFont(awtFont);
        
        return font;
    }
    
    private int getStyle(String str)
    {
        int style = Font.PLAIN;
        
        if (str.contains("bold"))
        {
            style = Font.BOLD | style;
        }
        if (str.contains("italic"))
        {
            style = Font.ITALIC | style;
        }
        
        return style;
    }
    
}
