package ee.ttu.loengutereitimine;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar to show tabs.
		TabHost tabHost = getTabHost();

		Intent intentLoppenud = new Intent().setClass(this, TanaLoppenud.class);
		TabSpec oppekavad = tabHost.newTabSpec("Täna lõppenud").setContent(
				intentLoppenud);
		oppekavad.setIndicator("Täna lõppenud");

		Intent intentOtsi = new Intent().setClass(this, Otsi.class);
		TabSpec otsi = tabHost.newTabSpec("Otsing").setContent(intentOtsi);
		otsi.setIndicator("Otsing");

		tabHost.addTab(oppekavad);
		tabHost.addTab(otsi);

		tabHost.setCurrentTab(0);
	}

	public boolean checkConnectivity() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnected();
	}
}
