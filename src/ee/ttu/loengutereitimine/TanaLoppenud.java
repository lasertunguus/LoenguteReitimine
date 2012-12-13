package ee.ttu.loengutereitimine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TanaLoppenud extends Activity{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tana_loppenud_layout);
        
		ListView listView = (ListView) findViewById(R.id.mylist);
		ListAdapter listAdapter = createAdapter();
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if(position==0){
					startActivity(new Intent(view.getContext(), proov.class));
					
				}

				String aine = ((TextView)view).getText().toString();
//				Toast.makeText(getApplicationContext(),
//						"�ppekava " + aine , Toast.LENGTH_SHORT)
//						.show();
			
			}
		});
    }
	
	protected ListAdapter createAdapter()
    {
    	// Create some mock data
    	String[] loengud = new String[] { "Matemaatiline anal��s I", "Informaatika I", "V�rgurakendused II"};
 
    	// Create a simple array adapter (of type string) with the test values
    	ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, loengud);
 
    	return adapter;
    }
}
