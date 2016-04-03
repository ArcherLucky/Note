package com.archer.note.db;

import com.archer.note.NoteApplication;
import com.archer.note.R;

import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.CountQuery;

/**
 * 常用增删改查封装类
 * Created by 84113 on 2016/4/1.
 */
public class NoteDB {

    private static DaoSession daoSession;
    private static NoteDB noteDB;

    private NoteDB() {
        daoSession = NoteApplication.getDaoSession();
    }

    /**
     * 获取 NoteDB 实例
     * @return NoteDB 实例
     */
    private static NoteDB getInstance() {
        if (noteDB == null) {
            synchronized (NoteDB.class) {
                if (noteDB == null) {
                    noteDB = new NoteDB();
                }
            }
        }
        return noteDB;
    }

    /**
     * 插入一个Note
     * @param note 要插入的Note
     * @return 该Note的Id
     */
    private long _addNote(Note note) {
        return daoSession.getNoteDao().insert(note);
    }

    /**
     * 删除一个Note
     */
    private void _removeNote(Long id) {
        daoSession.getNoteDao().deleteByKey(id);
    }

    /**
     * 修改后保存该Note
     * @param note 要保存的Note
     */
    private void _saveNote(Note note) {
        note.setDate(new Date());
        daoSession.getNoteDao().insertOrReplace(note);
    }

    /**
     * 批量插入或保存Note
     * @param list 需要保存的Note数组
     */
    private void _saveNotes(final List<Note> list) {
        if(list != null && !list.isEmpty()) {
            daoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0; i < list.size(); i++){
                        Note note = list.get(i);
                        daoSession.getNoteDao().insertOrReplace(note);
                    }
                }
            });
        }
    }

    /**
     * 获取所有的Note
     * @return 所有的Note
     */
    private List<Note> _getAllNote() {
        return daoSession.getNoteDao().loadAll();
    }

    /**
     * 获取Note的数量
     * @return
     */
    private long _getNoteCount() {
        return daoSession.getNoteDao().queryBuilder().count();
    }

    /**
     * 获取按时间排序的所有Note
     * @return 所有Note
     */
    private List<Note> _getAllNoteByDate() {
        return daoSession.getNoteDao().queryBuilder().orderDesc(NoteDao.Properties.Date).list();
    }

    /**
     * 获取指定类别下的所有Note
     * @param type 指定的类别
     * @return 该类别下的所有Note
     */
    private List<Note> _getNotesByType(long type) {
        return daoSession.getNoteDao()._queryNoteType_Notes(type);
    }

    /**
     * 添加一个NoteType，如果已存在则抛出异常
     * @param noteType 要添加的NoteType
     */
    private void _addNoteType(NoteType noteType) {
        CountQuery<NoteType> query = daoSession.getNoteTypeDao().queryBuilder()
                .where(NoteTypeDao.Properties.Name.eq(noteType.getName())).buildCount();
        if(query.count() > 0) {
            throw new RuntimeException(NoteApplication.getNoteApplication().getString(R.string.category_already_exists));
        } else {
            daoSession.getNoteTypeDao().insert(noteType);
        }
    }

    /**
     * 删除一个类别
     * @param noteType 要删除的类别
     */
    private void _deleteNoteType(NoteType noteType) {
        daoSession.getNoteTypeDao().delete(noteType);
    }

    /**
     * 批量删除类别
     * @param list 要删除的类别集合
     */
    private void _deleteNoteTypes(final List<NoteType> list) {
        if(list != null && !list.isEmpty()) {
            daoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < list.size(); i++) {
                        NoteType noteType = list.get(i);
                        daoSession.getNoteTypeDao().delete(noteType);
                    }
                }
            });
        }
    }

    /**
     * 保存一个类别
     * @param noteType 要保存的类别
     */
    private void _saveNoteType(NoteType noteType) {
        daoSession.getNoteTypeDao().insertOrReplace(noteType);
    }

    /**
     * 获取所有的类别
     * @return 所有的类别
     */
    private List<NoteType> _getAllNoteType() {
        return daoSession.getNoteTypeDao().loadAll();
    }

    /**
     * 获取所有的类别数量
     * @return 类别数量
     */
    private long _getAllNoteTypeCount() {
        CountQuery<NoteType> countQuery = daoSession.getNoteTypeDao().queryBuilder().buildCount();
        return countQuery.count();
    }

    /********************* 对外的接口开始 ***********************/

    /** 对外的addNote方法 */
    public static long addNote(Note note) {
        return getInstance()._addNote(note);
    }

    /** 对外的removeNote方法 */
    public static void removeNote(Long id) {
        getInstance()._removeNote(id);
    }

    /** 对外的_saveNote方法 */
    public static void saveNote(Note note) {
        getInstance()._saveNote(note);
    }
    /** 对外的_saveNotes方法 */
    public static void saveNotes(final List<Note> list) {
        getInstance()._saveNotes(list);
    }

    /** 对外的_getNoteCount方法 */
    public static long getNoteCount() {
        return getInstance()._getNoteCount();
    }


    /** 对外的_getAllNote方法 */
    public static List<Note> getAllNote() {
        return getInstance()._getAllNote();
    }

    /** 对外的_getNotesByType方法 */
    public static List<Note> getNotesByType(long type) {
        return getInstance()._getNotesByType(type);
    }

    /** 对外的_addNoteType方法 */
    public static void addNoteType(NoteType noteType) {
        getInstance()._addNoteType(noteType);
    }

    /** 对外的getAllNoteByDate方法 */
    public static List<Note> getAllNoteByDate() {
        return getInstance()._getAllNoteByDate();
    }


    /** 对外的_deleteNoteType方法 */
    public static void deleteNoteType(NoteType noteType) {
        getInstance()._deleteNoteType(noteType);
    }

    /** 对外的_deleteNoteTypes方法 */
    public static void deleteNoteTypes(final List<NoteType> list) {
        getInstance()._deleteNoteTypes(list);
    }

    /** 对外的_saveNoteType方法 */
    private void saveNoteType(NoteType noteType) {
        getInstance()._saveNoteType(noteType);
    }

    /** 对外的_getAllNoteType方法 */
    public static List<NoteType> getAllNoteType() {
        return getInstance()._getAllNoteType();
    }

    /** 对外的_getAllNoteTypeCount方法 */
    public static long getAllNoteTypeCount() {
        return getInstance()._getAllNoteTypeCount();
    }


}
