package ru.inversion.jinvfc.controller;

import javafx.fxml.FXML;
import ru.inversion.fx.form.JInvFXEntityController;
import ru.inversion.fx.form.controls.JInvTextField;
import ru.inversion.fx.form.controls.combobox.JInvComboBoxSimple;
import ru.inversion.jinvfc.entity.Pparam;


/**
 * @author  psh
 * @since   Mon Apr 04 15:43:54 MSK 2016
 */
public class EditParamController extends JInvFXEntityController <Pparam> 
{  
//
//
//
//    @FXML JInvLongField IFSBCODE;
//    @FXML JInvTextField CFSBNAME;
    @FXML JInvTextField NAME;
    @FXML JInvComboBoxSimple MODE;
//    @FXML JInvLongField IFSBOSB_NUM;
//    @FXML JInvLongField IFSBKT_TRB;
//    @FXML JInvLongField IFSBKT_OSB;

//
// Initializes the controller class.
//
    @Override
    protected void init () throws Exception 
    {
        super.init (); 
        
        MODE.getItems ().setAll ("IN", "OUT", "INOUT");
    }    
//
//
//    
    @Override
    protected boolean onOK () 
    {
        getFXEntity ().commit ();
        return true; 
    }
}

