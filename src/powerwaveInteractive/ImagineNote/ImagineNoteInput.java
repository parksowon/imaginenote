package powerwaveInteractive.ImagineNote;

import android.app.Activity;
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.*; //most of data structure class is in here!

public class ImagineNoteInput extends Activity {
	
	/**
	 * custom action strings list below : 
	 */
	//public static final String INTENT_EDIT = ""; 
	private static final String TAG = "ImagineNoteInput";

	private Uri mUri;
    private Cursor mCursor;
    private int index;
        
    /**
     * FIXME:
     * 현재 open된 Note에 대한 데이터(_id/본문/title). 일단 클래스 멤버변수에 저장하고 나중에 
     * 다른 방법을 찾아볼것. 
     */
   
    private int _id = 0;
    private String _title="";
    private String _text = "";
    
    /**
     * Standard projection for the interesting columns of a normal note.
     */
    private static final String[] PROJECTION = new String[] {
            Note._ID, // 0
            Note.NOTE, // 1
            Note.TITLE
    };
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main); 
    
    	final Intent intent = getIntent();
    	
    	// get passed intent action & data.
        final String action = intent.getAction();
        final Uri uri = intent.getData();
        Log.println(2, this.TAG , "intent action=" +  action + " data=" + uri.toString());
                
        /**
         * Activity로 넘어온 intent action string을 사용해서 생성 시 초기화 및 이후
         * 동작을 구분해서 정의할 수 있다. 특정 action이 특정한 데이터를 넘겨주도록 
         * 정의되어 있다면 intent에서 데이터를 추출해서 사용하면 된다.(호출하는 곳에서
         * 잘 넘겼는지 확인하고~)
         */
        if (Intent.ACTION_EDIT.equals(action)) {        	
        	Log.println(2,ImagineNoteInput.TAG, "received action matches=" + "ACTION_EDIT");
        	mUri = intent.getData();
        }
        else if (Intent.ACTION_INSERT.equals(action)) {
        	Log.println(2,ImagineNoteInput.TAG, "received action matches=" + "ACTION_INSERT");
        	
        	// make new note. (URI를 인자로 해서 insert 호출)
        	mUri = getContentResolver().insert(intent.getData(), null);
        	

            // If we were unable to create a new note, then just finish
            // this activity.  A RESULT_CANCELED will be sent back to the
            // original activity if they requested a result.
            if (mUri == null) {
                Log.e(TAG, "Failed to insert new note into " + getIntent().getData());
                finish();
                return;
            }
                    
        }
        else if (Intent.ACTION_DELETE.equals(action)) {
        	Log.println(2, "received action matches=", "ACTION_DELETE");
        	
        	/** 
        	 * delete selected note and finish.
        	 * maybe showing alert message box looks better... ha ha! 
        	 */
        	finish();
        }
        else {
        	/**
        	 * unknown or not-supported action. show error message. 
        	 */
        }
        
        // Get the note!
        /**
         * 관리되는 cursor를 얻기 위해 Activity.managedQuery()함수를 사용해야 한다.
         * 
         * public final Cursor  managedQuery(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder); 
         * 
         * uri : query할 content provider의 URI
         * projection : 돌려받을 column의 리스트 
         * selection : SQL WHERE 구문
         * selectionArgs : selection에 대한 인자, (selection의 ?를 이것으로 대체함)
         * sortOrder : SQL ORDER BY 구문
         */
        mCursor = managedQuery(mUri, PROJECTION, null, null, null);
                
        /**
         * Set Event Handler for controls : 
         * Get reference for controls by calling findViewById() 
         * with control's id and call reference's member function
         * setOnClickListener(). with Listener object.
         * 
         */
        
        /**
         * 이전에 추가했던 버튼은 메뉴로 대체함. 아래 코드는 추후 UI 내에 버튼을
         * 사용해서 기능을 추가할 경우에 참조하여 사용할것.
         */
        
        /*
        Button button = (Button)findViewById(R.id.button_save); 
        button.setOnClickListener(_buttonSaveListener);
        
        button = (Button)findViewById(R.id.button_clear);
        button.setOnClickListener(_buttonClearListener);
        
        button = (Button)findViewById(R.id.button_show_list);
        button.setOnClickListener(_buttonShowListListener);
        */
        
        
        /**
         * Cursor을 얻은 후에는 반드시 아래처럼 해서 커서 위치를 초기화해야 한다.
         * 아래 코드 없으면 런타임 에러 발생.
         */
        mCursor.moveToFirst();        	
        
        EditText et = (EditText)findViewById(R.id.text_memo);
        if (et != null) {
        	et.setText(mCursor.getString(1));
        }
        else {
        	Log.println(2, ImagineNoteInput.TAG, "can't get EditText!!");
        }
        
        // Get info from cursor. check PROJECTION.
        this._id 	= mCursor.getInt(0);
        this._text 	= mCursor.getString(1);
        this._title = mCursor.getString(2);
        
        this.setTitle(mCursor.getString(2));
        	
    }
    
    /**
     * @deprecated
     * event handler definition for button_save. get current text and push data stack.
     * @return void.
     */
    private OnClickListener _buttonSaveListener = new OnClickListener() {
 
		public void onClick(View v) {
			// TODO Auto-generated method stub
			android.util.Log.i("androidtest01", "onClick() called");
		}    	
    };
    
    /**
     * @deprecated
     * event handler definition for button_clear. clears all text in text_memo. 
     * @return void.
     */
    private OnClickListener _buttonClearListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			TextView textView = (TextView)findViewById(R.id.text_memo);
			textView.setText("");
		}    	
    };
    
    /**
     * @deprecated
     * event handler definition for button_clear. clears all text in text_memo. 
     * @return void.
     */
    private OnClickListener _buttonShowListListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			/**
			 * Example for opening a new screen : 
			 * make intent, adding data(if needed), and call 
			 * startActivity with intent.
			 */

			/*
			 * Intent(Context, Class<?>)생성자를 사용하여 Intent생성함. 특정 component를 위한 Intent를 생성한다.
			 * 다른 모든 field들(action,data,type,class)는 null로 설정된다.
			 */
			//Intent myIntent = new Intent(ImagineNoteInput.this, ImagineNoteList.class);
			
			//Log.println(2, "trace", "action=" + myIntent.getAction() );
				
			// or use below 2 code line instead. (same function as above code.)			
			/*			
			Intent myIntent = new Intent();
			myIntent.setClassName("powerwaveInteractive.ImagineNote", "powerwaveInteractive.ImagineNote.ImagineNoteList");
			*/
			
			//startActivity(myIntent);
			finish();
		}    	
    };
    
    // menu IDs for ImagineNoteInput activity :    
    private static final int DISCARD_ID = Menu.FIRST + 0;    
    private static final int UNDO_ID = Menu.FIRST + 1;    
    private static final int SET_TITLE_ID = Menu.FIRST + 2;
    private static final int SAVE_ID = Menu.FIRST + 3;
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // Build the menus that are shown when editing.
      
            menu.add(0, DISCARD_ID, 0, R.string.menu_discard)
            	.setShortcut('0', 'd')
                .setIcon(android.R.drawable.ic_menu_delete);
            
            menu.add(0, UNDO_ID, 0, R.string.menu_undo)
            	.setShortcut('1', 'u')
            	.setIcon(android.R.drawable.ic_menu_revert);
            
            menu.add(0, SET_TITLE_ID, 0, R.string.menu_set_title)
	        	.setShortcut('1', 'a')
	        	.setIcon(android.R.drawable.ic_menu_agenda);
            
            menu.add(0, SAVE_ID, 0, R.string.menu_save)
	        	.setShortcut('1', 's')
	        	.setIcon(android.R.drawable.ic_menu_save);

        // Build the menus that are shown when inserting.      
        return true;
    }
    

    @Override
    protected void onResume() {
        super.onResume();
        
        /**
         * ImagineNoteTitleEdit에서 복귀할 경우, Title이 수정되었을 수 있으니
         * 다시 읽어서 갱신한다.
         */
        Cursor tmpCursor = getContentResolver().query(mUri, PROJECTION, null, null, Note.DEFAULT_SORT_ORDER);
        tmpCursor.moveToFirst();
        this.setTitle(tmpCursor.getString(2));        
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle all of the possible menu actions.
    	StringBuilder sb = new StringBuilder();
    	sb.delete(0, sb.length());
    	sb.append(item.getItemId());
    	Log.println(2, "menu selection event handling=", sb.toString());
    	int count;
    	Intent intent;
    	
    	EditText mText = (EditText)this.findViewById(R.id.text_memo);
    	
        switch (item.getItemId()) {
        case DISCARD_ID:            
        	/**
        	 * mUri에 _ID값이 붙어있기 때문에 따로 where절을 붙이지 않아도 된다.
        	 */
        	count = getContentResolver().delete(mUri, null, null);
        	Log.println(2, ImagineNoteInput.TAG, count + " rows deleted.");       	
            finish();
            break;
        case UNDO_ID:        	
        	/**
        	 * Text의 내용을 원본으로 되돌린다.
        	 */
        	mText.setText(this._text);       
            break;
        case SET_TITLE_ID:     	
        	intent = this.getIntent();
        	intent.setClass(this, powerwaveInteractive.ImagineNote.ImagineNoteTitleEditor.class);
        	intent.setData(mUri);
        	startActivity(intent);
            break;
        case SAVE_ID:
        	
        	/**
        	 * title을 입력받고 저장하는 기능을 넣음. 
        	 */
        	// The user is going somewhere else, so make sure their current
            // changes are safely saved away in the provider.  We don't need
            // to do this if only editing.
        	
        	
        	// Get EditText View :        	
        	
        	
        	// Get the cursor.
        	if (mCursor == null){
        		mCursor = managedQuery(mUri,
        				PROJECTION,
        				null, 
        				null, 
        				null);
        	}
        	
            if (mCursor != null) {
            	
            	// Get text from TextEdit control : 
                String text = mText.getText().toString();
                int length = text.length();

                // Make ContentValues :  
                ContentValues values = new ContentValues();

                
                /**
                 * 최초 생성인지 Edit인지 확인해야 함
                 */
                
                intent = getIntent();
                String action = intent.getAction();
                String title ="";
            	int spaceIndex = 0;
            	int titleLength = 0;
            	int index = 0;
            	
                if (Intent.ACTION_INSERT.equals(action)) {

                    // Get Title :                	
                	index = text.indexOf(' ', 30);
                	if (index >= 30) {
                		titleLength = (int) Math.min((int)30 * 1.5 , index);
                	}
                	else{
                		titleLength = text.length();
                	}
                	            	
                	title = text.substring(0, titleLength);
                	
                	/**
                	 * 저장되어 있는 Title이 "Untitled"이나 ""일 경우에만 제목을 갱신할것 
                	 */
                	
                	Cursor tmpCursor = getContentResolver().query(mUri, PROJECTION, null, null, null);
                	
                	if (tmpCursor != null) {
                		tmpCursor.moveToFirst();
                		if (tmpCursor.getString(2).length() == 0 || tmpCursor.getString(2).compareTo(Note.DEFAULT_TITLE) == 0 ) {
                			values.put(Note.TITLE, title);
                		}
                	}else {
                		values.put(Note.TITLE, title);
                	}
                	tmpCursor.close();
                }
                else { 
                	/**
                	 * ACTION_EDIT일 경우인데, 이럴 경우 처음에 불러온 _title 을 그냥 사용한다.
                	 * (사용자가 따로 메뉴 버튼을 눌러 this._title값을 변경하도록 만들것)
                	 */
                	
                }
                
                Log.println(2, ImagineNoteInput.TAG, "set note title=" + title);
                
                        
            
                // Write our text back into the provider.            
                values.put(Note.NOTE, text);

                                
                /**
                 * public final int update(mUri, 		 
                 * 		ContentValues values,
                 * 		String where,
                 * 		String[] selectionArgs);
                 * 
                 * 정의되어 있는 Provider에 정의되어 있는 update()함수를 호출한다.  
                 * public int update(Uri uri, ContentValues values, String where, String[] whereArgs);
                 * 
                 * contentProvider에 update동작이 정의되어 있어야 한다.
                 */
                
                // Commit all of our changes to persistent storage. When the update completes
                // the content provider will notify the cursor of the change, which will
                // cause the UI to be updated.  

                index = getContentResolver().update(mUri,
                		values, 
                		null,
                		null);
                
                Log.println(2, ImagineNoteInput.TAG, "returned index=" + String.valueOf(index));
               
            }else {
            	Log.println(2, ImagineNoteInput.TAG,"Can't get Cursor!");
            }

            finish();
            break;
        }
        return super.onOptionsItemSelected(item);
    }    
}