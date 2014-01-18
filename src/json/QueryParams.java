package json;

/*
 * Paramètres utilisés pour une requête sur les évènements musicaux
 * 
 * @author Sébastien Paradis
 */
public class QueryParams {
	private String latitude;
	private String longitude;
	private int radius;
	private String city;
	private String genre;
	
	public QueryParams() {
		super();
	}
	
	public QueryParams(String latitude, String longitude, int radius,
			String city, String genre) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		this.city = city;
		this.genre = genre;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	

}
