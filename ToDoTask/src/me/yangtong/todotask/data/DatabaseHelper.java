package me.yangtong.todotask.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by yangtong on 2015/11/23.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TB_NAME = "task";
    //	Task 在database中保存的字段
    public static final String ID = "id";
    public static final String LEVEL = "level";    
    public static final String TITLE = "title";
    public static final String DESCRIPTION ="description";
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";
    public static final String STATUS = "status";
    public static final String CLOED_TIME = "closedTime";
    
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                        TB_NAME + "(" +
                        ID + " integer primary key autoincrement," +
                        LEVEL + " integer," +
                        TITLE + " text," +
                        DESCRIPTION +" text," +
                        START_TIME + " bigint," +
                        END_TIME + " bigint," +
                        STATUS + " integer," +
                        CLOED_TIME + " bigint" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	//TODO 这个升级问题大哦，这样做直接把之前的数据都抛弃了
        db.execSQL("DROP TABLE IF EXISTS "+TB_NAME);
        onCreate(db);
    }
}
