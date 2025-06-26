package ru.inversion.countriescatalog.countries;

import java.io.Serializable;
import javax.persistence.*;

/**
@author  orlov
@since   2022/06/09 14:05:22
*/
@Entity (name="ru.inversion.countriescatalog.countries.POkSm")
@Table (name="OK_SM")
public class POkSm implements Serializable
{
    private static final long serialVersionUID = 9_06_2022_14_05_22l;


/*
* Цифровой код страны
*/
    private String CDIGITAL;

/*
* Альфа 2
*/
    private String CALFA_2;

/*
* Альфа 3
*/
    private String CALFA_3;

/*
* Краткое наименование страны
*/
    private String CSHORTNAME;

/*
* Полное наименование страны
*/
    private String CLONGNAME;

    public POkSm(){}

    @Id 
    @Column(name="CDIGITAL",nullable = false,length = 3)
    public String getCDIGITAL() {
        return CDIGITAL;
    }
    public void setCDIGITAL(String val) {
        CDIGITAL = val; 
    }
    @Column(name="CALFA_2",nullable = false,length = 2)
    public String getCALFA_2() {
        return CALFA_2;
    }
    public void setCALFA_2(String val) {
        CALFA_2 = val; 
    }
    @Column(name="CALFA_3",nullable = false,length = 3)
    public String getCALFA_3() {
        return CALFA_3;
    }
    public void setCALFA_3(String val) {
        CALFA_3 = val; 
    }
    @Column(name="CSHORTNAME",length = 100)
    public String getCSHORTNAME() {
        return CSHORTNAME;
    }
    public void setCSHORTNAME(String val) {
        CSHORTNAME = val; 
    }
    @Column(name="CLONGNAME",length = 250)
    public String getCLONGNAME() {
        return CLONGNAME;
    }
    public void setCLONGNAME(String val) {
        CLONGNAME = val; 
    }
}