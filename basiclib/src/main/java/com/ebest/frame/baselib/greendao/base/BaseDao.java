package com.ebest.frame.baselib.greendao.base;

import android.database.Cursor;
import android.util.Log;

import com.ebest.frame.baselib.greendao.DaoSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ztw on 2017/9/29.
 */

public class BaseDao<T> {
    public static final String TAG = BaseDao.class.getSimpleName();
    public DaoManager manager;
    public DaoSession daoSession;

    public BaseDao() {
        manager = DaoManager.getInstance();
        daoSession = manager.getDaoSession();
    }

    /**
     * 插入单个对象
     *
     * @param object
     * @return
     */
    public boolean insertObject(T object) {
        boolean flag = false;
        try {
            flag = manager.getDaoSession().insert(object) != -1 ? true : false;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return flag;
    }

    /**
     * 插入多个对象，并开启新的线程
     *
     * @param objects
     * @return
     */
    public boolean insertMultObject(final List<T> objects) {
        boolean flag = false;
        if (null == objects || objects.isEmpty()) {
            return false;
        }
        try {
            manager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (T object : objects) {
                        manager.getDaoSession().insertOrReplace(object);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            flag = false;
        } finally {
//            manager.CloseDataBase();
        }
        return flag;
    }

    /**
     * 以对象形式进行数据修改
     * 其中必须要知道对象的主键ID
     *
     * @param object
     * @return
     */
    public void updateObject(T object) {

        if (null == object) {
            return;
        }
        try {
            manager.getDaoSession().update(object);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 批量更新数据
     *
     * @param objects
     * @return
     */
    public void updateMultObject(final List<T> objects, Class clss) {
        if (null == objects || objects.isEmpty()) {
            return;
        }
        try {

            daoSession.getDao(clss).updateInTx(new Runnable() {
                @Override
                public void run() {
                    for (T object : objects) {
                        daoSession.update(object);
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }


    /**
     * 删除某个数据库表
     *
     * @param clss
     * @return
     */
    public boolean deleteAll(Class clss) {
        boolean flag = false;
        try {
            manager.getDaoSession().deleteAll(clss);
            flag = true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            flag = false;
        }
        return flag;
    }

    /**
     * 删除某个对象
     *
     * @param object
     * @return
     */
    public void deleteObject(T object) {
        try {
            daoSession.delete(object);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 异步批量删除数据
     *
     * @param objects
     * @return
     */
    public boolean deleteMultObject(final List<T> objects, Class clss) {
        boolean flag = false;
        if (null == objects || objects.isEmpty()) {
            return false;
        }
        try {

            daoSession.getDao(clss).deleteInTx(new Runnable() {
                @Override
                public void run() {
                    for (T object : objects) {
                        daoSession.delete(object);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            flag = false;
        }
        return flag;
    }


    /**
     * 获得某个表名
     *
     * @return
     */
    public String getTablename(Class object) {
        return daoSession.getDao(object).getTablename();
    }

    /**
     * 根据主键ID来查询
     *
     * @param id
     * @return
     */
    public T queryById(long id, Class object) {
        return (T) daoSession.getDao(object).loadByRowId(id);
    }

    /**
     * 查询某条件下的对象
     *
     * @param object
     * @return
     */
    public List<T> queryObject(Class object, String where, String... params) {
        Object obj = null;
        List<T> objects = null;
        try {
            obj = daoSession.getDao(object);
            if (null == obj) {
                return null;
            }
            objects = daoSession.getDao(object).queryRaw(where, params);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return objects;
    }

    /**
     * 查询所有对象
     *
     * @param object
     * @return
     */
    public List<T> queryAll(Class object) {
        List<T> objects = null;
        try {
            objects = (List<T>) daoSession.getDao(object).loadAll();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return objects;
    }

    public List<String> getDbTables(){
        Cursor cursor = daoSession.getDatabase().rawQuery("SELECT name FROM sqlite_master WHERE type='table' ORDER BY name", null);
        List<String> tables = new ArrayList<String>();
        while (cursor.moveToNext()){
            tables.add(cursor.getString(cursor.getColumnIndex("name")));
        }
        cursor.close();
        return tables;
    }

    /**
     * 关闭数据库一般在Odestory中使用
     */
    public void CloseDataBase() {
        manager.closeDataBase();
    }
}
