package WsSem.model;

public class JsonEventLocation {
	private String locName;
	private String latitude;
	private String longitude;
	private String address;

	public JsonEventLocation() {
		this.locName = "";
		this.latitude = "";
		this.longitude = "";	
		this.address = "";
	}
	public JsonEventLocation(String name, String lat, String lng, String address) {
		this.locName = name;
		this.latitude = lat;
		this.longitude = lng;	
		this.address = address;
	}
	
	public String getLocName() {
		
		return locName;
	}
	public void setLocName(String locName) {
		this.locName = locName;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
