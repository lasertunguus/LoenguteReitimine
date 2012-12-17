package ee.ttu.loengutereitimine;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;

public class OppeaineSisu extends Activity {

	static protected ProgressBar progressBar;
	static protected OppeaineSisu os;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oppeaine_layout);

		os = this;

		Bundle b = getIntent().getExtras();
		DataSingleton data = DataSingleton.getInstance();
		final Lecture lecture;
		
		if (b.getBoolean("finished")) {
			lecture = data.getFinishedLectureList().get(b.getInt("position"));
		} else if (b.getBoolean("ongoing")) {
			lecture = data.getOngoingLectureList().get(b.getInt("position"));
		} else {
			lecture = null;
		}

		final ListView listView = (ListView) findViewById(R.id.kommentaarid);

		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		MainActivity.helper.new Query(this, listView).execute("comments?key="
				+ lecture.getKey());

		final RatingBar rBar = (RatingBar) findViewById(R.id.ratingBar1);
		rBar.setRating(lecture.getRating());
		rBar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int rating = (int) rBar.getRating();
				MainActivity.helper.new Query(os,
						listView) // context
																		// pigem
						.execute("rate?key="
						+ lecture.getKey() + "&rating="
						+ Integer.toString(rating));
				rBar.setClickable(false);
				rBar.onHoverChanged(false);
				rBar.setIsIndicator(true);
			}
		});
		
		ArrayList<HashMap<String, String>> loeng = new ArrayList<HashMap<String, String>>(1);
		
		HashMap<String, String> loengMap = new HashMap<String, String>();
		loengMap.put("oppeaine", lecture.getName());
		loengMap.put("ainekood", lecture.getCode());
		loengMap.put("oppejoud", lecture.getLecturer());
		loengMap.put("tuup", lecture.getType());
		loengMap.put("kellaaeg", lecture.getTimeStart() + "-" + lecture.getTimeEnd());
		loengMap.put("reiting", Float.toString(lecture.getRating()));
		loengMap.put("kirjeldus", lecture.getDescription());
		loengMap.put("kodulehekulg", lecture.getHomepage());
		loengMap.put("reitingute_arv",
				Integer.toString(lecture.getNrOfRatings()));
		// @id/reitingute_arv
		loeng.add(loengMap);

		new SimpleAdapter(this, loeng, R.layout.oppeaine_layout, new String[] {
				"oppeaine", "ainekood", "oppejoud", "tuup", "kellaaeg",
				"reiting", "kirjeldus", "kodulehekulg", "reitingute_arv" },
				new int[] { R.id.oppeaine, R.id.ainekood, R.id.oppejoud,
						R.id.tuup, R.id.kellaaeg, R.id.reiting, R.id.kirjeldus,
						R.id.kodulehekulg, R.id.reitingute_arv });

		// TextView textview = new TextView(this);
		// textview.setText("Siin me otsime");
		// setContentView(textview);
	}
}
