package ru.inversion.countriescatalog.countries;

import ru.inversion.fx.form.JInvFXFormController;
import ru.inversion.fx.form.controls.*;
import javafx.fxml.FXML;
import ru.inversion.bicomp.util.ParamMap;
import ru.inversion.db.expr.SQLExpressionException;

/**
 * @author  orlov
 * @since   Tue Jun 07 16:44:21 MSK 2022
 */
public class DeleteCountriesController extends JInvFXFormController <POkSm> 
{  

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
    protected void init () throws Exception 
    {
        super.init (); 
    }    
 @Override
    protected boolean onOK() {
        try {
            new ParamMap()
                    .add("cnum", CDIGITAL.getText())
                    .exec(this, "deleteOksm");
            
        } catch (SQLExpressionException ex) {
            handleException(ex);
        }
        return true;
    }
}

