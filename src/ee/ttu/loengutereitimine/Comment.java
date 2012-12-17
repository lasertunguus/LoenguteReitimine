package ee.ttu.loengutereitimine;


class Comment {

	private final String text;
	private final String date;

	public Comment(String[] s) {
		this.text = s[0];
		this.date = s[1].split(".")[1];
	}

	public String getText() {
		return text;
	}

	public String getDate() {
		return date;
	}

}