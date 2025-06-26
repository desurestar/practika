/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.inversion.jinvfc.facade;

import java.util.ResourceBundle;
import javafx.scene.text.Font;
import ru.inversion.fx.app.BaseApp;
import ru.inversion.fx.app.es.JInvErrorService;
import ru.inversion.fx.form.Alerts;
import ru.inversion.fx.form.JInvFXBrowserController;

/**
 *
 * @author perov
 */
public class Main extends BaseApp {

  
    @Override
	public String getAppID() {
		return "XXI.JInvFC";
	}
    
     static {
        Font.loadFont(Main.class.getResource("/font/fontawesome-webfont.ttf").toExternalForm(), 10);
    }         
    
     
     
    @Override
    public void showMainWindow( )  
    {
	String fxml = "fxml/JInvMainPane.fxml";
        
        if ("file".equals (Main.class.getClassLoader ().getResource (fxml).getProtocol()))
           Alerts.info (this, getCommonResourceBundle().getString ("FILEWARN_TITLE"), getCommonResourceBundle().getString ("FILEWARN"));
                
	try {
                    
	    JInvFXBrowserController.show (getCommonTaskContext(), null, fxml, true, getCommonResourceBundle(), null, null);
	}
	catch( Throwable ex ) {
	    JInvErrorService.handleException(null, ex);
                        
	}
    }

    @Override
	public ResourceBundle getCommonResourceBundle() {
		return ResourceBundle.getBundle("bndl");
	}    
    
    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }


    
    
    

}
