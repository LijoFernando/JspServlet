<%@page import="com.myWeb.model.pojo.Customer"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>CustomerPage</title>
<style>
*
.topnav {
	background-color: #333;
	overflow: hidden;
	color: #f2f2f2;
	padding: 14px 16px;
	font-size: 17px;
	text-align: center;
}

table {
	font-family: arial, sans-serif;
	border-collapse: collapse;
	width: 50%;
}

td, th {
	border: 1px solid #dddddd;
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #dddddd;
}

.button {
	border: none;
	color: white;
	padding: 15px 32px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 2px;
	cursor: pointer;
	background-color: #008CBA;
}

.column {
	float: left;
	width: 50%;
}

/* Clear floats after the columns */
.row:after {
	content: "";
	display: table;
	clear: both;
}
</style>
</head>
<body>

	<div class="topnav">
		<h1>Customer Information Page</h1>
	</div>
	<%@page import="java.util.*"%>
	<div class="row">
		<form action="${pageContext.request.contextPath}/welcome?">
			<div class="column">
				<div>

					<table>
						<tr>
							<td>Customer ID</td>
							<td>Name</td>
							<td>Location</td>
							<td>Status</td>
							<td>Select</td>

						</tr>

						<c:forEach items="${customer}" var="entry">
							<tr>
								<td>${entry.key}</td>
								<td>${entry.value.name}</td>
								<td>${entry.value.location}</td>
								<td>${entry.value.cusStatus}</td>
								<td><input type="checkbox" name="selectcusid"
									value="${entry.key}"></td>
							</tr>

						</c:forEach>
					</table>
				</div>
				<div style="align-content: center">

					<button class="button" name="name" value="createCustomer">Add
						Customer</button>
					<button class="button" name="name" value="deleteCustomer">Delete</button>
					<input type="button" class="button" name="cancel_button"
						value="Back" onclick="history.back()"></input>

				</div>

			</div>
		</form>
	</div>


</body>
</html>