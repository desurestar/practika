/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.inversion.jinvfc.impl;

import java.io.*;
import java.util.Formatter;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import ru.inversion.dataset.mark.MarkModeEnum;
import ru.inversion.fx.app.es.JInvErrorService;
import ru.inversion.jinvfc.entity.ColumnPlace;
import ru.inversion.jinvfc.entity.PColumn;

/**
 *
 * @author psh
 */
public class ViewWork 
{
    private final List<PColumn> columnList;
    private final String pkgName;
    private final String className;
    private final String tableName;
    private final String tab = "    ";
    private final MarkModeEnum markMode;
    

    public ViewWork (List<PColumn> columnList, String pkgName, String className, String tableName, MarkModeEnum markMode) 
    {
        this.columnList = columnList;
        this.pkgName = pkgName;
        this.className = className;
        this.tableName = tableName;
        this.markMode = markMode;
    }
    
    private boolean hasGridPane () 
    {
        Optional<PColumn> findFirst = columnList.stream ().filter (p->p.getPlace () == ColumnPlace.GRIDPANE).findFirst ();
        return findFirst.isPresent ();
    }

    private boolean hasTableView () 
    {
        Optional<PColumn> findFirst = columnList.stream ().filter (p->p.getPlace () == ColumnPlace.TABLE).findFirst ();
        return findFirst.isPresent ();
    }
    
    public void doWork (File fi) 
    {
        File propFile = new File (fi.getParentFile ().getParentFile (), "res/View" + className + ".properties");
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
        
        if (prop.getProperty ("VIEW.TITLE") == null)
            prop.setProperty ("VIEW.TITLE", "VIEW.TITLE");
        
        fi.getParentFile ().mkdirs ();
        
        try (Formatter frm = new Formatter (fi))
        {
            String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                          + "\n"                
                          + "<?import javafx.geometry.*?>\n"
                          + "<?import ru.inversion.fx.form.controls.*?>\n"
                          + "<?import ru.inversion.fx.form.controls.dsbar.DSInfoBar?>\n"
                          + "<?import java.lang.*?>\n"
                          + "<?import java.util.*?>\n"
                          + "<?import javafx.scene.*?>\n"
                          + "<?import javafx.scene.control.*?>\n"
                          + "<?import javafx.scene.layout.*?>\n"
                    
                          + "\n";                

            String vBoxH = "<VBox prefHeight=\"400.0\" prefWidth=\"800.0\""
                         + (hasTableView () && hasGridPane () ? "" : " spacing=\"5.0\"")
                         + " xmlns=\"http://javafx.com/javafx/8.0.40\" xmlns:fx=\"http://javafx.com/fxml/1\""
                         + " fx:controller=\"" + pkgName + ".View" + className + "Controller\">\n";
            
                  vBoxH += "<children>\n"
                         + "<JInvToolBar fx:id=\"toolBar\" />\n";
                  
            int c = 0;  
            for (PColumn p : columnList)
            {
                if (p.getPlace() != ColumnPlace.TABLE)
                     continue;

                if (c == 0) 
                {
                    if (hasGridPane ())
                    {
                        vBoxH += "<SplitPane dividerPositions=\"0.5\" orientation=\"VERTICAL\" VBox.vgrow=\"ALWAYS\">\n"
                               + "<items>\n"
                               + "<AnchorPane>\n"
                               + "<children>\n"
                               + "<JInvTable fx:id=\"" + tableName + "\" tableMenuButtonVisible=\"true\" AnchorPane.bottomAnchor=\"5.0\" AnchorPane.leftAnchor=\"5.0\" AnchorPane.rightAnchor=\"5.0\" AnchorPane.topAnchor=\"5.0\">\n";
                    }    
                    else
                    {    
                        vBoxH += "<JInvTable fx:id=\"" + tableName + "\" tableMenuButtonVisible=\"true\" VBox.vgrow=\"ALWAYS\">\n";
                    }        
                    
                    vBoxH += "<columns>\n";
                }   

//                        vBoxH += "<TableColumn fx:id=\"" + p.getColumnName () + "\" text=\"%%" + p.getColumnName () + "\" />\n";

//                        vBoxH += String.format ("<JInvTableColumn fx:id=\"%1$s\" text=\"%2$s%1$s\" />\n", p.getColumnName (), "%%");
                vBoxH += String.format ("<%1$s fx:id=\"%2$s\" text=\"%%%%%2$s\" fieldName=\"%2$s\" %3$s />\n",
                                        p.getJInvColumnClassName (),
                                        p.getColumnName (),
                                        (p.getLength () != null && Integer.parseInt (p.getLength ()) > 50) ? "prefWidth=\"300.0\"" : "");

                if (prop.getProperty (p.getColumnName ()) == null)
                    prop.setProperty (p.getColumnName (), p.getColumnName ());

                c++;
            }  

            if (c > 0) 
            {
                vBoxH += "</columns>\n";

                if (! hasGridPane ()) 
                    vBoxH += "<VBox.margin>\n"
                           + "<Insets " + (markMode == MarkModeEnum.NONE ? "bottom=\"5.0\" " : "") + "left=\"5.0\" right=\"5.0\" />\n"
                           + "</VBox.margin>\n";
                
                vBoxH += "</JInvTable>\n";
                
                if (hasGridPane ()) 
                    vBoxH += "</children>\n"
                           + "</AnchorPane>\n"
                           + "<ScrollPane fitToHeight=\"true\" fitToWidth=\"true\" SplitPane.resizableWithParent=\"false\">\n"
                           + "<content>" ;
            }   
                                            
                    int r = 0;  
                    for (PColumn p : columnList)
                    {
                        if (p.getPlace() != ColumnPlace.GRIDPANE)
                             continue;
                    
                        if (r == 0)
                            vBoxH += "<GridPane hgap=\"5.0\" vgap=\"5.0\">\n"
                                   + "<children>\n";
                            
//                        vBoxH += "<JInvLabel fx:id=\"lbl" + p.getColumnName () + "\" text=\"%%" + p.getColumnName () + "\" GridPane.columnIndex=\"0\" GridPane.rowIndex=\"" + r+ "\" />\n"
                        vBoxH += "<JInvLabel linkFieldName=\"" + p.getColumnName () + "\" text=\"%%" + p.getColumnName () + "\" GridPane.columnIndex=\"0\" GridPane.rowIndex=\"" + r+ "\" />\n"
                               + "<JInvTextField fx:id=\"" + p.getColumnName () + "\" fieldName=\"" + p.getColumnName () + "\" GridPane.columnIndex=\"1\" GridPane.rowIndex=\"" + r+ "\" />\n";

                        if (prop.getProperty (p.getColumnName ()) == null)
                            prop.setProperty (p.getColumnName (), p.getColumnName ());
                        
                        r++;    
                    }
                    
                    if ( r > 0)
                    {    
                        vBoxH += "</children>\n"
                               + "<columnConstraints>\n"
                               + "<ColumnConstraints />\n"
                               + "<ColumnConstraints hgrow=\"ALWAYS\" />\n"
                               + "</columnConstraints>\n"
//                               + "<VBox.margin>\n"
//                               + "<Insets />\n"
//                               + "</VBox.margin>\n"
                               + "<rowConstraints>\n";
                        
                        int rc = r;
                        if (hasTableView ())
                            rc--;
                        
                        for (int i=0; i <=/*+1*/ rc; i++)
                            vBoxH += String.format ("<RowConstraints %1$s/>\n", (i == r ? "minHeight=\"1.0\"" : "")) ; 
                        
                        vBoxH += "</rowConstraints>\n"
                               + "</GridPane>\n";
                    }           
/*                    
                  vBoxH += "<ButtonBar buttonMinWidth=\"80.0\">\n"
                         + "<buttons>\n"
                         + "<JInvButton fx:id=\"btEXIT\" onAction=\"#onExit\" text=\"%%VIEW.EXIT\" />\n"
                         + "</buttons>\n"
                         + "</ButtonBar>\n"
                         + "</children>\n";
                  
                  if (prop.getProperty ("VIEW.EXIT") == null)
                     prop.setProperty ("VIEW.EXIT", "VIEW.EXIT");
*/                  

            if (c > 0 && hasGridPane ())
            {
                vBoxH += "</content>\n"
                       + "<padding>\n"
                       + "<Insets bottom=\"5.0\" left=\"5.0\" right=\"5.0\" top=\"5.0\" />\n"
                       + "</padding>\n"
                       + "</ScrollPane>\n"
                       + "</items>\n"
                       + "</SplitPane>\n";
            }    
            
            if (hasTableView () && markMode != MarkModeEnum.NONE)
                vBoxH += "<DSInfoBar fx:id=\"" + tableName + "$MARK\">\n"
                       + "<VBox.margin>\n"
                       + "<Insets bottom=\"5.0\" left=\"5.0\" right=\"5.0\" "
                       + (hasGridPane () ? "top=\"5.0\" " : "")
                       + "/>\n"
                       + "</VBox.margin>\n"
                       + "</DSInfoBar>\n";

            vBoxH += "</children>\n";
/*
                  vBoxH += "<padding>\n"
                         + "<Insets bottom=\"5.0\" left=\"5.0\" right=\"5.0\" />\n"
                         + "</padding>\n"
                         + "</VBox>";
*/
            vBoxH += "</VBox>";
            
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
