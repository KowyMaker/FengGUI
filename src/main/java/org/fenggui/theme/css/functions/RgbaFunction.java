package org.fenggui.theme.css.functions;

import java.util.List;

import org.fenggui.util.Color;

import aurelienribon.ui.css.primitives.BaseFunction;

public class RgbaFunction extends BaseFunction
{
    
    public RgbaFunction()
    {
        super("rgba");
    }
    
    public Class<?>[][] getParams()
    {
        return new Class<?>[][] { { Integer.class, Integer.class,
                Integer.class, Integer.class } };
    }
    
    public String[][] getParamsNames()
    {
        return new String[][] { { "r", "g", "b", "a" } };
    }
    
    public Class<?> getReturn()
    {
        return Color.class;
    }
    
    public Object process(List<Object> params)
    {
        int r = (Integer) params.get(0);
        int g = (Integer) params.get(1);
        int b = (Integer) params.get(2);
        int a = (Integer) params.get(3);
        return new Color(r, g, b, a);
    }
    
}
