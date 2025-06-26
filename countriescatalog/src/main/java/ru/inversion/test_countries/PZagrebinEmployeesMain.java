package ru.inversion.test_countries;

import java.util.Collections;
import java.util.Map;
import ru.inversion.fx.form.ViewContext;
import ru.inversion.fx.app.BaseApp;
import ru.inversion.fx.form.FXFormLauncher;
import ru.inversion.tc.TaskContext;
import ru.inversion.annotation.StartMethod;

/**
 *
 * @author  admin
 * @since   Thu Jun 26 12:29:26 MSK 2025
 */
public class PZagrebinEmployeesMain extends BaseApp 
{
    
    @Override
    protected void showMainWindow () 
    {
        showViewZagrebinEmployees (getPrimaryViewContext (), null, Collections.emptyMap ());
    }

    @Override
    public String getAppID () 
    {
        return "XXI.PZAGREBINEMPLOYEES"; 
    }
    
    public static void main (String[] args) 
    {
        launch (args);
    }

    @StartMethod (description = "Не поленитесь указать описание для showViewZagrebinEmployees, JInvDesktop будет премного благодарен") 
    public static void showViewZagrebinEmployees ( ViewContext vc, TaskContext tc, Map<String, Object> map ) 
    {
        new FXFormLauncher<> (tc, vc, ViewZagrebinEmployeesController.class)
            .initProperties (map)
            .show ();
    }
    
}

