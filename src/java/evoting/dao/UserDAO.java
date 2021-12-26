/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dao;

import evoting.dbutil.DBConnection;
import evoting.dto.UserDTO;
import evoting.dto.UserDetails;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author
 */
public class UserDAO {
    private static PreparedStatement ps,ps1;
    private static Statement st;
    static
    {
        try
        {
            ps=DBConnection.getConnection().prepareStatement("select user_type from user_details where adhar_no=? and password=?");
            ps1=DBConnection.getConnection().prepareStatement("delete from user_details where adhar_no=?");
            st=DBConnection.getConnection().createStatement();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static String validateUser(UserDTO user)throws SQLException
    {
        ps.setString(1,user.getUserid());
        ps.setString(2,user.getPassword());
        ResultSet rs=ps.executeQuery();
        if(rs.next())
            return rs.getString(1);
        return null;
    }
    public static ArrayList<UserDetails> getAllUser() throws SQLException
    {
        ResultSet rs=st.executeQuery("select adhar_no,username,address,city,email,mobile_no from user_details where user_type='Voter'");
        ArrayList<UserDetails> arr=new ArrayList<>();
        while(rs.next())
        {
            UserDetails cd=new UserDetails();
            cd.setUserid(rs.getString(1));
            cd.setUsername(rs.getString(2));
            cd.setAddress(rs.getString(3));
            cd.setCity(rs.getString(4));
            cd.setEmail(rs.getString(5));
            cd.setMobile(Long.parseLong(rs.getString(6)));
            arr.add(cd);
        }
        return arr;
    }
    
    public static int deleteUser(String uid) throws SQLException
    {
        ps1.setString(1, uid);
        int res=ps1.executeUpdate();
        return res;
    }
}
