package com.myWeb.model.logicLayer;

import com.myWeb.model.BankException.CustomizedException;
import com.myWeb.model.configuration.DataHandler;
import com.myWeb.model.pojo.*;


import java.util.*;

public class LoadDataToHMap {
    //Customer Information
    public static Map<Integer, Customer> customerInfoHashMap = null ;
    //Customer Account Information
    public static Map<Integer,Map<Long, AccountInfo> > accountInfoHashMap = null;
    //Inactive Customer
    public static ArrayList<Integer> deletedList = null;
    public static List<AccountInfo> accountInfoList = null;

    //load All data to HashMap
    public static void loadHashMap() throws CustomizedException {
        customerInfoHashMap = new HashMap<>();
        accountInfoHashMap = new HashMap<>();
        //ArrayList of customer and Account Details
        List <Customer> customerInfoList = DataHandler.getPersistenceManager().customerInfoRecords();
        accountInfoList = DataHandler.getPersistenceManager().accountInfoRecords();
        try {
            for (Customer customerInfo: customerInfoList){
                int cusId = customerInfo.getCusID();
                customerInfoHashMap.put(cusId,customerInfo);
            }

            //System.out.println(customerInfoHashMap);
            for (AccountInfo accInfo : accountInfoList) {
                int cusId = accInfo.getCusId();
                long accNo = accInfo.getAccNo();
                Map<Long, AccountInfo> innerHashMap = accountInfoHashMap.computeIfAbsent(cusId, k -> new HashMap<>());
                innerHashMap.put(accNo, accInfo);
            }
            //System.out.println(accountInfoHashMap.entrySet());
        }
        catch (Exception e) {
            //throw new CustomizedException("AccountInfo HashMap not loaded");
            e.printStackTrace();
        }
    }

    //returnCustomerMap And AccountMap
    public static Map<Integer,Customer> getCustomerHMap(){
        return  customerInfoHashMap;
    }
    public static Map<Integer,Map<Long,AccountInfo>> getAccountInfoHMap(){
        return accountInfoHashMap;
    }
    
    //List Of DELETED ACCOUNT
    public static ArrayList<Integer> deletedCustomer(){
        Collection<Customer> customersList = getCustomerHMap().values();
        deletedList = new ArrayList<>();
        for (Customer cus:customersList) {
            if(cus.getCusStatus().equals("Inactive")){
                deletedList.add(cus.getCusID());
            }
        }
        return deletedList;
    }
    
    //Get AccountList
    public static List<AccountInfo> getAccountList(){
    	if(accountInfoList != null) {
    		
    		return accountInfoList;
    	}
    	return null;
    }
    //Get Account Details For Specific Customer
    public static List<String> getSpecificCustomerAccount(Integer cusId) throws CustomizedException {
        try {
            List<String> specificAccountList = new ArrayList<>();
            if(!deletedList.contains(cusId)){
                accountInfoHashMap.get(cusId);
            }
            return specificAccountList ;
        } catch (NullPointerException e){
            throw  new CustomizedException("Customer ID is Invalid");
        }
    }
}
