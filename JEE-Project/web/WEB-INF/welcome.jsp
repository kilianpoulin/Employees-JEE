<%@page import="model.Employee"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.userSession"%>
<%@page import="model.DataAccess"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/welcome.css">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    
</head><body>
    <p class="logout">
        <font color="blue">Your session is active</font>
        <a href="Controller?sub=Disconnect"><img style="width:20px;" src="img/logout.png" alt="Log out"></a>
    </p>
        <h1>List of Employees</h1><br/>
<% 
    //Print all the employees
    DataAccess dTransac = new DataAccess(); 
    String query = "SELECT * FROM EMPLOYEES";
    ArrayList <Employee> Employees = dTransac.getDBEmployees(dTransac.getResultSet(dTransac.getStatement(dTransac.getConnection()), query));
    //If there are no employees
    if(Employees.isEmpty()) 
        out.println("<p class='no_member'>The club has no member!</p>");
    else{
        //Else print the details of each employees
        out.println("<table class='w3-table w3-striped w3-centered'><form method ='GET' action='Controller'>");
        out.println("<tr class='w3-blue'><th>Sel</th><th>NAME</th><th>FIRST NAME</th><th>HOME PHONE</th><th>MOBILE PHONE</th><th>WORK PHONE</th><th>ADDRESS</th><th>POSTAL CODE</th><th>CITY</th><th>EMAIL</th></tr>");
        for(Employee e : Employees)
        {
            out.println("<tr>");
            //Put the id in the radio button
            out.println("<td><input type='radio' name='edit' value='" + e.getID() + "'/> </td>");
            out.println("<td>" + e.getName() + "</td><td>" + e.getFirstName() + "</td><td>" + e.getHomePhone() + "</td><td>" + e.getMobilePhone() + "</td><td>" + e.getWorkPhone() + "</td><td>" + e.getAddress() + "</td><td>" + e.getPostalCode() + "</td><td>" + e.getCity() + "</td><td>" + e.getEmail() + "</td>");
            out.println("</tr>");
        }
    
%>
</table>
    </div>
    <br/><br/>
<div class="w3-container">
    <input type="submit" name="sub" value="Delete" class="button"/>
    <input type="submit" name="sub" value="Details" class="button"/>
    <input type="submit" name="sub" value="Add" class="button"/>
</div>
    </form>
</body>
<% } %>