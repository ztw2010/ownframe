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
 * Created by ztw on 2017/9/29.
 */
@XStreamAlias("t")
@Entity
public class Form extends Base{

    @XStreamImplicit(itemFieldName = "r")
    @Transient
    private List<Form> values;

    @XStreamAlias("ID")
    @Id
    private Long id;

    @XStreamAlias("CODE")
    @Property(nameInDb = "CODE")
    private String code;

    @XStreamAlias("NAME")
    @Property(nameInDb = "NAME")
    private String name;

    @XStreamAlias("BRAND_CODE")
    @Property(nameInDb = "BRAND_CODE")
    private String brandCode;

    @XStreamAlias("MANUFACTURER_CODE")
    @Property(nameInDb = "MANUFACTURER_CODE")
    private String manufacturerCode;

    @XStreamAlias("CATEGORIE_CODE")
    @Property(nameInDb = "CATEGORIE_CODE")
    private String categorieCode;

    @XStreamAlias("DOMAIN_ID")
    @Property(nameInDb = "DOMAIN_ID")
    private int domainId;

    @XStreamAlias("VALID")
    @Property(nameInDb = "VALID")
    private int valid;

    @Generated(hash = 131459776)
    public Form(Long id, String code, String name, String brandCode,
            String manufacturerCode, String categorieCode, int domainId,
            int valid) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.brandCode = brandCode;
        this.manufacturerCode = manufacturerCode;
        this.categorieCode = categorieCode;
        this.domainId = domainId;
        this.valid = valid;
    }

    @Generated(hash = 535210737)
    public Form() {
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

    public String getBrandCode() {
        return this.brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getManufacturerCode() {
        return this.manufacturerCode;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public String getCategorieCode() {
        return this.categorieCode;
    }

    public void setCategorieCode(String categorieCode) {
        this.categorieCode = categorieCode;
    }

    public int getDomainId() {
        return this.domainId;
    }

    public void setDomainId(int domainId) {
        this.domainId = domainId;
    }

    public int getValid() {
        return this.valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public List<Form> getValues() {
        return values;
    }

    public void setValues(List<Form> values) {
        this.values = values;
    }
}
