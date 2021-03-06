package aurelienribon.ui.css;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class Selector
{
    private final List<Atom> atoms = new ArrayList<Atom>();
    private final String     pseudoClass;
    
    public Selector(String rawSelector)
    {
        rawSelector = rawSelector.trim();
        rawSelector = rawSelector.replaceAll("\\s+", " ");
        rawSelector = rawSelector.replaceAll("\\s*>\\s*", " >");
        
        final int pcIdx = rawSelector.indexOf(":");
        if (pcIdx > -1)
        {
            pseudoClass = rawSelector.substring(pcIdx).trim();
            rawSelector = rawSelector.substring(0, pcIdx);
        }
        else
        {
            pseudoClass = "";
        }
        
        final String[] parts = rawSelector.split(" ");
        for (int i = 0; i < parts.length; i++)
        {
            atoms.add(new Atom(parts[i]));
        }
    }
    
    public List<Atom> getAtoms()
    {
        return atoms;
    }
    
    public Atom getLastAtom()
    {
        return atoms.get(atoms.size() - 1);
    }
    
    public String getPseudoClass()
    {
        return pseudoClass;
    }
    
    @Override
    public String toString()
    {
        String str = "";
        for (final Atom atom : atoms)
        {
            str += atom.toString() + " > ";
        }
        return str.substring(0, str.length() - 3);
    }
    
    // -------------------------------------------------------------------------
    
    public static class Atom
    {
        private final Class<?>     type;
        private final List<String> classes;
        private final boolean      strictNext;
        
        public Atom(String str)
        {
            strictNext = str.startsWith(">");
            
            str = str.replaceAll(">", "");
            str = str.replaceAll("#", ".");
            
            final String[] parts = str.startsWith(".") ? str.substring(1)
                    .split("\\.") : str.split("\\.");
            
            if (parts[0].equals("*"))
            {
                type = Object.class;
                classes = Arrays.asList(parts).subList(1, parts.length);
            }
            else if (str.startsWith("."))
            {
                type = Object.class;
                classes = Arrays.asList(parts);
            }
            else
            {
                Class<?> type = Style.getRegisteredType(parts[0]);
                if (type == null)
                {
                    try
                    {
                        type = Class.forName(parts[0].replaceAll("-", "."));
                    }
                    catch (final ClassNotFoundException ex)
                    {
                        throw new RuntimeException(ex);
                    }
                }
                
                this.type = type;
                classes = Arrays.asList(parts).subList(1, parts.length);
            }
        }
        
        public Atom(Class<?> type, List<String> classes)
        {
            this.type = type;
            this.classes = classes == null ? new ArrayList<String>() : classes;
            strictNext = false;
        }
        
        public Class<?> getType()
        {
            return type;
        }
        
        public List<String> getClasses()
        {
            return classes;
        }
        
        public boolean isStrictNext()
        {
            return strictNext;
        }
        
        @Override
        public String toString()
        {
            String str = type.getSimpleName();
            for (final String c : classes)
            {
                str += " " + c;
            }
            return str;
        }
    }
}
