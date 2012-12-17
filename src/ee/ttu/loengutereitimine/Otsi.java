package ee.ttu.loengutereitimine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Otsi extends Activity {
	
	static ListView listView;
	Button btn;
	Context context;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otsi_layout);
        
        context = this;
        listView = (ListView) findViewById(R.id.mylist3);
        btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				 if (MainActivity.connectivity) {									
					
					 	RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1); 					 					
						int selectedButton = radioGroup.getCheckedRadioButtonId();					 
						RadioButton radioButtonDay = (RadioButton) findViewById(selectedButton);					 					
						
						
						RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2); 					 					
						int selectedButton2 = radioGroup.getCheckedRadioButtonId();					 
						RadioButton radioButtonTime = (RadioButton) findViewById(selectedButton2);					 					
						radioButtonDay.getText();
					 
			        	(MainActivity.querySearchResults = (MainActivity.helper).new Query(context,
								listView))
								.execute("find/day=" +radioButtonDay.getText()+ "" + "&period_nr=" +radioButtonTime.getText()+ "");
			        } 
			        else{
			        	
			        }
				
			}
        });
        
        
//        TextView textview = new TextView(this);
//        textview.setText("Siin me otsime");
//        setContentView(textview);
    }
}
