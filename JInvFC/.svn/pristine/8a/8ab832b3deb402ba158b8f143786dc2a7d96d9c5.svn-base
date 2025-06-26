/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.inversion.jinvfc.impl;

import java.io.*;
import java.util.Formatter;
import java.util.List;
import java.util.Properties;
import ru.inversion.fx.app.es.JInvErrorService;
import ru.inversion.jinvfc.entity.ColumnPlace;
import ru.inversion.jinvfc.entity.PColumn;

/**
 *
 * @author psh
 */
public class PojoPropWork 
{
    private final List<PColumn> columnList;

    public PojoPropWork (List<PColumn> columnList) 
    {
        this.columnList = columnList;
    }
    
    public void doWork (File propFile) 
    {
        propFile.getParentFile ().mkdirs ();
        
        Properties prop = new Properties ();
        
        try
        {    
            prop.load (new FileInputStream (propFile));
        } 
        catch (FileNotFoundException e) {} 
        catch (IOException ex) {
            JInvErrorService.handleException (null, ex);
        }
        
        try
        {    
            if (prop.getProperty (LOV_TITLE) == null)
                prop.setProperty (LOV_TITLE, LOV_TITLE);

            
            for (PColumn p : columnList)
            {
                if (prop.getProperty (p.getColumnName ()) == null)
                    prop.setProperty (p.getColumnName (), p.getColumnName ());
            }
                    
            OutputStream os = new FileOutputStream (propFile);
            prop.store (os, "");
            os.close ();
            
        } catch (Throwable ex)
        {
            JInvErrorService.handleException (null, ex);
        }
    }

    public static final String LOV_TITLE = "LOV_TITLE";
}
