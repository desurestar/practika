package ru.inversion.jinvfc.entity;

import java.math.BigDecimal;
import java.sql.*;
import java.io.Serializable;
import javax.persistence.*;
import ru.inversion.dataset.mark.*;

/**
@author  psh
@since   2016/02/20 11:09:02
*/
@Entity
@NamedNativeQuery (name="ru.inversion.jinvfc.entity.PAllConsColumns",
                   query="select column_name, position, c.table_name " +
                         "from all_cons_columns c join all_constraints p on " +
                         "(c.OWNER = p.OWNER and c.CONSTRAINT_NAME = p.CONSTRAINT_NAME " +
                         "and c.table_name = p.table_name and p.constraint_type = 'P') " +
                         "and c.table_name=?")
public class PAllConsColumns implements Serializable  {

    private String COLUMN_NAME;
    private BigDecimal POSITION;

    public PAllConsColumns(){}

    @Id 
    @Column(name="COLUMN_NAME")
    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }
    public void setCOLUMN_NAME(String val) {
        COLUMN_NAME = val; 
    }
    @Id 
    @Column(name="POSITION")
    public BigDecimal getPOSITION() {
        return POSITION;
    }
    public void setPOSITION(BigDecimal val) {
        POSITION = val; 
    }
}