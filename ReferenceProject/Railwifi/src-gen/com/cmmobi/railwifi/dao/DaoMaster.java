package com.cmmobi.railwifi.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

import com.cmmobi.railwifi.dao.PassengerDao;
import com.cmmobi.railwifi.dao.HistoryOrderFormDao;
import com.cmmobi.railwifi.dao.TravelOrderInfoDao;
import com.cmmobi.railwifi.dao.FavDao;
import com.cmmobi.railwifi.dao.PlayHistoryDao;
import com.cmmobi.railwifi.dao.DownloadHistoryDao;
import com.cmmobi.railwifi.dao.ReceiptAddrDao;
import com.cmmobi.railwifi.dao.MileageRecordDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 12): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 12;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        PassengerDao.createTable(db, ifNotExists);
        HistoryOrderFormDao.createTable(db, ifNotExists);
        TravelOrderInfoDao.createTable(db, ifNotExists);
        FavDao.createTable(db, ifNotExists);
        PlayHistoryDao.createTable(db, ifNotExists);
        DownloadHistoryDao.createTable(db, ifNotExists);
        ReceiptAddrDao.createTable(db, ifNotExists);
        MileageRecordDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        PassengerDao.dropTable(db, ifExists);
        HistoryOrderFormDao.dropTable(db, ifExists);
        TravelOrderInfoDao.dropTable(db, ifExists);
        FavDao.dropTable(db, ifExists);
        PlayHistoryDao.dropTable(db, ifExists);
        DownloadHistoryDao.dropTable(db, ifExists);
        ReceiptAddrDao.dropTable(db, ifExists);
        MileageRecordDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(PassengerDao.class);
        registerDaoClass(HistoryOrderFormDao.class);
        registerDaoClass(TravelOrderInfoDao.class);
        registerDaoClass(FavDao.class);
        registerDaoClass(PlayHistoryDao.class);
        registerDaoClass(DownloadHistoryDao.class);
        registerDaoClass(ReceiptAddrDao.class);
        registerDaoClass(MileageRecordDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}
