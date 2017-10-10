package com.ebest.frame.baselib.greendao.base;

import com.ebest.frame.baselib.table.Base;

/**
 * Created by ztw on 2017/10/9.
 */

public interface BeanFactory {

    public void initAdvanceBeans();

    public <T extends BaseDao> T getDaoManagerByBeanClass(Class<? extends Base> clazz) throws Exception;

}
