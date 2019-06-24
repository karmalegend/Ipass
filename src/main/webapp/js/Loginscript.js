function login(event){
    let formData = new FormData(document.querySelector("#loginForm"));
    let encData = new URLSearchParams(formData.entries());

    fetch("restservices/auth",{ method: 'POST', body: encData})
    .then(function(response){
        if(response.ok) return response.json();
        else throw "Wrong username/password";
    })
    .then( (myJson) => {
        console.log("test 1.25");
            window.sessionStorage.setItem("myJWT", myJson.JWT);
            window.location.href = "http://localhost:8080/LandingPage.html";
            console.log("test 1.5");
            console.log("things");
        })
    .catch(error => console.log(error));
}

document.querySelector("#Login").addEventListener("click",login);