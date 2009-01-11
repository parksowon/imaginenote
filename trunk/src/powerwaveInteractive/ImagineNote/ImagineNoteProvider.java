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
 * http://code.google.com/intl/ko-KR/android/reference/android/content/ContentProvider.html
 * Content Provider는 application에 content를 제공하는 역할을 한다. 
 * data를 encapsulate하고 그 데이터를 단일 ContentResolver를 통해 
 * 응용프로그램에 제공한다. 여러 application사이에 프로그램을 공유하기
 * 위해서는 Content Provider를 사용해야 한다. 데이터를 공유할 필요가 
 * 없다면 SQLiteDatabase를 이용하여 데이터베이스에 직접 접근하면 된다.
 * 
 * Android's content provider들은 그들의 clients와 느슨하게 연결되어 있다. 
 * 각각의 content provider들은 처리할 수 있는 데이터 타입을 표현하는 
 * URI(unique string identifier)를 노출하며, client는 URI에 대응하여
 * 데이터를 저장하거나 얻어오는 동작을 정의해야 한다. 
 * 
 * 각각의 content provider는 클라이언트가 query/add/update/delete
 * 하기 위해 사용하는 URI를 노출한다. 
 * URI는 두가지의 형식을 가진다. 
 *
 * ContentResolver를 통해 생성되면 시스템은 주어진 uri에 대한 authority를 
 * 검사한 후 요청을 등록된 content provider로 authority와 함께 넘긴다.  
 * 
 * You can see some of Android's native content providers in the provider package.
 * Check 'android.provider' packages. 
 * 
 * The primary methods that need to be implemented are : 
 * query(Uri, String[], String, String[], String); 	// returns data to the caller
 * insert(Uri, ContentValues);						// inserts new data into the content provider
 * update(Uri, ContentValues, String, String[]);	// updates existing data int the content provider
 * delete(Uri, String, String[]);					// deletes data from the content provider
 * getType(Uri);									// returns the MIME type of data int the content providre
 * 
 * This class takes care of cross process calls so subclasses don't have to worry 
 * about which process a request is coming from.
 */
public class ImagineNoteProvider extends ContentProvider{

}
