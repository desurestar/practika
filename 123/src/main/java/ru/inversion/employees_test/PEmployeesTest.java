package ru.inversion.employees_test;

import java.math.BigDecimal;
import java.sql.*;
import java.time.*;
import java.io.Serializable;
import javax.persistence.*;
import ru.inversion.dataset.mark.*;
import ru.inversion.db.entity.ProxyFor;

/**
@author  admin
@since   2024/07/01 11:56:27
*/
@Entity (name="ru.inversion.employees_test.PEmployeesTest")
@Table (name="EMPLOYEES_TEST")
public class PEmployeesTest implements Serializable
{
    private static final long serialVersionUID = 1_07_2024_11_56_27l;

    private Long ID;
    private String SURNAME;
    private String NAME;
    private String PATRONYMIC;

    public PEmployeesTest(){}

    @Id 
    @Column(name="ID",nullable = false,length = 0)
    public Long getID() {
        return ID;
    }
    public void setID(Long val) {
        ID = val; 
    }
    @Column(name="SURNAME",nullable = false,length = 100)
    public String getSURNAME() {
        return SURNAME;
    }
    public void setSURNAME(String val) {
        SURNAME = val; 
    }
    @Column(name="NAME",nullable = false,length = 100)
    public String getNAME() {
        return NAME;
    }
    public void setNAME(String val) {
        NAME = val; 
    }
    @Column(name="PATRONYMIC",length = 100)
    public String getPATRONYMIC() {
        return PATRONYMIC;
    }
    public void setPATRONYMIC(String val) {
        PATRONYMIC = val; 
    }
}