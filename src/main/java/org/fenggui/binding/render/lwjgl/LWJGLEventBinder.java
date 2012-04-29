package org.fenggui.binding.render.lwjgl;

import java.util.HashMap;
import java.util.Map;

import org.fenggui.Display;
import org.fenggui.event.key.Key;
import org.fenggui.event.mouse.MouseButton;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * Events binder for LWJGL, allow Mouse and Key capture from LWJGL and send it
 * to org.fenggui.Display
 * 
 * @author Koka El Kiwi
 * 
 */
public class LWJGLEventBinder
{
    private Display                       display;
    private final Map<MouseButton, State> mouseStates = new HashMap<MouseButton, State>();
    private final Map<Integer, State>     keyStates   = new HashMap<Integer, State>();
    private final Map<Integer, Character> keyChars    = new HashMap<Integer, Character>();
    
    public LWJGLEventBinder(Display display)
    {
        this.display = display;
        
        mouseStates.put(MouseButton.LEFT,
                new State(Mouse.isButtonDown(MouseButton.LEFT.getCode())));
        mouseStates.put(MouseButton.RIGHT,
                new State(Mouse.isButtonDown(MouseButton.RIGHT.getCode())));
        mouseStates.put(MouseButton.MIDDLE,
                new State(Mouse.isButtonDown(MouseButton.MIDDLE.getCode())));
    }
    
    /**
     * Get Display attached to this binder.
     * 
     * @return The display attached to this class
     */
    public Display getDisplay()
    {
        return display;
    }
    
    /**
     * Attach a new Display to this!
     * 
     * @param display
     *            THE Display
     */
    public void setDisplay(Display display)
    {
        this.display = display;
    }
    
    /**
     * Check all events, and ping them to Display.
     */
    public void update()
    {
        if (Mouse.isInsideWindow())
        {
            display.fireMouseMovedEvent(Mouse.getX(), Mouse.getY(),
                    MouseButton.LEFT, 1);
        }
        
        for (final MouseButton mouseButton : mouseStates.keySet())
        {
            final State state = mouseStates.get(mouseButton);
            final long current = System.currentTimeMillis();
            final long diff = current - state.getLastPressed();
            
            if (Mouse.isButtonDown(mouseButton.getCode()))
            {
                if (!state.isPressed())
                {
                    display.fireMousePressedEvent(Mouse.getX(), Mouse.getY(),
                            mouseButton, 1);
                    state.setPressed(true);
                    state.setLastPressed(current);
                }
                
                if (mouseButton == MouseButton.LEFT)
                {
                    display.fireMouseDraggedEvent(Mouse.getX(), Mouse.getY(),
                            mouseButton, 1);
                }
            }
            else
            {
                if (state.isPressed())
                {
                    display.fireMouseReleasedEvent(Mouse.getX(), Mouse.getY(),
                            mouseButton, 1);
                    
                    if (diff < 300)
                    {
                        display.fireMouseClickEvent(Mouse.getX(), Mouse.getY(),
                                mouseButton, 1);
                    }
                    
                    state.setPressed(false);
                }
            }
        }
        
        while (Keyboard.next())
        {
            final int code = Keyboard.getEventKey();
            final char c = EventHelper.mapKeyChar();
            final Key key = EventHelper.mapEventKey();
            
            System.out.println("Key '" + code + "' - State: "
                    + (Keyboard.getEventKeyState() ? "pressed" : "released"));
            
            if (!keyChars.containsKey(code))
            {
                keyChars.put(code, c);
            }
            
            if (!keyStates.containsKey(code))
            {
                final State state = new State(Keyboard.getEventKeyState());
                keyStates.put(code, state);
            }
            
            final State state = keyStates.get(code);
            final long current = System.currentTimeMillis();
            final long diff = current - state.getLastPressed();
            
            if (Keyboard.getEventKeyState())
            {
                if (!state.isPressed())
                {
                    display.fireKeyPressedEvent(c, key);
                    state.setPressed(true);
                    state.setLastPressed(current);
                }
            }
            else
            {
                if (state.isPressed())
                {
                    display.fireKeyReleasedEvent(c, key);
                    
                    if (diff < 300)
                    {
                        display.fireKeyTypedEvent(c);
                    }
                    
                    state.setPressed(false);
                }
            }
        }
        
        for (final int code : keyStates.keySet())
        {
            if (Keyboard.isKeyDown(code))
            {
                final char c = keyChars.get(code);
                final Key key = EventHelper.mapEventKey(code);
                
                display.fireKeyPressedEvent(c, key);
            }
        }
    }
    
    public static class State
    {
        private boolean                   pressed;
        private long                      lastPressed;
        private final Map<String, Object> data = new HashMap<String, Object>();
        
        public State()
        {
            this(false, 0);
        }
        
        public State(long lastPressed)
        {
            this(false, lastPressed);
        }
        
        public State(boolean pressed)
        {
            this(pressed, 0);
        }
        
        public State(boolean pressed, long lastPressed)
        {
            this.pressed = pressed;
            this.lastPressed = lastPressed;
        }
        
        public boolean isPressed()
        {
            return pressed;
        }
        
        public void setPressed(boolean pressed)
        {
            this.pressed = pressed;
        }
        
        public long getLastPressed()
        {
            return lastPressed;
        }
        
        public void setLastPressed(long lastPressed)
        {
            this.lastPressed = lastPressed;
        }
        
        public void setData(String name, Object value)
        {
            data.put(name, value);
        }
        
        public Object getData(String name)
        {
            return data.get(name);
        }
    }
}
