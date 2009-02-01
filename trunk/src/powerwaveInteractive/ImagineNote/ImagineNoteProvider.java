/** 
 * @author firsttimelove@gmail.com
 * Content Provider for ImagineNote App.
 *
 */


package powerwaveInteractive.ImagineNote;



import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import java.util.HashMap;



/**
 * study 내용은 아래 링크 참조
 * http://zestime.cafe24.com/wiki/moin.cgi/Content_Provider?action=show
 * 
 * Provides access to a database of notes. Each note has a title, note itself, 
 * creation date and modified date.
 */
public class ImagineNoteProvider extends ContentProvider{
	
	private static final String TAG = "ImagineNoteProvider";
	
	private static HashMap<String, String> NotesProjectionMap;
	private static final String databaseName="Database.db";
	private static final int databaseVersion=1;
	private static final String tableName_note="Notes";
	
	private static final UriMatcher sUriMatcher;
	private DatabaseHelper dbHelper;
	
	private static final int NOTES = 1;
	private static final int NOTE = 2;
	
	/** 
	 * @author firsttimelove@gmail.com
	 * DB접근은 ContentProvider에 종속적이고 자바 파일 하나에 클래스 하나만
	 * 넣을 수 있으므로 내부에서 정의한 것으로 추정...
	 * 
	 * SQLiteOpenHelper 클래스는 기본으로 onCreate(), onUpgrade() 두 함수를
	 * 재정의 해야 한다.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper{
		
		private static String queryString = "";
		
		DatabaseHelper(Context context) {
			super(context, databaseName, null, databaseVersion);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db){
			
			//db.execSQL()함수를 호출한다.
			queryString = "CREATE TABLE " + tableName_note + "("
				+ Note._ID + " INTEGER PRIMARY KEY,"
				+ Note.TITLE + " TEXT,"
				+ Note.NOTE + " TEXT,"
				+ Note.CREATED_DATE + " INTEGER,"
				+ Note.MODIFIED_DATE + " INTEGER"
				+ ");";
			Log.println(2,ImagineNoteProvider.TAG ,"building database... query=" + queryString);
			db.execSQL(queryString);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			// 기존의 테이블을 삭제하고 onCreate()를 호출하면 된다.
            db.execSQL("DROP TABLE IF EXISTS " + tableName_note);
            onCreate(db);
		}
	}
	
	@Override
    public boolean onCreate() {		
		this.dbHelper = new DatabaseHelper(getContext());
		Log.println(2, ImagineNoteProvider.TAG, "ImagineNoteProvider.onCreate() called.");
        return true;
    }

    @Override
    public Cursor query(Uri uri,
    		String[] projection,
    		String selection,
    		String[] selectionArgs, 
    		String sortOrder) 
    {
    	SQLiteQueryBuilder qb = new SQLiteQueryBuilder();    	

        switch (sUriMatcher.match(uri)) {
	        case NOTES :
	        	qb.setTables(ImagineNoteProvider.tableName_note);
	        	
	        	/**
	        	 * public void setProjectionMap(Map<String, String> columnMap)
	        	 * 
	        	 * query에 대한 projection map을 설정한다. caller가 넘겨준 column name들을 database column name으로 
	        	 * 매핑하는 역할을 한다. 
	        	 *  
	        	 */
	            qb.setProjectionMap(ImagineNoteProvider.NotesProjectionMap);
	            
	        	break;
	        case NOTE :
	        	qb.setTables(ImagineNoteProvider.tableName_note);
	            qb.setProjectionMap(ImagineNoteProvider.NotesProjectionMap);
	        	qb.appendWhere(Note._ID + "=" + uri.getPathSegments().get(1));
	        	break;
        	default : 
        		throw new IllegalArgumentException("Unknown URI " + uri);       		
        		
        }
        Log.println(2, this.TAG, "query string = " + uri.toString() );
	    	
        // order by 구문 설정. 넘어온 값이 있으면 그 값을 설정하고, 없으면 기본값 설정.
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = Note.DEFAULT_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }
        
        // Get the database and run the query : 
        SQLiteDatabase db = dbHelper.getReadableDatabase();        
        Cursor c = qb.query(db, 
        		projection, 
        		selection, 
        		selectionArgs, 
        		null, 
        		null, 
        		orderBy);

        // Tell the cursor what uri to watch, so it knows when its source data changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;        
    }

    @Override
    public String getType(Uri uri) {
        return "";
    }

    public Uri insert(Uri uri, ContentValues initialValues) {
        // Validate the requested uri
        if (sUriMatcher.match(uri) != NOTES) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        Long now = Long.valueOf(System.currentTimeMillis());

        // Make sure that the fields are all set
        if (values.containsKey(Note.CREATED_DATE) == false) {
            values.put(Note.CREATED_DATE, now);
        }

        if (values.containsKey(Note.MODIFIED_DATE) == false) {
            values.put(Note.MODIFIED_DATE, now);
        }

        if (values.containsKey(Note.TITLE) == false) {
            Resources r = Resources.getSystem();
            values.put(Note.TITLE, Note.DEFAULT_TITLE);
        }

        if (values.containsKey(Note.NOTE) == false) {
            values.put(Note.NOTE, "");
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.insert(tableName_note, Note.NOTE, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(Note.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }


    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
    	int count = 0;
    	String noteId;
    	SQLiteDatabase db = dbHelper.getWritableDatabase();
    	switch (sUriMatcher.match(uri)) {
	        case NOTES:
	            count = db.delete(ImagineNoteProvider.tableName_note,	            		
	            		where, 
	            		whereArgs);
	            break;
	
	        case NOTE:
	            noteId = uri.getPathSegments().get(1);
	            count = db.delete(ImagineNoteProvider.tableName_note,
	            		Note._ID + "=" + noteId + (!TextUtils.isEmpty(where)?" AND(" + where + ")" : ""), 
	            		whereArgs);
	            break;
	
	        default:
	            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
    	SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        String noteId;
        switch (sUriMatcher.match(uri)) {
        case NOTES:
            count = db.update(ImagineNoteProvider.tableName_note, values, where, whereArgs);
            break;

        case NOTE:
            noteId = uri.getPathSegments().get(1);
            count = db.update(ImagineNoteProvider.tableName_note, values, Note._ID + "=" + noteId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
    
    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(Note.AUTHORITY, "Notes", NOTES);
        sUriMatcher.addURI(Note.AUTHORITY, "Notes/#", NOTE );        
        
        NotesProjectionMap = new HashMap<String, String>();
        NotesProjectionMap.put(Note._ID, Note._ID);
        NotesProjectionMap.put(Note.TITLE, Note.TITLE);
        NotesProjectionMap.put(Note.NOTE, Note.NOTE);
        NotesProjectionMap.put(Note.CREATED_DATE, Note.CREATED_DATE);
        NotesProjectionMap.put(Note.MODIFIED_DATE, Note.MODIFIED_DATE);
    }
}
