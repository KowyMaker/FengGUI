package aurelienribon.ui.css.primitives;

import aurelienribon.ui.css.Function;

public class MultiFunctionProperty extends BaseProperty
{
    private final Function[] functions;
    private final String[]   paramsNames;
    
    public MultiFunctionProperty(String name, Function[] functions,
            String[] paramsNames)
    {
        super(name);
        this.functions = functions;
        this.paramsNames = paramsNames;
    }
    
    public Class<?>[][] getParams()
    {
        Class<?>[][] paramsClasses = new Class<?>[paramsSize()][];
        int index = 0;
        for (Function function : functions)
        {
            paramsClasses[index] = new Class<?>[] { function.getReturn() };
            System.arraycopy(function.getParams(), 0, paramsClasses, index + 1,
                    function.getParams().length);
            index += function.getParams().length + 1;
        }
        
        return paramsClasses;
    }
    
    public String[][] getParamsNames()
    {
        String[][] paramsNames = new String[paramsSize()][];
        int index = 0;
        for (int i = 0; i < functions.length; i++)
        {
            Function function = functions[i];
            paramsNames[index] = new String[] { this.paramsNames[i] };
            System.arraycopy(function.getParamsNames(), 0, paramsNames,
                    index + 1, function.getParamsNames().length);
            index += function.getParams().length + 1;
        }
        
        return paramsNames;
    }
    
    private int paramsSize()
    {
        int size = 0;
        
        size += functions.length;
        for (Function function : functions)
        {
            size += function.getParams().length;
        }
        
        return size;
    }
}
