package ee.ttu.loengutereitimine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;

public class proov extends Activity {
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

		AsyncTask<String, Void, String[]> task = ((new Helper()).new Query())
				.execute("comments?key=" + lecture.getKey());

		final RatingBar rBar = (RatingBar) findViewById(R.id.ratingBar1);
		rBar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int rating = (int) rBar.getRating();
				((new Helper()).new Query()).execute("rate?key="
						+ lecture.getKey() + "&rating="
						+ Integer.toString(rating));
				rBar.setClickable(false);
				rBar.onHoverChanged(false);
			}
		});

		List<Comment> comments = data.getComments();
		HashMap<String, String> commentMap;

		ArrayList<HashMap<String, String>> commentList = new ArrayList<HashMap<String, String>>(
				comments.size());

		// let's populate dis
		for (Comment c : comments) {
			commentMap = new HashMap<String, String>(2);
			commentMap.put("kellaaeg", c.getDate());
			commentMap.put("kommentaar", c.getText());
			commentList.add(commentMap);
		}

		if (task.getStatus() == AsyncTask.Status.FINISHED) {
			((ProgressBar) findViewById(R.id.progressBar1))
					.setVisibility(View.GONE);
		}

		final ListView listView = (ListView) findViewById(R.id.kommentaarid);
		ListAdapter listAdapter = new SimpleAdapter(this, commentList,
				R.layout.kommentaar, new String[] { "kellaaeg", "kommentaar" },
				new int[] { R.id.kellaaegLisatud, R.id.kommentaar });
		listView.setAdapter(listAdapter);

		// TextView textview = new TextView(this);
		// textview.setText("Siin me otsime");
		// setContentView(textview);
	}
}
