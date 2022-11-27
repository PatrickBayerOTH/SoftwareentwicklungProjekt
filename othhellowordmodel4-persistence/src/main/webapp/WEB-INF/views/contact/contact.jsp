<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>

<body>	

<h1>Contact us!</h1>

<form action="/contact/add" method="post">

   Name:
   <input type="text" id="name" name="name" value="">
   <br>
   Email:
   <input type="text" id="email" name="email" value="">
   <br>   
   Message:
   <input type="text" id="message" name="message" value="">
   <br>
   <button type="submit" name="submit"  >Submit</button>
   <button type="reset" name="reset">Reset</button>
</form>

</body>
</html>