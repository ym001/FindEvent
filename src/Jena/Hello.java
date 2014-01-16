package Jena;

import java.util.ArrayList;

import WsSem.model.JsonArtist;

public class Hello {
	public static void main(String args[]){
		ArrayList<JsonArtist> myArray = new ArrayList<JsonArtist>();
		
		JsonArtist a = new JsonArtist();
		a.setName("gao");
		a.setBio("gaobio");
		a.setImg("myImage");
		a.setIdJamendo("489302");
		
		JsonArtist b = new JsonArtist();
		b.setName("gao");
		b.setBio("");
		b.setHomepage("myhomepage");
		
		myArray.add(a);
		myArray.add(b);
		
		
		for(int i=0; i<myArray.size(); i++){
			System.out.println(myArray.get(i).getBio());
			System.out.println(myArray.get(i).getHomepage());
			System.out.println(myArray.get(i).getIdJamendo());
			System.out.println(myArray.get(i).getImg());
			System.out.println(myArray.get(i).getName());
			System.out.println(myArray.get(i).getResume());
			System.out.println(myArray.get(i).getTag());
		}
		
	}
	
}
