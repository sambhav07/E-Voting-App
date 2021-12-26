<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String userid=(String)request.getAttribute("userid");
    String result=(String)request.getAttribute("result");
    if(userid!=null && result!=null)
    {
        String url="";
        HttpSession sess=request.getSession();
        sess.setAttribute("userid",userid);
        if(result.equalsIgnoreCase("admin"))
            url="AdminController;jsessionid"+sess.getId();
        else
            url="VotingController;jsessionid"+sess.getId();
        out.println(url);    
    }
    else
        out.println("error");
%>