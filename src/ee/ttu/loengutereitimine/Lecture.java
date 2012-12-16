package ee.ttu.loengutereitimine;

class Lecture {
	private final String key, code, name, type, lecturer;
	private final short day;
	private final String timeStart, timeEnd, description;
	private final float rating;
	private final short nrOfRatings;
	private final String homepage;
	private final boolean evenWeekly, oddWeekly;

	public Lecture(String[] s) {
		this.key = s[0];
		this.code = s[1];
		this.name = s[2];
		this.type = s[3];
		this.lecturer = s[4];
		this.day = Short.parseShort(s[5]);
		this.timeStart = s[6];
		this.timeEnd = s[7];
		this.description = s[8];
		this.rating = Float.parseFloat(s[9]);
		this.nrOfRatings = Short.parseShort(s[10]);
		this.homepage = s[11];
		this.evenWeekly = Boolean.parseBoolean(s[12]);
		this.oddWeekly = Boolean.parseBoolean(s[13]);
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public float getRating() {
		return rating;
	}

	public short getNrOfRatings() {
		return nrOfRatings;
	}

	public String getHomepage() {
		return homepage;
	}

	public boolean isOddWeekly() {
		return oddWeekly;
	}

	public short getDay() {
		return day;
	}

	public String getKey() {
		return key;
	}

	public String getType() {
		return type;
	}

	public String getLecturer() {
		return lecturer;
	}

	public boolean isEvenWeekly() {
		return evenWeekly;
	}
}