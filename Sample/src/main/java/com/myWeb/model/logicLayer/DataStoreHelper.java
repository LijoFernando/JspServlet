package com.myWeb.model.logicLayer;

import com.myWeb.model.BankException.CustomizedException;
import com.myWeb.model.configuration.DataHandler;



public class DataStoreHelper {

            //Delete CustomerInfo From Database
            public static void deleteExistingCustomer(Integer cusID) throws CustomizedException{
                if (!LoadDataToHMap.deletedList.contains(cusID)) {
                        DataHandler.getPersistenceManager().deleteCustomer(cusID);
                }
            }


}
