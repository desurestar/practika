package ru.inversion.countriescatalog.audit;

import javafx.fxml.FXML;
import ru.inversion.bicomp.util.ParamMap;
import ru.inversion.db.expr.SQLExpressionException;
import ru.inversion.fx.form.JInvFXFormController;
import ru.inversion.fx.form.controls.JInvTextArea;

/**
 * FXML Controller class
 *
 * @author bayurov
 */
public class EditCommentsActionController extends JInvFXFormController<PAuditAction> {

    @FXML
    private JInvTextArea COMM;

    /**
     * Initializes the controller class.
     */
    @Override
    public void init() throws Exception {
        setTitle(getBundleString("EDIT.TITLE"));
        COMM.setText(dataObject.getCAUACTIONCOMM());
    }

    @Override
    protected boolean onOK() {
        updateComm();
        return super.onOK();
    }

    private void updateComm() {
        try {
            new ParamMap()
                    .add("nfield", dataObject.getIAUACTION_ID())
                    .add("cncomm", COMM.getText())
                    .exec(this, "updateCommAction", this::doOkResult);
        } catch (SQLExpressionException ex) {
            handleException(ex);
        }
    }

    public void doOkResult(ParamMap p) {
    }
}
