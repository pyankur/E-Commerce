<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Home Of Furniture</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 <spring:url value="/user/login" var="loginrURL"></spring:url>
<%-- <%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setHeader("Expires", "0"); // Proxies.

if(session.getAttribute("user")==null){
	response.sendRedirect("${loginrURL}"); 
}
%> --%>
</head>
<body >
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
      <ul class="nav navbar-nav">
     		<spring:url value="/user/home" var="homeURL"></spring:url>
		    <li><a href="${homeURL}">Home</a></li>
		    
		    <spring:url value="/user/address" var="addrURL"></spring:url>
		   	<li><a href="${addrURL}" style="float:right">Address</a></li>
		   	
		   	<spring:url value="/user/viewcart" var="cartURL"></spring:url>
			<li><a href="${cartURL}" style="float:right">View Cart</a></li>
			
			<spring:url value="/user/logout" var="logURL"></spring:url>
		    <li><a href="${logURL}" style="float:right">Log out</a></li>  
      </ul>
    </div>
</nav>
<div class="container">
       <div class="form-container">
       <spring:url value="/user/addAddress" var="addrUpdate" />
		<form:form class="form-horizontal" modelAttribute="addressform" method="POST" action="${addrUpdate}">
              <h2>Please provide us your location</h2><hr />
              <div class="form-group">
                  <label class="control-label col-sm-2" for="address">Address: </label>
                <div class="col-sm-3">
                  <form:input type="text" class="form-control" name="address" path="address" /><br>
                </div>
              </div>
              <div class="form-group">
   			      <label class="control-label col-sm-2" for="city">City: </label>
   	  			  <div class="col-sm-3">
   				        <form:input type="text" class="form-control" name="city" path="city"  /><br>
   				   </div>
		      </div>
 			  <div class="form-group">
   			      <label class="control-label col-sm-2" for="state">State: </label>
   	  			  <div class="col-sm-3">
   				        <form:input type="text" class="form-control" name="state" path="state"  /><br>
   				   </div>
 			  </div>
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="zipcode">Zipcode: </label>
	                <div class="col-sm-3">
	                    <form:input type="text" class="form-control" name="zipcode" path="zipcode"  /><br>
	                    <form:input type="hidden" class="form-control" name="email" path="email"  />
	                    
	               </div>
	            </div>
                <div class="clearfix"></div><hr />
                <div class="form-group">
                  <div class="col-sm-offset-2 col-sm-2">
	                 <button type="submit" class="btn btn-block btn-primary" name="signup">
	                    Update
                    </button>
                  </div>
              </div>
              <br>
        </form:form>
         </div>
  </div>
</body>
<script type="text/javascript"> window.onload = alertName; </script>
</html>