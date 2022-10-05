package com.zoho.contactmanagementapplication.model;

public class User {
	private Integer userId;
	private String firstName;
	private String lastName;
	private String address;
	private MobileNumber mobileNumber;
	private Email mailId;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public MobileNumber getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(MobileNumber mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Email getMailId() {
		return mailId;
	}
	public void setMailId(Email mailId) {
		this.mailId = mailId;
	}
	
	

}
