package ru.inversion.jinvfc.controller;

import javafx.fxml.FXML;
import ru.inversion.fx.form.JInvFXEntityController;
import ru.inversion.fx.form.controls.JInvTextField;
import ru.inversion.fx.form.controls.combobox.JInvComboBoxSimple;
import ru.inversion.jinvfc.entity.Pproc;


/**
 * @author  psh
 * @since   Mon Apr 04 15:43:54 MSK 2016
 */
public class EditProcController extends JInvFXEntityController <Pproc> 
{  
//
//
//
//    @FXML JInvLongField IFSBCODE;
//    @FXML JInvTextField CFSBNAME;
    @FXML JInvTextField NAME;
    @FXML JInvComboBoxSimple LANG;
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
        
        LANG.getItems ().setAll ("SQL", "PL/SQL");
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

