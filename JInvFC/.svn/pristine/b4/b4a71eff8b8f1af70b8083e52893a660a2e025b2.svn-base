/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.inversion.jinvfc.controller;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.*;
import java.util.function.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import ru.inversion.dataset.DataSetException;
import ru.inversion.fx.app.BaseApp;
import ru.inversion.fx.app.es.JInvErrorService;
import ru.inversion.fx.app.property.PropertiesTypeEnum;
import ru.inversion.fx.form.JInvFXBrowserController;
import ru.inversion.jinvfc.entity.PColumn;
import ru.inversion.jinvfc.impl.*;
import ru.inversion.dataset.mark.*;
import ru.inversion.fx.app.property.LOCAL_AppProperties;
import ru.inversion.jinvfc.entity.*;
import javax.persistence.*;
import ru.inversion.dataset.SQLDataSet;
import ru.inversion.db.rs.RSUtils;
import ru.inversion.fx.form.Alerts;
import ru.inversion.utils.S;
import ru.inversion.utils.U;

/**
 * FXML Controller class
 *
 * @author perov
 */
public class JInvMainPaneController extends JInvFXBrowserController {

    /**
     * Initializes the controller class.
     */
    private final Boolean DEF_ON = true;
    private final Boolean DEF_OFF = false;
    private final ResourceBundle bndl = ResourceBundle.getBundle ("bndl");
    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat ("application/x-java-serialized-object");
    
    
    private List<PColumn> columnList;
    @FXML
    private TextArea edQuery;
    @FXML
    private Button btMetadata;
    @FXML
    private Button btFileChoose; 
    @FXML
    private TableView<PColumn> tbl;
    @FXML
    private TableColumn<PColumn, String> columnName;
    @FXML
    private TableColumn<PColumn, String> sqlType;
    @FXML
    private TableColumn<PColumn, String> javaType;
    @FXML
    private TableColumn<PColumn, Boolean> checkOn;
    @FXML
    private TableColumn<PColumn, Boolean> primaryKey;
    @FXML
    private TableColumn<PColumn, String> length;
    @FXML
    private TableColumn<PColumn, String> regexp;
    @FXML
    private TableColumn<PColumn, String> checkColumn;
    @FXML
    private TableColumn<PColumn, Boolean> isNotNull;
    @FXML
    private TableColumn<PColumn, Boolean> isLov;
    
    @FXML
    private TableColumn <PColumn, ColumnPlace> columnPlace;
    
    @FXML
    private Label lPackage;
    @FXML
    private Label lClass;
    @FXML
    private Label lDir;
    @FXML
    private Label lECode;
    @FXML
    private Label lEDesc;
    @FXML
    private TextField edFileChoose;
    @FXML
    private TextField edClass, edTable;
    @FXML
    private TextField edPackage;
    @FXML
    private ComboBox <MarkModeEnum> edMarkMode;
    @FXML
    private ComboBox <PojoAnnotationMode> edPojoAnnotationMode;

    
    private final LOCAL_AppProperties localProp = (LOCAL_AppProperties) BaseApp.APP ().getProperties (PropertiesTypeEnum.LOCAL_USER);

    @Override
    protected void init() throws Exception 
    {
            setTitle(ResourceBundle.getBundle("bndl").getString("APPTITLE"));
                        
            
            lPackage.setText(ResourceBundle.getBundle("bndl").getString("LABEL_PACKAGE"));
            lClass.setText(ResourceBundle.getBundle("bndl").getString("LABEL_CLASS"));
            lDir.setText(ResourceBundle.getBundle("bndl").getString("LABEL_DIR"));
            edFileChoose.setEditable(false);
            
            edQuery.setText (localProp.getStringProperty (localProp.getApp ().getAppID () + "edQuery"));             
            edPackage.setText (localProp.getStringProperty (localProp.getApp ().getAppID () + "edPackage"));             
            edFileChoose.setText (localProp.getStringProperty (localProp.getApp ().getAppID () + "edFileChoose"));
            
            edMarkMode.setItems (FXCollections.observableArrayList (MarkModeEnum.values ()));
            edPojoAnnotationMode.setItems (FXCollections.observableArrayList (PojoAnnotationMode.values ()));
            
            edQuery.setOnKeyReleased (this::onQueryKeyReleased);
            
//            this.populateDataSet(clazz, TARGET_CLASSES, TARGET_CLASSES, TARGET_CLASSES, EXECUTE)
            
  //          edQuery.setText("select * from XD_PROD t");
//            edClass.setText("q");
//            edPackage.setText("a");
//            edFileChoose.setText("d:\\!");

//            p.getStringProperty ("QQ");
  //          p.setProperty("QQ", "!!!");
  
//            localProp.setProperty (localProp.getApp ().getAppID () +"QQ", "!!!!"); 
            
/*
            try {    
                prop.load (new FileInputStream (propFile ()));
            } catch (FileNotFoundException e) {
                LogManager.
            };
*/            
	}   
    
    @FXML
    public void onCancel( ActionEvent ae ) {
               
    }
	
    @FXML
    protected void onInsert(ActionEvent event) {

    }
    
    @FXML
    private void onQueryKeyReleased ( KeyEvent event )
    {
        if (event.getCode () == KeyCode.F8)
        {    
            event.consume ();
            handleQueryMetaData ();
        }
    }            
    
    @FXML
    protected void handleFileChoose(ActionEvent event) {
        
        DirectoryChooser dir = new DirectoryChooser();
        dir.setTitle("Open");
        File f = dir.showDialog(getStage());
        if (f != null)
        {
            edFileChoose.setText(f.getAbsolutePath());
        }
    }
    
    @FXML
    protected void handleLoadClass (ActionEvent event) 
    {
        FileChooser fc = new FileChooser();        
        fc.setTitle("Load");
        
        if (edFileChoose.getText() != null)
        {
            File fi = new File (edFileChoose.getText()).getParentFile().getParentFile();
            if (edPackage.getText () != null)
               fi = new File (fi.getAbsolutePath(), TARGET_CLASSES + edPackage.getText ().replace (".", File.separator)); 
            
            fc.setInitialDirectory (fi);
        }   
        
        File clf = fc.showOpenDialog (getStage());
        if (clf != null)
        {
            Class cl = null;
            
            String fp = clf.getAbsolutePath ();
            int i = fp.indexOf (TARGET_CLASSES);
            String clname = fp.substring (i + TARGET_CLASSES.length ()).replace (".class", "").replace ("\\", ".");
            
            try {            
                URLClassLoader ldr = new URLClassLoader (new URL[] {new File (fp.substring (0, i+TARGET_CLASSES.length())).toURI().toURL ()});
                cl = ldr.loadClass (clname);
            } catch (Exception ex) {
                JInvErrorService.handleException (getStage (), ex);
            }
            
            StringBuilder sb = new StringBuilder ();
            for (Method m : cl.getMethods ())
                if ((m.getModifiers () & Modifier.STATIC) != 0)
                    sb.append (m.toString ())
                      .append ("\n");
            
            if (sb.length () != 0)
                Alerts.info (getStage (), cl.getCanonicalName (), sb.toString ());
            
            if (cl != null)
            {
   	        String sql = null;
                String tab = null;
                
                NamedNativeQuery nnq = (NamedNativeQuery) cl.getAnnotation (NamedNativeQuery.class);
	        if (nnq != null) 
                    sql = nnq.query ();                
                else
                {
                    Table table = (Table) cl.getAnnotation (Table.class);
                    if (table != null)
                    {    
                        tab = table.name ();
                        sql = Arrays.asList (cl.getMethods ())
                                .stream ()
                                .filter ((m)->!m.isAnnotationPresent (Transient.class))
                                .filter ((Method m) -> {
                                    Column c = m.getAnnotation (Column.class);
                                    return c != null && !c.name ().isEmpty ();
                                })
                                .map ((m)->m.getAnnotation (Column.class).name() )
                                .collect (Collectors.joining (",", "SELECT ", " FROM " + table.name ()) );                            
                    }    
                }
                
                if (sql == null)
                    Alerts.error (getStage (), bundle.getString ("NO_CLASS_ANNOTATIONS") + " " + cl.getName());
                else
                {
                    Alerts.info (getStage (), bundle.getString ("SUCCESS_CLASS_LOAD") + " " + cl.getName());
                    edPackage.setText (cl.getPackage ().getName ());
                    edQuery.setText (sql);
                    
                    edTable.setText (nnq == null ? tab : ""); 
                    edPojoAnnotationMode.setValue (nnq == null ? PojoAnnotationMode.TABLE : PojoAnnotationMode.NAMEDNATIVEQUERY);
                }    
            }    
        }    
    }

    
    @FXML
    private void handleRefresh()
    {
        //tbl.refresh ();
    }    

    @FXML
    private void handleWhereUps ()
    {
        
        String sql = edQuery.getText ();
        if (sql == null || sql.length () == 0) {
            throw new RuntimeException (ResourceBundle.getBundle ("bndl").getString ("REQUEST_ERROR"));
        }
        
        Iterable<PUps> iter;
        
        try {
            iter = RSUtils.createIterable (getTaskContext ().getConnection (), PUps.class, sql, null);
        } catch ( Exception ex ) {
            handleException (ex);
            return;
        }   
        
        if (S.isNullOrEmpty (edClass.getText ()))
            edClass.setText ("POptions");
        
        columnList = new ArrayList<> ();
        
        iter.forEach (pref->{
            
            if (S.isNullOrEmpty (pref.getCUPSPREF ()))
                throw new RuntimeException ("CUPSPREF IS NULL");
            
            String cn = pref.getCUPSPREF ().replaceAll (".*\\.", "");
            
            PColumn p = new PColumn (cn, "VARCHAR2", "java.lang.String");
            p.setLength ("");
            p.setPlace (ColumnPlace.POJO);
            p.setUpsPrefName (pref.getCUPSPREF ());
        
            columnList.add (p);
        });
        
        
        /*
                columnList.add( new PColumn( metaData.getColumnName(i), 
                                             metaData.getColumnTypeName(i), 
                                             javaClassName,
                                             DEF_ON, // включено
                                             p.isPresent (), // первичный ключ
                                             length, // длинна
                                             null,//regexp
                                             null, //check
                                             metaData.isNullable(i)==0, //not null
                                             ColumnPlace.TABLE, // куда включить
                                             false, // isLov
                                             metaData.getScale(i) // точность
                ) );
        */        
                
        initTable ();
        tbl.getItems ().addAll (columnList);
        
        
    }    
    
    @FXML
    private void onPlsql ()
    {
        if (U.in (edFileChoose.getText (), null, ""))
            return;
        
        Map m = new HashMap ();
        File dir = new File (edFileChoose.getText (), "resources/" + edPackage.getText().replace (".", "/") + "/plsql");                
        if (! dir.exists ())
            dir.mkdirs ();
        
        File def = new File (dir, "def.xml");

        if (!def.exists ())
        {
            FileChooser fc = new FileChooser();        
            fc.setTitle("Load PL/SQL def file");
            fc.setInitialDirectory (dir);
            def = fc.showOpenDialog (getStage ());
            
            if (def != null)
                dir = def.getParentFile ();
        }    

        m.put (JInvPlsqlPaneController.PLSQL_DIR, dir);
        m.put (JInvPlsqlPaneController.PLSQL_FILE, def);
        
	try {
            String fxml = "fxml/JInvPlsqlPane.fxml";                    
	    JInvFXBrowserController.show (getTaskContext (), getViewContext (), fxml, true, ResourceBundle.getBundle ("JInvPlsqlPane"), m, null);
	}
	catch (Throwable ex) {
	    JInvErrorService.handleException(null, ex);
	}
//        Alerts.error(this, def.toURI().toURL().toString());
        
//        URL u = getClass ().getResource ("plsql/def.xml");
        
//        PlsqlDefLoader dl = new PlsqlDefLoader (u);
        
//        PlsqlDefLoader.PlsqlDef def = dl.getDef ("UF.Load_PacketEPD11");
//        PlsqlDefLoader.PlsqlDef def = dl.getDef ("Pref.SetPU");
        
//        System.out.println  ("SQL=" + def.getSql ());
//        System.out.println  ("Param count=" + def.getParam().getLength());
/*
try
{
if (checkParameters (true))
{
String clName = edClass.getText();
File fid = new File (edFileChoose.getText (), "resources/" + edPackage.getText().replace (".", "/") + "/plsql");
File fis = new File (fid, clName + "save.sql");

if (util.alertYesNo (fis.getAbsolutePath (), getAlertContent (fis), bndl.getString ("SAVE_POJO")))
{
SaveWork w = new SaveWork (columnList, edPackage.getText(), clName, edTable.getText());
w.doWork (fis);
}                    

File fil = new File (fid, clName + "load.sql");

if (util.alertYesNo (fil.getAbsolutePath (), getAlertContent (fil), bndl.getString ("LOAD_POJO")))
{
LoadWork w = new LoadWork (columnList, edPackage.getText(), clName, edTable.getText());
w.doWork (fil);
}

}
}
catch (RuntimeException ex)
{
JInvErrorService.handleException(null, ex);
}
            */      
    }    
    
    
    @FXML
    private void handleQueryMetaData()
    {
        List <PAllConsColumns> listPK = null;
        try 
        {
            String sql = edQuery.getText();
            if (sql == null || sql.length() == 0) {
                throw new RuntimeException(ResourceBundle.getBundle("bndl").getString("REQUEST_ERROR"));
            }
            
            String tab = sql.replaceFirst ("(?si-).*from\\s+", "")
                            .replaceFirst("(?si-)\\s+where\\s+.*", "")
                            .replaceFirst("(?si-)\\s+order\\s+by\\s+.*", "")
                            .replaceAll ("\\W", "").toUpperCase();
            
            edTable.setText (tab);

            listPK = this.<PAllConsColumns> populateDataSet (PAllConsColumns.class, "", "", "1, 2",  Arrays.asList (tab), EXECUTE_AND_FETCH).getRows ();
            
            StringBuilder cl = new StringBuilder ("P").append (tab.substring(0, 1)).append (tab.substring (1).toLowerCase ());
            int l = 0;
            while ((l = cl.indexOf ("_", l + 1)) >= 0)
                if ( l < cl.length () - 1) 
                   cl.deleteCharAt (l).replace (l, l+1, cl.substring (l, l+1).toUpperCase ());
            
            edClass.setText (cl.toString ());
            edMarkMode.setValue (MarkModeEnum.NONE);
            edPojoAnnotationMode.setValue(PojoAnnotationMode.TABLE);
            
            PreparedStatement ps = getTaskContext().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
                       
            int columnCount = rs.getMetaData().getColumnCount();
            
            ResultSetMetaData metaData = rs.getMetaData();

            columnList = new ArrayList<>();
            
            for (int i = 1; i <= columnCount; i++) {
                
                String length = null;                
                if ("NUMBER".equals(metaData.getColumnTypeName(i)) ||
                    "VARCHAR2".equals(metaData.getColumnTypeName(i)) ||
                    "CHAR".equals(metaData.getColumnTypeName(i)) ||
                    "NVARCHAR2".equals(metaData.getColumnTypeName(i)) )
                {
                    length = Integer.toString (metaData.getPrecision (i)) ;
                }
                
                String javaClassName = metaData.getColumnClassName(i);
                if ("NUMBER".equals(metaData.getColumnTypeName(i)) && metaData.getScale(i) == 0)
                   javaClassName = "java.lang.Long";
                
                if ("DATE".equals(metaData.getColumnTypeName(i)))
                   javaClassName = "java.time.LocalDate";

                if ("ROWID".equals(metaData.getColumnTypeName(i)))
                   javaClassName = "java.lang.String";
                
                String cn = metaData.getColumnName (i);
                Optional <PAllConsColumns> p = Optional.empty();
                    
                if (listPK != null)
                   p = listPK.stream ().filter (it -> it.getCOLUMN_NAME ().equals (cn)).findFirst ();

                columnList.add( new PColumn( metaData.getColumnName(i), 
                                             metaData.getColumnTypeName(i), 
                                             javaClassName,
                                             DEF_ON, // включено
                                             p.isPresent (), // первичный ключ
                                             length, // длинна
                                             null,//regexp
                                             null, //check
                                             metaData.isNullable(i)==0, //not null
                                             ColumnPlace.TABLE, // куда включить
                                             false, // isLov
                                             metaData.getScale(i) // точность
                ) );
            }
            
            initTable();
                      
            tbl.getItems().addAll(columnList);
            
        } catch (RuntimeException | SQLException | DataSetException ex)
        {
            JInvErrorService.handleException (null, ex);
        }
    }
    
    private String getAlertContent ( File fi )
    {        
        return fi.exists () ? bndl.getString ("FILE_EXISTS") + new Date (fi.lastModified ()).toString () : "";
    }    
    
    @FXML
    private void onPojo ()
    {
        try
        {
            if (checkParameters (false))
            {
                File fi = new File (edFileChoose.getText (), "java/" + edPackage.getText ().replaceAll ("\\.", "/"));                
                fi = new File (fi, edClass.getText () + ".java");                    
                
                if (util.alertYesNo (fi.getAbsolutePath (), getAlertContent (fi), bndl.getString ("CREATE_POJO")))                
                {    
                    PojoWork w = new PojoWork (columnList, edPackage.getText (), edClass.getText (), 
                                               edTable.getText (),  
                                               edMarkMode.getValue (), edPojoAnnotationMode.getValue (), edQuery.getText ());

                    TextInputDialog dialog = new TextInputDialog (edTable.getText ());
                    dialog.setTitle (bndl.getString ("CREATE_POJO"));
                    dialog.setHeaderText (fi.getAbsolutePath ());
                    dialog.setContentText (bndl.getString ("COMMENT_POJO"));    
                    Optional<String> commentsTable = dialog.showAndWait ();                

                    if (commentsTable.isPresent ())
                        try {
                            w.initComments (getTaskContext (), commentsTable.get ());
                        } catch ( Exception ex ) {
                            handleException (ex);
                        }   
                    
                    w.doWork (fi);
                }      
                
                if (edPojoAnnotationMode.getValue () == PojoAnnotationMode.NAMEDNATIVEQUERY)
                {
                    fi = new File (edFileChoose.getText (), "resources/" + edPackage.getText().replace (".", "/") + "/res");                                    
                    fi = new File (fi, edClass.getText () + ".properties");                    

                    if (util.alertYesNo (fi.getAbsolutePath (), getAlertContent (fi), bndl.getString ("CREATE_POJO_PROP")))                
                    {    
                        PojoPropWork pw = new PojoPropWork (columnList);
                        pw.doWork (fi);
                    }                    
                }    
            } 
        } 
        catch (RuntimeException ex)
        {
             JInvErrorService.handleException(null, ex);
        }
    }
    
    @FXML
    private void onViewScene ()
    {
        try
        {
            if (checkParameters (true))
            {
                String clName = edClass.getText().substring (1);
                File fi = new File (edFileChoose.getText (), "resources/" + edPackage.getText().replace (".", "/") + "/fxml");                
                fi = new File (fi, "View" +  clName + ".fxml");                    

                if (util.alertYesNo (fi.getAbsolutePath (), getAlertContent (fi), bndl.getString ("CREATE_VIEW")))                
                {    
                    ViewWork w = new ViewWork (columnList, edPackage.getText(), clName, edTable.getText(), edMarkMode.getValue());
                    w.doWork (fi);
                }                
            } 
        } 
        catch (RuntimeException ex)
        {
             JInvErrorService.handleException(null, ex);
        }
    }

    @FXML
    private void onEditScene ()
    {
        try
        {
            if (checkParameters (true))
            {
                String clName = edClass.getText().substring (1);
                File fi = new File (edFileChoose.getText (), "resources/" + edPackage.getText().replace (".", "/") + "/fxml");                
                fi = new File (fi, "Edit" +  clName + ".fxml");                    

                if (util.alertYesNo (fi.getAbsolutePath (), getAlertContent (fi), bndl.getString ("CREATE_EDIT")))                
                {    
                    EditWork w = new EditWork (columnList, edPackage.getText(), clName, edTable.getText());
                    w.doWork (fi);
                }                
            } 
        } 
        catch (RuntimeException ex)
        {
             JInvErrorService.handleException(null, ex);
        }
    }
    
    @FXML
    private void onViewController ()
    {
        try
        {
            if (checkParameters (false))
            {
                String clName = edClass.getText ().substring (1);
                File fi = new File (edFileChoose.getText (), "java/" + edPackage.getText ().replaceAll ("\\.", "/"));                
                fi = new File (fi, "View" + clName + "Controller.java");                    

                if (util.alertYesNo (fi.getAbsolutePath (), getAlertContent (fi), bndl.getString ("CREATE_VIEWCONTROLLER")))                
                {    
                    ViewControllerWork w = new ViewControllerWork (columnList, edPackage.getText(), edClass.getText (), edTable.getText (), edMarkMode.getValue());
                    w.doWork (fi);
                }                
            } 
        } 
        catch (RuntimeException ex)
        {
             JInvErrorService.handleException(null, ex);
        }
    }    

    @FXML
    private void onEditController ()
    {
        try
        {
            if (checkParameters (false))
            {
                String clName = edClass.getText ().substring (1);
                File fi = new File (edFileChoose.getText (), "java/" + edPackage.getText ().replaceAll ("\\.", "/"));                
                fi = new File (fi, "Edit" + clName + "Controller.java");                    

                if (util.alertYesNo (fi.getAbsolutePath (), getAlertContent (fi), bndl.getString ("CREATE_EDITCONTROLLER")))                
                {    
                    EditControllerWork w = new EditControllerWork (columnList, edPackage.getText(), edClass.getText (), edTable.getText ());
                    w.doWork (fi);
                }                
            } 
        } 
        catch (RuntimeException ex)
        {
             JInvErrorService.handleException(null, ex);
        }
    }    
    
    @FXML
    private void onMainClass ()
    {
        try
        {
            if (checkParameters (false))
            {
                String clName = edClass.getText ();
                File fi = new File (edFileChoose.getText (), "java/" + edPackage.getText ().replaceAll ("\\.", "/"));                
                fi = new File (fi, edClass.getText() + "Main.java");                    

                if (util.alertYesNo (fi.getAbsolutePath (), getAlertContent (fi), bndl.getString ("CREATE_MAIN")))                
                {    
                    MainWork mw = new MainWork (edPackage.getText(), clName, edTable.getText ());
                    mw.doWork (fi);
                }                
            } 
        } 
        catch (RuntimeException ex)
        {
             JInvErrorService.handleException(null, ex);
        }
    }
    
    
    private boolean checkParameters ( boolean checkTable ) throws RuntimeException
        {
            boolean result = false;
            if (columnList == null)
            {
               throw new RuntimeException(ResourceBundle.getBundle("bndl").getString("ERROR_NO_DATA"));
            }
            
            if(!checkPK())
            {
                tbl.requestFocus();
                throw new RuntimeException(ResourceBundle.getBundle("bndl").getString("PK_ERROR"));
            }
            
            if (edPackage.getText()==null || edPackage.getText().length()==0){
                edPackage.requestFocus();
                throw new RuntimeException(ResourceBundle.getBundle("bndl").getString("PACKAGE_ERROR"));
            }
            if (edClass.getText()==null || edClass.getText().length()==0){
                edClass.requestFocus();
                throw new RuntimeException(ResourceBundle.getBundle("bndl").getString("CLASS_ERROR"));
            }
            if (edFileChoose.getText() == null || edFileChoose.getText().length() == 0) {
                edFileChoose.requestFocus();
                throw new RuntimeException(ResourceBundle.getBundle("bndl").getString("FOLDER_ERROR"));
            }
            
            if (checkTable && (edTable.getText()==null || edTable.getText().length()==0)) {    
                edTable.requestFocus();
                throw new RuntimeException (bndl.getString ("TABLE_ERROR"));
            }
            
            localProp.setProperty (localProp.getApp ().getAppID () +"edQuery", edQuery.getText ());             
            localProp.setProperty (localProp.getApp ().getAppID () +"edPackage", edPackage.getText ());             
            localProp.setProperty (localProp.getApp ().getAppID () +"edFileChoose", edFileChoose.getText ());             
            
            for (PColumn p : columnList)
            {
                    //Длинна
                    if (p.getLength()!=null && p.getLength().length()>0)
                    {
                        int value = new Integer(p.getLength());
                        if (value < 0) {
                                throw new RuntimeException(ResourceBundle.getBundle("bndl").getString("ERROR_BAD_LENGTH") + "  "+p.getColumnName()+": длинна = "+ p.getLength());
                        }                        
                    }
                    
                    //Диапазон
                    if (p.getCheckColumn()!=null && !p.getCheckColumn().equals(""))
                    {
                        Pattern pattern = Pattern.compile("^\\d+\\s*-\\s*\\d+$");
                        Matcher matcher = pattern.matcher(p.getCheckColumn());
                        if (!matcher.find() /*&& matcher.group(0) == null*/) {
                                throw new RuntimeException(ResourceBundle.getBundle("bndl").getString("ERROR_BAD_RANGE") + "  "+p.getColumnName()+": диапазон = "+ p.getCheckColumn());
                        }
                    }
                    
                    if (p.getRegexp() != null && p.getRegexp().equals("")) {

                        try {
                            Pattern.compile(p.getRegexp());
                        } catch (PatternSyntaxException ex) {
                            throw new RuntimeException(ResourceBundle.getBundle("bndl").getString("REGEXP_ERROR") + "  " + p.getColumnName()+": regExp = "+ p.getRegexp());
                        }
                    }                   
            }
            
            result = true;
            return result; 
        }
    
     void initTable()
     {
        tbl.getItems().clear();//чистим таблицу
        tbl.setEditable(true); // разрешаем редактирование
         
        columnName.setCellValueFactory((TableColumn.CellDataFeatures<PColumn, String> param) -> new SimpleStringProperty(param.getValue().getColumnName()));       
        sqlType.setCellValueFactory((TableColumn.CellDataFeatures<PColumn, String> param) -> new SimpleStringProperty(param.getValue().getSqlType()));       
        
        javaType.setCellValueFactory((TableColumn.CellDataFeatures<PColumn, String> param) -> new SimpleStringProperty(param.getValue().getJavaType()));         
        javaType.setCellFactory(new Callback<TableColumn<PColumn, String>, TableCell<PColumn, String>>() {

            @Override
            public TableCell<PColumn, String> call(TableColumn<PColumn, String> param) {
                return new ComboBoxCell(); 
            }
        });
/*
        checkOn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PColumn, Boolean>, ObservableValue<Boolean>>() {                
                @Override
                public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<PColumn, Boolean> param) {
                        
                    return  new SimpleBooleanProperty(param.getValue().getCheckOn());
                }
            });
            
        checkOn.setCellFactory( new Callback<TableColumn<PColumn, Boolean>, TableCell<PColumn, Boolean>>() {

             @Override
             public TableCell<PColumn, Boolean> call(TableColumn<PColumn, Boolean> param) {
                    return new CheckboxCell(param, "checkOn");             
             }
            });
*/            
        primaryKey.setCellValueFactory ((TableColumn.CellDataFeatures<PColumn, Boolean> param) -> new SimpleBooleanProperty (param.getValue().getPrimaryKey()));
        primaryKey.setCellFactory ((TableColumn<PColumn, Boolean> param) -> new CheckboxCell (param, PColumn::setPrimaryKey));

        isLov.setCellValueFactory ((TableColumn.CellDataFeatures<PColumn, Boolean> param) -> new SimpleBooleanProperty (param.getValue().getIsLov()));
        isLov.setCellFactory ((TableColumn<PColumn, Boolean> param) -> new CheckboxCell (param, PColumn::setIsLov));        
        
        length.setCellValueFactory((TableColumn.CellDataFeatures<PColumn, String> param) -> new SimpleStringProperty(param.getValue().getLength()));
        length.setCellFactory(TextFieldTableCell.forTableColumn());
        length.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<PColumn, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<PColumn, String> event) {
                PColumn p = event.getTableView().getItems().get(event.getTablePosition().getRow());
                try
                {
                    if (event.getNewValue()!= null && event.getNewValue().length()>0) {
                        int value = new Integer(event.getNewValue());
                    }                   
                    p.setLength(event.getNewValue());
                }
                catch (NumberFormatException ex)
                {
                    p.setLength(event.getOldValue());
                    JInvErrorService.handleException(null, ex);                    
                    length.setVisible(false);
                    length.setVisible(true);
                }
                
                                   
            }
            });
            
        regexp.setCellValueFactory((TableColumn.CellDataFeatures<PColumn, String> param) -> new SimpleStringProperty(param.getValue().getRegexp()));
        regexp.setCellFactory(TextFieldTableCell.forTableColumn());
        regexp.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<PColumn, String>>() {

             @Override
             public void handle(TableColumn.CellEditEvent<PColumn, String> event) {
                PColumn p = event.getTableView().getItems().get(event.getTablePosition().getRow());      
                try {
                        Pattern.compile(event.getNewValue());
                        p.setRegexp(event.getNewValue());
                    } catch (PatternSyntaxException ex) {
                            throw new RuntimeException(ResourceBundle.getBundle("bndl").getString("REGEXP_ERROR") + "  " + p.getColumnName()+": regExp = "+ event.getNewValue());
                    }   
                    
             }
            });
            
        checkColumn.setCellValueFactory((TableColumn.CellDataFeatures<PColumn, String> param) -> new SimpleStringProperty(param.getValue().getCheckColumn()));
        checkColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        checkColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<PColumn, String>>() {

             @Override
            public void handle(TableColumn.CellEditEvent<PColumn, String> event) {
                 PColumn p = event.getTableView().getItems().get(event.getTablePosition().getRow());
                 try
                 {
                    Pattern pattern = Pattern.compile("^\\d+\\s*-\\s*\\d+$");
                    Matcher matcher = pattern.matcher(event.getNewValue());
                    if (!matcher.find()) {
                                   throw new RuntimeException(ResourceBundle.getBundle("bndl").getString("ERROR_BAD_RANGE") + "  "+p.getColumnName()+": диапазон = "+ event.getNewValue());
                    }
                    p.setCheckColumn(event.getNewValue());
                 }
                 catch (RuntimeException ex)
                 {
                    JInvErrorService.handleException(null, ex);                    
                    checkColumn.setVisible(false);
                    checkColumn.setVisible(true);
                 }
                 
            }
            });
            
        isNotNull.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PColumn, Boolean>, ObservableValue<Boolean>>() {
                
                @Override
                public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<PColumn, Boolean> param) {
                        
                    return  new SimpleBooleanProperty(param.getValue().getIsNotNull());
                }
            });
        isNotNull.setCellFactory( new Callback<TableColumn<PColumn, Boolean>, TableCell<PColumn, Boolean>>() {

             @Override
             public TableCell<PColumn, Boolean> call(TableColumn<PColumn, Boolean> param) {
                    
                    return new CheckboxCell (param, PColumn::setIsNotNull);             
             }
            });
        
//        columnPlace.setGraphic (new ComboBox());
        columnPlace.setCellValueFactory (cellData -> new SimpleObjectProperty (cellData.getValue().getPlace()));        
        columnPlace.setCellFactory(new Callback<TableColumn<PColumn, ColumnPlace>, TableCell<PColumn, ColumnPlace>>() 
        {
            @Override
            public TableCell<PColumn, ColumnPlace> call(TableColumn<PColumn, ColumnPlace> param) {
                return new ComboBoxCell2 <ColumnPlace> (FXCollections.observableArrayList (ColumnPlace.values ()), PColumn::setPlace); 
            }
        });

        
            
            
//            ComboBoxTableCell.forTableColumn (FXCollections.observableArrayList (ColumnPlace.values ())));
//        columnPlace.    
            
//
// DnD
//
        tbl.setRowFactory(tv -> {
            TableRow<PColumn> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (! row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                }
                event.consume();
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != ((Integer)db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                }
                event.consume();
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent (SERIALIZED_MIME_TYPE);                    

                    tbl.getItems().clear();                   
                    PColumn draggedColumn = columnList.remove (draggedIndex);
                    
                    int dropIndex = row.isEmpty () ? columnList.size () : row.getIndex ();
                    
                    columnList.add (dropIndex, draggedColumn);
                    tbl.getItems ().addAll (columnList);
                    tbl.getSelectionModel ().select (dropIndex);                    
                    //tbl.refresh ();           
/*                    
                    int draggedIndex = (Integer) db.getContent (SERIALIZED_MIME_TYPE);                    
                    PColumn draggedColumn = tbl.getItems().remove(draggedIndex);

                    int dropIndex ; 

                    if (row.isEmpty()) {
                        dropIndex = tbl.getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }

                    tbl.getItems().clear();
                    
                    tbl.getItems ().add (dropIndex, draggedColumn);
                    tbl.getSelectionModel ().select (dropIndex);
                    tbl.refresh();           
*/
                    event.setDropCompleted (true);
                }
                event.consume ();
            });

            return row ;
        });

        
     }

    boolean checkPK ()
    {
        for(PColumn p : columnList)
        {
            if (p.getPrimaryKey())
            {
                return true;
            }            
        }
        return false;
    }
     
     
     class CheckboxCell extends TableCell<PColumn, Boolean> 
     {
        private final CheckBox checkBox ;

        private CheckboxCell (TableColumn<PColumn, Boolean> param, BiConsumer <PColumn, Boolean> setter) 
        {
             this.checkBox = new CheckBox() ;
             checkBox.setOnAction (new EventHandler<ActionEvent>() 
             {
                 @Override
                 public void handle(ActionEvent event) 
                 {
                     if (getTableRow()!=null && getTableRow ().getIndex ()!=-1 && getTableRow ().getIndex ()<columnList.size ())
                     {
                        PColumn p = columnList.get (getTableRow ().getIndex ());
                        setter.accept (p, checkBox.isSelected ());
                     }
                 }
             });
             
        }
        
        @Override
        protected void updateItem (Boolean item, boolean empty) 
        {
            super.updateItem (item, empty);
            if (empty) 
               setGraphic (null);
            else 
            {
                setGraphic (checkBox);
                setAlignment (Pos.CENTER);
                checkBox.setSelected (item);
            }
        }
            
    }
    
     
    class ComboBoxCell extends TableCell<PColumn, String>
    {
        private ComboBox<String> comboBox;
        private ComboBoxCell() {
            comboBox = new ComboBox();
            comboBox.getItems().addAll("java.lang.String", "java.lang.Long", /*"java.lang.Integer", "java.lang.Short",*/ "java.math.BigDecimal",
                "java.time.LocalDate", "java.time.LocalDateTime",     
                /*"java.util.Date", "java.sql.Date", "java.lang.Boolean", "java.sql.Timestamp", "java.sql.Time",*/ "java.sql.Blob", "java.sql.Clob"/*, "oracle.jdbc.OracleBlob"*/);
            comboBox.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                     //System.out.println("Committed: " + comboBox.getSelectionModel().getSelectedItem());
                     if (getTableRow()!=null && getTableRow().getIndex()!=-1 && getTableRow().getIndex()<columnList.size())
                     {
                      PColumn p = columnList.get(getTableRow().getIndex());
                      p.setJavaType(comboBox.getSelectionModel().getSelectedItem());
                     }
                }
            });
        }
        
        
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
            setText(null);
            setGraphic(null);
            } 
            else {
                
                    if (comboBox!=null)
                    {
                       comboBox.setValue(item); 
                    }
                    setGraphic(comboBox);
               
            }
        }

    }

    class ComboBoxCell2 <T> extends TableCell<PColumn, T>
    {
        private final ComboBox <T> comboBox;
        
        private ComboBoxCell2 ( ObservableList <T> list, BiConsumer <PColumn, T> setter ) 
        {
            comboBox = new ComboBox ();
            comboBox.setItems (list);

            comboBox.setOnAction (new EventHandler<ActionEvent>() 
            {
                @Override
                public void handle (ActionEvent event) 
                {
                     if (getTableRow()!=null && getTableRow().getIndex()!=-1 && getTableRow().getIndex()<columnList.size())
                     {
                        PColumn p = columnList.get (getTableRow ().getIndex ());
                        setter.accept (p, comboBox.getSelectionModel ().getSelectedItem ());
                     }                     
                }
            });            
        }    
        
        @Override
        public void updateItem (T item, boolean empty) 
        {
            super.updateItem (item, empty);
            if (empty) {
               setText (null);
               setGraphic (null);
            } else {
               comboBox.setValue (item); 
               setGraphic (comboBox);
            }
        }
    }    
    
    private final String TARGET_CLASSES = "target\\classes\\";
}
