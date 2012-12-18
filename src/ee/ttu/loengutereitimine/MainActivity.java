package ee.ttu.loengutereitimine;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import ee.ttu.loengutereitimine.Helper.Query;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	static Helper helper = new Helper();
	static Query queryFinished, queryOngoing, queryFind;
	static boolean connectivity;
	static MainActivity ma; // INCEPTION

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ma = this;
		connectivity = checkConnectivity();

		if (!connectivity) {
			// 1. Instantiate an AlertDialog.Builder with its constructor
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			// 2. Chain together various setter methods to set the dialog
			// characteristics
			builder.setMessage("Internetiühendus puudub.").setTitle(
					"Ühenduse viga");
			
			builder.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
							ma.recreate();
		           }
		       });
			builder.setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							ma.finish();
						}
					});
			AlertDialog alert = builder.create();
			alert.show();
		} else {
			// Set up the action bar to show tabs.
			TabHost tabHost = getTabHost();

			Intent intentLoppenud = new Intent().setClass(this,
					TanaLoppenud.class);
			TabSpec oppekavad = tabHost.newTabSpec("Tänased").setContent(
					intentLoppenud);
			oppekavad.setIndicator("Tänased");

			Intent intentOtsi = new Intent().setClass(this, Otsi.class);
			TabSpec otsi = tabHost.newTabSpec("Otsing").setContent(intentOtsi);
			otsi.setIndicator("Otsing");

			tabHost.addTab(oppekavad);
			tabHost.addTab(otsi);
			tabHost.setCurrentTab(0);

		}
	}

	public boolean checkConnectivity() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnected();
	}
}
