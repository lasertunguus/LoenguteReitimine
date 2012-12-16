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

	static Helper helper = new Helper();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar to show tabs.
		TabHost tabHost = getTabHost();

		// 'T�na l�ppenud' asemel 'T�nased'?
		Intent intentLoppenud = new Intent().setClass(this, TanaLoppenud.class);
		TabSpec oppekavad = tabHost.newTabSpec("T�na l�ppenud").setContent(
				intentLoppenud);
		oppekavad.setIndicator("T�na l�ppenud");

		Intent intentOtsi = new Intent().setClass(this, Otsi.class);
		TabSpec otsi = tabHost.newTabSpec("Otsing").setContent(intentOtsi);
		otsi.setIndicator("Otsing");

		tabHost.addTab(oppekavad);
		tabHost.addTab(otsi);

		tabHost.setCurrentTab(0);

		if (checkConnectivity()) {
			// ilmselt tuleb onPause ja onResume meetodites l�imede olekut muuta
			((helper).new Query()).execute("recent");
			((helper).new Query()).execute("recent?ongoing=true");
		}
	}

	public boolean checkConnectivity() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnected();
	}
}
