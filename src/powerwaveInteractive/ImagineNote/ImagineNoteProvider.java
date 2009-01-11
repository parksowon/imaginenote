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
 */

public class ImagineNoteProvider extends ContentProvider{
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
