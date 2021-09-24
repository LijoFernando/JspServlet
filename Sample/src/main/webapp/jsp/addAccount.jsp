<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta charset="UTF-8">
<title>Account Form</title>
<link rel="stylesheet" href="./responsiveRegistration.css">
<script type="text/javascript" lang="javascript"
	src="./responsiveRegistaration.js"></script>
<style type="text/css">
* {
	box-sizing: border-box;
}

input[type=text], input[type=number], input[type=select], input[type=tel]
	{
	width: 45%;
	padding: 12px;
	border: 1px solid rgb(168, 166, 166);
	border-radius: 4px;
	resize: vertical;
}

textarea {
	width: 45%;
	padding: 12px;
	border: 1px solid rgb(168, 166, 166);
	border-radius: 4px;
	resize: vertical;
}

h1 {
	font-family: Arial;
	font-size: medium;
	font-style: normal;
	font-weight: bold;
	color: brown;
	text-align: center;
	text-decoration: underline;
}

label {
	padding: 12px 12px 12px 0;
	display: inline-block;
}

input[type=submit] {
	background-color: #4CAF50;
	color: white;
	padding: 12px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	float: left;
}

input[type=submit]:hover {
	background-color: #32a336;
}

.container {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
}

.col-10 {
	float: left;
	width: 10%;
	margin-top: 6px;
}

.col-90 {
	float: left;
	width: 90%;
	margin-top: 6px;
}

.row:after {
	content: "";
	display: table;
	clear: both;
}

@media screen and (max-width: 600px) {
	.col-10, .col-90, input[type=submit] {
		width: 100%;
		margin-top: 0;
	}
}
</style>
</head>


<body>
	<h1>Account Detail Form</h1>


	<form
		action="${pageContext.request.contextPath }/welcome?name=insertaccount"
		method="post">
		<div class="container">
			<div class="row">

				<div class="col-10">
					<label for="mobile">Customer ID:</label>
				</div>
				<div class="col-90">
					<input type="tel" id="cusId" name="cusId"
						placeholder="Enter your customer ID">
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<label for="mobile">Account Number:</label>
				</div>
				<div class="col-90">
					<input type="tel" id="accno" name="accno"
						placeholder="only numbers are allowed">
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<label for="balance">Account Balance:</label>
				</div>
				<div class="col-90">
					<input type="tel" id="accBalance" name="accBalance"
						placeholder="Account Balance">
				</div>
			</div>

			<div class="row">
				<div class="col-10">
					<label for="city">Branch Location:</label>
				</div>
				<div class="col-90">
					<input type="text" id="city" name="city"
						placeholder="Bank Branch Location">
				</div>
			</div>

			<div class="row">
				<!--  <input type="submit" value="Submit" onclick="SaveStudentDetails()"> -->
				<button type="submit" value ="submit" >Submit</button>
			</div>
		</div>
	</form>

</body>
</html>