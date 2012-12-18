package ee.ttu.loengutereitimine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TanaLoppenud extends Activity {

	static ListView listView;
	static ListView listView2;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tana_loppenud_layout);

		listView = (ListView) findViewById(R.id.mylist);
		listView2 = (ListView) findViewById(R.id.mylist2);

		if (MainActivity.connectivity) {
			// ilmselt tuleb onPause ja onResume meetodites lõimede olekut muuta
			(MainActivity.queryFinished = (MainActivity.helper).new Query(this,
					listView))
					.execute("recent");
			(MainActivity.queryOngoing = (MainActivity.helper).new Query(this,
					listView2))
					.execute("recent?ongoing=true");
		} else {
			return;
		}

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplication().getBaseContext(),
						OppeaineSisu.class);
				intent.putExtra("finished", true);
				intent.putExtra("position", position);
				startActivity(intent);
			}
		});

		listView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplication().getBaseContext(),
						OppeaineSisu.class);
				intent.putExtra("ongoing", true);
				intent.putExtra("position", position);
				startActivity(intent);
			}
		});
	}
}