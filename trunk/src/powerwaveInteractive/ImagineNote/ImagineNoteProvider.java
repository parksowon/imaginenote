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
	
	private static final String databaseName="ImagineNoteDatabase.db";
	private static final int databaseVersion=1;
	private static final String tableName_note="Notes";
	
	
	/** 
	 * @author firsttimelove@gmail.com
	 * DB접근은 ContentProvider에 종속적이고 자바 파일 하나에 클래스 하나만
	 * 넣을 수 있으므로 내부에서 정의한 것으로 추정...
	 * 
	 * SQLiteOpenHelper 클래스는 기본으로 onCreate(), onUpgrade() 두 함수를
	 * 재정의 해야 한다.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper{
		DatabaseHelper(Context context) {
			super(context, databaseName, null, databaseVersion);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db){
			//db.execSQL()함수를 호출한다.
			/*
			db.execSQL("create table " + tableName_note + "("
					)
					*/
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// 기존의 테이블을 삭제하고 onCreate()를 호출하면 된다.			
		}
	}
	
	@Override
    public boolean onCreate() {
       
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    	return null;        
    }

    @Override
    public String getType(Uri uri) {
        return "";
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
		return uri;        
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        return 0;
    }
}
