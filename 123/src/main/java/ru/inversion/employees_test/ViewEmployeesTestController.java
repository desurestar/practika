package ru.inversion.employees_test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ru.inversion.dataset.IDataSet;
import ru.inversion.dataset.XXIDataSet;
import ru.inversion.dataset.fx.DSFXAdapter;
import ru.inversion.dataset.aggr.AggrFuncEnum;
import ru.inversion.fx.form.controls.dsbar.DSInfoBar;
import ru.inversion.fx.form.controls.table.toolbar.AggregatorType;
import ru.inversion.meta.EntityMetadataFactory;
import ru.inversion.meta.IEntityProperty;

import ru.inversion.fx.form.*;
import ru.inversion.fx.form.controls.*;

import ru.inversion.bicomp.action.JInvButtonPrint;
import ru.inversion.bicomp.action.StopExecuteActionBiCompException;
import ru.inversion.bicomp.fxreport.ApReport;

/**
 *
 * @author  admin
 * @since   Mon Jul 01 11:55:24 MSK 2024
 */
public class ViewEmployeesTestController extends JInvFXBrowserController 
{
    @FXML private JInvTable<PEmployeesTest> EMPLOYEES_TEST;   
    @FXML private JInvToolBar toolBar;

 
   
    private final XXIDataSet<PEmployeesTest> dsEMPLOYEES_TEST = new XXIDataSet<> ();    
//
// initDataSet
//    
    private void initDataSet () throws Exception 
    {
        dsEMPLOYEES_TEST.setTaskContext (getTaskContext ());
        dsEMPLOYEES_TEST.setRowClass (PEmployeesTest.class);
    }
//
// Initializes the controller class.
//
    @Override
    protected void init() throws Exception
    {
        setTitle (getBundleString ("VIEW.TITLE"));
        
        initDataSet ();
        DSFXAdapter<PEmployeesTest> dsfx = DSFXAdapter.bind (dsEMPLOYEES_TEST, EMPLOYEES_TEST, null, false); 

        dsfx.setEnableFilter (true);
 
                
        initToolBar ();

        EMPLOYEES_TEST.setToolBar (toolBar);       
        EMPLOYEES_TEST.setAction (ActionFactory.ActionTypeEnum.CREATE, (a) -> doOperation (FormModeEnum.VM_INS));
        EMPLOYEES_TEST.setAction (ActionFactory.ActionTypeEnum.CREATE_BY, (a) -> doOperation (FormModeEnum.VM_NONE));
        EMPLOYEES_TEST.setAction (ActionFactory.ActionTypeEnum.VIEW, (a) -> doOperation (FormModeEnum.VM_SHOW));
        EMPLOYEES_TEST.setAction (ActionFactory.ActionTypeEnum.UPDATE, (a) -> doOperation (FormModeEnum.VM_EDIT));
        EMPLOYEES_TEST.setAction (ActionFactory.ActionTypeEnum.DELETE, (a) -> doOperation (FormModeEnum.VM_DEL));
        EMPLOYEES_TEST.setAction (ActionFactory.ActionTypeEnum.REFRESH, (a) -> doRefresh ());

        doRefresh ();
    }        
//
// doRefresh
//    
    private void doRefresh () 
    {
        EMPLOYEES_TEST.executeQuery ();
    }
//
// initToolBar
//    
    private void initToolBar () 
    {
        JInvButtonPrint bp = new JInvButtonPrint (this::setPrintParam);        
        bp.setReportTypeId (200000);
        toolBar.getItems ().add (bp);

        toolBar.setStandartActions (ActionFactory.ActionTypeEnum.CREATE, 
                                    ActionFactory.ActionTypeEnum.CREATE_BY, 
                                    ActionFactory.ActionTypeEnum.VIEW,
                                    ActionFactory.ActionTypeEnum.UPDATE,
                                    ActionFactory.ActionTypeEnum.DELETE);
    }
//
// setPrintParam
//
    private void setPrintParam ( ApReport ap ) 
    {
        if (dsEMPLOYEES_TEST.isEmpty ())
            throw new StopExecuteActionBiCompException ();
    }
//
// doOperation
//    
    private void doOperation ( JInvFXFormController.FormModeEnum mode ) 
    {
        PEmployeesTest p = null;

        switch (mode) {
            case VM_INS:
                p = new PEmployeesTest ();
                break;
            case VM_NONE:
                if (dsEMPLOYEES_TEST.getCurrentRow () == null) 
                    break;
                mode = FormModeEnum.VM_INS;
                p = new PEmployeesTest ();
                for (IEntityProperty<PEmployeesTest, ?> ep : EntityMetadataFactory.getEntityMetaData (PEmployeesTest.class).getPropertiesMap ().values ())
                    if (! (ep.isTransient () || ep.isId ()))
                        ep.invokeSetter (p, ep.invokeGetter (dsEMPLOYEES_TEST.getCurrentRow ()));
                break;
            case VM_EDIT:
            case VM_SHOW:
            case VM_DEL:
                p = dsEMPLOYEES_TEST.getCurrentRow ();
                break;
        }

        if (p != null) 
            new FXFormLauncher<> (this, EditEmployeesTestController.class)
                .dataObject (p)
                .dialogMode (mode)
                .initProperties (getInitProperties ())
                .callback (this::doFormResult)    
                .doModal ();
    }
//
// doFormResult 
//
    private void doFormResult ( JInvFXFormController.FormReturnEnum ok, JInvFXFormController<PEmployeesTest> dctl )    
    {
        if (JInvFXFormController.FormReturnEnum.RET_OK == ok)
        {
            switch (dctl.getFormMode ()) 
            {
                case VM_INS:
                    dsEMPLOYEES_TEST.insertRow (dctl.getDataObject (), IDataSet.InsertRowModeEnum.AFTER_CURRENT, true);
                    break;
                case VM_EDIT:                
                    dsEMPLOYEES_TEST.updateCurrentRow (dctl.getDataObject ());
                    break;
                case VM_DEL:
                    dsEMPLOYEES_TEST.removeCurrentRow ();
                    break;
                default:
                    break;
            }                
        }    

        EMPLOYEES_TEST.requestFocus ();
    }        
//
//
//    
}

