function showalluser()
{
    $.ajax({
        type:"POST",
        url:"GetAllUserDetailsController",
        processData:false,
        contentType:false,
        timeout: 60000,
        success:function(data)
        {
            $("#result").empty();
            $("#result").append(data);
        },
        error:function(e)
        {
            swal("Error","Something went wrong !","error");
        }
        
    });
}

function deleteData(e)
{
    let str={id:e.value};
    let xhr=$.post("DeleteUserController",str,processResponse);
    xhr.fail(handleError);
}

function  processResponse(responseText)
{
    let str=Number(responseText.trim());
    if(str===1)
    {
        swal("Success","User deleted successfully","success").then(value=>{
            location.reload();
        });
        
    }
    else
    {
        swal("Failed","Cannot delete user","error");
    }
}

function handleError()
{
    swal("Error","Something went wrong","error");
}