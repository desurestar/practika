package ru.inversion.countriescatalog.countries;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import ru.inversion.fx.form.JInvFXFormController;
import ru.inversion.fx.form.controls.*;
import javafx.fxml.FXML;
import ru.inversion.bicomp.util.ParamMap;
import ru.inversion.db.expr.SQLExpressionException;
import ru.inversion.fx.form.valid.Validator;

/**
 * @author orlov
 * @since Tue Jun 07 16:44:21 MSK 2022
 */
public class EditCountriesController extends JInvFXFormController<POkSm> {

    @FXML
    private JInvLabel lblCDIGITAL;
    @FXML
    private JInvTextField CDIGITAL;
    @FXML
    private JInvLabel lblCALFA_2;
    @FXML
    private JInvTextField CALFA_2;
    @FXML
    private JInvLabel lblCALFA_3;
    @FXML
    private JInvTextField CALFA_3;
    @FXML
    private JInvLabel lblCSHORTNAME;
    @FXML
    private JInvTextArea CLONGNAME;
    @FXML
    private JInvLabel lblCLONGNAME;
    @FXML
    private JInvButton btOK;
    @FXML
    private JInvButton btCancel;
    @FXML
    private JInvTextField CSHORTNAME;

//
// Initializes the controller class.
//
    @Override
    protected void init() throws Exception {
        addDigitLimiter(CDIGITAL, 3);
        validationTextFieldDigit(CDIGITAL);
        addTextFieldLimiter(CALFA_2, 2);
        addTextFieldLimiter(CALFA_3, 3);
        addTextUpperCase(CSHORTNAME);
        addTextAreaUpperCase(CLONGNAME);
        super.init();
    }
    @Override
    protected boolean onOK() {
        try {
            new ParamMap()
                    .add("cnum", CDIGITAL.getText())
                    .add("calfa2", CALFA_2.getText())
                    .add("calfa3", CALFA_3.getText())
                    .add("cshname", CLONGNAME.getText())
                    .add("clnname", CSHORTNAME.getText())
                    .exec(this, "updateOksm");
            
        } catch (SQLExpressionException ex) {
            handleException(ex);
        }
        return true;
    }
//
// Валидация чисел
//

    private void validationTextFieldDigit(final JInvTextField tf) {
        getValidMan().bindControls2Validator((value) -> {
            try {
                if (value != null) {
                    Integer.parseInt(CDIGITAL.getText());
                }

            } catch (Exception ex) {
                return new Validator.Result(null, getBundleString("tooldigit"));
            }
            return null;
        }, tf);
    }
//
// Текст Caps для TextArea
//

    private static void addTextUpperCase(final JInvTextField tf) {
        tf.textProperty().addListener((final ObservableValue<? extends String> ov, final String oldValue, final String newValue) -> {
            tf.setText(tf.getText().toUpperCase());
        });
    }
//
// Текст Caps для TextArea
//

    private static void addTextAreaUpperCase(final JInvTextArea tf) {
        tf.textProperty().addListener((final ObservableValue<? extends String> ov, final String oldValue, final String newValue) -> {
            tf.setText(tf.getText().toUpperCase());
        });
    }
//
// Ограничение на количество символов с текст caps
//

    private static void addTextFieldLimiter(final JInvTextField tf, final int maxLength) {
        tf.textProperty().addListener((final ObservableValue<? extends String> ov, final String oldValue, final String newValue) -> {
            if (tf.getText().length() > maxLength) {
                tf.setText(tf.getText().substring(0, maxLength).toUpperCase());
            }
        });
    }
//
// Ограничение на количество чисел
//

    private static void addDigitLimiter(final JInvTextField tf, final int maxLength) {
        tf.textProperty().addListener((final ObservableValue<? extends String> ov, final String oldValue, final String newValue) -> {
            if (tf.getText().length() > maxLength) {
                tf.setText(tf.getText().substring(0, maxLength));
            }
        });
    }
}
