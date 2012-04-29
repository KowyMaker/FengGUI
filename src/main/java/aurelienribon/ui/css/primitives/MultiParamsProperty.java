package aurelienribon.ui.css.primitives;

/**
 * Convenience class to define a property accepting one set of parameters as
 * value.
 * 
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class MultiParamsProperty extends BaseProperty
{
    private final Class<?>[] paramsClasses;
    private final String[]   paramsNames;
    
    public MultiParamsProperty(String name, Class<?>[] paramsClasses,
            String[] paramsNames)
    {
        super(name);
        this.paramsClasses = paramsClasses;
        this.paramsNames = paramsNames;
    }
    
    public Class<?>[][] getParams()
    {
        return new Class[][] { paramsClasses };
    }
    
    public String[][] getParamsNames()
    {
        return new String[][] { paramsNames };
    }
}
