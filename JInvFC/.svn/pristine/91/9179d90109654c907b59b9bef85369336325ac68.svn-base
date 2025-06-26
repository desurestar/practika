/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.inversion.jinvfc.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.*;
import org.apache.commons.io.Charsets;
import ru.inversion.dataset.SQLDataSet;
import ru.inversion.fx.app.es.JInvErrorService;
import ru.inversion.jinvfc.entity.*;
import ru.inversion.dataset.mark.*;
import ru.inversion.tc.TaskContext;
import ru.inversion.utils.S;

/**
 *
 * @author perov
 */
public class PojoWork {

    private final String tab = "    ";
    public PojoWork (List<PColumn> columnList, 
                     String pkgName, String className, String tableName, 
                     MarkModeEnum markMode,
                     PojoAnnotationMode annMode, String query) 
    {
        this.columnList = columnList;
        this.pkgName = pkgName;
        this.className = className;
        this.tableName = tableName;

        this.markMode = markMode;
        this.annMode = annMode;
        this.query = query;
    }
    
    private final List<PColumn> columnList;
    private final String pkgName;
    private final String className;
    private final String tableName;
    private final MarkModeEnum markMode;
    private final PojoAnnotationMode annMode;
    private final String query;
    
    public void doWork (File fi) 
    {
        fi.getParentFile ().mkdirs ();
        
//        fi.createNewFile ();
        
                    try (Formatter frm = new Formatter (fi, "UTF-8"))
                    {
                        
                    boolean usePreference = columnList.stream ().filter (p-> S.isNotNullOrEmpty (p.getUpsPrefName ())).findAny ().isPresent ();
                        
                    String header = "package " + pkgName + ";\n"
                        + "\n"
                        + "import java.math.BigDecimal;\n"
                        + "import java.sql.*;\n"
                        + "import java.time.*;\n"
                        + "import java.io.Serializable;\n"
//                        + "import java.beans.*;\n"
                        + "import javax.persistence.*;\n"
//                        + "import ru.inversion.form.valid.annotations.*;\n"
                        + "import ru.inversion.dataset.mark.*;\n"  
                        + "import ru.inversion.db.entity.ProxyFor;\n" ;
                        
                        if (usePreference)
                            header += "import ru.inversion.bicomp.pref.Preference;\n" ;
                    
                        header += "\n";
                    
                    // Описание
                    String classDescr = "/**\n";
                    classDescr += "@author  "+ System.getProperty("user.name") + "\n";
                    classDescr += "@since   " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()) + "\n";
                    classDescr += "*/\n";       
                    
                    classDescr += "@Entity (name=\"" + pkgName + "." + className + "\")\n";
                    
                    if (tableName != null && tableName.length() > 0)
                    {    
                       if (annMode == PojoAnnotationMode.TABLE) 
                          classDescr += String.format ("@Table (name=\"%s\")\n", tableName);
                       if (annMode == PojoAnnotationMode.NAMEDNATIVEQUERY) 
                       {
                            String querynew = query.replaceAll ("\"", "\\\\\"").replaceAll ("\\n", "\"\n\t+\"");

                            if (querynew.contains ("*"))
                            {
                               StringBuilder s = new StringBuilder ();
                               columnList.forEach (c -> s.append (",").append (c.getColumnName ()));
                               
                               if (util.alertYesNo (ResourceBundle.getBundle("bndl").getString("REPLACE_ASTERISK"), s.substring (1), null))
                                   querynew = querynew.replaceFirst ("\\*", s.substring (1));
                            }    
                           
                            classDescr += String.format ("@NamedNativeQuery (name=\"%s.%s\", query=\"%s\")\n", pkgName, className, querynew);
                       }   
                    }
                    
                    List <String > listPK = columnList.stream ()
                                                      .filter ((p)->p.getPrimaryKey ())
                                                      .map ((p)->p.getColumnName())
                                                      .collect (Collectors.toList ());
                        
                    
                    if (markMode == MarkModeEnum.MRK)
                       classDescr += "@MarkColumns (columns = {\"ROWID\"})\n";
                    if (markMode == MarkModeEnum.MRK_ID2)
                       classDescr += "@MarkColumns (columns = {\"" + listPK.get (0)+ "\",\"" + listPK.get (1) + "\"})\n";
                    
                    classDescr += "public class " + className;
                    
                    if (null != markMode)
                        switch (markMode) {
                            case MRK_ID:
                                classDescr += " extends IDMarkable";
                                break;
                            case MRK_ID2:
                                classDescr += " extends ID2Markable";
                                break;
                            case MRK_U:
                                classDescr += " extends UMarkable";
                                break;
                            case MRK:
                                classDescr += " extends RowIDMarkable";
                                break;
                            default:
                                break;
                        }

                    classDescr += " implements Serializable\n{\n";
                    String uid = new SimpleDateFormat ("d_MM_yyyy_HH_mm_ss").format(Calendar.getInstance().getTime());
                    classDescr += tab + String.format ("private static final long serialVersionUID = %sl;\n", uid);
                    
                    //поля класса
                    String classField = "\n";
                    // конструктор 
                    String classConstructor = "\n"+tab+"public "+className+"(){}\n\n";
                    //Методы
                    String classMetod = ""; 
                    
                    for (PColumn p : columnList)
                    {
//                        if (!p.getCheckOn())
                        if (p.getPlace() == ColumnPlace.NONE)
                        {
                             continue;
                        }
                        
                        String colName = p.getColumnName();
                        String filedName = colName.toUpperCase();
                        String clsName = p.getJavaType();
                        String shortClsName = getShortClsName(clsName);
                        
                        //Формируем поля класса

                        if (! S.isNullOrEmpty (p.getComment ()))
                            classField += String.format ("\n/*\n* %s\n*/\n", p.getComment ());
                                
                        classField += tab+"private "+shortClsName+" "+filedName+";\n";
                        
                        //get
                        if (p.getPrimaryKey ())  
                            classMetod += tab+"@Id \n";
                        
                        if (usePreference)
                            classMetod += tab+"@Preference(dbName=\""+p.getUpsPrefName ()+"\")\n";
                        
                        classMetod += tab+"@Column(name=\""+filedName+"\"";
                        
                        if (p.getIsNotNull()) 
                            classMetod += ",nullable = false";
//                        else { classMetod += ",nullable = true"; }

                        if ("ROWID".equals (p.getSqlType ()))
                        {
                            classMetod += ",insertable = false, updatable = false";
                        }                        

                        
                        if (p.getLength()!=null && !p.getLength().equals("")) { classMetod += ",length = "+p.getLength();}
                        classMetod += ")\n";
                        
                        //get
                        classMetod += tab+"public "+shortClsName+" get"+filedName+"() {\n";
                        classMetod += tab+tab+"return "+filedName+";\n"+tab+"}\n";
                                                
                        //set
                        classMetod += tab+"public void set"+filedName+"("+shortClsName+" val) {\n";
                        classMetod += tab+tab+filedName+" = val; \n"+tab+"}\n";
                        
                        if (shortClsName.equals ("Date"))
                        {
                            classMetod += tab + "@Transient\n";
                            classMetod += tab + "@ProxyFor(columnName=\"" + filedName + "\")\n";
                            classMetod += tab+"public LocalDate get"+filedName+"_LD() {\n";
                            classMetod += tab+tab+"return get"+filedName+" () == null ? null : get"+filedName+" ().toLocalDate ();\n"+tab+"}\n";                            
                            
                            classMetod += tab+"public void set"+filedName+"_LD(LocalDate val) {\n";
                            classMetod += tab+tab+"set"+filedName+"(val == null ? null : Date.valueOf (val)); \n"+tab+"}\n";
                        }    
                        
                    }
                    frm.format(header);
                    frm.format(classDescr);
                    frm.format(classField);
                    frm.format(classConstructor);
                    frm.format(classMetod);
                    
                    if (null != markMode && markMode != MarkModeEnum.NONE)
                    {    
                        String classMarkable = tab + "@Transient\n"
                                             + tab + "@Override\n";
                        
                        switch (markMode) {
                            case MRK_ID:
                                classMarkable += tab + "public Long getMarkLongID() {\n";
                                classMarkable += tab + tab + "return get" + listPK.get (0) + "();\n";                                    
                                classMarkable += tab + "}\n";
                                break;
                            case MRK_ID2:
                                classMarkable += tab + "public Long getMarkLongID1() {\n";
                                classMarkable += tab + tab + "return get" + listPK.get (0) + "();\n";                                    
                                classMarkable += tab + "}\n";
                                classMarkable += tab + "@Transient\n"
                                               + tab + "@Override\n";
                                classMarkable += tab + "public Long getMarkLongID2() {\n";
                                classMarkable += tab + tab + "return get" + listPK.get (1) + "();\n";                                    
                                classMarkable += tab + "}\n";
                                break;
                            case MRK_U:
                                classMarkable += tab + "public String getMarkStringID() {\n";
                                classMarkable += tab + tab + "return get" + listPK.get (0) + "();\n";                                    
                                classMarkable += tab + "}\n";
                                break;
                            case MRK:
                                classMarkable += tab + "public String getMarkRowID() {\n";
                                classMarkable += tab + tab + "return getROWID();\n";                                    
                                classMarkable += tab + "}\n";
                                break;
                            default:
                                break;
                        }

                        classMarkable += tab + "@Override\n"
                                       + tab + "public boolean isMark() {\n"
                                       + tab + tab + "return super.isMark();\n"
                                       + tab + "}\n";
                        
                        frm.format (classMarkable);  
                    }
                    
                    frm.format("}");  
                    }
                    catch (Throwable ex)
                        {
                            JInvErrorService.handleException(null, ex);
                        }
                }
    
    
    protected String getShortClsName(String clsFullName) {
        if (clsFullName.compareTo("java.lang.String") == 0) {
            return "String";
        } else if (clsFullName.compareTo("java.lang.Long") == 0) {
            return "Long";
        } else if (clsFullName.compareTo("java.lang.Integer") == 0) {
            return "Integer";
        } else if (clsFullName.compareTo("java.lang.Short") == 0) {
            return "Short";
        } else if (clsFullName.compareTo(Boolean.class.getName()) == 0) {
            return "Boolean";
        } else if (clsFullName.compareTo("java.math.BigDecimal") == 0) {
            return "BigDecimal";
        } else if (clsFullName.compareTo("java.sql.Date") == 0) {
            return "Date";
        } else if (clsFullName.compareTo("java.time.LocalDate") == 0) {
            return "LocalDate";
        } else if (clsFullName.compareTo("java.util.Date") == 0) {
            return "Date";
        } else if (clsFullName.compareTo("java.time.LocalDateTime") == 0) {
            return "LocalDateTime";
        } else if (clsFullName.compareTo("java.sql.Timestamp") == 0) {
            return "Timestamp";
        } else if (clsFullName.compareTo("java.sql.Time") == 0) {
            return "Time";
        } else if (clsFullName.compareTo("java.sql.Blob") == 0) {
            return "Blob";
        } else if (clsFullName.compareTo("java.sql.Clob") == 0) {
            return "Clob";
        } else if (clsFullName.compareTo("oracle.jdbc.OracleBlob") == 0) {
            return "Blob";
        } else if (clsFullName.compareTo("oracle.sql.ROWID") == 0) {
            return "String";    
        } else {
            throw new RuntimeException("Неизвестный тип");
        }

    }

    public void initComments ( TaskContext tc, String table ) throws Exception
    {
        SQLDataSet<PAllColComments> ds = new SQLDataSet<> (tc, PAllColComments.class);
        ds.setWherePredicat ("TABLE_NAME=?");
        ds.setParameter (0, table);
        ds.executeQuery (true);
        
        for ( PAllColComments com : ds.getRows ())
        {
            if (S.isNullOrEmpty (com.getCOLUMN_NAME ()) || S.isNullOrEmpty (com.getCOMMENTS ()))
                continue;
            
            String cn = com.getCOLUMN_NAME ();
            Optional<PColumn> col = columnList.stream ()
                                              .filter (p->cn.equals (p.getColumnName ()))
                                              .findAny ();
            if (col.isPresent ())
                col.get ().setComment (com.getCOMMENTS ());
        }    
    }            
}
