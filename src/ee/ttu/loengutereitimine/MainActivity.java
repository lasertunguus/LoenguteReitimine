package ee.ttu.loengutereitimine;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;



public class MainActivity extends TabActivity{

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current tab position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

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
		
		Intent intentLoppenud = new Intent().setClass(this, oppekavad.class);
		TabSpec oppekavad = tabHost
		  .newTabSpec("Täna lõppenud")
		  .setContent(intentLoppenud);
		
		tabHost.addTab(otsi);
		tabHost.addTab(oppekavad);
		
		tabHost.setCurrentTab(0);
	}

	}
