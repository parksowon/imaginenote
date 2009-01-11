package powerwaveInteractive.ImagineNote;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import java.util.*; //most of data structure class is in here!

public class ImagineNoteList extends Activity {

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listpage);        
        
    }    
    
    
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
    

}