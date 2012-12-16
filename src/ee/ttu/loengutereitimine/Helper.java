/**
 * 
 */
package ee.ttu.loengutereitimine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

/**
 * @author taavi
 * 
 */
public class Helper {

	// TODO:
	// Ühenduse kontroll

	private static final String APPENGINE_PAGE = "http://loengutereitimine.appspot.com/";

	/**
	 * TODO: vb ei pea synchronized olema
	 * 
	 * @param query
	 * @throws IOException
	 */
	protected static synchronized ResponseObject getContent(String query)
			throws IOException {
		HttpURLConnection conn = null;
		BufferedReader br = null;
		int responseCode = 0;
		String line = null, response = null;
		try {
			conn = (HttpURLConnection) new URL(APPENGINE_PAGE + query)
					.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				br = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				while ((line = br.readLine()) != null)
					response += line;
				// } else if (responseCode ==
				// HttpURLConnection.HTTP_BAD_REQUEST) {
				// // TODO
				// } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND)
				// {
				// // TODO
				response = response == null ? null : response.trim();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null)
				br.close();
		}
		return new ResponseObject(responseCode, response);
	}

	private static class ResponseObject {

		private final int responseCode;
		private final String responseText;

		private ResponseObject(int responseCode, String responseText) {
			this.responseCode = responseCode;
			this.responseText = responseText;
		}

		public int getResponseCode() {
			return responseCode;
		}

		public String getResponseText() {
			return responseText;
		}

	}

	public class Query extends AsyncTask<String, Void, String[]> {

		// private final ListView listView;
		// private final Context context;

		// public Query(ListView listView) {
		// this.listView = listView;
		// this.context = listView.getContext();
		// }

		@Override
		protected String[] doInBackground(String... params) {
			// TODO
			// Võimalikud tegevused: * get hiljuti lõppenud loengud
			// * get täna lõppenud loengud
			// * get loengu kommentaarid
			// * anna loengule reiting
			// * postita loengule kommentaar
			String[] s = null;
			ResponseObject ro = null;
			try {
				String p = params[0];
				String query = p.split("\\?")[0].equals(p) ? ""
						: p.split("\\?")[1];
				ro = getContent(p);
				if (ro.getResponseCode() != HttpURLConnection.HTTP_BAD_REQUEST
						&& ro.getResponseCode() != HttpURLConnection.HTTP_BAD_METHOD
						&& ro.getResponseText() != null
						&& ro.getResponseText().length() > 0
						&& !ro.getResponseText().equals("")) {
					DataSingleton data = DataSingleton.getInstance();
					if (p.substring(0, 6).equals("recent")) { // tänased loengud
						s = ro.getResponseText().split("\n");
						List<Lecture> lectures = new ArrayList<Lecture>();
						for (String ss : s) {
							lectures.add(new Lecture(ss.split(";")));
						}
						if (query.split("=")[0].equals("ongoing")
								&& query.split("=")[1].equals("true")) {
							data.getOngoingLectureList().clear();
							data.getOngoingLectureList().addAll(lectures);
						} else {
							data.getFinishedLectureList().clear();
							data.getFinishedLectureList().addAll(lectures);
						}
					} else if (p.substring(0, 8).equals("comments")) {
						s = ro.getResponseText().split("\n");
						List<Comment> comments = new ArrayList<Comment>();
						for (String ss : s) {
							comments.add(new Comment(ss.split(";")));
						}
						data.getComments().clear();
						data.getComments().addAll(comments);
					} else if (p.substring(0, 4).equals("rate")) {
						// TODO Siin pole midagi vaja teha. Kustuta!
					}
				}
			} catch (IOException e) {
				// TODO
			}
			return s;
		}

		// @Override
		// protected void onPostExecute(String[] result) {
		// // TODO Auto-generated method stub
		// if (result != null)
		// listView.setAdapter(new ArrayAdapter<String>(context,
		// android.R.layout.simple_list_item_1,
		// android.R.id.text1, result));
		// }

		// Uses AsyncTask to create a task away from the main UI thread. This
		// task
		// takes a
		// URL string and uses it to create an HttpUrlConnection. Once the
		// connection
		// has been established, the AsyncTask downloads the contents of the
		// webpage
		// as
		// an InputStream. Finally, the InputStream is converted into a string,
		// which is
		// displayed in the UI by the AsyncTask's onPostExecute method.
		// private class DownloadWebpageText extends AsyncTask {
		//
		// @Override
		// protected String doInBackground(String... urls) {
		//
		// // params comes from the execute() call: params[0] is the url.
		// try {
		// return downloadUrl(urls[0]);
		// } catch (IOException e) {
		// return "Unable to retrieve web page. URL may be invalid.";
		// }
		// }
		// // // onPostExecute displays the results of the AsyncTask.
		// // @Override
		// // protected void onPostExecute(String result) {
		// // textView.setText(result);
		// // }
		// // }
		// }
	}
}
