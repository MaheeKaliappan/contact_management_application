package com.zoho.contactmanagementapplication.controller;

import java.sql.SQLException;

public class ContactManagementController {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		ContactController contactController = new ContactController();
		contactController.initContactController();

	}

}
