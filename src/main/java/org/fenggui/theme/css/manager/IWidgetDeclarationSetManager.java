package org.fenggui.theme.css.manager;

import java.util.LinkedHashMap;
import java.util.Map;

import org.fenggui.IWidget;
import org.fenggui.StatefullWidget;
import org.fenggui.appearance.DecoratorAppearance;
import org.fenggui.event.ActivationEvent;
import org.fenggui.event.FocusEvent;
import org.fenggui.event.IActivationListener;
import org.fenggui.event.IFocusListener;
import org.fenggui.event.mouse.MouseAdapter;
import org.fenggui.event.mouse.MouseEnteredEvent;
import org.fenggui.event.mouse.MouseExitedEvent;
import org.fenggui.event.mouse.MousePressedEvent;
import org.fenggui.event.mouse.MouseReleasedEvent;
import org.fenggui.theme.css.StyleUtils;

import aurelienribon.ui.css.DeclarationSet;
import aurelienribon.ui.css.DeclarationSetManager;
import aurelienribon.ui.css.Style;

public class IWidgetDeclarationSetManager implements
        DeclarationSetManager<IWidget>
{
    public void manage(IWidget target, Map<String, DeclarationSet> dss)
    {
        if (dss.isEmpty())
        {
            return;
        }
        
        TargetManager.applyStates(target, dss);
        
        if (target instanceof StatefullWidget)
        {
            TargetManager.manage(target, dss);
        }
        else
        {
            Style.apply(target, dss.get(""));
        }
    }
    
    private static class TargetManager extends MouseAdapter implements
            IFocusListener, IActivationListener
    {
        private final StatefullWidget<? extends DecoratorAppearance> target;
        private final Map<String, DeclarationSet>                    dss;
        private final Map<String, Boolean>                           states;
        
        public TargetManager(
                StatefullWidget<? extends DecoratorAppearance> target,
                Map<String, DeclarationSet> dss)
        {
            this.target = target;
            this.dss = dss;
            states = new LinkedHashMap<String, Boolean>(dss.size());
            
            boolean moused = false;
            for (String pseudoClass : dss.keySet())
            {
                if (!pseudoClass.equals(":disabled"))
                {
                    states.put(pseudoClass, false);
                }
                if (pseudoClass.equals(":focus"))
                {
                    states.put(pseudoClass, target.hasFocus());
                }
                if ((pseudoClass.equals(":hover") || pseudoClass
                        .equals(":active")) && !moused)
                {
                    moused = true;
                    target.addMouseListener(this);
                }
                else if (pseudoClass.equals(":focus"))
                {
                    target.addFocusListener(this);
                }
                else if (pseudoClass.equals(":disabled"))
                {
                    target.addActivationListener(this);
                }
            }
        }
        
        public void apply()
        {
            if (dss.containsKey(""))
            {
                Style.apply(target, dss.get(""));
            }
            
            if (target.isEnabled())
            {
                for (String pseudoClass : states.keySet())
                {
                    if (states.get(pseudoClass) == true
                            && dss.containsKey(pseudoClass))
                    {
                        Style.apply(target, dss.get(pseudoClass));
                    }
                }
            }
            else if (dss.containsKey(":disabled"))
            {
                Style.apply(target, dss.get(":disabled"));
            }
        }
        
        public void focusChanged(FocusEvent e)
        {
            if (e.isFocusGained())
            {
                states.put(":focus", true);
            }
            else
            {
                states.put(":focus", false);
            }
            apply();
        }
        
        public void widgetActivationChanged(ActivationEvent e)
        {
            if (e.isEnabled())
            {
                states.put(":disabled", false);
            }
            else
            {
                states.put(":disabled", true);
            }
            apply();
        }
        
        @Override
        public void mouseEntered(MouseEnteredEvent e)
        {
            states.put(":hover", true);
            apply();
        }
        
        @Override
        public void mouseExited(MouseExitedEvent e)
        {
            states.put(":hover", false);
            apply();
        }
        
        @Override
        public void mousePressed(MousePressedEvent e)
        {
            states.put(":active", true);
            apply();
        }
        
        @Override
        public void mouseReleased(MouseReleasedEvent e)
        {
            states.put(":active", false);
            apply();
        }
        
        @SuppressWarnings("unchecked")
        public static void manage(IWidget target,
                Map<String, DeclarationSet> dss)
        {
            TargetManager tm = new TargetManager(
                    (StatefullWidget<? extends DecoratorAppearance>) target,
                    dss);
            tm.apply();
        }
        
        public static void applyStates(IWidget target,
                Map<String, DeclarationSet> dss)
        {
            StyleUtils.applyStates = true;
            if (dss.containsKey(""))
            {
                StyleUtils.state = StatefullWidget.STATE_DEFAULT;
                Style.apply(target, dss.get(""));
            }
            if (dss.containsKey(":hover"))
            {
                StyleUtils.state = StatefullWidget.STATE_HOVERED;
                Style.apply(target, dss.get(":hover"));
            }
            if (dss.containsKey(":focus"))
            {
                StyleUtils.state = StatefullWidget.STATE_FOCUSED;
                Style.apply(target, dss.get(":focus"));
            }
            if (dss.containsKey(":active"))
            {
                StyleUtils.state = StatefullWidget.STATE_ACTIVE;
                Style.apply(target, dss.get(":active"));
            }
            if (dss.containsKey(":disabled"))
            {
                StyleUtils.state = StatefullWidget.STATE_DISABLED;
                Style.apply(target, dss.get(":disabled"));
            }
            StyleUtils.applyStates = false;
        }
    }
}
