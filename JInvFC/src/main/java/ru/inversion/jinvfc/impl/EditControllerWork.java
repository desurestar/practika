/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.inversion.jinvfc.impl;

import java.io.*;
import java.util.*;
import ru.inversion.fx.app.es.JInvErrorService;
import ru.inversion.jinvfc.entity.ColumnPlace;
import ru.inversion.jinvfc.entity.PColumn;
import ru.inversion.dataset.mark.*;

/**
 *
 * @author psh
 */
public class EditControllerWork {

    private final List<PColumn> columnList;
    private final String pkgName;
    private final String className;
    private final String tableName;

    public EditControllerWork (List<PColumn> columnList, String pkgName, String className, String tableName) 
    {
        this.columnList = columnList;
        this.pkgName = pkgName;
        this.className = className;
        this.tableName = tableName;
    }
    
    public void doWork (File fi) 
    {
        fi.getParentFile ().mkdirs ();

        String templ = util.getResourceAsString (getClass (), "EditController.template");
        if (templ != null)
            try (Formatter frm = new Formatter (fi))
            {
                StringBuilder def = new StringBuilder ();
                for (PColumn p : columnList)
                {
                    if (p.getPlace() == ColumnPlace.NONE)
                        continue;
                    
                    def.append ("//    @FXML ")
                       .append (p.getJInvFieldClassName ())
                       .append (" ")
                       .append (p.getColumnName ())
                       .append (";\n");        
                }
                
                frm.format (templ, pkgName, 
                                   className, 
                                   className.substring (1), 
                                   System.getProperty ("user.name"), 
                                   new Date (), 
                                   tableName,
                                   pkgName.replace (".", "/"),
                                   def.toString ());  
            } catch (Throwable ex) {
                JInvErrorService.handleException (null, ex);
            }
    }
    
}
