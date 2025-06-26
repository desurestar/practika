package ru.inversion.countriescatalog.audit;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @author bayurov
 * @since 2022/05/31 12:25:07
 */
@Entity(name = "ru.inversion.countriescatalog.audit.PAuditData")
@Table(name = "AUDIT_DATA")
public class PAuditData implements Serializable {

    private static final long serialVersionUID = 31_05_2022_12_25_07l;


    /*
* id действия
     */
    private Long IAUDATAACTID;

    /*
* Тип действия (I - добавление, U - изменение, D - удаление)
     */
    private String CAUDATAOPER;

    /*
* Название таблицы
     */
    private String CAUDATATABLE;

    /*
* Название поля
     */
    private String CAUDATAFIELD;

    /*
* Старое значение
     */
    private String CAUDATAOLDVAL;

    /*
* Новое значение
     */
    private String CAUDATANEWVAL;

    /*
* Комментарий
     */
    private String CAUDATACOMM;

    public PAuditData() {
    }

    @Id
    @Column(name = "IAUDATAACTID", length = 0)
    public Long getIAUDATAACTID() {
        return IAUDATAACTID;
    }

    public void setIAUDATAACTID(Long val) {
        IAUDATAACTID = val;
    }

    @Column(name = "CAUDATAOPER", length = 1)
    public String getCAUDATAOPER() {
        return CAUDATAOPER;
    }

    public void setCAUDATAOPER(String val) {
        CAUDATAOPER = val;
    }

    @Column(name = "CAUDATATABLE", length = 64)
    public String getCAUDATATABLE() {
        return CAUDATATABLE;
    }

    public void setCAUDATATABLE(String val) {
        CAUDATATABLE = val;
    }

    @Column(name = "CAUDATAFIELD", length = 32)
    public String getCAUDATAFIELD() {
        return CAUDATAFIELD;
    }

    public void setCAUDATAFIELD(String val) {
        CAUDATAFIELD = val;
    }

    @Column(name = "CAUDATAOLDVAL", length = 64)
    public String getCAUDATAOLDVAL() {
        return CAUDATAOLDVAL;
    }

    public void setCAUDATAOLDVAL(String val) {
        CAUDATAOLDVAL = val;
    }

    @Column(name = "CAUDATANEWVAL", length = 64)
    public String getCAUDATANEWVAL() {
        return CAUDATANEWVAL;
    }

    public void setCAUDATANEWVAL(String val) {
        CAUDATANEWVAL = val;
    }

    @Column(name = "CAUDATACOMM", length = 250)
    public String getCAUDATACOMM() {
        return CAUDATACOMM;
    }

    public void setCAUDATACOMM(String val) {
        CAUDATACOMM = val;
    }
}
