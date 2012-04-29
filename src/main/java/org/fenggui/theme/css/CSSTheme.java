package org.fenggui.theme.css;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.fenggui.IWidget;
import org.fenggui.theme.ITheme;

import aurelienribon.ui.css.Style;

public class CSSTheme implements ITheme
{
    private final Style style;
    
    public CSSTheme(File file) throws IOException
    {
        this(file.toURI().toURL());
    }
    
    public CSSTheme(URL styleUrl)
    {
        FengGUIStyle.init();
        
        style = new Style(styleUrl);
    }
    
    public void setUp(IWidget widget)
    {
        String id = widget.getId();
        String styleClass = widget.getStyleClass();
        
        if (id != null)
        {
            Style.registerCssClasses(widget, "#" + id);
        }
        
        if (styleClass != null)
        {
            Style.registerCssClasses(widget, "." + styleClass);
        }
        
        Style.apply(widget, style);
        
        Style.unregister(widget);
    }
    
    public Style getStyle()
    {
        return style;
    }
}
