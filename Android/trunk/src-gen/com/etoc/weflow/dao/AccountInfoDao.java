package com.etoc.weflow.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.etoc.weflow.dao.AccountInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table ACCOUNT_INFO.
*/
public class AccountInfoDao extends AbstractDao<AccountInfo, String> {

    public static final String TABLENAME = "ACCOUNT_INFO";

    /**
     * Properties of entity AccountInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Tel = new Property(0, String.class, "tel", true, "TEL");
        public final static Property Userid = new Property(1, String.class, "userid", false, "USERID");
        public final static Property Flowcoins = new Property(2, String.class, "flowcoins", false, "FLOWCOINS");
        public final static Property Isregistration = new Property(3, String.class, "isregistration", false, "ISREGISTRATION");
        public final static Property Makeflow = new Property(4, String.class, "makeflow", false, "MAKEFLOW");
        public final static Property Useflow = new Property(5, String.class, "useflow", false, "USEFLOW");
    };


    public AccountInfoDao(DaoConfig config) {
        super(config);
    }
    
    public AccountInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'ACCOUNT_INFO' (" + //
                "'TEL' TEXT PRIMARY KEY NOT NULL ," + // 0: tel
                "'USERID' TEXT," + // 1: userid
                "'FLOWCOINS' TEXT," + // 2: flowcoins
                "'ISREGISTRATION' TEXT," + // 3: isregistration
                "'MAKEFLOW' TEXT," + // 4: makeflow
                "'USEFLOW' TEXT);"); // 5: useflow
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ACCOUNT_INFO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, AccountInfo entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getTel());
 
        String userid = entity.getUserid();
        if (userid != null) {
            stmt.bindString(2, userid);
        }
 
        String flowcoins = entity.getFlowcoins();
        if (flowcoins != null) {
            stmt.bindString(3, flowcoins);
        }
 
        String isregistration = entity.getIsregistration();
        if (isregistration != null) {
            stmt.bindString(4, isregistration);
        }
 
        String makeflow = entity.getMakeflow();
        if (makeflow != null) {
            stmt.bindString(5, makeflow);
        }
 
        String useflow = entity.getUseflow();
        if (useflow != null) {
            stmt.bindString(6, useflow);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public AccountInfo readEntity(Cursor cursor, int offset) {
        AccountInfo entity = new AccountInfo( //
            cursor.getString(offset + 0), // tel
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // flowcoins
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // isregistration
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // makeflow
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // useflow
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, AccountInfo entity, int offset) {
        entity.setTel(cursor.getString(offset + 0));
        entity.setUserid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFlowcoins(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setIsregistration(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMakeflow(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUseflow(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(AccountInfo entity, long rowId) {
        return entity.getTel();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(AccountInfo entity) {
        if(entity != null) {
            return entity.getTel();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
