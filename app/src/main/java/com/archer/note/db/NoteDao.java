package com.archer.note.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "NOTES".
*/
public class NoteDao extends AbstractDao<Note, Long> {

    public static final String TABLENAME = "NOTES";

    /**
     * Properties of entity Note.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Status = new Property(1, String.class, "status", false, "STATUS");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property TextContent = new Property(3, String.class, "textContent", false, "TEXT_CONTENT");
        public final static Property VoiceContent = new Property(4, String.class, "voiceContent", false, "VOICE_CONTENT");
        public final static Property Type = new Property(5, Long.class, "type", false, "TYPE");
        public final static Property Date = new Property(6, java.util.Date.class, "date", false, "DATE");
    };

    private DaoSession daoSession;

    private Query<Note> noteType_NotesQuery;

    public NoteDao(DaoConfig config) {
        super(config);
    }
    
    public NoteDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NOTES\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"STATUS\" TEXT," + // 1: status
                "\"TITLE\" TEXT NOT NULL ," + // 2: title
                "\"TEXT_CONTENT\" TEXT," + // 3: textContent
                "\"VOICE_CONTENT\" TEXT," + // 4: voiceContent
                "\"TYPE\" INTEGER," + // 5: type
                "\"DATE\" INTEGER);"); // 6: date
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NOTES\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Note entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(2, status);
        }
        stmt.bindString(3, entity.getTitle());
 
        String textContent = entity.getTextContent();
        if (textContent != null) {
            stmt.bindString(4, textContent);
        }
 
        String voiceContent = entity.getVoiceContent();
        if (voiceContent != null) {
            stmt.bindString(5, voiceContent);
        }
 
        Long type = entity.getType();
        if (type != null) {
            stmt.bindLong(6, type);
        }
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(7, date.getTime());
        }
    }

    @Override
    protected void attachEntity(Note entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Note readEntity(Cursor cursor, int offset) {
        Note entity = new Note( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // status
            cursor.getString(offset + 2), // title
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // textContent
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // voiceContent
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // type
            cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)) // date
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Note entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setStatus(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTitle(cursor.getString(offset + 2));
        entity.setTextContent(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setVoiceContent(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setType(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setDate(cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Note entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Note entity) {
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
    
    /** Internal query to resolve the "notes" to-many relationship of NoteType. */
    public List<Note> _queryNoteType_Notes(Long type) {
        synchronized (this) {
            if (noteType_NotesQuery == null) {
                QueryBuilder<Note> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Type.eq(null));
                queryBuilder.orderRaw("T.'DATE' ASC");
                noteType_NotesQuery = queryBuilder.build();
            }
        }
        Query<Note> query = noteType_NotesQuery.forCurrentThread();
        query.setParameter(0, type);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getNoteTypeDao().getAllColumns());
            builder.append(" FROM NOTES T");
            builder.append(" LEFT JOIN NOTE_TYPE T0 ON T.\"TYPE\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Note loadCurrentDeep(Cursor cursor, boolean lock) {
        Note entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        NoteType noteType = loadCurrentOther(daoSession.getNoteTypeDao(), cursor, offset);
        entity.setNoteType(noteType);

        return entity;    
    }

    public Note loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Note> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Note> list = new ArrayList<Note>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Note> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Note> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
