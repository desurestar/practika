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
public class SaveWork {

    private final List<PColumn> columnList;
    private final String pkgName;
    private final String className;
    private final String tableName;

    public SaveWork (List<PColumn> columnList, String pkgName, String className, String tableName) 
    {
        this.columnList = columnList;
        this.pkgName = pkgName;
        this.className = className;
        this.tableName = tableName;
    }
    
    public void doWork (File fi) 
    {
        fi.getParentFile ().mkdirs ();

        try (Formatter frm = new Formatter (fi))
        {
            frm.format ("DECLARE\n" +
                        "    r" + tableName + "  " + tableName + "%%ROWTYPE;\n" +
                        "BEGIN\n");  
            
            for ( PColumn p : columnList )
            {
                frm.format ("    r" + tableName + "." + p.getColumnName () + " := :" + p.getColumnName () + ";\n");
            }    
            
            frm.format ("END;");
            
        } catch (Throwable ex) {
            JInvErrorService.handleException (null, ex);
        }
    }
    
}
