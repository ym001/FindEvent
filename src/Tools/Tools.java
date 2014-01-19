package Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.lang3.text.WordUtils;

public class Tools {


	/*
	 * Formatage d'une chaine : 
	 *   + toutes les 1ères lettres en MAJ
	 *   + suppression des "" en début/fin de chaine
	 *   + suppression des espaces en début/fin de chaine
	 * 
	 * @author Sébastien Paradis
	 */

	public static String getBusinessRdfPath(){
		//return "/home/cgao/Travail/FindEvent/WebContent/ressources/meo-business.rdf";
		return "D:/Travaille/FindEvent/WebContent/ressources/meo-business.rdf";
	}
	
	//From null to "", for Jena alimentation
	public static String nullToString(Object o){
		if(o==null){
			return "";
		}else{
			return o.toString();
		}
	}
	
	//return 'sth'
	public static String myString(String txt){
		return "'"+txt+"'";
	}

	public static String uppercaseWords(String original) {
		if(original.length() == 0)
			return original;
		// 1er "
		if (original.charAt(0) == '"') {
			original = original.substring(1);
		}
		// Dernier "
		if (original.charAt(original.length() - 1) == '"') {
			original = original.substring(0, original.length() - 1);
		}
		original = original.trim();
		return WordUtils.capitalizeFully(original);
	}


	/***Parse last element***/
	public static String getLastItemInLink(String link){
		String result = link.substring(link.lastIndexOf("/")+1);
		return result;
	}


	/***Parse first ^^***/
	public static String getFirstTitle(String link){
		StringTokenizer st = new StringTokenizer(link, "^^");
		String result ="";
		while (st.hasMoreElements()) {
			result=(String) st.nextElement();
			break;
		}
		return result;
	}


	/**Geo**/

	private static float toRad(float num){
		return (float) (num*Math.PI/180);
	}

	private static float toDeg(float num){
		return (float) (num*180/Math.PI);
	}

	//generer la fenetre de lat et lgt
	public static ArrayList<Float> createFenetreGeo (float latOrigin, float lgtOrigin, float distance){

		ArrayList<Float> result = new ArrayList<Float>();
		float latMax = 0;
		float latMin = 0;
		float lgtMax = 0;
		float lgtMin = 0;
		float R = (float) 6371.0; //R est radius de terre.

		/*
		 * Algorithm
		 * 		var lat2 = Math.asin( Math.sin(lat1)*Math.cos(d/R) + 
	              Math.cos(lat1)*Math.sin(d/R)*Math.cos(brng) );
				var lon2 = lon1 + Math.atan2(Math.sin(brng)*Math.sin(d/R)*Math.cos(lat1), 
	                     Math.cos(d/R)-Math.sin(lat1)*Math.sin(lat2));
		 *                
		 */

		System.out.println(latOrigin+", "+lgtOrigin+", "+distance);

		latOrigin = Tools.toRad(latOrigin);
		lgtOrigin = Tools.toRad(lgtOrigin);
		float distance_R = (float)distance/R;

		float bearing_nord = toRad(0);
		float bearing_est = toRad(90);
		float bearing_sud = toRad(180);
		float bearing_ouest = toRad(270);

		//le point le plus Nord

		float latNord = (float) Math.asin( Math.sin(latOrigin)*Math.cos(distance_R) + 
				Math.cos(latOrigin)*Math.sin(distance_R)*Math.cos(bearing_nord) );
		float lgtNord = (float)(lgtOrigin + Math.atan2(Math.sin(bearing_nord)*Math.sin(distance_R)*Math.cos(latOrigin), 
				Math.cos(distance_R)-Math.sin(latOrigin)*Math.sin(latNord)));


		//le point est
		float latEst = (float) (Math.asin(Math.sin(latOrigin)*Math.cos(distance_R)+
				Math.cos(latOrigin)*Math.sin(distance_R)*Math.cos(bearing_est)));
		float lgtEst = (float)(lgtOrigin + Math.atan2(Math.sin(bearing_est)*Math.sin(distance_R)*Math.cos(latOrigin), 
				Math.cos(distance_R)-Math.sin(latOrigin)*Math.sin(latEst)));


		//le point sud
		float latSud = (float) (Math.asin(Math.sin(latOrigin)*Math.cos(distance_R)+
				Math.cos(latOrigin)*Math.sin(distance_R)*Math.cos(bearing_sud)));
		float lgtSud = (float)(lgtOrigin + Math.atan2(Math.sin(bearing_sud)*Math.sin(distance_R)*Math.cos(latOrigin), 
				Math.cos(distance_R)-Math.sin(latOrigin)*Math.sin(latSud)));

		//le point ouest
		float latOuest = (float) (Math.asin(Math.sin(latOrigin)*Math.cos(distance_R)
				+Math.cos(latOrigin)*Math.sin(distance_R)*Math.cos(bearing_ouest)));
		float lgtOuest = (float)(lgtOrigin + Math.atan2(Math.sin(bearing_ouest)*Math.sin(distance_R)*Math.cos(latOrigin), 
				Math.cos(distance_R)-Math.sin(latOrigin)*Math.sin(latOuest)));

		/*		System.out.println("point Nord:"+Tools.toDeg(latNord)+", "+Tools.toDeg(lgtNord));
		System.out.println("point Est:"+Tools.toDeg(latEst)+", "+Tools.toDeg(lgtEst));
		System.out.println("point Sud:"+Tools.toDeg(latSud)+", "+Tools.toDeg(lgtSud));
		System.out.println("point Nord:"+Tools.toDeg(latOuest)+", "+Tools.toDeg(lgtOuest));*/

		latMax = Tools.toDeg(latNord);
		latMin = Tools.toDeg(latSud);
		lgtMax = Tools.toDeg(lgtEst);
		lgtMin = Tools.toDeg(lgtOuest);

		/*		System.out.println("latMax"+latMax);
		System.out.println("latMin:"+latMin);
		System.out.println("lgtMax:"+lgtMax);
		System.out.println("lgtMin:"+lgtMin);*/

		result.add(latMax);
		result.add(latMin);
		result.add(lgtMax);
		result.add(lgtMin);

		return result;
	}


}
