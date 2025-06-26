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
public class ViewControllerWork {

    private final List<PColumn> columnList;
    private final String pkgName;
    private final String className;
    private final String tableName;
    private final MarkModeEnum markMode;    
    private final String tab = "    ";

    public ViewControllerWork (List<PColumn> columnList, String pkgName, String className, String tableName, MarkModeEnum markMode) 
    {
        this.columnList = columnList;
        this.pkgName = pkgName;
        this.className = className;
        this.tableName = tableName;
        this.markMode = markMode;
    }
    
    public void doWork (File fi) 
    {
        fi.getParentFile ().mkdirs ();

        String templ = util.getResourceAsString (getClass (), "ViewController.template");
        if (templ != null)
            try (Formatter frm = new Formatter (fi))
            {
                String gpParam = "";
                String gpBind = "";
                
                for (PColumn p : columnList)
                {
                    if (p.getPlace() != ColumnPlace.GRIDPANE)
                        continue;
                    
                    gpParam += tab + "@FXML private JInvTextField " + p.getColumnName() + ";\n";
//                             + tab + "@FXML\n"
//                             + tab + "private JInvLabel lbl" + p.getColumnName() + ";\n";
                    
//                    gpBind  += tab + tab + p.getColumnName () + ".setLabel (lbl"  + p.getColumnName () + ");\n";
//                    gpBind  += tab + tab + "d.bindControl (" + p.getColumnName () + ", " + className + "::get" + p.getColumnName () + ");\n";                
                    gpBind  += tab + tab + "dsfx.bindControl (" + p.getColumnName () + ");\n";                
                }
                
                if (markMode != MarkModeEnum.NONE)
                {
                    gpParam += tab + "@FXML private DSInfoBar " + tableName + "$MARK;\n";
                    gpBind  += tab + tab + tableName + "$MARK.init (" + tableName + ".getDataSetAdapter ());\n";                                    
                    gpBind  += tab + tab + tableName + "$MARK.addAggregator (\"1\", AggrFuncEnum.COUNT, AggregatorType.MARK, null, null);\n";                                    
                }    
                
                frm.format (templ, pkgName, 
                                   className, 
                                   className.substring (1), 
                                   System.getProperty ("user.name"), 
                                   new Date (), 
                                   tableName,
                                   gpParam,
                                   gpBind,
                                   markMode == null || markMode == MarkModeEnum.NONE ? "false" : "true",
                                   pkgName.replace (".", "/"));  
            } catch (Throwable ex) {
                JInvErrorService.handleException (null, ex);
            }
    }
    
}
