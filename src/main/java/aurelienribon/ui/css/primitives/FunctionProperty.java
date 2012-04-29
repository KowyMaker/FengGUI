package aurelienribon.ui.css.primitives;

import aurelienribon.ui.css.Function;

/**
 * A FunctionProperty is a property that accepts the exact same parameters as an
 * existing function, plus the function result itself. To avoid duplicating
 * code, the params classes and names of the property are computed from the
 * function.
 * 
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class FunctionProperty extends BaseProperty
{
    private final Function function;
    private final Class<?> paramClass;
    private final String   paramName;
    
    public FunctionProperty(String name, Function function, String paramName)
    {
        super(name);
        this.function = function;
        paramClass = function.getReturn();
        this.paramName = paramName;
    }
    
    public Class<?>[][] getParams()
    {
        return concat(paramClass, function);
    }
    
    public String[][] getParamsNames()
    {
        return concat(paramName, function);
    }
    
    private Class<?>[][] concat(Class<?> newParam, Function function)
    {
        final Class<?>[][] ps = function.getParams();
        final Class<?>[][] result = new Class<?>[ps.length + 1][];
        result[0] = new Class<?>[] { newParam };
        System.arraycopy(ps, 0, result, 1, ps.length);
        return result;
    }
    
    private String[][] concat(String newParam, Function function)
    {
        final String[][] ps = function.getParamsNames();
        final String[][] result = new String[ps.length + 1][];
        result[0] = new String[] { newParam };
        System.arraycopy(ps, 0, result, 1, ps.length);
        return result;
    }
}
