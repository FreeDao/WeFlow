package com.cmmobi.railwifi.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.cmmobi.railwifi.dao.MileageRecord;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table MILEAGE_RECORD.
*/
public class MileageRecordDao extends AbstractDao<MileageRecord, Long> {

    public static final String TABLENAME = "MILEAGE_RECORD";

    /**
     * Properties of entity MileageRecord.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Starting = new Property(1, String.class, "starting", false, "STARTING");
        public final static Property Ending = new Property(2, String.class, "ending", false, "ENDING");
        public final static Property Mileage = new Property(3, String.class, "mileage", false, "MILEAGE");
        public final static Property Hours = new Property(4, String.class, "hours", false, "HOURS");
        public final static Property Date = new Property(5, String.class, "date", false, "DATE");
        public final static Property Train_num = new Property(6, String.class, "train_num", false, "TRAIN_NUM");
        public final static Property Points = new Property(7, String.class, "points", false, "POINTS");
    };


    public MileageRecordDao(DaoConfig config) {
        super(config);
    }
    
    public MileageRecordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'MILEAGE_RECORD' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'STARTING' TEXT," + // 1: starting
                "'ENDING' TEXT," + // 2: ending
                "'MILEAGE' TEXT," + // 3: mileage
                "'HOURS' TEXT," + // 4: hours
                "'DATE' TEXT," + // 5: date
                "'TRAIN_NUM' TEXT," + // 6: train_num
                "'POINTS' TEXT);"); // 7: points
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'MILEAGE_RECORD'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, MileageRecord entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String starting = entity.getStarting();
        if (starting != null) {
            stmt.bindString(2, starting);
        }
 
        String ending = entity.getEnding();
        if (ending != null) {
            stmt.bindString(3, ending);
        }
 
        String mileage = entity.getMileage();
        if (mileage != null) {
            stmt.bindString(4, mileage);
        }
 
        String hours = entity.getHours();
        if (hours != null) {
            stmt.bindString(5, hours);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(6, date);
        }
 
        String train_num = entity.getTrain_num();
        if (train_num != null) {
            stmt.bindString(7, train_num);
        }
 
        String points = entity.getPoints();
        if (points != null) {
            stmt.bindString(8, points);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public MileageRecord readEntity(Cursor cursor, int offset) {
        MileageRecord entity = new MileageRecord( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // starting
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // ending
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // mileage
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // hours
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // date
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // train_num
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // points
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, MileageRecord entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setStarting(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setEnding(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMileage(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setHours(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDate(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTrain_num(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setPoints(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(MileageRecord entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(MileageRecord entity) {
        if(entity != null) {
            return entity.getId();
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
