package ru.inversion.countriescatalog.audit;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import ru.inversion.countriescatalog.countries.POkSm;
import ru.inversion.dataset.DataLinkBuilder;
import ru.inversion.dataset.IDataSetLink;
import ru.inversion.dataset.XXIDataSet;
import ru.inversion.dataset.fx.DSFXAdapter;
import ru.inversion.fx.form.FXFormLauncher;
import ru.inversion.fx.form.JInvFXFormController;
import ru.inversion.fx.form.controls.JInvTable;

/**
 * FXML Controller class
 *
 * @author bayurov
 */
public class ViewAuditEntitiesController extends JInvFXFormController<POkSm> {

    @FXML
    private JInvTable<PAuditAction> AUDIT_ACTION;
    @FXML
    private JInvTable<PAuditData> AUDIT_DATA;

    ContextMenu menuAction = new ContextMenu();
    ContextMenu menuData = new ContextMenu();

    private final XXIDataSet<PAuditAction> dsAUDIT_ACTION = new XXIDataSet<>();
    private final XXIDataSet<PAuditData> dsAUDIT_DATA = new XXIDataSet<>();
IDataSetLink<PAuditAction> linkDataSet;
    //
// initDataSet
//    
    private void initDataSet() {
        dsAUDIT_ACTION.setTaskContext(getTaskContext());
        dsAUDIT_ACTION.setRowClass(PAuditAction.class);
        dsAUDIT_ACTION.set("keyval", dataObject.getCDIGITAL());
        linkDataSet=DataLinkBuilder.linkDataSet(dsAUDIT_ACTION, dsAUDIT_DATA, PAuditAction::getIAUACTION_ID, "IAUDATAACTID", true);
        dsAUDIT_DATA.setTaskContext(getTaskContext());
        dsAUDIT_DATA.setRowClass(PAuditData.class);

        MenuItem itemAction = new MenuItem(getBundleString("MENU_ITEM"));
        itemAction.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!AUDIT_ACTION.getItems().isEmpty()) {
                    updateCommAction();
                }
            }
        });
        menuAction.getItems().add(itemAction);
        MenuItem itemData = new MenuItem(getBundleString("MENU_ITEM"));
        itemData.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!AUDIT_DATA.getItems().isEmpty()) {
                    updateCommdata();
                }
            }
        });
        menuData.getItems().add(itemData);
        
        AUDIT_ACTION.setContextMenu(menuAction);
        AUDIT_DATA.setContextMenu(menuData);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void init() throws Exception {
        setTitle(getBundleString("VIEW.TITLE"));
        initDataSet();

        DSFXAdapter.bind(dsAUDIT_ACTION, AUDIT_ACTION, null, false);
        DSFXAdapter.bind(dsAUDIT_DATA, AUDIT_DATA, null, false);
        
        doRefresh();
    }

    private void updateCommAction() {
        PAuditAction p = dsAUDIT_ACTION.getCurrentRow();
        new FXFormLauncher<>(this, EditCommentsActionController.class)
                .dataObject(p)
                .dialogMode(JInvFXFormController.FormModeEnum.VM_NONE)
                .initProperties(getInitProperties())
                .callback(this::doFormResultAction)
                .doModal();
    }

    private void updateCommdata() {
        PAuditData p = dsAUDIT_DATA.getCurrentRow();
        new FXFormLauncher<>(this, EditCommentsDataController.class)
                .dataObject(p)
                .dialogMode(JInvFXFormController.FormModeEnum.VM_NONE)
                .initProperties(getInitProperties())
                .callback(this::doFormResultData)
                .doModal();
    }

    private void doFormResultData(JInvFXFormController.FormReturnEnum ok, JInvFXFormController<PAuditData> dctl) {
        if (JInvFXFormController.FormReturnEnum.RET_OK == ok) {
            switch (dctl.getFormMode()) {
                case VM_NONE:
                    AUDIT_DATA.executeQuery();
                    break;
            }
        }
        AUDIT_DATA.requestFocus();
    }

    private void doFormResultAction(JInvFXFormController.FormReturnEnum ok, JInvFXFormController<PAuditAction> dctl) {
        if (JInvFXFormController.FormReturnEnum.RET_OK == ok) {
            switch (dctl.getFormMode()) {
                case VM_NONE:
                    AUDIT_ACTION.executeQuery();
                    break;
            }
        }
        AUDIT_ACTION.requestFocus();
    }
    
    //
// doRefresh
//    
    private void doRefresh() {
        AUDIT_ACTION.executeQuery();
    }
}
