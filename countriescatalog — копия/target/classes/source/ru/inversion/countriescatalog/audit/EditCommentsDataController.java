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
public class EditCommentsDataController extends JInvFXFormController<PAuditData> {

    @FXML
    private JInvTextArea COMM;

    /**
     * Initializes the controller class.
     */
    @Override
    public void init() throws Exception {
        COMM.setText(dataObject.getCAUDATACOMM());
        setTitle(getBundleString("EDIT.TITLE"));
    }

    @Override
    protected boolean onOK() {
        updateComm();
        return super.onOK();
    }

    private void updateComm() {
        try {
            new ParamMap()
                    .add("nfield", dataObject.getIAUDATAACTID())
                    .add("ctabel", dataObject.getCAUDATATABLE())
                    .add("cfield", dataObject.getCAUDATAFIELD())
                    .add("cncomm", COMM.getText())
                    .exec(this, "updateCommdata", this::doOkResult);
        } catch (SQLExpressionException ex) {
            handleException(ex);
        }
    }

    public void doOkResult(ParamMap p) {
    }

}
