package org.fenggui.theme.css.functions;

import java.util.List;

import org.fenggui.decorator.background.Background;
import org.fenggui.decorator.background.GradientBackground;
import org.fenggui.decorator.background.PlainBackground;
import org.fenggui.util.Color;

import aurelienribon.ui.css.primitives.BaseFunction;

public class ColorBackgroundFunction extends BaseFunction
{
    
    public ColorBackgroundFunction()
    {
        super("background-color");
    }
    
    public Class<?>[][] getParams()
    {
        return new Class<?>[][] {
                { Color.class },
                { String.class, Color.class, Color.class },
                { String.class, Color.class, Color.class, Color.class,
                        Color.class } };
    }
    
    public String[][] getParamsNames()
    {
        return new String[][] {
                { "color" },
                { "type", "top", "bottom" },
                { "type", "lowerLeft", "lowerRight", "upperLeft", "upperRight" } };
    }
    
    public Class<?> getReturn()
    {
        return Background.class;
    }
    
    public Object process(List<Object> params)
    {
        Background background = null;
        
        if (params.get(0) instanceof Color)
        {
            Color color = (Color) params.get(0);
            background = new PlainBackground(color);
        }
        else if (params.get(0) instanceof String)
        {
            String type = (String) params.get(0);
            if (type.equals("gradient"))
            {
                if (params.size() == 3)
                {
                    Color top = (Color) params.get(1);
                    Color bottom = (Color) params.get(2);
                    
                    background = new GradientBackground(top, bottom);
                }
                else if (params.size() == 5)
                {
                    Color lowerLeft = (Color) params.get(1);
                    Color lowerRight = (Color) params.get(2);
                    Color upperLeft = (Color) params.get(3);
                    Color upperRight = (Color) params.get(4);
                    
                    background = new GradientBackground(lowerLeft, lowerRight,
                            upperRight, upperLeft);
                }
            }
        }
        
        return background;
    }
    
}
