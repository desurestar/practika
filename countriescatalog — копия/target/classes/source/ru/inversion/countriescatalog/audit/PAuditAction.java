package ru.inversion.countriescatalog.audit;

import java.time.*;
import java.io.Serializable;
import javax.persistence.*;

/**
 * @author bayurov
 * @since 2022/05/31 12:22:12
 */
@Entity(name = "ru.inversion.countriescatalog.audit.PAuditAction")
@Table(name = "AUDIT_ACTION")
@NamedNativeQuery(name = "ru.inversion.countriescatalog.audit.PAuAction", query = "select  *"
        + "        from audit_action where CAUAKEY = :keyval"
        + "   order by DAUACTIONDATE desc")
public class PAuditAction implements Serializable {

    private static final long serialVersionUID = 31_05_2022_12_22_12l;


    /*
* id действия
     */
    private Long IAUACTION_ID;

    /*
* Действие
     */
    private String CAUACTIONNAME;

    /*
* Дата действия
     */
    private LocalDateTime DAUACTIONDATE;

    /*
* Пользователь
     */
    private String CAUACTIONUSER;

    /*
* Адрес ПК
     */
    private String CAUACTIONMACHINE;

    /*
* Номер сессии
     */
    private Long IAUACTIONSESSION;

    /*
* ip пользователя
     */
    private String CAUCATIONIP;

    /*
* Комментарии
     */
    private String CAUACTIONCOMM;

    /*
* Имя аудирумого значения
     */
    private String CAUAKEY;

    public PAuditAction() {
    }

    @Id
    @Column(name = "IAUACTION_ID", nullable = false, length = 0)
    public Long getIAUACTION_ID() {
        return IAUACTION_ID;
    }

    public void setIAUACTION_ID(Long val) {
        IAUACTION_ID = val;
    }

    @Column(name = "CAUACTIONNAME", length = 32)
    public String getCAUACTIONNAME() {
        return CAUACTIONNAME;
    }

    public void setCAUACTIONNAME(String val) {
        CAUACTIONNAME = val;
    }

    @Column(name = "DAUACTIONDATE")
    public LocalDateTime getDAUACTIONDATE() {
        return DAUACTIONDATE;
    }

    public void setDAUACTIONDATE(LocalDateTime val) {
        DAUACTIONDATE = val;
    }

    @Column(name = "CAUACTIONUSER", length = 32)
    public String getCAUACTIONUSER() {
        return CAUACTIONUSER;
    }

    public void setCAUACTIONUSER(String val) {
        CAUACTIONUSER = val;
    }

    @Column(name = "CAUACTIONMACHINE", length = 64)
    public String getCAUACTIONMACHINE() {
        return CAUACTIONMACHINE;
    }

    public void setCAUACTIONMACHINE(String val) {
        CAUACTIONMACHINE = val;
    }

    @Column(name = "IAUACTIONSESSION", length = 0)
    public Long getIAUACTIONSESSION() {
        return IAUACTIONSESSION;
    }

    public void setIAUACTIONSESSION(Long val) {
        IAUACTIONSESSION = val;
    }

    @Column(name = "CAUCATIONIP", length = 35)
    public String getCAUCATIONIP() {
        return CAUCATIONIP;
    }

    public void setCAUCATIONIP(String val) {
        CAUCATIONIP = val;
    }

    @Column(name = "CAUACTIONCOMM", length = 250)
    public String getCAUACTIONCOMM() {
        return CAUACTIONCOMM;
    }

    public void setCAUACTIONCOMM(String val) {
        CAUACTIONCOMM = val;
    }

    @Column(name = "CAUAKEY", nullable = false, length = 64)
    public String getCAUAKEY() {
        return CAUAKEY;
    }

    public void setCAUAKEY(String val) {
        CAUAKEY = val;
    }
}
