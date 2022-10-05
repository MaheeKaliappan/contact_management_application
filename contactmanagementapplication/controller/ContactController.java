package com.zoho.contactmanagementapplication.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.zoho.contactmanagementapplication.dao.ContactApplicationDAO;
import com.zoho.contactmanagementapplication.model.Email;
import com.zoho.contactmanagementapplication.model.MobileNumber;
import com.zoho.contactmanagementapplication.model.User;
import com.zoho.contactmanagementapplication.validation.Validation;

public class ContactController {

	ContactApplicationDAO contactApplicationDAO = new ContactApplicationDAO();
	Validation validation = new Validation();

	public void initContactController() throws ClassNotFoundException, SQLException {

		System.out.println(
				"1.Add New Contact\n2.Update Contact\n3.View Contact\n4.Delete Contact\n5.View All Contact\n6.Exit");
		Scanner reader = new Scanner(System.in);
		int userChoice = reader.nextInt();
		switch (userChoice) {
		case 1:
			addContact();
			break;
		case 2:
			updateContact();
			break;
		case 3:
			viewContact();
			break;
		case 4:
			deleteContact();
			break;
		case 5:
			viewAllContact();
			break;
		case 6:
			System.out.println("Thanks For Visit");
			break;

		default:
			System.out.println("Invalid Input");
			break;
		}

	}

	private void updateContact() throws ClassNotFoundException, SQLException {
		Scanner reader = new Scanner(System.in);

		String emailType[] = { "PERSONAL", "OFFICE" };
		User user = new User();
		MobileNumber mobileNumber = new MobileNumber();
		Email email = new Email();
		System.out.println("1.Name Update\n2.Mobile Number Update\n3.Email Update");
		int userChoice = reader.nextInt();
		switch (userChoice) {
		case 1:
			User UserValues = getNameValues();
			nameUpdate(UserValues);
			break;
		case 2:
			UserValues = getNameValues();
			mobileUpdate(UserValues);
			break;
		case 3:
			UserValues = getNameValues();
			emailUpdate(UserValues);
			break;

		default:
			System.out.println("Invalid Input");
			break;
		}

	}

	private User getNameValues() throws ClassNotFoundException, SQLException {
		Scanner reader = new Scanner(System.in);
		User user = new User();
		System.out.println("Enter find a contact  First Name");
		user.setFirstName(reader.next());
		System.out.println("Enter find a contact Last Name");
		user.setLastName(reader.next());

		user.setUserId(contactApplicationDAO.fetchTheUserId(user));
		return user;
	}

	private void emailUpdate(User userValues) throws ClassNotFoundException, SQLException {

		Email email = new Email();
		String emailType[] = { "PERSONAL", "OFFICE" };
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter mail id");
		boolean mailStatus = true;
		while (mailStatus) {
			email.setMailId(reader.next());
			String userMessage = "emailValidation";

			boolean mailIdStatus = validation.checkUserDetails(userMessage, email.getMailId());
			if (mailIdStatus) {
				mailStatus = false;
				boolean mailType = true;
				while (mailType) {
					System.out.println("Mail Type");
					for (int index = 0; index < emailType.length; index++) {
						System.out.println((index + 1) + " : " + emailType[index]);
					}
					int userChoice = reader.nextInt();
					if (userChoice - 1 <= emailType.length) {
						mailType = false;
						email.setMailType(emailType[userChoice - 1]);

					} else {
						System.out.println("Invalid Input Mail Type");
					}
				}
				userValues.setMailId(email);
				contactApplicationDAO.updateMailId(userValues);
			}

			initContactController();

		}
	}

	private void mobileUpdate(User userValues) throws ClassNotFoundException, SQLException {
		MobileNumber mobileNumber = new MobileNumber();
		String mobileTypes[] = { "HOME", "OFFICE", "PERSONAL", "OTHERS" };
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter a MobileNumber");

		boolean mobileStatus = true;
		while (mobileStatus) {
			mobileNumber.setMobileNumber(reader.next());

			String userMessage = "mobileValidation";
			boolean status = validation.checkUserDetails(userMessage, mobileNumber.getMobileNumber());
			if (status) {
				mobileStatus = false;
				System.out.println("Mobile Type");
				boolean validInput = true;
				while (validInput) {
					for (int index = 0; index < mobileTypes.length; index++) {
						System.out.println((index + 1) + " : " + mobileTypes[index]);
					}
					int userChoice = reader.nextInt();
					if (userChoice - 1 <= mobileTypes.length) {
						validInput = false;
						mobileNumber.setMobileType(mobileTypes[userChoice - 1]);
						System.out.println("This Number added in EMERGENCY CONTACT ? YES for 1 OR NO for 2 ");
						int choice = reader.nextInt();
						if (choice == 1) {
							mobileNumber.setEmergencyContact(true);
						} else {
							mobileNumber.setEmergencyContact(false);
						}
						userValues.setMobileNumber(mobileNumber);
						contactApplicationDAO.updateMobileDetails(userValues);
					}

					else {
						System.out.println("Invalid Input");
					}
				}
			} else {
				System.out.println("Invalid phone number");
			}
		}
		System.out.println("Mobile Number Updated Successfully");
		initContactController();

	}

	private void nameUpdate(User userValues) throws ClassNotFoundException, SQLException {
		Scanner reader = new Scanner(System.in);
	
		System.out.println("Enter First Name");
		userValues.setFirstName(reader.next());
		System.out.println("Enter Last Name");
		userValues.setLastName(reader.next());
		contactApplicationDAO.updateUserName(userValues);
		System.out.println("Name Updated Successfully");
		initContactController();
	}

	private void deleteContact() throws ClassNotFoundException, SQLException {
		Scanner reader = new Scanner(System.in);
		User user = new User();
		System.out.println("Enter the first name");
		user.setFirstName(reader.next());
		System.out.println("Enter the last name");
		user.setLastName(reader.next());
		Integer deleteResult = contactApplicationDAO.deleteTheUser(user);
		if (deleteResult == 1) {
			System.out.println("Contact Deleted Successfully");
			initContactController();

		} else {
			System.out.println("Invalid User Name");
			deleteContact();
		}

	}

	private void viewAllContact() throws ClassNotFoundException, SQLException {
		List<User> allUserDetail = contactApplicationDAO.fetchAllUser();
		showAllUserDetails(allUserDetail);
	}

	private void showAllUserDetails(List<User> allUserDetail) throws ClassNotFoundException, SQLException {
		System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %-15s ", "First Name", "Last  Name", "Address",
				"Mobile Number", "Mobile Type", "Emergency Contact", "Email Id", "Email Type");
		System.out.println();
		System.out.println(
				"*********************************************************************************************************************************************************************");
		for (int index = 0; index < allUserDetail.size(); index++) {
			System.out.println(
					"********************************************************************************************************************************************");

			System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s",
					allUserDetail.get(index).getFirstName(), allUserDetail.get(index).getLastName(),
					allUserDetail.get(index).getAddress(), allUserDetail.get(index).getMobileNumber().getMobileNumber(),
					allUserDetail.get(index).getMobileNumber().getMobileType(),
					allUserDetail.get(index).getMobileNumber().getEmergencyContact(),
					allUserDetail.get(index).getMailId().getMailId(),
					allUserDetail.get(index).getMailId().getMailType());

			System.out.println();

		}
		initContactController();

	}

	private void viewContact() throws ClassNotFoundException, SQLException {
		Scanner reader = new Scanner(System.in);
		User user = new User();
		System.out.println("Enter the first name");
		user.setFirstName(reader.next());
		System.out.println("Enter the last name");
		user.setLastName(reader.next());
		Integer userId = contactApplicationDAO.fetchTheUserId(user);
		if (userId == null) {
			System.out.println("Names are mismatched");
			viewContact();
		} else {
			user.setUserId(userId);
			User userValues = contactApplicationDAO.fetchAllDetails(user);
			showTheUserDetails(userValues);
		}

	}

	private void showTheUserDetails(User userValues) throws ClassNotFoundException, SQLException {
		System.out.println("First Name       : " + userValues.getFirstName());
		System.out.println("Last  Name       : " + userValues.getLastName());
		System.out.println("Address          : " + userValues.getAddress());
		System.out.println("Mobile Number    : " + userValues.getMobileNumber().getMobileNumber());
		System.out.println("Mobile Type      : " + userValues.getMobileNumber().getMobileType());
		System.out.println("Mobile Emergency : " + userValues.getMobileNumber().getEmergencyContact());
		System.out.println("Email Id         : " + userValues.getMailId().getMailId());
		System.out.println("Email Type       : " + userValues.getMailId().getMailType());
		initContactController();
	}

	private void addContact() throws SQLException, ClassNotFoundException {
		String mobileTypes[] = { "HOME", "OFFICE", "PERSONAL", "OTHERS" };
		String emailType[] = { "PERSONAL", "OFFICE" };
		User user = new User();
		MobileNumber mobileNumber = new MobileNumber();
		Email email = new Email();

		Scanner reader = new Scanner(System.in);

		System.out.println("Enter First Name");
		user.setFirstName(reader.next());
		System.out.println("Enter Last Name");
		user.setLastName(reader.next());

		boolean nameStatus = contactApplicationDAO.checkUserPresentOrNot(user);
		if (nameStatus) {
			System.out.println("Enter address");
			reader.next();
			user.setAddress(reader.nextLine());

			contactApplicationDAO.addUser(user);
			user.setUserId(contactApplicationDAO.fetchTheUserId(user));
		} else

		{
			System.out.println("Name is Already Taken, Please Give a another name");
			addContact();
		}
		System.out.println("Enter a MobileNumber");

		boolean mobileStatus = true;
		while (mobileStatus) {
			mobileNumber.setMobileNumber(reader.next());

			String userMessage = "mobileValidation";
			boolean status = validation.checkUserDetails(userMessage, mobileNumber.getMobileNumber());
			if (status) {
				mobileStatus = false;
				System.out.println("Mobile Type");
				boolean validInput = true;
				while (validInput) {
					for (int index = 0; index < mobileTypes.length; index++) {
						System.out.println((index + 1) + " : " + mobileTypes[index]);
					}
					int userChoice = reader.nextInt();
					if (userChoice - 1 <= mobileTypes.length) {
						validInput = false;
						mobileNumber.setMobileType(mobileTypes[userChoice - 1]);
						System.out.println("This Number added in EMERGENCY CONTACT ? YES for 1 OR NO for 2 ");
						int choice = reader.nextInt();
						if (choice == 1) {
							mobileNumber.setEmergencyContact(true);
						} else {
							mobileNumber.setEmergencyContact(false);
						}
						user.setMobileNumber(mobileNumber);
						contactApplicationDAO.addMobileDetails(user);
					}

					else {
						System.out.println("Invalid Input");
					}
				}
			} else {
				System.out.println("Invalid phone number");
			}
		}

		System.out.println("Enter mail id");
		boolean mailStatus = true;
		while (mailStatus) {
			email.setMailId(reader.next());
			String userMessage = "emailValidation";

			boolean mailIdStatus = validation.checkUserDetails(userMessage, email.getMailId());
			if (mailIdStatus) {
				mailStatus = false;
				boolean mailType = true;
				while (mailType) {
					System.out.println("Mail Type");
					for (int index = 0; index < emailType.length; index++) {
						System.out.println((index + 1) + " : " + emailType[index]);
					}
					int userChoice = reader.nextInt();
					if (userChoice - 1 <= emailType.length) {
						mailType = false;
						email.setMailType(emailType[userChoice - 1]);

					} else {
						System.out.println("Invalid Input Mail Type");
					}
				}
				user.setMailId(email);
				contactApplicationDAO.addMailId(user);
				initContactController();

			}

		}

	}

}
