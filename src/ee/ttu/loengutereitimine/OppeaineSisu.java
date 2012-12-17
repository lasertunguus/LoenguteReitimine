package ee.ttu.loengutereitimine;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class OppeaineSisu extends Activity {

	static protected ProgressBar progressBar;
	static protected OppeaineSisu os;
	Lecture lecture;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oppeaine_layout);

		os = this;

		Bundle b = getIntent().getExtras();
		DataSingleton data = DataSingleton.getInstance();
		
		if (b.getBoolean("finished")) {
			lecture = data.getFinishedLectureList().get(b.getInt("position"));
		} else if (b.getBoolean("ongoing")) {
			lecture = data.getOngoingLectureList().get(b.getInt("position"));
		} else {
			lecture = null;
		}

		final ListView listView = (ListView) findViewById(R.id.kommentaarid);

		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		progressBar.setProgress(0);
		MainActivity.helper.new Query(this, listView).execute("comments?key="
				+ lecture.getKey());
		
		final RatingBar rBar = (RatingBar) findViewById(R.id.ratingBar1);
		rBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar rBar, float arg1, boolean arg2) {
				// TODO Auto-generated method stub
				rBar.setRating((int) rBar.getRating());
				rBar.setClickable(false);
				rBar.onHoverChanged(false);
				rBar.setIsIndicator(true);
				MainActivity.helper.new Query(os, null).execute("rate?key="
						+ lecture.getKey() + "&rating="
						+ Integer.toString((int) rBar.getRating()));
				((TextView) findViewById(R.id.text_add_rating))
						.setText("REITING ANTUD!");
			}
		});

		((TextView) findViewById(R.id.oppeaine)).setText(lecture.getName());
		((TextView) findViewById(R.id.ainekood)).setText(lecture.getCode());
		((TextView) findViewById(R.id.oppejoud)).setText(lecture.getLecturer());
		((TextView) findViewById(R.id.tuup)).setText(lecture.getType());
		((TextView) findViewById(R.id.kellaaeg)).setText(lecture.getTimeStart()
				+ "-" + lecture.getTimeEnd());
		((TextView) findViewById(R.id.reiting)).setText(Float.toString(lecture
				.getRating()));
		((TextView) findViewById(R.id.kirjeldus)).setText(lecture
				.getDescription());
		((TextView) findViewById(R.id.kodulehekulg)).setText(lecture
				.getHomepage());
		((TextView) findViewById(R.id.reitingute_arv)).setText("("
				+ Integer.toString(lecture.getNrOfRatings()) + ")");

	}
}
