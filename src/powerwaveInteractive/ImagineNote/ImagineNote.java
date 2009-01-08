package powerwaveInteractive.ImagineNote;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import java.util.*; //most of data structure class is in here!

public class ImagineNote extends Activity {

	
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
}