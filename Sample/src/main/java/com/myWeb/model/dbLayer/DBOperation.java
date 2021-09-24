package com.myWeb.model.dbLayer;

import com.myWeb.model.BankException.CustomizedException;
import com.myWeb.model.persistence.PersistenceManager;
import com.myWeb.model.pojo.*;

import java.sql.*;
import java.util.*;

public class DBOperation implements PersistenceManager {
	private static Connection con;

	private static void loadConnection() throws CustomizedException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer", "root", "Root@123");
		} catch (SQLException e) {
			// TODO: handle exception
			throw new CustomizedException("JDBC Driver Error", e);
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			throw new CustomizedException("Class Not Found in JDBC Driver", e);
		}

	}

	public static Connection getConnection() throws CustomizedException {
		if (con == null) {
			loadConnection();
		}
		return con;
	}

	// All Query
	private static final String selectAllRecordsFromCustomerInfo = "SELECT * FROM CustomerInfo WHERE CusStatus != 'Inactive' ";
	private static final String selectAllRecordsFromAccountInfo = "SELECT * FROM AccountInfo";
	private static final String insertRecordsToCustomerTable = "insert into CustomerInfo (CusName, CusDoB, Location) values (?, ?, ?)";
	private static final String insertRecordsToAccountInfoTable = "insert into AccountInfo (AccNumber, AccBalance, Branch, CusID ) values (?, ?, ?,?)";

	@Override
	public List<Customer> customerInfoRecords() throws CustomizedException {
		List<Customer> customerInfoList = new ArrayList<>();
		try {
			PreparedStatement ps = getConnection().prepareStatement(selectAllRecordsFromCustomerInfo);
			ResultSet rs = ps.executeQuery();
			try {
				while (rs.next()) {
					Customer customerInfo = new Customer();
					customerInfo.setCusID(rs.getInt(1));
					customerInfo.setName(rs.getString(2));
					customerInfo.setDofBirth(rs.getLong(3));
					customerInfo.setLocation(rs.getString(4));
					customerInfo.setCusStatus(rs.getString(5));
					customerInfoList.add(customerInfo);
				}
			} finally {
				rs.close();
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomizedException("Customer HashMap Loading is Unsuccessful !!");
		}
		return customerInfoList;
	}

	@Override
	public List<AccountInfo> accountInfoRecords() throws CustomizedException {
		List<AccountInfo> accountInfoArray = new ArrayList<>();
		try {
			PreparedStatement ps = getConnection().prepareStatement(selectAllRecordsFromAccountInfo);
			ResultSet rs = ps.executeQuery();
			try {
				while (rs.next()) {
					AccountInfo accountInfo = new AccountInfo();
					accountInfo.setAccId(rs.getInt(1));
					accountInfo.setAccNo(rs.getLong(2));
					accountInfo.setAccBalance(rs.getInt(3));
					accountInfo.setAccBranch(rs.getString(4));
					accountInfo.setCusId(rs.getInt(5));
					accountInfoArray.add(accountInfo);
				}
			} finally {
				rs.close();
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomizedException("Account HashMap Loading is Unsuccessful,Invalid Query!!");
		}
		return accountInfoArray;
	}

	// insert Customer Info to Database
	public int[] persistCustomerList(List<Customer> customerArrayList) throws CustomizedException {
		ResultSet rs = null;
		try {
			PreparedStatement ps = getConnection().prepareStatement(insertRecordsToCustomerTable,
					Statement.RETURN_GENERATED_KEYS);
			int[] cusId = new int[customerArrayList.size()];
			try {
				for (Customer customer : customerArrayList) {
					String name = customer.getName();
					long date = System.currentTimeMillis();
					String location = customer.getLocation();
					ps.setString(1, name);
					ps.setLong(2, date);
					ps.setString(3, location);
					ps.addBatch();
				}
				ps.executeBatch();
				rs = ps.getGeneratedKeys();
				int i = 0;
				while (rs.next()) {
					cusId[i] = rs.getInt(1);
					i++;
				}
				return cusId; // return Customers ID
			} catch (SQLException e) {
				throw new CustomizedException("SQL Exception", e);
			} finally {
				rs.close();
				ps.close();
			}
		} catch (SQLException e) {
			throw new CustomizedException("Customer Records Submission Unsuccessful", e);
		}

	}

	// Insert AccountInfo to Database
	public void insertAccountToDB(int[] cusID, List<AccountInfo> accountInfoArrayList) throws CustomizedException {
		try {
			PreparedStatement ps = getConnection().prepareStatement(insertRecordsToAccountInfoTable);
			try {
				for (int i = 0; i < accountInfoArrayList.size(); i++) {
					AccountInfo accountInfo = accountInfoArrayList.get(i);
					long accNo = accountInfo.getAccNo();
					int accBalance = accountInfo.getAccBalance();
					String accBranch = accountInfo.getAccBranch();
					int cusId = cusID[i];
					ps.setLong(1, accNo);
					ps.setInt(2, accBalance);
					ps.setString(3, accBranch);
					ps.setInt(4, cusId);
					ps.addBatch();
				}
				ps.executeBatch();
				System.out.println("Account Record inserted");
			} finally {
				ps.close();
			}
		} catch (SQLException e) {
			throw new CustomizedException("Account Detail Submission Failed, Query Error");
		}
	}

	public void removeAccountDetail(int cusId) throws CustomizedException {
		String query = "DELETE FROM AccountInfo WHERE CusID=" + cusId + "";
		try {
			Statement st = getConnection().createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			throw new CustomizedException("Account Deletion Failed");
		}
	}

	// Delete Customer From Database
	public void deleteCustomer(Integer cusId) throws CustomizedException {
		String query = "Update CustomerInfo SET CusStatus = IF(CusStatus = 1, 2, CusStatus) WHERE CusId =" + cusId + "";
		try {
			Statement st = getConnection().createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			throw new CustomizedException("Customer Deletion Failed // No Records Found");
		}
	}
}