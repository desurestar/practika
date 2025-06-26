package ru.inversion.jinvfc.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
@author  psh
@since   2018/06/30 13:41:53
*/
@Entity (name="ru.inversion.jinvfc.entity.PUps")
@Table (name="UPS")
public class PUps implements Serializable
{
    private static final long serialVersionUID = 30_06_2018_13_41_53l;

    private String CUPSUSER;
    private String CUPSPREF;
    private String CUPSVALUE;

    public PUps(){}

    @Id 
    @Column(name="CUPSUSER",nullable = false,length = 30)
    public String getCUPSUSER() {
        return CUPSUSER;
    }
    public void setCUPSUSER(String val) {
        CUPSUSER = val; 
    }
    @Id 
    @Column(name="CUPSPREF",nullable = false,length = 60)
    public String getCUPSPREF() {
        return CUPSPREF;
    }
    public void setCUPSPREF(String val) {
        CUPSPREF = val; 
    }
    @Column(name="CUPSVALUE",length = 2000)
    public String getCUPSVALUE() {
        return CUPSVALUE;
    }
    public void setCUPSVALUE(String val) {
        CUPSVALUE = val; 
    }
}