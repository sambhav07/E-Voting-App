
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
 boolean result=(boolean)request.getAttribute("result");
 boolean userfound=(boolean)request.getAttribute("userfound");
 String username=(String)request.getAttribute("username");
 if(userfound)
    out.println("uap");
 else if(result==true)
    out.println("success");
 else
    out.println("error");
%>