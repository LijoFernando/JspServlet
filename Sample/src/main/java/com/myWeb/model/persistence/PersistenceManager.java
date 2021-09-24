package com.myWeb.model.persistence;

import com.myWeb.model.BankException.CustomizedException;
import com.myWeb.model.pojo.*;

import java.util.List;

public interface PersistenceManager {

	List<Customer> customerInfoRecords() throws CustomizedException;

	List<AccountInfo> accountInfoRecords() throws CustomizedException;

	int[] persistCustomerList(List<Customer> customerArrayList) throws CustomizedException;

	void insertAccountToDB(int[] cusID, List<AccountInfo> accountInfoArrayList) throws CustomizedException;

	void removeAccountDetail(int cusId) throws CustomizedException;

	void deleteCustomer(Integer cusId) throws CustomizedException;

}
