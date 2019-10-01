/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DataAccess {
        // declare globally, intialize locally
    private Connection dbConn;
    private Statement stmt;
    private ResultSet rs;
    private String dbUrl;
    private String user;
    private String pwd;

        public Connection getConnection() throws IOException {
        InputStream input = null;
            try {
            Properties prop = new Properties();
            //InputStream input;
            ClassLoader c1 = Thread.currentThread().getContextClassLoader();
            input = c1.getResourceAsStream("utils/db.properties");
            prop.load(input);
            dbUrl = prop.getProperty("dbUrl");
            user = prop.getProperty("user");
            pwd = prop.getProperty("pwd");
            //Class.forName("org.apache.derby.jdbc.ClientDriver");
            //Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            dbConn = DriverManager.getConnection(dbUrl, user, pwd);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        input.close();
        return dbConn;
    }
    
        
    public Statement getStatement(Connection dbConn){
         try{
            stmt = dbConn.createStatement();
        } catch(SQLException sqle){
            System.out.println(sqle.getMessage());
        }
        return stmt;
    }
    
        public ResultSet getResultSet(Statement stmt, String query){
        try{
            rs = stmt.executeQuery(query);
        } catch(SQLException sqle){
            System.out.println(sqle.getMessage());
        }
        return rs;
    }
        
        public ArrayList getDBUsers(ResultSet rs){
        ArrayList<userSession> userDBList = new ArrayList<userSession>();
        try{
            while(rs.next()){
                userSession uS = new userSession();
                uS.setLogin(rs.getString("LOGIN"));
                uS.setPassword(rs.getString("PWD"));
                userDBList.add(uS);
            }
        } catch(SQLException sqle){
            System.out.println(sqle.getMessage());
        }
        return userDBList;
    }
        
    public ArrayList getDBEmployees(ResultSet rs){
        ArrayList<Employee> Employees = new ArrayList<Employee>();
        try{
            while(rs.next()){
                Employee e = new Employee();
                e.setName(rs.getString("NAME"));
                e.setFirstName(rs.getString("FIRSTNAME"));
                e.setHomePhone(rs.getString("HOMEPHONE"));
                e.setMobilePhone(rs.getString("MOBILEPHONE"));
                e.setWorkPhone(rs.getString("WORKPHONE"));
                e.setAddress(rs.getString("ADDRESS"));
                e.setPostalCode(rs.getString("POSTALCODE"));
                e.setCity(rs.getString("CITY"));
                e.setEmail(rs.getString("EMAIL"));
                e.setID(rs.getString("ID_EMPLOYEES"));
                Employees.add(e);
            }
        } catch(SQLException sqle){
            System.out.println(sqle.getMessage());
        }
        return Employees;
    }
    
    public void executeUpdate(String query) throws IOException{
        try {
            getStatement(getConnection()).executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
