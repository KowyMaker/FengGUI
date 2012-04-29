package org.fenggui.theme.css.functions;

import java.net.URL;
import java.util.List;

import aurelienribon.ui.css.primitives.BaseFunction;

public class UrlFunction extends BaseFunction
{
    public UrlFunction()
    {
        super("url");
    }
    
    public Class<?>[][] getParams()
    {
        return new Class<?>[][] { { String.class } };
    }
    
    public String[][] getParamsNames()
    {
        return new String[][] { { "url" } };
    }
    
    public Class<?> getReturn()
    {
        return URL.class;
    }
    
    public Object process(List<Object> params)
    {
        String url = (String) params.get(0);
        URL url2 = UrlFunction.class.getResource(url);
        return url2;
    }
    
}
