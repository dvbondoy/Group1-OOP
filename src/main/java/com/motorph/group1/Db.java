/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motorph.group1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jon
 */
public class Db {
    
    private String password_db = System.getenv("MOTORPH");
    private String user_db = "root";
    private String url_db = "jdbc:mysql://localhost/MotorphDB";
    
    private Connection conn = null;
    
    public Connection connect() {
        try {
            conn = DriverManager.getConnection(url_db, user_db, password_db);
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
    public void close() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setPassword_db(String password_db) {
        this.password_db = password_db;
    }

    public void setUser_db(String user_db) {
        this.user_db = user_db;
    }

    public void setUrl_db(String url_db) {
        this.url_db = url_db;
    }
}
