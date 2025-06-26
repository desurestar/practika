package ru.inversion.employees_test;

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
 * @since   Mon Jul 01 11:55:26 MSK 2024
 */
public class PEmployeesTestMain extends BaseApp 
{
    
    @Override
    protected void showMainWindow () 
    {
        showViewEmployeesTest (getPrimaryViewContext (), null, Collections.emptyMap ());
    }

    @Override
    public String getAppID () 
    {
        return "XXI.PEMPLOYEESTEST"; 
    }
    
    public static void main (String[] args) 
    {
        launch (args);
    }

    @StartMethod (description = "Не поленитесь указать описание для showViewEmployeesTest, JInvDesktop будет премного благодарен") 
    public static void showViewEmployeesTest ( ViewContext vc, TaskContext tc, Map<String, Object> map ) 
    {
        new FXFormLauncher<> (tc, vc, ViewEmployeesTestController.class)
            .initProperties (map)
            .show ();
    }
    
}

