/**
 * 
 */
package ee.ttu.loengutereitimine;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * @author taavi
 * 
 */
public class Helper {

	// TODO:
	// Ühenduse kontroll

	private static final String APPENGINE_PAGE = "http://loengutereitimine.appspot.com/";

	protected static ResponseObject postContent(String query, String postContent) {
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(APPENGINE_PAGE + query);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"text/html; charset=utf-8");
			connection.setRequestProperty("Content-Length",
					Integer.toString(postContent.getBytes().length));
			// connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.write(postContent.getBytes());
			// wr.write(postContent.getBytes().toString());
			wr.flush();
			wr.close();

			// Get Response
			int responseCode = connection.getResponseCode();
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return MainActivity.helper.new ResponseObject(responseCode,
					response.toString());

		} catch (Exception e) {
			e.printStackTrace();
			return MainActivity.helper.new ResponseObject(400, "");
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	/**
	 * 
	 * @param query
	 * @throws IOException
	 */
	protected static synchronized ResponseObject getContent(String query)
			throws IOException {
		HttpURLConnection conn = null;
		BufferedReader br = null;
		int responseCode = 0;
		String line = "", response = "";
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
					response += line + "\n";
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
			// return MainActivity.helper.new ResponseObject(400, "");
		} finally {
			if (br != null)
				br.close();
		}
		return MainActivity.helper.new ResponseObject(responseCode, response);
	}

	private class ResponseObject {

		private final int responseCode;
		private final String responseText;
		private String actionName;

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

		public void setActionName(String actionName) {
			this.actionName = actionName;
		}

		public String getActionName() {
			return actionName;
		}

	}

	public class Query extends AsyncTask<String, Void, ResponseObject> {

		private final Context context;
		private final ListView listView;

		public Query(Context context, ListView listView) {
			this.context = context;
			this.listView = listView;
		}

		@Override
		protected ResponseObject doInBackground(String... params) {
			// TODO
			// Võimalikud tegevused: * get hiljuti lõppenud loengud
			// * get täna lõppenud loengud
			// * get loengu kommentaarid
			// * anna loengule reiting
			// * postita loengule kommentaar
			String[] s = null;
			ResponseObject ro = null;
			List<Lecture> lectures;
			try {
				String p = params[0];
				String query = p.split("\\?")[0].equals(p) ? ""
						: p.split("\\?")[1];
				if (p.startsWith("/postcomment")
						&& OppeaineSisu.postContent.length() > 0) {
					ro = postContent("/comments?" + query,
							OppeaineSisu.postContent);
					ro.setActionName("postcomment");
					return ro;
				}
				ro = getContent(p);
				if (ro.getResponseCode() != HttpURLConnection.HTTP_BAD_REQUEST
						&& ro.getResponseCode() != HttpURLConnection.HTTP_BAD_METHOD) {
					DataSingleton data = DataSingleton.getInstance();
					String rText = ro.getResponseText();
					if (p.startsWith("recent") && !isEmpty(rText)) { // tänased
						lectures = getLecturesFromResponse(ro.getResponseText());
						ro.setActionName("finished");
						if (query.length() > 0
								&& query.split("=")[0].equals("ongoing")
								&& !query.split("=")[1].equals("0")) {
							data.getOngoingLectureList().clear();
							data.getOngoingLectureList().addAll(lectures);
							ro.setActionName("ongoing");
						} else {
							data.getFinishedLectureList().clear();
							data.getFinishedLectureList().addAll(lectures);
						}
					} else if (p.startsWith("comments")) {
						ro.setActionName("comments");
						data.getComments().clear();
						if (!isEmpty(rText)) {
							s = ro.getResponseText().split("\n");
							List<Comment> comments = new ArrayList<Comment>();
							for (String ss : s) {
								comments.add(new Comment(ss.split(";")));
							}
							data.getComments().addAll(comments);
						}
					} else if (p.startsWith("find")) {
						ro.setActionName("find");
						data.getSearchResults().clear();
						// YOLO #SWAG
						if (query.length() > 0) {
							if (!isEmpty(rText)) {
								lectures = getLecturesFromResponse(ro
										.getResponseText());
								data.getSearchResults().addAll(lectures);
							}
						}
					} else if (p.startsWith("rate")) {
						// TODO Siin pole midagi vaja teha. Kustuta!
					}
				}
			} catch (IOException e) {
				// TODO
			}
			return ro;
		}

		private List<Lecture> getLecturesFromResponse(String response) {
			ArrayList<Lecture> lectures = new ArrayList<Lecture>();
			String[] s = response.split("\n");
			for (String ss : s) {
				lectures.add(new Lecture(ss.split(";")));
			}
			return lectures;
		}

		private boolean isEmpty(String s) {
			return s == null || s.equals("") || s.length() == 0;
		}

		@Override
		protected void onPostExecute(ResponseObject result) {
			// super.onPostExecute(result);
			String actionName = result.getActionName();
			if (actionName != null) { // kui 400, 404 või empty content
				if (actionName.equals("kommentaarid")) {
					OppeaineSisu.progressBar.setVisibility(View.INVISIBLE);
				} else if (actionName.equals("find")) {
					Otsi.btn.setPressed(false);
					Otsi.btn.setClickable(true);
					Otsi.btn.setText("Otsi");
					if (isEmpty(result.getResponseText())) {
						Otsi.status.setText("Tulemusi ei leitud");
					}
				}
				ListAdapter listAdapter = createAdapter(actionName);
				if (listAdapter != null)
					listView.setAdapter(listAdapter);

			}
		}

		protected ListAdapter createAdapter(String actionName) {
			SimpleAdapter adapter = null;
			List<Lecture> loenguteList = null;
			List<Comment> kommentaarideList = null;
			ArrayList<HashMap<String, String>> loengud, kommentaarid;

			DataSingleton data = DataSingleton.getInstance();

			if (actionName.equals("finished")) {
				loenguteList = data.getFinishedLectureList();
			} else if (actionName.equals("ongoing")) {
				loenguteList = data.getOngoingLectureList();
			} else if (actionName.equals("find")) {
				loenguteList = data.getSearchResults();
			} else if (actionName.equals("comments")) {
				kommentaarideList = data.getComments();
			} else {
				return null;
			}

			HashMap<String, String> map;

			if (!actionName.equals("comments")) {
				loengud = new ArrayList<HashMap<String, String>>(
						loenguteList.size());
				for (Lecture l : loenguteList) {
					map = new HashMap<String, String>(6); // suuruse panen kohe
					map.put("oppeaine", l.getName());
					map.put("ainekood", l.getCode());
					map.put("tuup", l.getType());
					map.put("oppejoud", l.getLecturer());
					map.put("kellaaeg", l.getTimeStart() + "-" + l.getTimeEnd());
					map.put("reiting", Float.toString(l.getRating()));
					loengud.add(map);
				}
				adapter = new SimpleAdapter(context, loengud,
						R.layout.oppeaine, new String[] { "oppeaine",
								"ainekood", "oppejoud", "tuup", "kellaaeg",
								"reiting" }, new int[] { R.id.oppeaine,
								R.id.ainekood, R.id.oppejoud, R.id.tuup,
								R.id.kellaaeg, R.id.reiting });
			} else if (actionName.equals("comments")) {
				if (kommentaarideList.size() > 0) {
					kommentaarid = new ArrayList<HashMap<String, String>>(
							kommentaarideList.size());
					// let's populate dis
					for (Comment c : kommentaarideList) {
						map = new HashMap<String, String>(2);
						map.put("kellaaeg", c.getDate());
						map.put("kommentaar", c.getText());
						kommentaarid.add(map);
					}

					adapter = new SimpleAdapter(context, kommentaarid,
							R.layout.kommentaar, new String[] { "kellaaeg",
									"kommentaar" }, new int[] {
									R.id.kellaaegLisatud, R.id.kommentaar });
				}
			}

			return adapter;
		}
	}
}