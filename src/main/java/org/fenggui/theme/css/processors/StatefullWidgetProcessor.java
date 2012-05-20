package org.fenggui.theme.css.processors;

import java.util.List;

import org.fenggui.StandardWidget;
import org.fenggui.StatefullWidget;
import org.fenggui.appearance.DecoratorAppearance;
import org.fenggui.decorator.background.Background;
import org.fenggui.decorator.border.Border;
import org.fenggui.decorator.border.PlainBorder;
import org.fenggui.decorator.border.RoundedBorder;
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
                List<Object> params = ds.getValue(FengGUIProperties.background);
                Background background = (Background) FengGUIFunctions.backgroundColor
                        .process(params);
                if (background == null)
                {
                    background = (Background) FengGUIFunctions.backgroundImage
                            .process(params);
                }
                
                appearance.add(StyleUtils.state, background);
            }
            
            if (ds.contains(FengGUIProperties.border) && StyleUtils.applyStates)
            {
                Border border = ds.getValue(FengGUIProperties.border,
                        Border.class, FengGUIFunctions.border);
                if (border instanceof PlainBorder
                        && ds.contains(FengGUIProperties.borderRadius))
                {
                    int radius = ds.getValue(FengGUIProperties.borderRadius,
                            Integer.class);
                    int size = border.getTop();
                    Color c = ((PlainBorder) border).getColor();
                    
                    border = new RoundedBorder(c, size, radius);
                }
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
