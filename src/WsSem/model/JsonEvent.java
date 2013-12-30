package WsSem.model;

import java.util.ArrayList;

public class JsonEvent {
	private String name;
	private String wikilink;
	private String date;
	private String description;
	private JsonEventLocation location = null;
	private ArrayList<String> performers = null;
	
	// Evenement composite = festival
	private ArrayList<JsonEvent> subEvents = null;
	private boolean isFestival = false;

	public JsonEvent() {
		super();
		performers = new ArrayList<String>();
	}
	public JsonEvent(String nom, String wikilink, String date, String desc) {
		super();
		this.name = nom;
		this.wikilink = wikilink;
		this.date = date;
		this.description = desc;
		performers = new ArrayList<String>();
	}
	
	public boolean isFestival() {
		return isFestival;
	}
	public void setFestival(boolean isFestival) {
		this.isFestival = isFestival;
		this.performers.clear();
	}

	public void AddPerformer(String p) {
		this.performers.add(p);
	}
	public ArrayList<String> getPerformers() {
		return performers;
	}	
	public void setPerformers(ArrayList<String> participants) {
		this.performers = participants;
	}
	

	// Ajout de sous-évènement(s) dans le cas d'un festival
    public void AddSubEvent(JsonEvent e){
        this.subEvents.add(e);
        setFestival(true);
    }
    public void AddSubEvents(ArrayList<JsonEvent> es){
        for (JsonEvent e : es) {
            this.AddSubEvent(e);
        }
    }
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getWikilink() {
		return wikilink;
	}
	public void setWikilink(String wikilink) {
		this.wikilink = wikilink;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public JsonEventLocation getLocation() {
		return location;
	}
	public void setLocation(JsonEventLocation lieu) {
		this.location = lieu;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name + "\r\n\tle " + date + " à " + location.getLocName() + 
				" (lat=" + location.getLatitude() + ", long=" + location.getLongitude() + 
				", " + location.getAddress() + 
				")\r\n\tavec " + performers;
	}
}
