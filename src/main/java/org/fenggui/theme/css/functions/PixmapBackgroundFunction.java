package org.fenggui.theme.css.functions;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.fenggui.binding.render.Binding;
import org.fenggui.binding.render.ITexture;
import org.fenggui.binding.render.Pixmap;
import org.fenggui.decorator.background.Background;
import org.fenggui.decorator.background.PixmapBackground;

import aurelienribon.ui.css.primitives.BaseFunction;

public class PixmapBackgroundFunction extends BaseFunction
{
    
    public PixmapBackgroundFunction()
    {
        super("background-image");
    }
    
    public Class<?>[][] getParams()
    {
        return new Class<?>[][] {
                { URL.class },
                { URL.class, Integer.class, Integer.class, Integer.class,
                        Integer.class } };
    }
    
    public String[][] getParamsNames()
    {
        return new String[][] { { "url" },
                { "url", "x", "y", "width", "height" } };
    }
    
    public Class<?> getReturn()
    {
        return Background.class;
    }
    
    public Object process(List<Object> params)
    {
        Background background = null;
        
        if (params.size() > 0 && params.get(0) instanceof URL)
        {
            URL url = (URL) params.get(0);
            try
            {
                ITexture tex = Binding.getInstance().getTexture(url);
                
                int x = 0;
                int y = 0;
                int width = tex.getImageWidth();
                int height = tex.getImageHeight();
                
                if (params.size() > 1 && params.get(1) instanceof Integer
                        && params.get(2) instanceof Integer
                        && params.get(3) instanceof Integer
                        && params.get(4) instanceof Integer)
                {
                    x = (Integer) params.get(1);
                    y = (Integer) params.get(2);
                    width = (Integer) params.get(3);
                    height = (Integer) params.get(4);
                }
                
                Pixmap pixmap = new Pixmap(tex, x, y, width, height);
                
                background = new PixmapBackground(pixmap, true);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        
        return background;
    }
    
}
