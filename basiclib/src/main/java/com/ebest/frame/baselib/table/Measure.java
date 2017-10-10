package com.ebest.frame.baselib.table;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

/**
 * Created by abc on 2017/9/29.
 */
@XStreamAlias("t")
@Entity
public class Measure extends Base{

    @XStreamImplicit(itemFieldName = "r")
    @Transient
    private List<Measure> values;

    @XStreamAlias("ID")
    @Id
    private Long id;

    @XStreamAlias("CODE")
    @Property(nameInDb = "CODE")
    private String code;

    @XStreamAlias("NAME")
    @Property(nameInDb = "NAME")
    private String name;

    @XStreamAlias("VALID")
    @Property(nameInDb = "VALID")
    private int valid;

    @XStreamAlias("TYPE")
    @Property(nameInDb = "TYPE")
    private int type;

    @XStreamAlias("ANSWER_TYPE")
    @Property(nameInDb = "ANSWER_TYPE")
    private int answerType;

    @XStreamAlias("DESCRIPTION")
    @Property(nameInDb = "DESCRIPTION")
    private String description;

    @XStreamAlias("SHORT_NAME")
    @Property(nameInDb = "SHORT_NAME")
    private String shortName;

    @XStreamAlias("PAGETYPE")
    @Property(nameInDb = "PAGETYPE")
    private String pageType;

    @XStreamAlias("REQUIRED")
    @Property(nameInDb = "REQUIRED")
    private String required;

    @XStreamAlias("Forced_photo")
    @Property(nameInDb = "FORCED_PHOTO")
    private int forcedPhoto;

    @XStreamAlias("Grid_photo")
    @Property(nameInDb = "GRID_PHOTO")
    private int gridPhoto;

    @Generated(hash = 447661022)
    public Measure(Long id, String code, String name, int valid, int type,
                   int answerType, String description, String shortName, String pageType,
                   String required, int forcedPhoto, int gridPhoto) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.valid = valid;
        this.type = type;
        this.answerType = answerType;
        this.description = description;
        this.shortName = shortName;
        this.pageType = pageType;
        this.required = required;
        this.forcedPhoto = forcedPhoto;
        this.gridPhoto = gridPhoto;
    }

    @Generated(hash = 1840334633)
    public Measure() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValid() {
        return this.valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAnswerType() {
        return this.answerType;
    }

    public void setAnswerType(int answerType) {
        this.answerType = answerType;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getPageType() {
        return this.pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getRequired() {
        return this.required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public int getForcedPhoto() {
        return this.forcedPhoto;
    }

    public void setForcedPhoto(int forcedPhoto) {
        this.forcedPhoto = forcedPhoto;
    }

    public int getGridPhoto() {
        return this.gridPhoto;
    }

    public void setGridPhoto(int gridPhoto) {
        this.gridPhoto = gridPhoto;
    }

    public List<Measure> getValues() {
        return values;
    }

    public void setValues(List<Measure> values) {
        this.values = values;
    }
}
