package powerwaveInteractive.ImagineNote;


import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.Intent.*;
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
import powerwaveInteractive.ImagineNote.*;
public class ImagineNoteList extends Activity {

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listpage);
    }
    
    /**
     * constant strings for intent :
     */
     
    
    /**
     * constants for ID of menu : 
     */
    private static final int INSERT_ID = Menu.FIRST;    
    private static final int DELETE_ID = Menu.FIRST + 1;
    private static final int EDIT_ID = Menu.FIRST + 2;

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // Build the menus that are shown when editing.
      
            menu.add(0, INSERT_ID, 0, R.string.menu_insert)
            	.setShortcut('0', 'r')
                .setIcon(android.R.drawable.ic_menu_add);
            
            menu.add(0, DELETE_ID, 0, R.string.menu_delete)
        		.setShortcut('1', 'd')
        		.setIcon(android.R.drawable.ic_menu_delete);
            
            menu.add(0, EDIT_ID, 0, R.string.menu_edit)
        		.setShortcut('2', 'e')
        		.setIcon(android.R.drawable.ic_menu_edit);
            

        // Build the menus that are shown when inserting.
      
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
    	final Intent intent;
    	final Uri uri;
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
	        	intent.setData( ContentUris.withAppendedId(Note.CONTENT_URI, 1234 ) );
	        	startActivity(intent);
	            break;
	        
	        case INSERT_ID:
	        	intent = new Intent(this, powerwaveInteractive.ImagineNote.ImagineNoteInput.class);        
	        	intent.setAction(Intent.ACTION_INSERT);
	        	intent.setData( ContentUris.withAppendedId(Note.CONTENT_URI, 5678 ) );
	        	startActivity(intent);            
	            break;
	            
	        case EDIT_ID:        	        	        	       	
	        	intent = new Intent(this, powerwaveInteractive.ImagineNote.ImagineNoteInput.class);        	
	        	intent.setAction(Intent.ACTION_EDIT);
	        	intent.setData( ContentUris.withAppendedId(Note.CONTENT_URI, 9012 ) );
	        	startActivity(intent);            
	        	break;
        	
        }        
        return super.onOptionsItemSelected(item);
    }    
}