package ee.ttu.loengutereitimine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

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
			        	(MainActivity.querySearchResults = (MainActivity.helper).new Query(context,
								listView))
								.execute("find/day=" + "" + "&period_nr=" + "");
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
