package org.fenggui.theme.css.processors;

import org.fenggui.StandardWidget;
import org.fenggui.StatefullWidget;
import org.fenggui.appearance.DecoratorAppearance;
import org.fenggui.decorator.background.Background;
import org.fenggui.decorator.background.PlainBackground;
import org.fenggui.decorator.border.Border;
import org.fenggui.theme.css.FengGUIFunctions;
import org.fenggui.theme.css.FengGUIProperties;
import org.fenggui.theme.css.StyleUtils;
import org.fenggui.util.Color;

import aurelienribon.ui.css.DeclarationSet;
import aurelienribon.ui.css.DeclarationSetProcessor;

public class StatefullWidgetProcessor implements
        DeclarationSetProcessor<StatefullWidget<? extends DecoratorAppearance>>
{
    public void process(StatefullWidget<? extends DecoratorAppearance> t,
            DeclarationSet ds)
    {
        processWidget(t, ds);
    }
    
    public static void processWidget(StandardWidget t, DeclarationSet ds)
    {
        if (t.getAppearance() instanceof DecoratorAppearance)
        {
            DecoratorAppearance appearance = (DecoratorAppearance) t
                    .getAppearance();
            
            if (ds.contains(FengGUIProperties.background)
                    && StyleUtils.applyStates)
            {
                Background background = ds.getValue(
                        FengGUIProperties.background, Background.class,
                        FengGUIFunctions.background);
                appearance.add(StyleUtils.state, background);
            }
            
            if (ds.contains(FengGUIProperties.border) && StyleUtils.applyStates)
            {
                Border border = ds.getValue(FengGUIProperties.border,
                        Border.class, FengGUIFunctions.border);
                appearance.add(StyleUtils.state, border);
            }
            
            if (!StyleUtils.state.equals(StatefullWidget.STATE_DEFAULT)
                    && StyleUtils.applyStates)
            {
                appearance.setEnabled(StyleUtils.state, false);
            }
        }
    }
}
