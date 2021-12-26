<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.json.JSONObject" %>
<%@page import="evoting.dto.CandidateDetails" %>
<%@page import="java.util.ArrayList" %>

<%
            String userid=(String)session.getAttribute("userid");
            if(userid==null)
            {
                response.sendRedirect("accessdenied.html");
                return;
            }
            StringBuffer displayBlock=new StringBuffer();
            String result=(String)request.getAttribute("result");
            if(result!=null && result.equalsIgnoreCase("candidatelist"))
            {
                displayBlock.append("<option value=' '>Choose Id</option>");
                ArrayList<String> candidateId=(ArrayList<String>)request.getAttribute("candidateid");
                for(String s:candidateId)
                {
                    displayBlock.append("<option value='"+s+"'>"+s+"</option>");
                }
                JSONObject json=new JSONObject();
                json.put("cid",displayBlock.toString());
                out.println(json);
                
            }
            else if(result!=null && result.equalsIgnoreCase("details"))
            {
                CandidateDetails cd=(CandidateDetails)request.getAttribute("candidate");
                String str="<img src='data:image/png;base64,"+cd.getSymbol()+"'style='width:300px;height:200px'/>"; 
                displayBlock.append("<table id='table1'><tr><th>User Id:</th><td>"+cd.getUserId()+"</td></tr>");
                displayBlock.append("<tr><th>Candidate Name:</th><td>"+cd.getCandidateName()+"</td></tr>");
                displayBlock.append("<tr><th>City:</th><td>"+cd.getCity()+"</td></tr>");
                displayBlock.append("<tr><th>Party:</th><td>"+cd.getParty()+"</td></tr>");
                displayBlock.append("<tr><th>Symbol:</th><td>"+str+"</td></tr>");
                displayBlock.append("</table>");
                JSONObject json=new JSONObject();
                json.put("subdetails",displayBlock.toString());
                out.println(json);
                
            }
%>

