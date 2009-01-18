package powerwaveInteractive.ImagineNote;

import android.app.Activity;
import android.content.*;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.*; //most of data structure class is in here!

public class ImagineNoteInput extends Activity {
	
	
	/**
	 * custom action strings list below : 
	 */
	public static final String INTENT_EDIT = ""; 
	private static final String TAG = "ImagineNoteInput";

	
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
        	Log.println(2, "received action matches=", "ACTION_EDIT");
        }
        else if (Intent.ACTION_INSERT.equals(action)) {
        	Log.println(2, "received action matches=", "ACTION_INSERT");
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle all of the possible menu actions.
    	StringBuilder sb = new StringBuilder();
    	sb.delete(0, sb.length());
    	sb.append(item.getItemId());
    	Log.println(2, "menu selection event handling=", sb.toString());
    	
        switch (item.getItemId()) {
        case DISCARD_ID:            
        	
            finish();
            break;
        case UNDO_ID:        	
        	
            break;
        case SET_TITLE_ID:        	
        	
            break;
        case SAVE_ID:
        	/**
        	 * title을 입력받고 저장하는 기능을 넣음. 
        	 */
        	finish();
            break;
        }
        return super.onOptionsItemSelected(item);
    }    
}