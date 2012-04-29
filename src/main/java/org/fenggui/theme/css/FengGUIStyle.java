package org.fenggui.theme.css;

import java.util.List;

import org.fenggui.*;
import org.fenggui.composite.Window;
import org.fenggui.composite.menu.Menu;
import org.fenggui.composite.menu.MenuBar;
import org.fenggui.theme.css.manager.IWidgetDeclarationSetManager;
import org.fenggui.theme.css.processors.*;
import org.fenggui.util.Color;

import aurelienribon.ui.css.ChildrenAccessor;
import aurelienribon.ui.css.ParamConverter;
import aurelienribon.ui.css.Style;
import aurelienribon.ui.css.StyleException;

public class FengGUIStyle
{
    public static void init() throws StyleException
    {
        // Properties
        FengGUIProperties.registerProperties();
        
        // Functions
        FengGUIFunctions.registerFunctions();
        
        // Types
        Style.registerType("button", Button.class);
        Style.registerType("window", Window.class);
        Style.registerType("label", Label.class);
        Style.registerType("textfield", TextEditor.class);
        Style.registerType("container", Container.class);
        Style.registerType("menubar", MenuBar.class);
        Style.registerType("menu", Menu.class);
        
        // Processors
        Style.registerProcessor(Widget.class, new WidgetProcessor());
        Style.registerProcessor(StandardWidget.class,
                new StandardWidgetProcessor());
        Style.registerProcessor(StatefullWidget.class,
                new StatefullWidgetProcessor());
        
        // Misc.
        Style.registerDeclarationSetManager(IWidget.class,
                new IWidgetDeclarationSetManager());
        Style.registerChildrenAccessor(IContainer.class,
                new ChildrenAccessor<IContainer>() {
                    
                    public List<?> getChildren(IContainer target)
                    {
                        return target.getContent();
                    }
                });
        Style.setParamConverter(new ParamConverter() {
            
            public Class<?> getColorClass()
            {
                return Color.class;
            }
            
            public Object convertColor(int argb)
            {
                Color color = new Color(argb);
                
                return color;
            }
        });
    }
}
