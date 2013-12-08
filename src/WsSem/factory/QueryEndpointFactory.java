package WsSem.factory;

public class QueryEndpointFactory{
	
	//Singleton to get instance
	
	private QueryEndpointFactory() {
	}
	
	private static QueryEndpointFactory instance = null;
	
	public static QueryEndpointFactory getJsonResultFactory(){
		if(instance==null){
			instance = new QueryEndpointFactory();
		}
		return instance;
	}
	
	
	public static final String NL = System.getProperty("line.separator") ;
	
	
	public static String getAlbumByArtists(String artistLink){
		
		
		
		return "";
	}
		
		
		
	
	
}