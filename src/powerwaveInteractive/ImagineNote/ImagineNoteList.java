package powerwaveInteractive.ImagineNote;

import android.app.Activity;
import android.app.ListActivity;
import android.content.*;
import android.content.Intent.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import java.util.*; //most of data structure class is in here!

import powerwaveInteractive.ImagineNote.*;
public class ImagineNoteList extends Activity implements View.OnClickListener {

	
	public static final String TAG = "ImagineNoteList";
	/**
	 * List에서는 _ID와 TITLE만 필요함
	 */
	private static final String[] PROJECTION = new String[] {
        Note._ID, // 0
        Note.TITLE, // 1
	};
		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listpage);
                
        /**
         * Intent 초기화 : 
         * 
         * 프로그램 시작 시에는 Intent가 없으므로 기본 Intent를 설정한다.
         * 이후 창 전환시 Intent를 변경하여 특정 상황에 다른 동작을 하도록
         * 코드를 작성할 수 있다.
         */        
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(Note.CONTENT_URI);
            Log.println(2, ImagineNoteList.TAG, "Initialized URI=" + intent.getDataString());
        }
        
        ListView lv = (ListView) findViewById(R.id.Notelist);
        lv.setOnItemClickListener(_listItemClickListener);
        
        Cursor cursor = managedQuery(getIntent().getData(),
        		ImagineNoteList.PROJECTION,
        		null, 
        		null,
                Note.DEFAULT_SORT_ORDER);        
        
        /**
         * android.widget.SimpleCursorAdaptor : 
         * 
         * 커서의 column을 XML파일에 정의된 TextView나 ImageView로 맵하는 간단한 adaptor. 원하는 column을 명시할 수 있고,
         * 어떤 view가 그 column을 표시할 것인지 명시할 수 있다.
         */
        
        Log.println(2,ImagineNoteList.TAG, String.valueOf(cursor.getCount()).toString());
        if (cursor.getCount() > 0) {
	        cursor.moveToFirst();
	        Log.println(2,ImagineNoteList.TAG, String.valueOf(cursor.getCount()).toString());
	        while( cursor.isLast() == false) {
	        	Log.println(2, ImagineNoteList.TAG, cursor.getString(0) + " " + cursor.getString(1) );	        	
	        	cursor.moveToNext();
	        }
        }
        
        
        // Used to map notes entries from the database to views
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
        		this, 
        		R.layout.notelist_item, 
        		cursor,
                new String[] { Note.TITLE },			// List of column names representing the data to bind to the UI
                new int[] { android.R.id.text1 }		// The views that should display column in the "from" parameters. 
        );        
        lv.setAdapter(adapter);
    }
    
    /**
     * Event handler for ListView OnClick event.  
     */
    private OnItemClickListener _listItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, 	// parent - The AdapterView where the click happened
				View arg1, 								// view
				int arg2,								// position - position of view in the adapter
				long arg3								// id - the row id of the item that was clicked
				 ) 
		{
			// TODO Auto-generated method stub
			Log.println(2, ImagineNoteList.TAG, "list clicked " + "view=" + arg1.toString() + " position=" + arg2 + " id=" + arg3);
			Intent intent = new Intent();
			intent.setClassName("powerwaveInteractive.ImagineNote", "powerwaveInteractive.ImagineNote.ImagineNoteInput" );        	
        	intent.setAction(Intent.ACTION_EDIT);
        	
        	intent.setData( ContentUris.withAppendedId(Note.CONTENT_URI, arg3) );
        	startActivity(intent);
		}    	
    };
    
    
    /**
     * constants for ID of menu : 
     */
    private static final int INSERT_ID = Menu.FIRST;    
    private static final int DELETE_ID = Menu.FIRST + 1;
    private static final int EDIT_ID = Menu.FIRST + 2;

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        
        /*
         * 처음 시작일 경우 intent의 데이터(URI)를 기본 Note 개체로 맞춰준다.
         */
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(Note.CONTENT_URI);
        }

        // Build the menus that are shown when editing.
      
        menu.add(0, INSERT_ID, 0, R.string.menu_insert)
        	.setShortcut('0', 'r')
            .setIcon(android.R.drawable.ic_menu_add);
        
        /*
        menu.add(0, DELETE_ID, 0, R.string.menu_delete)
    		.setShortcut('1', 'd')
    		.setIcon(android.R.drawable.ic_menu_delete);
        
        menu.add(0, EDIT_ID, 0, R.string.menu_edit)
    		.setShortcut('2', 'e')
    		.setIcon(android.R.drawable.ic_menu_edit);
         */
        
        // Build the menus that are shown when inserting.      
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
    	final Intent intent;
    	final Uri uri;
    	ListView lv = (ListView)this.findViewById(R.id.Notelist);
    	// Handle all of the possible menu actions.
        switch (item.getItemId()) {
        
        	
	        case DELETE_ID:            
	        	intent = new Intent(this, powerwaveInteractive.ImagineNote.ImagineNoteInput.class);        
	        	intent.setAction(Intent.ACTION_DELETE);
	        	
	        	/**
	        	 * Intent생성은 아래와 같은 방법으로 할 수 있다. 
	        	 * 
	        	 * public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/Note");
	        	 * 
	        	 * Uri에는 문자열을 그대로 넣을 수는 없고 parse()함수 등을 이용하여 1차 가공한다.
	        	 * intent에 넘길 때 뒤에 index을 붙일 수 있다.
	        	 * 
	        	 * intent.setData( ContentUris.withAppendedId(Note.CONTENT_URI, 5678 ) );
	        	 * 
	        	 * index는  _ID값을 넘겨 사용하면 유용할 것임.
	        	 * 
	        	 * 위 코드를 intent를 사용할 때마다 붙여넣을 수 있으나 Notepad예제에서는 데이터를 상징하는 개체를 
	        	 * 정의하고 그 개체 안에 위 코드를 넣어 개체의 Uri를 상징하도록 하였음. 
	        	 * 위 방법이 좋은 것 같음;; 
	        	 */
	        	
	        	intent.setData( ContentUris.withAppendedId(Note.CONTENT_URI, 0 ) );
	        	startActivity(intent);
	            break;
	        
	        case INSERT_ID:
	        	intent = new Intent(this, powerwaveInteractive.ImagineNote.ImagineNoteInput.class);        
	        	intent.setAction(Intent.ACTION_INSERT);
	        	intent.setData( this.getIntent().getData() );
	        	startActivity(intent);            
	            break;
	            
	        case EDIT_ID:        	        	        	       	
	        	intent = new Intent(this, powerwaveInteractive.ImagineNote.ImagineNoteInput.class);        	
	        	intent.setAction(Intent.ACTION_EDIT);
	        	/*
	        	 * FIXME : 아래에서 0은 선택된 노트의 index로 채워져야함. 
	        	 */
	        	intent.setData( ContentUris.withAppendedId(Note.CONTENT_URI, 0) );
	        	startActivity(intent);            
	        	break;
        	
        }        
        return super.onOptionsItemSelected(item);
    }

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}     
}