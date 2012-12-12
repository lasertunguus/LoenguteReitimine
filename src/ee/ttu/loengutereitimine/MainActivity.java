package ee.ttu.loengutereitimine;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;



@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar to show tabs.	 
		TabHost tabHost = getTabHost(); 

		Intent intentOtsi = new Intent().setClass(this, otsi.class);
		TabSpec otsi = tabHost
		  .newTabSpec("Otsi")
		  .setContent(intentOtsi);
		otsi.setIndicator("Otsi");
		
		Intent intentLoppenud = new Intent().setClass(this, oppekavad.class);
		TabSpec oppekavad = tabHost
		  .newTabSpec("Täna lõppenud")
		  .setContent(intentLoppenud);
		otsi.setIndicator("Täna lõppenud");
		
		tabHost.addTab(otsi);
		tabHost.addTab(oppekavad);
		
		tabHost.setCurrentTab(0);
	}

	}
