package endPoints;

public enum EndPoints {
	NEW("/new"), SHUFFLE("/shuffle"), DRAW("/draw"),DECKID("/{deck_id}");

	private final String URL;

	EndPoints(String url) {
		this.URL = url;
	}

	public String getEndPoint() {
		return this.URL;
	}

}
