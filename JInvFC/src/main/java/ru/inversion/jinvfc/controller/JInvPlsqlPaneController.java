/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.inversion.jinvfc.controller;

import java.io.File;
import java.net.MalformedURLException;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import ru.inversion.dataset.ArrayDataSet;
import ru.inversion.dataset.DataSetNavigationEvent;
import ru.inversion.dataset.IDataSet;
import ru.inversion.dataset.fx.DSFXAdapter;
import ru.inversion.fx.form.ActionFactory;
import ru.inversion.fx.form.Alerts;
import ru.inversion.fx.form.JInvFXBrowserController;
import ru.inversion.fx.form.JInvFXDialogController;
import ru.inversion.fx.form.JInvFXEntityController;
import ru.inversion.fx.form.controls.JInvTable;
import ru.inversion.fx.form.controls.JInvTextArea;
import ru.inversion.fx.form.controls.JInvToolBar;
import ru.inversion.jinvfc.entity.Pparam;
import ru.inversion.jinvfc.entity.Pproc;

/**
 *
 * @author psh
 */
public class JInvPlsqlPaneController extends JInvFXBrowserController 
{
    
    @FXML
    private JInvTable <Pproc> tabProc;

    @FXML
    private JInvTable <Pparam> tabParam;
    
    @FXML
    private JInvToolBar toolBar;
    
    @FXML
    private JInvTextArea script;
    
    @Override
    protected void init () 
        throws Exception 
    {
        setTitle (getBundleString ("TITLE") + " " + getInitProperties ().get (PLSQL_DIR));
        
        ArrayDataSet <Pproc> ds = new ArrayDataSet <> ();
        ds.setRowClass (Pproc.class);
        ds.addNavigationListener (this::tabProcNavigate);
        
        DSFXAdapter <Pproc> dsfx = DSFXAdapter.bind (ds, tabProc, null, false); 
        dsfx.bindControl (script, Pproc::getScript);

        tabProc.setToolBar (toolBar);
        tabProc.setAction (ActionFactory.ActionTypeEnum.CREATE, e->doProcOperation (JInvFXDialogController.DialogModeEnum.VM_INS));
        tabProc.setAction (ActionFactory.ActionTypeEnum.VIEW, e->doProcOperation (JInvFXDialogController.DialogModeEnum.VM_SHOW));
        tabProc.setAction (ActionFactory.ActionTypeEnum.UPDATE, e->doProcOperation (JInvFXDialogController.DialogModeEnum.VM_EDIT));
        tabProc.setAction (ActionFactory.ActionTypeEnum.DELETE, e->doProcOperation (JInvFXDialogController.DialogModeEnum.VM_DEL));
        
        ArrayDataSet <Pparam> dsp = new ArrayDataSet <> ();
        dsp.setRowClass (Pparam.class);

        DSFXAdapter <Pparam> dsfxp = DSFXAdapter.bind (dsp, tabParam, null, false); 
        tabParam.setToolBar (toolBar);
        tabParam.setAction (ActionFactory.ActionTypeEnum.CREATE, e->doParamOperation (JInvFXDialogController.DialogModeEnum.VM_INS));
        tabParam.setAction (ActionFactory.ActionTypeEnum.VIEW, e->doParamOperation (JInvFXDialogController.DialogModeEnum.VM_SHOW));
        tabParam.setAction (ActionFactory.ActionTypeEnum.UPDATE, e->doParamOperation (JInvFXDialogController.DialogModeEnum.VM_EDIT));
        tabParam.setAction (ActionFactory.ActionTypeEnum.DELETE, e->doParamOperation (JInvFXDialogController.DialogModeEnum.VM_DEL));
        
        initToolBar ();
        
        if (getInitProperties ().containsKey (PLSQL_FILE))
            loadFile ((File) getInitProperties ().get (PLSQL_FILE));
        
        tabProc.requestFocus ();
    }     
//
//
//    
    private void initToolBar () 
    {
        VBox.setMargin (toolBar, new Insets (0, -5, 0, -5));        
        toolBar.setStandartActions (ActionFactory.ActionTypeEnum.CREATE,
                                    ActionFactory.ActionTypeEnum.VIEW,
                                    ActionFactory.ActionTypeEnum.UPDATE,
                                    ActionFactory.ActionTypeEnum.DELETE);
        toolBar.setMultipleUse (true);
//        toolBar.getItems ().add (ActionFactory.createButton(ActionFactory.ActionTypeEnum.SETTINGS, (a) -> JInvMainFrame.showSettingsPane ()));
    }
    
    private void tabProcNavigate ( DataSetNavigationEvent ev )
    {
        IDataSet dsp = tabParam.getDataSetAdapter ().getDataSet ();
        dsp.clear ();
        
        Pproc p = (Pproc) ev.getNewRow ();
        if (p != null)
            dsp.insertRows (p.getParam (), IDataSet.InsertRowModeEnum.FIRST, false);
    }        
    
    @FXML
    private void onExit ()
    {
        close ();
    }        
 
    private void loadFile ( File def ) throws MalformedURLException    
    {
        if (def == null)
            return;
        
        if (! def.exists ())
        {    
            Alerts.error (this, getBundleString ("FILE_NOT_FOUND"), def.getAbsolutePath ());
            return;
        }
        
        IDataSet ds = tabProc.getDataSetAdapter ().getDataSet ();
        
        PlsqlDefLoader dl = new PlsqlDefLoader (def.toURI ().toURL ());
        ds.insertRows (dl.getProcList (), IDataSet.InsertRowModeEnum.LAST, false);
        
        tabProc.getSelectionModel ().selectFirst ();
    }        
//
//
//    
    private void doProcOperation (JInvFXEntityController.DialogModeEnum mode) {

        Pproc p = null;

        switch (mode) {
            case VM_INS:
                p = new Pproc ();
                break;
            case VM_EDIT:
            case VM_SHOW:
            case VM_DEL:
                p = (Pproc) tabProc.getDataSetAdapter ().getDataSet ().getCurrentRow ();
                break;
        }
        
        if (p != null) 
            JInvFXEntityController.doModal (getTaskContext (), getViewContext (), p, "fxml/EditProc.fxml", bundle, mode, getInitProperties (), (ok, ctrl) -> doProcOperationResult (ok, ctrl));
    }
//
//
//    
    private void doParamOperation (JInvFXEntityController.DialogModeEnum mode) 
    {
        if (tabProc.getDataSetAdapter ().getCurRow () == null)
        {
            Alerts.error (this, getBundleString ("NO_TABPROC_RECORD"));
            return;
        }    
        
        Pparam p = null;

        switch (mode) {
            case VM_INS:
                p = new Pparam ();
                break;
            case VM_EDIT:
            case VM_SHOW:
            case VM_DEL:
                p = (Pparam) tabParam.getDataSetAdapter ().getDataSet ().getCurrentRow ();
                break;
        }
        
        if (p != null) 
            JInvFXEntityController.doModal (getTaskContext (), getViewContext (), p, "fxml/EditParam.fxml", bundle, mode, getInitProperties (), (ok, ctrl) -> doParamOperationResult (ok, ctrl));
    }
//
//
//    
    public static final String PLSQL_DIR = "PLSQL_DIR";
    public static final String PLSQL_FILE = "PLSQL_FILE";
//
//
//    
    private void doProcOperationResult (JInvFXDialogController.DialogReturnEnum ok, JInvFXDialogController ctrl) 
    {
//        throw new UnsupportedOperationException ("Not supported yet."); 
    }
//
//
//    
    private void doParamOperationResult (JInvFXDialogController.DialogReturnEnum ok, JInvFXDialogController <Pparam> ctrl) 
    {
        if (JInvFXDialogController.DialogReturnEnum.RET_OK == ok)
        {
            Pproc proc = (Pproc) tabProc.getDataSetAdapter ().getCurRow ();
            Pparam param = ctrl.getDataObject ();
            int curr = tabParam.getDataSetAdapter ().getDataSet ().getCurrentRowNum ();
            IDataSet ds = tabParam.getDataSetAdapter ().getDataSet ();
            
            switch (ctrl.getDialogMode ()) 
            {
                case VM_INS:
                    proc.getParam ().add (++curr, param);
                    ds.insertRow (param, IDataSet.InsertRowModeEnum.AFTER_CURRENT, true);
                    break;
                case VM_EDIT:                
                    proc.getParam ().set (curr, param);
                    ds.updateCurrentRow (param);
                    break;
                case VM_DEL:
                    proc.getParam ().remove (curr);
                    ds.removeCurrentRow ();
                    break;
                default:
                    break;
            }                
            
            tabParam.getSelectionModel ().select (curr);
        }    

        tabParam.requestFocus ();
    }
//
//
//    
}
