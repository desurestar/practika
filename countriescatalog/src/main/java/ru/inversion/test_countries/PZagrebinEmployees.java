package ru.inversion.test_countries;

import java.math.BigDecimal;
import java.sql.*;
import java.time.*;
import java.io.Serializable;
import javax.persistence.*;
import ru.inversion.dataset.mark.*;
import ru.inversion.db.entity.ProxyFor;

/**
@author  admin
@since   2025/06/26 12:29:15
*/
@Entity (name="ru.inversion.test_countries.PZagrebinEmployees")
@Table (name="ZAGREBIN_EMPLOYEES")
public class PZagrebinEmployees implements Serializable
{
    private static final long serialVersionUID = 26_06_2025_12_29_15l;


/*
* РРґРµРЅС‚РёС„РёРєР°С‚РѕСЂ СЃРѕС‚СЂСѓРґРЅРёРєР°.
*/
    private Long EMPLOYEESID;

/*
* РџРѕР»РЅРѕРµ РёРјСЏ СЃРѕС‚СЂСѓРґРЅРёРєР°.
*/
    private String FULLNAME;

/*
* Р”РѕР»Р¶РЅРѕСЃС‚СЊ СЃРѕС‚СЂСѓРґРЅРёРєР°.
*/
    private String POSITION;

/*
* Р¤РѕС‚РѕРіСЂР°С„РёСЏ СЃРѕС‚СЂСѓРґРЅРёРєР°.
*/
    private Blob PHOTO;

/*
* РЎРµСЂРёСЏ РїР°СЃРїРѕСЂС‚Р° СЃРѕС‚СЂСѓРґРЅРёРєР°.
*/
    private String PASSPORTSERIES;

/*
* РќРѕРјРµСЂ РїР°СЃРїРѕСЂС‚Р° СЃРѕС‚СЂСѓРґРЅРёРєР°.
*/
    private String PASSPORTNUMBER;

/*
* Р­Р»РµРєС‚СЂРѕРЅРЅР°СЏ РїРѕС‡С‚Р° СЃРѕС‚СЂСѓРґРЅРёРєР°.
*/
    private String EMAIL;

    public PZagrebinEmployees(){}

    @Id 
    @Column(name="EMPLOYEESID",nullable = false,length = 0)
    public Long getEMPLOYEESID() {
        return EMPLOYEESID;
    }
    public void setEMPLOYEESID(Long val) {
        EMPLOYEESID = val; 
    }
    @Column(name="FULLNAME",nullable = false,length = 100)
    public String getFULLNAME() {
        return FULLNAME;
    }
    public void setFULLNAME(String val) {
        FULLNAME = val; 
    }
    @Column(name="POSITION",length = 100)
    public String getPOSITION() {
        return POSITION;
    }
    public void setPOSITION(String val) {
        POSITION = val; 
    }
    @Column(name="PHOTO")
    public Blob getPHOTO() {
        return PHOTO;
    }
    public void setPHOTO(Blob val) {
        PHOTO = val; 
    }
    @Column(name="PASSPORTSERIES",length = 10)
    public String getPASSPORTSERIES() {
        return PASSPORTSERIES;
    }
    public void setPASSPORTSERIES(String val) {
        PASSPORTSERIES = val; 
    }
    @Column(name="PASSPORTNUMBER",length = 10)
    public String getPASSPORTNUMBER() {
        return PASSPORTNUMBER;
    }
    public void setPASSPORTNUMBER(String val) {
        PASSPORTNUMBER = val; 
    }
    @Column(name="EMAIL",length = 100)
    public String getEMAIL() {
        return EMAIL;
    }
    public void setEMAIL(String val) {
        EMAIL = val; 
    }
}