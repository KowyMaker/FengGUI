package org.fenggui.theme.css;

import org.fenggui.theme.css.functions.*;

import aurelienribon.ui.css.Function;
import aurelienribon.ui.css.Style;

public class FengGUIFunctions
{
    public final static Function rgb             = new RgbFunction();
    public final static Function rgba            = new RgbaFunction();
    public final static Function insets          = new InsetsFunction();
    public final static Function border          = new BorderFunction();
    public final static Function url             = new UrlFunction();
    public final static Function font            = new FontFunction();
    public final static Function backgroundColor = new ColorBackgroundFunction();
    public final static Function backgroundImage = new PixmapBackgroundFunction();
    
    public static void registerFunctions()
    {
        Style.registerFunction(rgb);
        Style.registerFunction(rgba);
        Style.registerFunction(insets);
        Style.registerFunction(border);
        Style.registerFunction(url);
        Style.registerFunction(font);
        Style.registerFunction(backgroundColor);
        Style.registerFunction(backgroundImage);
    }
}
