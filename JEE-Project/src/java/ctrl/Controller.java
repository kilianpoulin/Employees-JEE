package ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DataAccess;
import model.Employee;
import model.userSession;

public class Controller extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try(PrintWriter out = response.getWriter()){
        String login = request.getParameter("login");
        String pwd = request.getParameter("pwd");
        String sub = (String)request.getParameter("sub");
        String name = request.getParameter("name");
        String fname = request.getParameter("firstname");
        String hphone = request.getParameter("home_phone");
        String mphone = request.getParameter("mobile_phone");
        String pphone = request.getParameter("pro_phone");
        String address = request.getParameter("address");
        String zipcode = request.getParameter("zip_code");
        String city = request.getParameter("city");
        String email = request.getParameter("email");
        String id = request.getParameter("edit");
        HttpSession session = request.getSession();
        userSession user = (userSession)session.getAttribute("user");
        String query;
        DataAccess dTransac = new DataAccess();
        
        //IF someone is connected
        if(user != null){
            if(sub != null){
                //If he make a chose
                switch (sub) {
                    case "Delete":
                        query = "DELETE FROM EMPLOYEES WHERE ID_EMPLOYEES=" + id;
                        dTransac.executeUpdate(query);
                        //Redirect to the controller with sub = succes so he will redirect to the form 
                        request.getRequestDispatcher("Controller?sub=success").forward(request, response);
                    case "Add":
                        request.setAttribute("choice", "Add");
                        request.getRequestDispatcher("WEB-INF/complete_form.jsp").forward(request, response);
                        break;
                    case "Details":
                        request.setAttribute("id", request.getParameter("edit"));
                        request.setAttribute("choice", "Details");
                        request.getRequestDispatcher("WEB-INF/complete_form.jsp").forward(request, response);
                        break;
                    case "Add New":
                        query = "INSERT INTO EMPLOYEES(NAME, FIRSTNAME, HOMEPHONE, MOBILEPHONE, WORKPHONE, ADDRESS, POSTALCODE, CITY, EMAIL)" +
                    "VALUES('"+ name + "','" + fname+ "','" + hphone + "','" + mphone + "','" + pphone + "','" + address + "','" + zipcode + "','" + city + "','" + email+"')";
                        dTransac.executeUpdate(query);
                        request.getRequestDispatcher("Controller?sub=success").forward(request, response);
                    case "Save":
                        query = "UPDATE EMPLOYEES SET NAME='" + name +"' ,FIRSTNAME='" + fname +"' ,HOMEPHONE='" + hphone +"' ,MOBILEPHONE='" + mphone+"' ,WORKPHONE='" + pphone+"' ,ADDRESS='" + address+"' ,POSTALCODE='" + zipcode+"' ,CITY='" + city+"' ,EMAIL='" + email+"' WHERE ID_EMPLOYEES=" + id;
                        dTransac.executeUpdate(query);
                        request.getRequestDispatcher("Controller?sub=success").forward(request, response);
                    case "Disconnect":{
                        session.setAttribute("user", null);
                        request.getRequestDispatcher("WEB-INF/goodbye.jsp").forward(request, response);
                        break;
                    }
                    default: 
                        request.getRequestDispatcher("WEB-INF/welcome.jsp").forward(request, response);
                        break;
                }
            } 
        }//If no user is connected
        else if(login == null && pwd == null){
            request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
        }
        //If the user enter login and password
        else{
            //If one of the login or password is empty
            if(login.equals("") || pwd.equals("")){
                //This message will be print before the form 
            request.setAttribute("ErrMessage", "<p>You must enter values in both fields</p>");
            request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
            }
            else
            {
                //Créer un utilisateur avec les identifiants rentrés
                user = new userSession();
                user.setLogin(login);
                user.setPassword(pwd);
                
                //Verify than the user is correct (in the bdd)
                query = "SELECT LOGIN, PWD FROM CREDENTIALS";
                ArrayList <userSession> userlist = dTransac.getDBUsers(dTransac.getResultSet(dTransac.getStatement(dTransac.getConnection()), query));
                for(userSession u : userlist)
                {
                    //If the user is correct
                    if(u.equals(user))
                    {
                       session.setAttribute("user", user);
                       request.getRequestDispatcher("WEB-INF/welcome.jsp").forward(request, response);
                        
                    }
                }
                request.setAttribute("ErrMessage", "<p>Verify your login/password and try again! </p>");
                request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
                
                
            } 
        }
        out.close();
        }
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
