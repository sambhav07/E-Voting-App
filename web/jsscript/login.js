var userid,password;

function connectUser()
{
    userid=$('#username').val();
    password=$('#password').val();
    if(validateInput()===false)
    {
        swal("Access Denied","Please fill Userid/Password","error");
        return;
    }
    let data={userid:userid,password:password};
    let xhr=$.post("LoginController",data,processResponse);
    xhr.fail(handleError);
}
function handleError(xhr)
{
    swal("Error","Problem in server communication: "+xhr.statusText,"error");
}
function processResponse(responseText)
{
    if(responseText.trim()==="error")
    {
        swal("Access Denied","Invalid Userid/Password","error");
    }
    else if(responseText.indexOf("jsessionid")!==-1)
    {
        let pr=swal("Success","Login Successful","success").then((value)=>{
            window.location=responseText.trim();
        });
    }
    else
    {
        swal("Error","Some problem occurred"+responseText,"error");
    }
}
function validateInput()
{
    if(userid==="" || password==="")
    {
        swal("Error!","All fields are mandatory","error");
        return false;
    }
    return true;
}