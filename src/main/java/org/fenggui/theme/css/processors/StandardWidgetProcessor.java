package org.fenggui.theme.css.processors;

import org.fenggui.StandardWidget;
import org.fenggui.appearance.DecoratorAppearance;
import org.fenggui.appearance.SpacingAppearance;
import org.fenggui.appearance.TextAppearance;
import org.fenggui.binding.render.IFont;
import org.fenggui.binding.render.text.DirectTextRenderer;
import org.fenggui.binding.render.text.ITextRenderer;
import org.fenggui.text.content.factory.simple.TextStyle;
import org.fenggui.text.content.factory.simple.TextStyleEntry;
import org.fenggui.theme.css.FengGUIFunctions;
import org.fenggui.theme.css.FengGUIProperties;
import org.fenggui.theme.css.StyleUtils;
import org.fenggui.util.Alignment;
import org.fenggui.util.Color;
import org.fenggui.util.Spacing;

import aurelienribon.ui.css.DeclarationSet;
import aurelienribon.ui.css.DeclarationSetProcessor;

public class StandardWidgetProcessor implements
        DeclarationSetProcessor<StandardWidget>
{
    public void process(StandardWidget t, DeclarationSet ds)
    {
        if (t.getAppearance() instanceof SpacingAppearance)
        {
            SpacingAppearance appearance = (SpacingAppearance) t
                    .getAppearance();
            
            // Padding
            if (ds.contains(FengGUIProperties.padding)
                    && !StyleUtils.applyStates)
            {
                Spacing padding = ds.getValue(FengGUIProperties.padding,
                        Spacing.class, FengGUIFunctions.insets);
                appearance.setPadding(padding);
            }
            
            if (ds.contains(FengGUIProperties.paddingTop)
                    && !StyleUtils.applyStates)
            {
                int value = ds.getValue(FengGUIProperties.paddingTop,
                        Integer.class);
                appearance.getPadding().setTop(value);
            }
            
            if (ds.contains(FengGUIProperties.paddingRight)
                    && !StyleUtils.applyStates)
            {
                int value = ds.getValue(FengGUIProperties.paddingRight,
                        Integer.class);
                appearance.getPadding().setRight(value);
            }
            
            if (ds.contains(FengGUIProperties.paddingBottom)
                    && !StyleUtils.applyStates)
            {
                int value = ds.getValue(FengGUIProperties.paddingBottom,
                        Integer.class);
                appearance.getPadding().setBottom(value);
            }
            
            if (ds.contains(FengGUIProperties.paddingLeft)
                    && !StyleUtils.applyStates)
            {
                int value = ds.getValue(FengGUIProperties.paddingLeft,
                        Integer.class);
                appearance.getPadding().setLeft(value);
            }
            
            // Margin
            if (ds.contains(FengGUIProperties.margin)
                    && !StyleUtils.applyStates)
            {
                Spacing margin = ds.getValue(FengGUIProperties.margin,
                        Spacing.class, FengGUIFunctions.insets);
                appearance.setMargin(margin);
            }
            
            if (ds.contains(FengGUIProperties.marginTop)
                    && !StyleUtils.applyStates)
            {
                int value = ds.getValue(FengGUIProperties.marginTop,
                        Integer.class);
                appearance.getMargin().setTop(value);
            }
            
            if (ds.contains(FengGUIProperties.marginRight)
                    && !StyleUtils.applyStates)
            {
                int value = ds.getValue(FengGUIProperties.marginRight,
                        Integer.class);
                appearance.getMargin().setRight(value);
            }
            
            if (ds.contains(FengGUIProperties.marginBottom)
                    && !StyleUtils.applyStates)
            {
                int value = ds.getValue(FengGUIProperties.marginBottom,
                        Integer.class);
                appearance.getMargin().setBottom(value);
            }
            
            if (ds.contains(FengGUIProperties.marginLeft)
                    && !StyleUtils.applyStates)
            {
                int value = ds.getValue(FengGUIProperties.marginLeft,
                        Integer.class);
                appearance.getMargin().setLeft(value);
            }
        }
        
        if (t.getAppearance() instanceof TextAppearance)
        {
            TextAppearance appearance = (TextAppearance) t.getAppearance();
            
            if (ds.contains(FengGUIProperties.textAlign)
                    && !StyleUtils.applyStates)
            {
                String textAlign = ds.getValue(FengGUIProperties.textAlign,
                        String.class);
                if (textAlign.equals("left"))
                {
                    appearance.setAlignment(Alignment.LEFT);
                }
                else if (textAlign.equals("center"))
                {
                    appearance.setAlignment(Alignment.MIDDLE);
                }
                else if (textAlign.equals("right"))
                {
                    appearance.setAlignment(Alignment.RIGHT);
                }
            }
            
            if (ds.contains(FengGUIProperties.color) && !StyleUtils.applyStates)
            {
                Color color = ds.getValue(FengGUIProperties.color, Color.class);
                appearance.getStyle(TextStyle.DEFAULTSTYLEKEY)
                        .getTextStyleEntry(TextStyleEntry.DEFAULTSTYLESTATEKEY)
                        .setColor(color);
            }
            
            ITextRenderer textRenderer = appearance
                    .getRenderer(ITextRenderer.DEFAULTTEXTRENDERERKEY);
            IFont font = textRenderer.getFont();
            
            if (ds.contains(FengGUIProperties.font) && !StyleUtils.applyStates)
            {
                font = ds.getValue(FengGUIProperties.font, IFont.class,
                        FengGUIFunctions.font);
            }
            
            textRenderer = new DirectTextRenderer();
            textRenderer.setFont(font);
            appearance.addRenderer(ITextRenderer.DEFAULTTEXTRENDERERKEY,
                    textRenderer);
        }
        
        if (t.getAppearance() instanceof DecoratorAppearance)
        {
            StatefullWidgetProcessor.processWidget(t, ds);
        }
    }
}
