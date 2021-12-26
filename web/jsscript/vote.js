function addVote()
{
    let cid=$("input[type='radio'][name='flat']:checked").val();
    let data={candidateid:cid};
    let xhr=$.post("AddVoteController",data,processResponse);
    xhr.fail(handleError);
}

function handleError()
{
    swal("Error","Something went wrong","error");
}

function processResponse(responseText)
{
    let str=responseText.trim();
    if(str==="success")
    {
        swal("Success","Vote Casted successfully","success").then(value=>{
            window.location="votingresponse.jsp";
        });
    }
    else
    {
        swal("Failed","Vote not casted","error").then(value=>{
            window.location="votingresponse.jsp";
        });
    }
    
}