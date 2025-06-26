package com.mycompany.mavenproject1;

import java.util.Collections;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.stage.WindowEvent;
import ru.inversion.fx.app.BaseApp;
import ru.inversion.fx.app.es.JInvErrorService;
import ru.inversion.fx.form.FXFormLauncher;
import ru.inversion.fx.form.JInvFXBrowserController;
import ru.inversion.tc.TaskContext;

public class App extends BaseApp
{
    @Override
    public String getAppID() {
        return "mavenproject1";
    }
    
    @Override
    public void showMainWindow() {

        try {

              new FXFormLauncher(new TaskContext(), null, "fxml/MainPane.fxml").bundle(getCommonResourceBundle()).show();
        
//            JInvFXBrowserController.show(new TaskContext(), getPrimaryViewContext(), "fxml/MainPane.fxml", true, getCommonResourceBundle(), Collections.EMPTY_MAP, null);
        } catch (Throwable ex) {
            JInvErrorService.handleException(null, ex);
        }
    }

    @Override
    public ResourceBundle getCommonResourceBundle() {
        return ResourceBundle.getBundle("bndl");
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Throwable ex) {
            if (appLog != null) {
                appLog.error(ex.toString());
            } else {
                ex.printStackTrace();
            }
        }

    }
}
