/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.inversion.jinvfc.impl;

import java.io.*;
import java.util.*;
import ru.inversion.fx.app.es.JInvErrorService;

/**
 *
 * @author psh
 */
public class MainWork 
{
    private final String pkgName;
    private final String className;
    private final String tableName;

    public MainWork (String pkgName, String className, String tableName) 
    {
        this.pkgName = pkgName;
        this.className = className;
        this.tableName = tableName;
    }
    
    public void doWork (File fi) 
    {
        fi.getParentFile ().mkdirs ();
        
        String templ = util.getResourceAsString (getClass (), "Main.template");
        if (templ != null)
            try (Formatter frm = new Formatter (fi))
            {
                frm.format (templ, pkgName, className, className.substring (1), System.getProperty ("user.name"), new Date (), pkgName.replace (".", "/"));  
            } catch (Throwable ex) {
                JInvErrorService.handleException (null, ex);
            }
    }

}
