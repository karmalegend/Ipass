document.querySelector("#register").addEventListener("click",function(){
    let formdata = new FormData(document.querySelector("#registerForm"));
    let encData = new URLSearchParams(formdata.entries());

    let fetchoptions = {
        method: 'POST',
        headers : {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        },
        body : encData
      };

    fetch("restservices/auth/add",fetchoptions)
    .then(function(response){
        if(response.ok){
            alert("User added!");
            window.location.href = "../LandingPage.html";
        }
        else{
            alert("something went wrong please try again.");
        }
    })

})