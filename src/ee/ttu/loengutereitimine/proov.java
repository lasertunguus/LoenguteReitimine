package ee.ttu.loengutereitimine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;

public class proov extends Activity {

	static protected ProgressBar progressBar;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oppeaine_layout);


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
				MainActivity.helper.new Query(progressBar.getContext(),
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

		// TextView textview = new TextView(this);
		// textview.setText("Siin me otsime");
		// setContentView(textview);
	}
}
