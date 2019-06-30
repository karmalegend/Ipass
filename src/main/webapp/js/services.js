function getservices(){
    let fetchoptions = {
        method: 'GET',
        headers : {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }

    };
    
    fetch("/restservices/service", fetchoptions)
    .then((response) => response.json())
    .then(parsedresponse =>{ 
        initialLoadIn(parsedresponse)
    })
    .catch(function(error){
        console.log(error);
        window.location.href = "../";
     });
}


function initialLoadIn(response){    
    for(var i = 0; i<response.length; i++){
        renderService(response[i]);
    }
}

function renderService(service){
    let ul = document.querySelector("ul");
    let li = document.createElement("li");
    li.textContent = `ID: ${service.serviceID} servicename ${service.serviceName}`;
    ul.appendChild(li);
}

getservices();

