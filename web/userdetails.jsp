<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="evoting.dto.UserDetails,java.util.*" %>
       <%
            String userid=(String)session.getAttribute("userid");
            if(userid==null)
            {
                response.sendRedirect("accessdenied.html");
                return;
            }
            ArrayList<UserDetails> arr=(ArrayList<UserDetails>)request.getAttribute("details");
            Iterator it=arr.iterator();
            StringBuffer displayBlock=new StringBuffer("");
            displayBlock.append("<table>");
            displayBlock.append("<tr><th>User Id</th><th>Username</th><th>Address</th><th>City</th><th>Email</th><th>Mobile No</th></tr>");
            while(it.hasNext())
            {
                UserDetails ud=(UserDetails)it.next();
                displayBlock.append("<tr><td>"+ud.getUserid()+"</td><td>"+ud.getUsername()+"</td><td>"+ud.getAddress()+"</td><td>"+ud.getCity()+"</td><td>"+ud.getEmail()+"</td><td>"+ud.getMobile()+"</td><td><button type='button' onclick='deleteData(this)' name='deleteBtn' value='"+ud.getUserid()+"'>Delete</button></td></tr>"); 
            }
            displayBlock.append("</table>");
            out.println(displayBlock.toString());
       %>
