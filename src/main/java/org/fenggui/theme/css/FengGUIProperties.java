package org.fenggui.theme.css;

import org.fenggui.util.Color;

import aurelienribon.ui.css.Property;
import aurelienribon.ui.css.Style;
import aurelienribon.ui.css.primitives.FunctionProperty;
import aurelienribon.ui.css.primitives.SingleParamProperty;

public class FengGUIProperties
{
    // Widget
    public final static Property visible       = new SingleParamProperty(
                                                       "visible",
                                                       Boolean.class, "v");
    public final static Property width         = new SingleParamProperty(
                                                       "width", Integer.class,
                                                       "w");
    public final static Property height        = new SingleParamProperty(
                                                       "height", Integer.class,
                                                       "h");
    public final static Property minWidth      = new SingleParamProperty(
                                                       "minWidth",
                                                       Integer.class, "w");
    public final static Property minHeight     = new SingleParamProperty(
                                                       "minHeight",
                                                       Integer.class, "h");
    
    // StandardWidget > SpacingAppearance
    public final static Property padding       = new FunctionProperty(
                                                       "padding",
                                                       FengGUIFunctions.insets,
                                                       "insets");
    public final static Property paddingTop    = new SingleParamProperty(
                                                       "padding-top",
                                                       Integer.class, "s");
    public final static Property paddingRight  = new SingleParamProperty(
                                                       "padding-right",
                                                       Integer.class, "s");
    public final static Property paddingBottom = new SingleParamProperty(
                                                       "padding-bottom",
                                                       Integer.class, "s");
    public final static Property paddingLeft   = new SingleParamProperty(
                                                       "padding-left",
                                                       Integer.class, "s");
    public final static Property margin        = new FunctionProperty("margin",
                                                       FengGUIFunctions.insets,
                                                       "insets");
    public final static Property marginTop     = new SingleParamProperty(
                                                       "margin-top",
                                                       Integer.class, "s");
    public final static Property marginRight   = new SingleParamProperty(
                                                       "margin-right",
                                                       Integer.class, "s");
    public final static Property marginBottom  = new SingleParamProperty(
                                                       "margin-bottom",
                                                       Integer.class, "s");
    public final static Property marginLeft    = new SingleParamProperty(
                                                       "margin-left",
                                                       Integer.class, "s");
    
    // StandardWidget > TextAppearance
    public final static Property textAlign     = new SingleParamProperty(
                                                       "text-align",
                                                       String.class, "align");
    public final static Property color         = new SingleParamProperty(
                                                       "color", Color.class,
                                                       "color");
    public final static Property font          = new FunctionProperty("font",
                                                       FengGUIFunctions.font,
                                                       "font");
    
    // StatefullWidget
    public final static Property background    = new FunctionProperty(
                                                       "background",
                                                       FengGUIFunctions.background,
                                                       "background");
    public final static Property border        = new FunctionProperty("border",
                                                       FengGUIFunctions.border,
                                                       "border");
    
    public static void registerProperties()
    {
        Style.registerProperty(visible);
        Style.registerProperty(width);
        Style.registerProperty(height);
        
        Style.registerProperty(padding);
        Style.registerProperty(paddingTop);
        Style.registerProperty(paddingRight);
        Style.registerProperty(paddingBottom);
        Style.registerProperty(paddingLeft);
        Style.registerProperty(margin);
        Style.registerProperty(marginTop);
        Style.registerProperty(marginRight);
        Style.registerProperty(marginBottom);
        Style.registerProperty(marginLeft);
        
        Style.registerProperty(textAlign);
        Style.registerProperty(color);
        Style.registerProperty(font);
        
        Style.registerProperty(background);
        Style.registerProperty(border);
    }
}
