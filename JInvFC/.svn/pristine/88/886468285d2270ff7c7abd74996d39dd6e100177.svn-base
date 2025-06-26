package ru.inversion.jinvfc.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
@author  psh
@since   2019/02/08 10:16:33
*/
@Entity 
@Table (name="ALL_COL_COMMENTS")
public class PAllColComments implements Serializable
{
    private static final long serialVersionUID = 8_02_2019_10_16_33l;

    private String COLUMN_NAME;
    private String COMMENTS;

    public PAllColComments(){}

    @Id 
    @Column(name="COLUMN_NAME",nullable = false,length = 128)
    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }
    public void setCOLUMN_NAME(String val) {
        COLUMN_NAME = val; 
    }
    @Column(name="COMMENTS",length = 4000)
    public String getCOMMENTS() {
        return COMMENTS;
    }
    public void setCOMMENTS(String val) {
        COMMENTS = val; 
    }
}