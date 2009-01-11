package powerwaveInteractive.ImagineNote;

import android.app.Activity;
import android.content.Intent;
import android.content.*;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.*; //most of data structure class is in here!

public class ImagineNoteInput extends Activity {

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);        
        
        // Listen for button clicks
        Button button = (Button)findViewById(R.id.button_save);
        button.setOnClickListener(_buttonSaveListener);
        
        button = (Button)findViewById(R.id.button_clear);
        button.setOnClickListener(_buttonClearListener);
        
        button = (Button)findViewById(R.id.button_show_list);
        button.setOnClickListener(_buttonShowListListener);
        
        
    }
    
    /**
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

			Intent myIntent = new Intent(ImagineNoteInput.this, ImagineNoteList.class);
				
			// or use below 2 code line instead. (same function as above code.)
			//Intent myIntent = new Intent();
			//myIntent.setClassName("powerwaveInteractive.ImagineNote", "powerwaveInteractive.ImagineNote.ImagineNoteList");
			
			
			startActivity(myIntent);			
		}    	
    };
        
    
    private static final int REVERT_ID = Menu.FIRST;
    private static final int DISCARD_ID = Menu.FIRST + 1;
    private static final int DELETE_ID = Menu.FIRST + 2;

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // Build the menus that are shown when editing.
      
            menu.add(0, REVERT_ID, 0, R.string.menu_revert)
            	.setShortcut('0', 'r')
                .setIcon(android.R.drawable.ic_menu_revert);
            
            menu.add(0, DELETE_ID, 0, R.string.menu_delete)
            	.setShortcut('1', 'd')
            	.setIcon(android.R.drawable.ic_menu_delete);
            

        // Build the menus that are shown when inserting.
      
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle all of the possible menu actions.
        switch (item.getItemId()) {
        case DELETE_ID:
            
            finish();
            break;
        case DISCARD_ID:
            
            break;
        case REVERT_ID:
            
            break;
        }
        return super.onOptionsItemSelected(item);
    }    
}