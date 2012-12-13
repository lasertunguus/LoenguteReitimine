package ee.ttu.loengutereitimine;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class TanaLoppenud extends Activity{
	
	ArrayList<HashMap<String, String>> loengud;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tana_loppenud_layout);
        
        loengud = new ArrayList<HashMap<String,String>>();
        
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

//				String aine = ((TextView)view).getText().toString();
//				Toast.makeText(getApplicationContext(),
//						"Õppekava " + aine , Toast.LENGTH_SHORT)
//						.show();
			
			}
		});
    }
	
	protected ListAdapter createAdapter()
    {
    	// Create some mock data
		String oppeaine1 = new String("Matemaatiline Analüüs I");
    	String ainekood1 = new String("IDU0444");
		String oppejoud1 = new String("Rain Õpik");
		String kellaaeg1 = new String("14:00 - 15:30");
		String raiting1 = new String("2.8");
		
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("oppeaine", oppeaine1);
		map1.put("ainekood", ainekood1);
		map1.put("oppejoud", oppejoud1);
		map1.put("kellaaeg", kellaaeg1);
		map1.put("raiting", raiting1);
		
		loengud.add(map1);
		
		String oppeaine2 = new String("Matemaatiline Analüüs II");
    	String ainekood2 = new String("IDU0445");
		String oppejoud2 = new String("Rain Õpikuke");
		String kellaaeg2 = new String("16:00 - 17:30");
		String raiting2 = new String("3.8");
		
		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("oppeaine", oppeaine2);
		map2.put("ainekood", ainekood2);
		map2.put("oppejoud", oppejoud2);
		map2.put("kellaaeg", kellaaeg2);
		map2.put("raiting", raiting2);
		
		loengud.add(map2);
		
    	// Create a simple array adapter (of type string) with the test values
    	ListAdapter adapter = new SimpleAdapter(this, loengud, android.R.layout.simple_list_item_1, 
    			new String[]{"oppeaine", "ainekood", "oppejoud", "kellaaeg", "raiting"}, 
    			new int[]{R.id.oppeaine,R.id.ainekood,R.id.oppejoud,R.id.kellaaeg,R.id.rating});
    			
//    			(this, android.R.layout.simple_list_item_1, loengud);
 
    	return adapter;
    }
}
