package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DB_raspi {
    
    String      url         = "jdbc:mysql://localhost/raspi_db";
    String      id          = "java";
    String      password    = "laravel";
    
    Connection  con         = null;
    Statement   stmt        = null;
    
    public int get_fingerprintNum (String std_id) {
        
        String      sql          = "";
        int         scan_num     = -1;
        
        /* -------------------------------------------------------- */
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            /* -------------------------------------------------------- */
            
            con     = DriverManager.getConnection(url, id, password);
            stmt    = con.createStatement();
            
            /* -------------------------------------------------------- */
            
            sql     = "select scan_num from std_list where std_id='" + std_id + "'";
            
            /* -------------------------------------------------------- */
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                if (rs.getString("scan_num") != null) {
                    scan_num = Integer.parseInt(rs.getString("scan_num"));
                }
            }
            
            /* -------------------------------------------------------- */
            
            return scan_num;
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        /* -------------------------------------------------------- */
        
        return -1;
    }
    
    public String get_stdId (int scan_num) {
        
        String          sql         = "";
        String          std_id      = "";
        
        /* -------------------------------------------------------- */
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            /* -------------------------------------------------------- */
            
            con     = DriverManager.getConnection(url, id, password);
            stmt    = con.createStatement();
            
            /* -------------------------------------------------------- */
            
            sql     = "select std_id from std_list where scan_num=" + scan_num;
            
            /* -------------------------------------------------------- */
            
            ResultSet   rs  = stmt.executeQuery(sql);
            
            while (rs.next()) {
                if (rs.getString("std_id") != null) {
                    std_id  = rs.getString("std_id");
                }
            }
            
            /* -------------------------------------------------------- */
            
            return std_id;
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        /* -------------------------------------------------------- */
        
        return null;
    }
    
    public void set_fingerprint (String std_id, int scan_num) {
        
        String      sql          = "";
        
        /* -------------------------------------------------------- */
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            /* -------------------------------------------------------- */
            
            con     = DriverManager.getConnection(url, id, password);
            stmt    = con.createStatement();
            
            /* -------------------------------------------------------- */
            
            sql     = "insert into std_list (std_id, scan_num) values ";
            sql     += "('" + std_id + "', '" + Integer.toString(scan_num) + "');";
            
            /* -------------------------------------------------------- */
            
            stmt.executeUpdate(sql);
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
