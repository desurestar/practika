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
public class EditWork 
{
    private final List<PColumn> columnList;
    private final String pkgName;
    private final String className;
    private final String tableName;
    private final String tab = "    ";

    public EditWork (List<PColumn> columnList, String pkgName, String className, String tableName) 
    {
        this.columnList = columnList;
        this.pkgName = pkgName;
        this.className = className;
        this.tableName = tableName;
    }
    
    public void doWork (File fi) 
    {
        File propFile = new File (fi.getParentFile ().getParentFile (), "res/Edit" + className + ".properties");
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
        
        if (prop.getProperty ("EDIT.TITLE") == null)
            prop.setProperty ("EDIT.TITLE", "EDIT.TITLE");
        
        fi.getParentFile ().mkdirs ();
        
        try (Formatter frm = new Formatter (fi))
        {
            String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                          + "\n"                
                          + "<?import javafx.geometry.*?>\n"
                          + "<?import ru.inversion.fx.form.controls.*?>\n"
                          + "<?import ru.inversion.fx.form.lov.*?>\n"
                          + "<?import java.lang.*?>\n"
                          + "<?import java.util.*?>\n"
                          + "<?import javafx.scene.*?>\n"
                          + "<?import javafx.scene.control.*?>\n"
                          + "<?import javafx.scene.layout.*?>\n"
                          + "\n";                

            String vBoxH = "<VBox spacing=\"5.0\""
                         + " xmlns=\"http://javafx.com/javafx/8.0.40\" xmlns:fx=\"http://javafx.com/fxml/1\""
                         + " fx:controller=\"" + pkgName + ".Edit" + className + "Controller\">\n";
            
                  vBoxH += "<children>\n";
                  
                    int r = 0;  
                    for (PColumn p : columnList)
                    {
                        if (p.getPlace() == ColumnPlace.NONE)
                             continue;
                    
                        if (r == 0)
                            vBoxH += "<GridPane hgap=\"5.0\" vgap=\"5.0\" VBox.vgrow=\"ALWAYS\">\n"
                                   + "<children>\n";
                            
                        vBoxH += "<JInvLabel fx:id=\"lbl" + p.getColumnName () + "\" text=\"%%" + p.getColumnName () + "\" GridPane.columnIndex=\"0\" GridPane.rowIndex=\"" + r+ "\" />\n";

                        String tf = "<" + p.getJInvFieldClassName () + " fx:id=\"" + p.getColumnName () +"\" "
                                  + (p.getLength () != null && Integer.parseInt (p.getLength ()) > 50 ? "prefColumnCount=\"50\" " : "")
                                  + (p.getLength () != null && Integer.parseInt (p.getLength ()) < 30 ? "maxWidth=\"-Infinity\" prefColumnCount=\"" + p.getLength () + "\" " : "")
                                  + "fieldName=\"" + p.getColumnName () + "\"";        
                        
                        if (p.getIsLov())        
                        {
                            vBoxH += "<HBox GridPane.columnIndex=\"1\" GridPane.rowIndex=\"" + r+ "\">\n"
                                   + "<children>\n"
                                   + tf + "/>\n"
                                   + "<JInvLOVButton fx:id=\"F9_" + p.getColumnName () + "\" idTextField=\"" + p.getColumnName () + "\" />\n"
                                   + "</children>\n"
                                   + "</HBox>\n";
                        }   
                        else
                        {    
                            vBoxH += tf + " GridPane.columnIndex=\"1\" GridPane.rowIndex=\"" + r+ "\" />\n";
                        }       

                        if (prop.getProperty (p.getColumnName ()) == null)
                            prop.setProperty (p.getColumnName (), p.getColumnName ());
                        
                        r++;    
                    }
                    
                    if ( r > 0)
                    {    
                        vBoxH += "</children>\n"
                               + "<columnConstraints>\n"
                               + "<ColumnConstraints halignment=\"RIGHT\" />\n"
                               + "<ColumnConstraints hgrow=\"ALWAYS\" />\n"
                               + "</columnConstraints>\n"
                               + "<VBox.margin>\n"
                               + "<Insets />\n"
                               + "</VBox.margin>\n"
                               + "<rowConstraints>\n";
                        
                        for (int i=0; i <=/*+1*/ r; i++)
                            vBoxH += String.format ("<RowConstraints %1$s/>\n", (i == r ? "minHeight=\"1.0\"" : "")) ; 
                        
                        vBoxH += "</rowConstraints>\n"
                               + "</GridPane>\n";
                    }           
                    
                  vBoxH += "<ButtonBar buttonMinWidth=\"80.0\">\n"
                         + "<buttons>\n"
                         + "<JInvButton fx:id=\"btOK\" />\n"
                         + "<JInvButton fx:id=\"btCancel\" />\n"
                         + "</buttons>\n"
                         + "</ButtonBar>\n"
                         + "</children>\n";
                  
//                  if (prop.getProperty ("EDIT.OK") == null)
//                     prop.setProperty ("EDIT.OK", "EDIT.OK");
//                  if (prop.getProperty ("EDIT.CANCEL") == null)
//                     prop.setProperty ("EDIT.CANCEL", "EDIT.CANCEL");
                  
            
                  vBoxH += "<padding>\n"
                         + "<Insets bottom=\"5.0\" left=\"5.0\" right=\"5.0\" top=\"5.0\" />\n"
                         + "</padding>\n"
                         + "</VBox>";
            
            frm.format (header);  
            frm.format (vBoxH);  
        
            try (OutputStream os = new FileOutputStream (propFile)) {
                prop.store (os, "");
            }
            
        } catch (Throwable ex)
        {
            JInvErrorService.handleException (null, ex);
        }
    }

}
