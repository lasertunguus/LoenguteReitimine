/**
 * 
 */
package ee.ttu.loengutereitimine;

import java.util.ArrayList;
import java.util.List;

class DataSingleton {
	private static DataSingleton instance = null;
	private static List<Lecture> finished = null;
	private static List<Lecture> ongoing = null;
	private static List<Lecture> searchResults = null;
	private static List<Comment> comments = null;

	private DataSingleton() {
	}

	public static DataSingleton getInstance() {
		if (instance == null) {
			instance = new DataSingleton();
		}
		return instance;
	}

	public List<Lecture> getFinishedLectureList() {
		if (finished == null)
			finished = new ArrayList<Lecture>();
		return finished;
	}

	public List<Lecture> getOngoingLectureList() {
		if (ongoing == null)
			ongoing = new ArrayList<Lecture>();
		return ongoing;
	}

	public List<Lecture> getSearchResults() {
		if (searchResults == null)
			searchResults = new ArrayList<Lecture>();
		return searchResults;
	}

	public List<Comment> getComments() {
		if (comments == null)
			comments = new ArrayList<Comment>();
		return comments;
	}
}
