package com.crunchytech.breeze;

public class LinkedInProfile {
	private class Profile {
		String firstName = "";
		String lastName = "";
		String headline = "";
	}
	
	public Profile profile;
	
	//Constructor
	public LinkedInProfile () {
		profile = new Profile();
	}
	
	public void setFirstName(String first) {
		profile.firstName = first;
	}
	
	public void setLastName(String last) {
		profile.lastName = last;
	}
	
	public void setHeadline(String headline) {
		profile.headline = headline;
	}
	
	public String getFirstName() {
		
		return profile.firstName;
	}
	public String getLastName() {
		
		return profile.lastName;
	}
	
	public String getHeadline() {
		return profile.headline;
	}
	
	public String getLinkedInAuthURL() {
		String requestURL = "https://www.linkedin.com/uas/oauth2/authorization?response_type=code" + 
                                           "&client_id=" + API_KEY + 
                                           "&scope=" + "" + SCOPE + 
                                           "&state=" + STATE + 
                                           "&redirect_uri=" + REDIRECT_URI;
		return requestURL;
	}
	
	private final String SCOPE = "";
	private final String API_KEY = "75g5lmhj1mfxi9";
	private final String SECRET_KEY = "S4zXc6kD9lE8shDU";
	private final String STATE = "BREEZYFOSHEEZEE";
	public static final String REDIRECT_URI = "https://breeze.ninja";
}
