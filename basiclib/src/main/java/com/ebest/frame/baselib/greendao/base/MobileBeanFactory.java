package com.ebest.frame.baselib.greendao.base;

import com.ebest.frame.baselib.greendao.dbmanager.FormDaoManager;
import com.ebest.frame.baselib.greendao.dbmanager.MeasureDaoManager;
import com.ebest.frame.baselib.table.Base;
import com.ebest.frame.baselib.table.Form;
import com.ebest.frame.baselib.table.Measure;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ztw on 2017/10/9.
 */

public class MobileBeanFactory implements BeanFactory {

    private Map<Class<? extends Base>, Object> beanRegist;

    private static MobileBeanFactory mobileBeanFactory;

    public static MobileBeanFactory getInstance() {
        MobileBeanFactory instance = null;
        if (mobileBeanFactory == null) {
            synchronized (MobileBeanFactory.class) {
                if (instance == null) {
                    instance = new MobileBeanFactory();
                    mobileBeanFactory = instance;
                }
            }
        }
        return mobileBeanFactory;
    }

    private MobileBeanFactory() {
        beanRegist = new HashMap<Class<? extends Base>, Object>();
    }

    @Override
    public void initAdvanceBeans() {
        beanRegist.put(Measure.class, new MeasureDaoManager());
        beanRegist.put(Form.class, new FormDaoManager());
    }

    @Override
    public <T extends BaseDao> T getDaoManagerByBeanClass(Class<? extends Base> clazz) {
        if (beanRegist.containsKey(clazz)) {
            return (T) beanRegist.get(clazz);
        }
        throw new RuntimeException("class not regist. class = " + clazz.getName());
    }
}
