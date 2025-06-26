/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.inversion.jinvfc.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import ru.inversion.fx.app.es.JInvErrorService;

/**
 *
 * @author psh
 */
public class util {

    public static boolean alertYesNo (String header, String content, String title)
    {
        Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
        ResourceBundle b = ResourceBundle.getBundle ("util");
        
        if (title != null)
           alert.setTitle (title);
        else
           alert.setTitle (b.getString ("YES_NO.TITLE"));
        
        if (content != null)
           alert.setContentText (content);
        else
           alert.setContentText (b.getString ("YES_NO.TEXT"));
        
        if (header != null)
           alert.setHeaderText (header);
        else
           alert.setHeaderText (b.getString ("YES_NO.HEADER"));
        
        return alert.showAndWait ().orElse (ButtonType.NO) == ButtonType.OK;
    }      
    
    public static void alertInfo (String header, String content, String title)
    {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        ResourceBundle b = ResourceBundle.getBundle ("util");
        
        if (title != null)
           alert.setTitle (title);
        else
           alert.setTitle (b.getString ("INFO.TITLE"));
        
        if (content != null)
           alert.setContentText (content);
        else
           alert.setContentText (b.getString ("INFO.TEXT"));
        
        if (header != null)
           alert.setHeaderText (header);
        else
           alert.setHeaderText (b.getString ("INFO.HEADER"));
        
        alert.showAndWait ();
    }        

    public static void alertError (String header, String content, String title)
    {
        Alert alert = new Alert (Alert.AlertType.ERROR);
        ResourceBundle b = ResourceBundle.getBundle ("util");
        
        if (title != null)
           alert.setTitle (title);
        else
           alert.setTitle (b.getString ("ERROR.TITLE"));
        
        if (content != null)
           alert.setContentText (content);
        else
           alert.setContentText (b.getString ("ERROR.TEXT"));
        
        if (header != null)
           alert.setHeaderText (header);
        else
           alert.setHeaderText (b.getString ("ERROR.HEADER"));
        
        alert.showAndWait ();
    }        
    
    public static String getResourceAsString (Class cl, String res)
    {    
        String templ = null;
        try (InputStream is = cl.getClassLoader ().getResourceAsStream (res)) 
        {
            byte data [] = new byte [is.available ()];
            is.read (data);
            
            templ = new String (data);            
        }
        catch (IOException ex) {
            JInvErrorService.handleException (null, ex);
        }    
        
        return templ;
    }
}
