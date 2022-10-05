package com.zoho.contactmanagementapplication.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zoho.contactmanagementapplication.model.Email;
import com.zoho.contactmanagementapplication.model.MobileNumber;
import com.zoho.contactmanagementapplication.model.User;

public class ContactApplicationDAO {

	static Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;

	private void getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/contactmanagementapplication?characterEncoding=latin1&useConfigs=maxPerformance",
				"root", "root");
	}

	public void addUser(User user) throws SQLException, ClassNotFoundException {
		getConnection();
		String sqlQuery = "INSERT INTO USER (First_NAME,LAST_NAME,ADDRESS) VALUES(?,?,?)";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setString(1, user.getFirstName());
		preparedStatement.setString(2, user.getLastName());
		preparedStatement.setString(3, user.getAddress());
		preparedStatement.executeUpdate();

	}

	public Integer fetchTheUserId(User user) throws ClassNotFoundException, SQLException {
		getConnection();
		Integer userId = null;
		String sqlQuery = "SELECT ID FROM USER WHERE FIRST_NAME= ? AND LAST_NAME=?";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setString(1, user.getFirstName());
		preparedStatement.setString(2, user.getLastName());

		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			userId = resultSet.getInt(1);

		}
		return userId;
	}

	public boolean checkUserPresentOrNot(User user) throws SQLException, ClassNotFoundException {
		getConnection();
		Boolean nameStatus = true;
		String sqlQuery = "SELECT FIRST_NAME,LAST_NAME FROM USER";
		preparedStatement = connection.prepareStatement(sqlQuery);

		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			String fName = resultSet.getString(1);
			String lName = resultSet.getString(2);
			String input = user.getFirstName() + user.getLastName();
			String checkValue = fName + lName;

			if (input.equalsIgnoreCase(checkValue)) {
				nameStatus = false;
				break;

			}

		}
		return nameStatus;
	}

	public void addMobileDetails(User user) throws ClassNotFoundException, SQLException {
		getConnection();
		String sqlQuery = "INSERT INTO MOBILE_NUMBER (mobile_number,mobile_type,emergency_contact,user_id) VALUES(?,?,?,?)";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setString(1, user.getMobileNumber().getMobileNumber());
		preparedStatement.setString(2, user.getMobileNumber().getMobileType());
		preparedStatement.setBoolean(3, user.getMobileNumber().getEmergencyContact());
		preparedStatement.setInt(4, user.getUserId());
		preparedStatement.executeUpdate();

	}

	public void addMailId(User user) throws ClassNotFoundException, SQLException {
		getConnection();
		String sqlQuery = "INSERT INTO email (email,email_type,user_id) VALUES(?,?,?)";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setString(1, user.getMailId().getMailId());
		preparedStatement.setString(2, user.getMailId().getMailType());
		preparedStatement.setInt(3, user.getUserId());
		preparedStatement.executeUpdate();

	}

	public User fetchAllDetails(User user) throws ClassNotFoundException, SQLException {
		getConnection();
		User userValues = new User();
		MobileNumber mobileNumber = new MobileNumber();
		Email email = new Email();
		String sqlQuery = "SELECT user.first_name,user.last_name,user.address,mobile_number.mobile_number,mobile_number.mobile_type,mobile_number.emergency_contact,email. email,email.email_type from USER INNER JOIN mobile_number ON USER.ID = mobile_number.USER_ID INNER JOIN EMAIL ON EMAIL.USER_ID=USER.ID WHERE USER.ID = ?;";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setInt(1, user.getUserId());

		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {

			userValues.setFirstName(resultSet.getString(1));
			userValues.setLastName(resultSet.getString(2));
			userValues.setAddress(resultSet.getString(3));
			mobileNumber.setMobileNumber(resultSet.getString(4));
			mobileNumber.setMobileType(resultSet.getString(5));
			mobileNumber.setEmergencyContact(resultSet.getBoolean(6));
			userValues.setMobileNumber(mobileNumber);
			email.setMailId(resultSet.getString(7));
			email.setMailType(resultSet.getString(8));
			userValues.setMailId(email);

		}
		return userValues;

	}

	public List<User> fetchAllUser() throws ClassNotFoundException, SQLException {
		getConnection();
		String sqlQuery = "SELECT user.first_name,user.last_name,user.address,mobile_number.mobile_number,mobile_number.mobile_type,mobile_number.emergency_contact,email. email,email.email_type from USER INNER JOIN mobile_number ON USER.ID = mobile_number.USER_ID INNER JOIN EMAIL ON EMAIL.USER_ID=USER.ID order by user.first_name asc";
		preparedStatement = connection.prepareStatement(sqlQuery);
		List<User> allUserDetails = new ArrayList<User>();

		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			User userValues = new User();
			MobileNumber mobileNumber = new MobileNumber();
			Email email = new Email();
			userValues.setFirstName(resultSet.getString(1));
			userValues.setLastName(resultSet.getString(2));
			userValues.setAddress(resultSet.getString(3));
			mobileNumber.setMobileNumber(resultSet.getString(4));
			mobileNumber.setMobileType(resultSet.getString(5));
			mobileNumber.setEmergencyContact(resultSet.getBoolean(6));
			userValues.setMobileNumber(mobileNumber);
			email.setMailId(resultSet.getString(7));
			email.setMailType(resultSet.getString(8));
			userValues.setMailId(email);
			allUserDetails.add(userValues);

		}
		return allUserDetails;

	}

	public Integer deleteTheUser(User user) throws ClassNotFoundException, SQLException {
		getConnection();
		String sqlQuery = "DELETE FROM USER WHERE USER.FIRST_NAME = ? AND LAST_NAME = ?";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setString(1, user.getFirstName());
		preparedStatement.setString(2, user.getLastName());

		int result = preparedStatement.executeUpdate();
		return result;

	}

	public void updateUserName(User userValues) throws ClassNotFoundException, SQLException {
		getConnection();
		String sqlQuery = "UPDATE USER SET FIRST_NAME =? ,LAST_NAME=? WHERE ID = ?";
		preparedStatement = connection.prepareStatement(sqlQuery);

		preparedStatement.setString(1, userValues.getFirstName());
		preparedStatement.setString(2, userValues.getLastName());
		preparedStatement.setInt(3, userValues.getUserId());

		preparedStatement.executeUpdate();

	}

	public void updateMobileDetails(User userValues) throws ClassNotFoundException, SQLException {
		getConnection();
		String sqlQuery = "UPDATE MOBILE_NUMBER SET MOBILE_NUMBER =? , MOBILE_TYPE =? , EMERGENCY_CONTACT =? WHERE USER_ID = ?";
		preparedStatement = connection.prepareStatement(sqlQuery);

		preparedStatement.setString(1, userValues.getMobileNumber().getMobileNumber());
		preparedStatement.setString(2, userValues.getMobileNumber().getMobileType());
		preparedStatement.setBoolean(3, userValues.getMobileNumber().getEmergencyContact());
		preparedStatement.setInt(4, userValues.getUserId());

		preparedStatement.executeUpdate();

	}

	public void updateMailId(User userValues) throws ClassNotFoundException, SQLException {
		getConnection();
		String sqlQuery = "UPDATE EMAIL SET EMAIL =? , EMAIL_TYPE =? WHERE USER_ID = ?";
		preparedStatement = connection.prepareStatement(sqlQuery);

		preparedStatement.setString(1, userValues.getMailId().getMailId());
		preparedStatement.setString(2, userValues.getMailId().getMailType());
		preparedStatement.setInt(3, userValues.getUserId());

		preparedStatement.executeUpdate();

	}

}
