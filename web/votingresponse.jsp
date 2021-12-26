<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="evoting.dto.CandidateInfo" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <link href="stylesheet/backgroundimage.css" rel="stylesheet">
        <link href="stylesheet/pageheader.css" rel="stylesheet">
        <link href="stylesheet/showcandidate.css" rel="stylesheet">
        <title>Voting Details</title>
    </head>
    
    <body>
        <%
        String userid=(String)session.getAttribute("userid");
        if(userid==null)
        {
            session.invalidate();
            response.sendRedirect("accessdenied.html");
            return;
        }
        CandidateInfo candidate=(CandidateInfo)session.getAttribute("candidate");
        StringBuffer displayBlock=new StringBuffer();
        displayBlock.append("<div class='sticky'><div class='candidate'>VOTE FOR CHANGE</div><br>");
        if(candidate==null)
        {
            displayBlock.append("<div class='subcandidate'>Sorry ,your vote could not be casted</div><br><br>");
            displayBlock.append("<div><h4 id='logout'><a href='LoginController?logout=logout'>Logout</a></h4></div>");
            out.println(displayBlock);
        }
        else
        {
            displayBlock.append("<div class='subcandidate'>Thank you for voting!</div><br><br>");
            displayBlock.append("<div><h4 id='logout'><a href='LoginController?logout=logout'>Logout</a></h4></div>");
            displayBlock.append("<br><div class='candidateprofile'>Your vote added successfully</div><br>");
            displayBlock.append("<br><div class='candidateprofile'><p>CandidateId: "+candidate.getCandidateId()+"<br>");
            displayBlock.append("<strong>You voted for </strong><img src='data:image/png;base64,"+candidate.getSymbol()+"'style='width:200px;height:200px;'/>");
            displayBlock.append("Candidate Name:"+candidate.getCandidateName()+"<br>");
            displayBlock.append("Party : "+candidate.getParty()+"<br></div>");
            out.println(displayBlock);
        }
       
        %>
    </body>
</html>
