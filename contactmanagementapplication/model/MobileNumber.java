package com.zoho.contactmanagementapplication.model;

public class MobileNumber {
	private String mobileNumber;
	private String mobileType;
	private Boolean emergencyContact;
	
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getMobileType() {
		return mobileType;
	}
	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}
	public Boolean getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(Boolean emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	
	
}
