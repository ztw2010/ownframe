package com.ebest.frame.baselib.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ztw on 2017/9/28.
 */

@Entity
public class Student {

    @Id(autoincrement = true)
    private Long id;

    @Index(unique=true)
    private String key;

    @Unique
    @NotNull
    @Property(nameInDb = "username")
    private String name;

    @NotNull
    private String age;

    @Transient
    private String info;

    @Generated(hash = 1385077159)
    public Student(Long id, String key, @NotNull String name, @NotNull String age) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.age = age;
    }

    @Generated(hash = 1556870573)
    public Student() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
