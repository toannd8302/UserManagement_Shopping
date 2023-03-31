/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author toan0
 */
public class UserDAO {

    private static final String SELECT_USER = "SELECT userID,fullName,password, roleID FROM [dbo].[tblUsers] WHERE userID=? and password=?";
    private static final String SELECT_USER_BYID = "SELECT userID,fullName,password, roleID FROM [dbo].[tblUsers] WHERE userID LIKE ?";

    public static User checkLogin(String userID, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        User user = null;

        try {
            //1. OPEN CONNECTION
            conn = utils.DBUtils.makeConnection();
            //2. CREATE SQL Query by STRING
            String sql = SELECT_USER;
            //3. SET SQL STATEMENT
            pst = conn.prepareStatement(sql);
            pst.setString(1, userID);
            pst.setString(2, password);
            //4. EXCUTE QUERY
            rs = pst.executeQuery();
            //5.PROCESS
            if (rs != null) {
                while (rs.next()) {
                    String fullname = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    return new User(userID, fullname, password, roleID);
                }
            }

        } catch (Exception e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return null;
    }

    public static List<User> findById(String searchValue) throws SQLException {
        List<User> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        User user = null;
        try {
            //1. OPEN CONNECTION
            conn = utils.DBUtils.makeConnection();
            //2. CREATE SQL Query by STRING
            String sql = SELECT_USER_BYID;
            //3. SET SQL STATEMENT
            pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + searchValue + "%");
            //4. EXCUTE QUERY
            rs = pst.executeQuery();
            //5.PROCESS
            if (rs != null) {
                while (rs.next()) {

                    String userID = rs.getString("userID");
                    String fullname = rs.getString("fullName");
                    String password = rs.getString("password");
                    String roleID = rs.getString("roleID");
                    users.add(new User(userID, fullname, password, roleID));
                }
            }
        } catch (Exception e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return users;
    }
}
