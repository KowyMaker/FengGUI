package org.fenggui.theme.css.functions;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.fenggui.binding.render.Binding;
import org.fenggui.binding.render.ITexture;
import org.fenggui.binding.render.Pixmap;
import org.fenggui.decorator.background.Background;
import org.fenggui.decorator.background.PixmapBackground;
import org.fenggui.decorator.background.PlainBackground;
import org.fenggui.util.Color;

import aurelienribon.ui.css.primitives.BaseFunction;

public class BackgroundFunction extends BaseFunction
{
    
    public BackgroundFunction()
    {
        super("background");
    }
    
    public Class<?>[][] getParams()
    {
        return new Class<?>[][] { { URL.class }, { Color.class } };
    }
    
    public String[][] getParamsNames()
    {
        return new String[][] { { "url" }, { "color" } };
    }
    
    public Class<?> getReturn()
    {
        return Background.class;
    }
    
    public Object process(List<Object> params)
    {
        Background background = new PlainBackground();
        
        if (params.size() == 1)
        {
            if (params.get(0) instanceof URL)
            {
                URL url = (URL) params.get(0);
                try
                {
                    ITexture tex = Binding.getInstance().getTexture(url);
                    Pixmap pixmap = new Pixmap(tex);
                    background = new PixmapBackground(pixmap);
                    ((PixmapBackground) background).setScaled(true);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else if (params.get(0) instanceof Color)
            {
                Color color = (Color) params.get(0);
                if (background instanceof PlainBackground)
                {
                    ((PlainBackground) background).setColor(color);
                }
            }
        }
        
        return background;
    }
    
}
