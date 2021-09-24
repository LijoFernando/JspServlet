import java.io.IOException;
import java.nio.file.Path;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.If;

import com.myWeb.model.BankException.CustomizedException;
import com.myWeb.model.configuration.DataHandler;
import com.myWeb.model.logicLayer.LoadDataToHMap;
import com.myWeb.model.pojo.*;

/**
 * Servlet implementation class WelcomeNew
 */
@WebServlet("/WelcomeNew")
public class WelcomeNew extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public WelcomeNew() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			LoadDataToHMap.loadHashMap();
		} catch (CustomizedException e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}

		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String name = request.getParameter("name");
		String pagePath = null;

		/*
		 * switch (name) {
		 * 
		 * case "customer": {
		 * 
		 * Map<Integer, Customer> customerHM = LoadDataToHMap.getCustomerHMap();
		 * request.setAttribute("customer", customerHM); pagePath =
		 * "/jsp/customerPage.jsp";
		 * 
		 * }
		 * 
		 * case "account": { List<AccountInfo> accountList =
		 * LoadDataToHMap.getAccountList(); request.setAttribute("account",
		 * accountList); pagePath = "/jsp/AccountPage.jsp"; }
		 * 
		 * case "createcustomer": { pagePath = "/jsp/createCustomer.jsp"; Customer
		 * customer = new Customer(); customer.setName(request.getParameter(name)); }
		 * 
		 * case "insertcustomer": {
		 * 
		 * Customer customer = new Customer();
		 * customer.setName(request.getParameter("fname"));
		 * 
		 * Date date = Date.valueOf(request.getParameter("dob").toString());
		 * customer.setDofBirth(date.getTime());
		 * customer.setLocation(request.getParameter("location"));
		 * 
		 * List<Customer> insertCustomerList = new ArrayList<>();
		 * insertCustomerList.add(customer); try {
		 * DataHandler.getPersistenceManager().persistCustomerList(insertCustomerList);
		 * } catch (Exception e) { // TODO: handle exception
		 * System.out.println(e.getMessage()); } } case "transact": { pagePath =
		 * "/jsp/transactionPage.jsp";
		 * 
		 * }
		 * 
		 * default: System.out.println("Request Declined"); }
		 */

		if (name.equalsIgnoreCase("customer")) {
			Map<Integer, Customer> customerHM = LoadDataToHMap.getCustomerHMap();
			request.setAttribute("customer", customerHM);
			pagePath = "/jsp/customerPage.jsp";

		} else if (name.equalsIgnoreCase("account")) {
			List<AccountInfo> accountList = LoadDataToHMap.getAccountList();
			request.setAttribute("account", accountList);
			pagePath = "/jsp/AccountPage.jsp";

		} else if (name.equalsIgnoreCase("addAccount")) {
			pagePath = "/jsp/addAccount.jsp";

		} else if (name.equalsIgnoreCase("createCustomer")) {
			pagePath = "/jsp/createCustomer.jsp";

		} else if (name.equalsIgnoreCase("insertCustomer")) {
			Customer customer = new Customer();
			String cusName = request.getParameter("fname");
			String location = request.getParameter("location");
			customer.setName(cusName);
			customer.setLocation(location);
			List<Customer> insertCustomerList = new ArrayList<>();
			insertCustomerList.add(customer);
			String message = "";
			try {
				int[] flag = DataHandler.getPersistenceManager().persistCustomerList(insertCustomerList);
				if (flag.length > 0) {
					message = "Record Inserted Successfully";
				} else {
					message = "Error!!";
				}
				LoadDataToHMap.loadHashMap();
				Map<Integer, Customer> customerHM = LoadDataToHMap.getCustomerHMap();
				request.setAttribute("customer", customerHM);
				pagePath = "/jsp/customerPage.jsp";

			} catch (Exception e) { // TODO: handle exception
				System.out.println(e.getMessage());
			}
		} else if (name.equalsIgnoreCase("insertaccount")) {
			AccountInfo accountInfo = new AccountInfo();
			accountInfo.setAccNo((long) Integer.parseInt(request.getParameter("accno")));
			accountInfo.setAccBalance(Integer.parseInt(request.getParameter("accBalance")));
			accountInfo.setAccBranch(request.getParameter("city"));
			Map<Integer, Customer> customerHM = LoadDataToHMap.getCustomerHMap();
			System.out.println(customerHM.entrySet());
			int cusId = Integer.parseInt(request.getParameter("cusId"));
			if (customerHM.containsKey(cusId)) {
				List<AccountInfo> accCustomersList = new ArrayList<>();
				accCustomersList.add(accountInfo);
				int[] arr = new int[1];
				arr[0] = cusId;
				try {

					DataHandler.getPersistenceManager().insertAccountToDB(arr, accCustomersList);
				} catch (Exception e) { // TODO: handle exception
					System.out.println(e.getMessage());
				}
			}

			try {
				LoadDataToHMap.loadHashMap();
				List<AccountInfo> accountList = LoadDataToHMap.getAccountList();
				request.setAttribute("account", accountList);
			} catch (Exception e) {
				// TODO: handle exception
			}

			pagePath = "/jsp/AccountPage.jsp";

		} else if (name.equalsIgnoreCase("deleteacc")) {
			String[] selectedId = request.getParameterValues("selectacc");
			for (int i = 0; i < selectedId.length; i++) {
				int cusid = Integer.parseInt(selectedId[i]);
				try {

					DataHandler.getPersistenceManager().removeAccountDetail(cusid);
				} catch (Exception e) {
					request.setAttribute("account", e);
				}
			}
			try {
				LoadDataToHMap.loadHashMap();
				List<AccountInfo> accountList = LoadDataToHMap.getAccountList();
				request.setAttribute("account", accountList);
			} catch (Exception e) {
				// TODO: handle exception
			}
			pagePath = "/jsp/AccountPage.jsp";
		} else if (name.equalsIgnoreCase("deletecustomer")) {

			String[] selectedId = request.getParameterValues("selectcusid");
			for (int i = 0; i < selectedId.length; i++) {
				int cusid = Integer.parseInt(selectedId[i]);
				try {

					DataHandler.getPersistenceManager().deleteCustomer(cusid);
				} catch (Exception e) {
					request.setAttribute("account", e);
				}
			}
			try {
				LoadDataToHMap.loadHashMap();
				Map<Integer, Customer> customerHM = LoadDataToHMap.getCustomerHMap();
				request.setAttribute("customer", customerHM);

			} catch (Exception e) {
				// TODO: handle exception
			}
			pagePath = "/jsp/customerPage.jsp";
		} else if (name.equalsIgnoreCase("transact")) {
			pagePath = "/jsp/transactionPage.jsp";

		} else {
			System.out.println("Request Declined");
		}

		if (pagePath != null) {
			RequestDispatcher rd = request.getRequestDispatcher(pagePath);
			rd.forward(request, response);
		} else {
			System.out.println("Requested Page NOT Found");

		}

	}

}
