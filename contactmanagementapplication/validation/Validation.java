package com.zoho.contactmanagementapplication.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
	
private static boolean nameValidation(String visitorName) {
		
		Pattern pattern; 
		Matcher matcher;
		pattern = Pattern.compile("^[a-zA-Z]{6,15}$");
		matcher = pattern.matcher(visitorName);
		return matcher.matches();
		
		
	}

private static boolean mobileNumberValidation(String visterDetail) {
	String mobileNumberValidation = "^[6-9][0-9]{9}$";
	
	Pattern pattern;
	Matcher matcher;
	pattern = Pattern.compile(mobileNumberValidation);
	matcher = pattern.matcher(visterDetail);
	return matcher.matches();
	
}

	public boolean checkUserDetails(String userMessage, String visterDetail) {
		if(userMessage.equals("nameValidation"))
		{
			return nameValidation(visterDetail);
		}
		
		else if(userMessage.equals("mobileValidation"))
		{
			return mobileNumberValidation(visterDetail);
		}
		else if(userMessage.equals("emailValidation"))
		{
			return emailValidation(visterDetail);
		}
		return false;
	}

	private boolean emailValidation(String visterDetail) {
		String emailValidation = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(emailValidation);
		matcher = pattern.matcher(visterDetail);
		return matcher.matches();
	}


}
