package org.fenggui.theme.css.functions;

import java.util.List;

import org.fenggui.util.Spacing;

import aurelienribon.ui.css.primitives.BaseFunction;

public class InsetsFunction extends BaseFunction
{
    
    public InsetsFunction()
    {
        super("insets");
    }
    
    public Class<?>[][] getParams()
    {
        return new Class[][] {
                { Integer.class, Integer.class, Integer.class, Integer.class },
                { Integer.class, Integer.class }, { Integer.class } };
    }
    
    public String[][] getParamsNames()
    {
        return new String[][] { { "top", "right", "bottom", "left" },
                { "topBottom", "leftRight" }, { "thickness" } };
    }
    
    public Class<?> getReturn()
    {
        return Spacing.class;
    }
    
    public Object process(List<Object> params)
    {
        int top = 0;
        int bottom = 0;
        int left = 0;
        int right = 0;
        
        if (params.size() == 1)
        {
            top = bottom = left = right = (Integer) params.get(0);
        }
        else if (params.size() == 2)
        {
            top = bottom = (Integer) params.get(0);
            right = left = (Integer) params.get(1);
        }
        else if (params.size() == 4)
        {
            top = (Integer) params.get(0);
            right = (Integer) params.get(1);
            bottom = (Integer) params.get(2);
            left = (Integer) params.get(3);
        }
        
        return new Spacing(top, left, right, bottom);
    }
    
}
