package com.archer.note;

import android.app.Application;
import android.content.Context;

import com.archer.note.db.DaoMaster;
import com.archer.note.db.DaoSession;
import com.archer.note.db.NoteDB;
import com.archer.note.db.NoteType;

/**
 * 继承Application
 * Created by 84113 on 2016/4/1.
 */
public class NoteApplication extends Application {

    private static Context context;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        if (NoteDB.getAllNoteTypeCount() < 1)
            NoteDB.addNoteType(new NoteType(getString(R.string.default_note_type)));
    }

    public static Context getNoteApplication() {
        return context;
    }

    /**
     * 取得DaoMaster
     *
     * @param context 上下文
     * @return DaoMaster
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, "note_db", null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @return DaoSession
     */
    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

}
