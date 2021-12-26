let username,password,cpassword,city,address,adhar,email,mobile,gender;
function addUser()
{
    username=$("#username").val();
    password=$("#password").val();
    cpassword=$("#cpassword").val();
    city=$("#city").val();
    address=$("#address").val();
    adhar=$("#adhar").val();
    email=$("#email").val();
    mobile=$("#mobile").val();
    gender=$('input[name="gender"]:checked').val();
    if(validateUser())
    {
        if(password!==cpassword)
        {
            swal("Error!","Passwords do not match","error");
            return;
        }
        else
        {
            if(checkEmail()===false)
                return;
            let data={
                    username:username,
                    password:password,
                    cpassword:cpassword,
                    city:city,
                    address:address,
                    userid:adhar,
                    email:email,
                    mobile:mobile,
                    gender:gender
                    };
                    console.log(data);
            let xhr=$.post("RegisterationController",data,processresponse);
            xhr.fail(handleError);
        }
    }
}

function processresponse(responseText,textStatus,xhr)
{
    let str=responseText.trim();
    if(str==="success")
    {
        swal("Success","Registration Successfull","success");
        setTimeout(()=>{window.location="login.html";},3000);
    }        
    else if(str==="uap")
        swal("Error!","Sorry !, the userid is already present","error");
    else
        swal("Error","Registration failed! Try Again","error");
}

function handleError(xhr)
{
    swal("Error","Problem in server communication: "+xhr.statusText,"error");
}

function validateUser()
{
    if(username==="" || password==="" || cpassword==="" || city==="" || cpassword==="" || address==="" || adhar==="" || email==="" || mobile==="")
    {
        swal("Error!","All fields are mandatory","error");
        return false;
    }
    return true;
}

function checkEmail()
{
    let attheratepos=email.indexOf("@");
    let dotpos=email.indexOf(".");
    if(attheratepos<1 || dotpos < attheratepos+2 || dotpos+2>email.length)
    {
        swal("Error!","Please enter a valid email","error");
        return false;
    }
    return true;
}
$(document).ready(check);
function check(){
let mob=document.querySelector("#mobile");
mob.addEventListener("keypress",(e)=>{
    if(e.charCode<48 || e.charCode>57)
        e.preventDefault();
});
}