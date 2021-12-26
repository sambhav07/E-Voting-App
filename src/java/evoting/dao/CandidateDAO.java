/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dao;

import evoting.dbutil.DBConnection;
import evoting.dto.CandidateDTO;
import evoting.dto.CandidateDetails;
import evoting.dto.CandidateInfo;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;

/**
 *
 * @author
 */
public class CandidateDAO {
    private static PreparedStatement ps,ps1,ps2,ps3,ps4,ps5,ps6,ps7,ps8,ps9;
    private static Statement st;
    
    static
    {
        try
        {
            st=DBConnection.getConnection().createStatement();
            ps=DBConnection.getConnection().prepareStatement("select max(CANDIDATE_ID) from candidate");
            ps1=DBConnection.getConnection().prepareStatement("select username from user_details where adhar_no=?");   
            ps2=DBConnection.getConnection().prepareStatement("select distinct(city) from user_details");   
            ps3=DBConnection.getConnection().prepareStatement("insert into candidate values(?,?,?,?,?)");   
            ps4=DBConnection.getConnection().prepareStatement("select * from candidate where candidate_id=?");
            ps5=DBConnection.getConnection().prepareStatement("delete from candidate where CANDIDATE_ID=?");
            ps6=DBConnection.getConnection().prepareStatement("update candidate set PARTY=?, CITY=?, SYMBOL=? where CANDIDATE_ID=?");
            ps7=DBConnection.getConnection().prepareStatement("Select candidate_id,username,party,symbol from candidate,user_details where candidate.user_id=user_details.adhar_no and candidate.city=(select city from user_details where adhar_no=?)");
            ps8=DBConnection.getConnection().prepareStatement("select candidate_id from candidate where user_id=?");
            ps9=DBConnection.getConnection().prepareStatement("select party from candidate where city=?");
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static String getNewId() throws SQLException
    {
        ResultSet rs=ps.executeQuery();
        rs.next();
        String cid=rs.getString(1);
        if(cid==null)
            return "C101";
        else
        {
            int id=Integer.parseInt(cid.substring(1));
            return "C"+(id+1);
        }
    }
    public static String getUserNameById(String uid) throws SQLException
    {
        ps1.setString(1, uid);
        ResultSet rs=ps1.executeQuery();
        if(rs.next())
        {
            return rs.getString(1);
        }
        else
        {
            return null;
        }
    }
    public static ArrayList<String> getCity() throws SQLException
    {
        ArrayList<String> cityList=new ArrayList<String>();
        ResultSet rs=ps2.executeQuery();
        while(rs.next())
        {
            cityList.add(rs.getString(1));
        }
        return cityList;
    }
    public static boolean addCandidate(CandidateDTO c) throws SQLException
    {
        ps3.setString(1,c.getCandidateId());
        ps3.setString(2,c.getParty());
        ps3.setString(3,c.getUserid());
        ps3.setBinaryStream(4,c.getSymbol());
        ps3.setString(5,c.getCity());
        return ps3.executeUpdate()!=0;
    }
    public static ArrayList<String> getCandidateId() throws SQLException
    {
        ArrayList<String> candidateIdList=new ArrayList<>();
        ResultSet rs=st.executeQuery("select candidate_id from candidate");
        while(rs.next())
        {
            candidateIdList.add(rs.getString(1));
        }
        return candidateIdList;
    }
    public static CandidateDetails getDetailsById(String cid) throws Exception
    {
        ps4.setString(1, cid);
        ResultSet rs=ps4.executeQuery();
        CandidateDetails cd=new CandidateDetails();
        Blob blob;
        InputStream inputStream;
        byte[] buffer;
        byte[] imageBytes;
        int byteRead;
        String base64Image;
        ByteArrayOutputStream outputStream;
        if(rs.next())
        {
            blob=rs.getBlob(4);
            inputStream=blob.getBinaryStream();
            outputStream=new ByteArrayOutputStream();
            buffer=new byte[4096];
            byteRead=-1;
            while((byteRead=inputStream.read(buffer))!=-1)
            {
                outputStream.write(buffer,0,byteRead);
            }
            imageBytes=outputStream.toByteArray();
            Base64.Encoder en=Base64.getEncoder();
            base64Image=en.encodeToString(imageBytes);
            
            cd.setSymbol(base64Image);
            cd.setCandidateId(cid);
            cd.setCandidateName(getUserNameById(rs.getString(3)));
            cd.setParty(rs.getString(2));
            cd.setCity(rs.getString(5));
            cd.setUserId(rs.getString(3));
        }
        return cd;
    }

    public static int deleteCandidate(String cid) throws SQLException
    {
        ps5.setString(1, cid);
        return ps5.executeUpdate();
    }
    
    public static boolean updateCandidate(CandidateDTO cd) throws SQLException
    {
        ps6.setString(1,cd.getParty());
        ps6.setString(2,cd.getCity());
        ps6.setBinaryStream(3,cd.getSymbol());
        ps6.setString(4,cd.getCandidateId());
        return ps6.executeUpdate()!=0;
    }
    
    public static ArrayList<CandidateInfo> viewCandidate(String adhar_id) throws Exception
    {
        ArrayList<CandidateInfo> candidateList=new ArrayList<CandidateInfo>();
        ps7.setString(1, adhar_id);
        ResultSet rs=ps7.executeQuery();
        Blob blob;
        InputStream inputStream;
        byte[] buffer;
        byte[] imageBytes;
        int byteRead;
        String base64Image;
        ByteArrayOutputStream outputStream;
        while(rs.next())
        {
            blob=rs.getBlob(4);
            inputStream=blob.getBinaryStream();
            outputStream=new ByteArrayOutputStream();
            buffer=new byte[4096];
            byteRead=-1;
            while((byteRead=inputStream.read(buffer))!=-1)
            {
                outputStream.write(buffer,0,byteRead);
            }
            imageBytes=outputStream.toByteArray();
            Base64.Encoder en=Base64.getEncoder();
            base64Image=en.encodeToString(imageBytes);
            
            CandidateInfo cd=new CandidateInfo();
            cd.setSymbol(base64Image);
            cd.setCandidateId(rs.getString(1));
            cd.setCandidateName(rs.getString(2));
            cd.setParty(rs.getString(3));
            candidateList.add(cd);
        }
        return candidateList;
    }
            
    public static boolean getCid(String uid) throws SQLException
    {
        ps8.setString(1, uid);
        ResultSet rs=ps8.executeQuery();
        return rs.next();
    }
    public static boolean getDetails(String city,String party) throws SQLException
    {
        ps9.setString(1, city);
        ResultSet rs=ps9.executeQuery();
        while(rs.next())
        {
            if(rs.getString(1).equals(party))
                return true;
        }
        return false;
    }
}
