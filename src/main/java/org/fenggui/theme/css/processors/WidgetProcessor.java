package org.fenggui.theme.css.processors;

import org.fenggui.Widget;
import org.fenggui.theme.css.FengGUIProperties;
import org.fenggui.theme.css.StyleUtils;

import aurelienribon.ui.css.DeclarationSet;
import aurelienribon.ui.css.DeclarationSetProcessor;

public class WidgetProcessor implements DeclarationSetProcessor<Widget>
{
    public void process(Widget t, DeclarationSet ds)
    {
        // Visible
        if (ds.contains(FengGUIProperties.visible) && !StyleUtils.applyStates)
        {
            boolean visible = ds.getValue(FengGUIProperties.visible,
                    Boolean.class);
            t.setVisible(visible);
        }
        
        // Width
        if (ds.contains(FengGUIProperties.width) && !StyleUtils.applyStates)
        {
            int width = ds.getValue(FengGUIProperties.width, Integer.class);
            t.setWidth(width);
        }
        
        // Height
        if (ds.contains(FengGUIProperties.height) && !StyleUtils.applyStates)
        {
            int height = ds.getValue(FengGUIProperties.height, Integer.class);
            t.setHeight(height);
        }
        
        // Minimum Width
        if (ds.contains(FengGUIProperties.minWidth) && !StyleUtils.applyStates)
        {
            int minWidth = ds.getValue(FengGUIProperties.minWidth,
                    Integer.class);
            t.setMinWidth(minWidth);
        }
        
        // Minimum Height
        if (ds.contains(FengGUIProperties.minHeight) && !StyleUtils.applyStates)
        {
            int minHeight = ds.getValue(FengGUIProperties.minHeight,
                    Integer.class);
            t.setMinHeight(minHeight);
        }
    }
}
