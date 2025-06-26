/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.inversion.jinvfc.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 *
 * @author psh
 */
//@Entity (name="ru.inversion.jinvfc.entity.Pproc")
public class Pproc implements Serializable {

    private String lang;

    @Column(name="lang", nullable=false)
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
 
    private String script;

    @Column(name="script", length=65536)
    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
 
    private String file;

    @Column(name="file")
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
  
    private String name;

    @Id
    @Column(name="name", nullable=false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List <Pparam> param;

    public List <Pparam> getParam() {
        return param;
    }

    public void setParam (List<Pparam> param) {
        this.param = param;
    }
}
