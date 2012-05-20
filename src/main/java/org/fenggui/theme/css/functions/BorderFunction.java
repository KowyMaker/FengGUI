package org.fenggui.theme.css.functions;

import java.util.List;

import org.fenggui.decorator.border.Border;
import org.fenggui.decorator.border.PlainBorder;
import org.fenggui.util.Color;

import aurelienribon.ui.css.primitives.BaseFunction;

public class BorderFunction extends BaseFunction
{
    
    public BorderFunction()
    {
        super("border");
    }
    
    public Class<?>[][] getParams()
    {
        return new Class<?>[][] { { String.class, Integer.class },
                { String.class, Integer.class, Color.class } };
    }
    
    public String[][] getParamsNames()
    {
        return new String[][] { { "type", "lineWidth" },
                { "type", "lineWidth", "color" } };
    }
    
    public Class<?> getReturn()
    {
        return Border.class;
    }
    
    public Object process(List<Object> params)
    {
        Border border = null;
        
        String type = (String) params.get(0);
        
        if (type.equals("plain"))
        {
            Color c = Color.BLACK;
            int size = (Integer) params.get(1);
            
            if (params.size() > 2)
            {
                c = (Color) params.get(2);
            }
            
            border = new PlainBorder(c, size);
        }
        
        return border;
    }
}
