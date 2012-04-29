package org.fenggui.theme.css.functions;

import java.util.List;

import org.fenggui.util.Color;

import aurelienribon.ui.css.primitives.BaseFunction;

public class RgbFunction extends BaseFunction
{
    
    public RgbFunction()
    {
        super("rgb");
    }
    
    public Class<?>[][] getParams()
    {
        return new Class<?>[][] { { Integer.class, Integer.class, Integer.class } };
    }
    
    public String[][] getParamsNames()
    {
        return new String[][] { { "r", "g", "b" } };
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
        return new Color(r, g, b);
    }
    
}
