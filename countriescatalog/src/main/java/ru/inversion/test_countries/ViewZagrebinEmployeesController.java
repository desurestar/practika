package ru.inversion.test_countries;

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
 * @since   Thu Jun 26 12:29:20 MSK 2025
 */
public class ViewZagrebinEmployeesController extends JInvFXBrowserController 
{
    @FXML private JInvTable<PZagrebinEmployees> ZAGREBIN_EMPLOYEES;   
    @FXML private JInvToolBar toolBar;

 
   
    private final XXIDataSet<PZagrebinEmployees> dsZAGREBIN_EMPLOYEES = new XXIDataSet<> ();    
//
// initDataSet
//    
    private void initDataSet () throws Exception 
    {
        dsZAGREBIN_EMPLOYEES.setTaskContext (getTaskContext ());
        dsZAGREBIN_EMPLOYEES.setRowClass (PZagrebinEmployees.class);
    }
//
// Initializes the controller class.
//
    @Override
    protected void init() throws Exception
    {
        setTitle (getBundleString ("VIEW.TITLE"));
        
        initDataSet ();
        DSFXAdapter<PZagrebinEmployees> dsfx = DSFXAdapter.bind (dsZAGREBIN_EMPLOYEES, ZAGREBIN_EMPLOYEES, null, false); 

        dsfx.setEnableFilter (true);
 
                
        initToolBar ();

        ZAGREBIN_EMPLOYEES.setToolBar (toolBar);       
        ZAGREBIN_EMPLOYEES.setAction (ActionFactory.ActionTypeEnum.CREATE, (a) -> doOperation (FormModeEnum.VM_INS));
        ZAGREBIN_EMPLOYEES.setAction (ActionFactory.ActionTypeEnum.CREATE_BY, (a) -> doOperation (FormModeEnum.VM_NONE));
        ZAGREBIN_EMPLOYEES.setAction (ActionFactory.ActionTypeEnum.VIEW, (a) -> doOperation (FormModeEnum.VM_SHOW));
        ZAGREBIN_EMPLOYEES.setAction (ActionFactory.ActionTypeEnum.UPDATE, (a) -> doOperation (FormModeEnum.VM_EDIT));
        ZAGREBIN_EMPLOYEES.setAction (ActionFactory.ActionTypeEnum.DELETE, (a) -> doOperation (FormModeEnum.VM_DEL));
        ZAGREBIN_EMPLOYEES.setAction (ActionFactory.ActionTypeEnum.REFRESH, (a) -> doRefresh ());

        doRefresh ();
    }        
//
// doRefresh
//    
    private void doRefresh () 
    {
        ZAGREBIN_EMPLOYEES.executeQuery ();
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
        if (dsZAGREBIN_EMPLOYEES.isEmpty ())
            throw new StopExecuteActionBiCompException ();
    }
//
// doOperation
//    
    private void doOperation ( JInvFXFormController.FormModeEnum mode ) 
    {
        PZagrebinEmployees p = null;

        switch (mode) {
            case VM_INS:
                p = new PZagrebinEmployees ();
                break;
            case VM_NONE:
                if (dsZAGREBIN_EMPLOYEES.getCurrentRow () == null) 
                    break;
                mode = FormModeEnum.VM_INS;
                p = new PZagrebinEmployees ();
                for (IEntityProperty<PZagrebinEmployees, ?> ep : EntityMetadataFactory.getEntityMetaData (PZagrebinEmployees.class).getPropertiesMap ().values ())
                    if (! (ep.isTransient () || ep.isId ()))
                        ep.invokeSetter (p, ep.invokeGetter (dsZAGREBIN_EMPLOYEES.getCurrentRow ()));
                break;
            case VM_EDIT:
            case VM_SHOW:
            case VM_DEL:
                p = dsZAGREBIN_EMPLOYEES.getCurrentRow ();
                break;
        }

        if (p != null) 
            new FXFormLauncher<> (this, EditZagrebinEmployeesController.class)
                .dataObject (p)
                .dialogMode (mode)
                .initProperties (getInitProperties ())
                .callback (this::doFormResult)    
                .doModal ();
    }
//
// doFormResult 
//
    private void doFormResult ( JInvFXFormController.FormReturnEnum ok, JInvFXFormController<PZagrebinEmployees> dctl )    
    {
        if (JInvFXFormController.FormReturnEnum.RET_OK == ok)
        {
            switch (dctl.getFormMode ()) 
            {
                case VM_INS:
                    dsZAGREBIN_EMPLOYEES.insertRow (dctl.getDataObject (), IDataSet.InsertRowModeEnum.AFTER_CURRENT, true);
                    break;
                case VM_EDIT:                
                    dsZAGREBIN_EMPLOYEES.updateCurrentRow (dctl.getDataObject ());
                    break;
                case VM_DEL:
                    dsZAGREBIN_EMPLOYEES.removeCurrentRow ();
                    break;
                default:
                    break;
            }                
        }    

        ZAGREBIN_EMPLOYEES.requestFocus ();
    }        
//
//
//    
}

