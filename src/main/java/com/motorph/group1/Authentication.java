/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motorph.group1;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

/**
 *
 * @author jon
 */
public class Authentication {
    private static final String HASH_ALGORITHM = "SHA-256";
    
    public static boolean authenticate(String username, String password) {
        String pwd = System.getenv("MOTORPH");
        System.out.println("PASSWORD:"+pwd);
    
        String qryUser = "SELECT * FROM User WHERE username = ? AND password = ?";
        String qryRole = "SELECT * FROM Role WHERE EmployeeID = ?";
        
        Db db = new Db();
        
        try{
            // connect to the database
            Connection conn = db.connect();
            // assign the statement
            PreparedStatement pst = conn.prepareStatement(qryUser);
            // assign values inside the statement
            pst.setString(1, username);
            pst.setString(2, hashPassword(password));
            // execute the query
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                // get roles and put it to UserInfo
                PreparedStatement pstRole = conn.prepareStatement(qryRole);
                pstRole.setInt(1, rs.getInt("EmployeeID"));
                ResultSet rsRole = pstRole.executeQuery();
                while(rsRole.next()){
                    UserInfo.ROLES.add(rsRole.getString("RoleName"));
                }
                // record other data to UserInfo
                UserInfo.USERNAME=rs.getString("username");
                UserInfo.EMPLOYEEID=rs.getInt("EmployeeID");
                
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            db.close();
        }

        return false;
    }

    public static boolean authorize(String username, Role requiredRole) {
        // query database here or check other data source of user role
        // for now let us return true
        return true;
    }

    public static String hashPassword(String password) {
        // TODO Auto-generated method stub
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error hashing password: " + e.getMessage());
            return null;
        }
    }
}