var customername;
var customerEmail;
var customerPhone;



//force github update because heroku doesn't see updated repo



//this function is the initial load function fo the webpage it fetches all the data
// and parsaes it into a json format.
// If the user does not have the correct permissions to acces the fetch
// They are not allowed to be on the current page
// And will get redirected to the login page.
function getData(){
    let fetchoptions = {
        method: 'GET',
        headers : {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }

    };
    
    fetch("/restservices/customer/all", fetchoptions)
    .then((response) => response.json())
    .then((parsedResponse) =>{
        initialLoadIn(parsedResponse);
    })
    .catch(function(error){
       console.log(error);
       window.location.href = "../";
    });
}

// This function calls the render function for every
// Customer in the array
function initialLoadIn(response){
  document.querySelector("#loading").innerHTML = "";    
    for(var i = 0; i<response.length; i++){
        renderCustomer(response[i]);
    }
}


//in hindsight i should've just made 1 var for <div> and <p> rather than create a new one everytime.
// This is a massive function that creates
// The elements needed for the customer
// It renders a row and puts all the needed info in there.
function renderCustomer(customer){
    //append all this to div class = container to add it to body

    console.log(customer);

    var row = document.createElement("div");
    row.className = "row";

    var leftCol = document.createElement("div");
    leftCol.className = "col-lg-1 col-md-1 cold-s-4 col-xs-0";

    row.appendChild(leftCol);

    //build main col here
    //don't forget to add maincol to row when done building

    var mainCol = document.createElement("div");

    mainCol.className = "col-lg-10 col-md-10 cold-s-8 col-xs-12s";

    //don't forget to add customerRow to maincol when done building 
    var customerRow = document.createElement("div");
    customerRow.className= "customerRow";

    //customername
    var customerName = document.createElement("div");
    customerName.className = "customerName";

    var customerNameP = document.createElement("P");
    var customerNamePTextNode = document.createTextNode(customer.companyname);
    customerNameP.setAttribute("title","Company Name");

    //customerID
    var customerIdP = document.createElement("P");
    customerIdP.textContent = customer.customerID;
    customerIdP.setAttribute("title","CustomerID");


    //add it all to the row
    customerNameP.appendChild(customerNamePTextNode);
    customerName.appendChild(customerNameP);
    customerName.appendChild(customerIdP);
    customerRow.appendChild(customerName);

    //customerPackage
    var customerPackage = document.createElement("div");
    customerPackage.className = "customerPackage";

    var customerPackageP = document.createElement("P");
    var customerPackagePTextNode = document.createTextNode(customer.pakket.packageName);
    customerPackageP.setAttribute("title","Package Name");


    //package ID
    var customerPackageIdP = document.createElement("P");
    customerPackageIdP.textContent = customer.pakket.packageID;
    customerPackageIdP.setAttribute("title","Package ID");

    //editbutton
    var editPackageButton = document.createElement("I");
    editPackageButton.className = "fas fa-edit";

    //add it all to the row
    customerPackageP.appendChild(customerPackagePTextNode);
    customerPackage.appendChild(customerPackageP);
    customerPackage.appendChild(customerPackageIdP);
    customerPackage.appendChild(editPackageButton);
    customerRow.appendChild(customerPackage);

    //packagePrice
    var packagePrice = document.createElement("div");
    packagePrice.className = "customerPayment";

    var packagePriceP = document.createElement("P");
    var packagePricePTextNode = document.createTextNode(customer.pakket.pagckagePrice);
    packagePriceP.setAttribute("title","Package price");


    //order id
    let cusOrderId = document.createElement("p");
    cusOrderId.textContent = customer.pakket.orders[0].orderID;
    cusOrderId.setAttribute("title","Orderid");


    //add it all to the row
    packagePriceP.appendChild(packagePricePTextNode);
    packagePrice.appendChild(packagePriceP);
    packagePrice.appendChild(cusOrderId);
    customerRow.append(packagePrice);


    //breakrule

    var br = document.createElement("BR")
    customerRow.appendChild(br);


    //customerInfo

    var customerInfo = document.createElement("div");
    customerInfo.className = "customerInfo";


    //customerEmail
    var customerInfoP = document.createElement("p");
    customerInfoPTextNode = document.createTextNode(customer.emailadress);
    customerInfoP.setAttribute("title","Emailadress");
    

    //add to row
    customerInfoP.appendChild(customerInfoPTextNode);
    customerInfo.appendChild(customerInfoP);


    //customerphone number
    var customerInfoP1 = document.createElement("p");
    customerInfoP1TextNode = document.createTextNode(customer.phonenumber);
    customerInfoP1.setAttribute("title","Phonenumber");

    //add to row
    customerInfoP1.appendChild(customerInfoP1TextNode);
    customerInfo.appendChild(customerInfoP1);

    customerRow.appendChild(customerInfo);


    //packageServices
    var customerServices = document.createElement("div");
    customerServices.className= "customerServices";
    var ul = document.createElement("ul");
    ul.setAttribute("title","Services");
    ul.id = `ul${customer.customerID}`;

    //make a LI element for every service in the package

    for(var i = 0; i<customer.pakket.orders[0].services.length; i++){
        var li = document.createElement("li");
        var liTextNode = document.createTextNode(customer.pakket.orders[0].services[i].serviceName);
        
        li.appendChild(liTextNode);
        ul.appendChild(li);
    }


    //icon to add a service.
    let addFavicon = document.createElement("i");
    addFavicon.className = "fas fa-plus";

    ul.appendChild(addFavicon);
    customerServices.appendChild(ul);
    customerRow.appendChild(customerServices);

    //buttonrow

    var customerButtons = document.createElement("div")
    customerButtons.className = "customerButtons";
    
    var delButton = document.createElement("button");
    delButton.className=("btn btn-danger delete");
    delButton.textContent = "Delete";

    var editButton = document.createElement("button");
    editButton.className = "btn btn-info edit";
    editButton.textContent = "Edit";
    

    //add it all to the row
    customerButtons.appendChild(delButton);
    customerButtons.appendChild(editButton);
    customerRow.appendChild(customerButtons);




    mainCol.appendChild(customerRow);

    row.appendChild(mainCol);

    document.querySelector(".container").appendChild(row);

    arrlistEditButtons = document.querySelectorAll(".edit")



    //add button functionalities
    //this is not the most efficient way probably
    // but it does the job
    for(var j = 0; j<arrlistEditButtons.length; j++){
        arrlistEditButtons[j].addEventListener("click",edit);
    }

    arlistDelButtons = document.querySelectorAll(".delete")

    for(var l=0; l<arlistDelButtons.length; l++){
        arlistDelButtons[l].addEventListener("click",deleteCus);
    }


    arrListEditPackageFavicon = document.querySelectorAll(".fa-edit");

    for(var k = 0; k<arrListEditPackageFavicon.length; k++){
      arrListEditPackageFavicon[k].addEventListener("click",editPackage);
    }


    arrListAddServiceButtons = document.querySelectorAll(".fa-plus");
    for(var l=0; l<arrListAddServiceButtons.length; l++){
      arrListAddServiceButtons[l].addEventListener("click",addService);
    }


    //set localstorage variables for when
    //adding a new customer
    localStorage.setItem("orderID",customer.pakket.orders[0].orderID+1);
    localStorage.setItem("customerID",customer.customerID+1);
    localStorage.setItem("packageID",customer.pakket.packageID+1);


}


//add an EXISTING service to a customer
//if the serviceID does not match any existing
//service the function will alert an error.

function addService(event){
  let cusId = event.path[3].childNodes[0].childNodes[1].textContent;
  let ul = document.querySelector(`#ul${cusId}`);
  let input = document.createElement("input");
  input.setAttribute("type","number");
  input.setAttribute("placeholder","ONLY SERVICE ID'S");

  let iarray = document.querySelectorAll("i");

  //hide all the favicon buttons to prevent user from bugging out the system.
  for(let i =0; i<iarray.length; i++){
      iarray[i].style.visibility = "hidden";
    }


  //create button to fetch data
  let li = document.createElement("li");
  li.innerHTML = `<button type="button" class="btn btn-dark" id="addServiceButton">Add</button>`;

  ul.appendChild(li);
  ul.insertBefore(input,ul.childNodes[event.path[1].childElementCount-1]);

  addServiceEventListener();
}


//add event listener to the add service button
function addServiceEventListener(){
  document.querySelector("#addServiceButton").addEventListener("click",addServiceEventHandler);
}


//handle the event.
//this function gets all the data from the parent divs
//then checks if the service is available 
//and asks the user if it's the correct service
//if the user confirms it sends a POST request to the backend
//to add the service to the customers order.
function addServiceEventHandler(event){
  let formData = new FormData();
  let idIdentifier = event.path[2].childNodes.length - 2;
  let serviceID = event.path[2].childNodes[idIdentifier].value;
  let packageID = event.path[4].childNodes[1].children[1].textContent;
  let orderID = event.path[4].childNodes[2].childNodes[1].textContent;


  console.log(event.path[2].childNodes[idIdentifier]);
  console.log(event);

  console.log(event.path[2].childNodes[4].value);
  console.log("service id = " + serviceID + " package id = " + packageID + " order id = " + orderID);

  formData.append("serviceID",serviceID);
  formData.append("packageID",packageID);
  formData.append("orderID",orderID);

  let encData = new URLSearchParams(formData.entries());
  
  let fetchoptions = {
    method: 'POST',
    headers : {
        'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
    },
    body : encData
  };

  let fetchoptionsGet = {
    method: 'GET',
    headers : {
        'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
    }
  };


  if(serviceID > 8){
    alert("Ongeldig service ID");
  }
  else{

    //first fetch the service to ask user
    //if the ID matches what they wanted to do.
    fetch("restservices/service/get/"+serviceID,fetchoptionsGet)
    .then(response => response.json())
    .then(function(response){
      console.log(response);
      let cfbox = confirm(`Are you sure you want to add: "${response.serviceName}". \nWith the following description: "${response.serviceDienst}" to this customer?`);
      
      //if user confirms and all goes well service is added
      if(cfbox){
        fetch("restservices/service", fetchoptions)
        .then(function(response){
          if(response.ok){
            location.reload();
          }
          //if all doesn't go well, alert the user something has gone wrong
          //and they either need to try again or take a step back
          //and try later
          //if issue remains unresolved
          //contact system admin
          else{
            alert("Something went wrong while processing your request please try again later, or contact your system admin.")
          }
        });
      }
    });
    
  }
}


// Delete a customer
function deleteCus(event){
    let id = event.path[2].childNodes[0].children[1].innerText;
    console.log("customer id = " + id);

    let delfetch = {
        method: 'DELETE',
        headers : {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }

    };

    // check if user is sure
    let cfBox = confirm("Are you sure? This action will permanently remove a customer and is non-reversible")
    if(cfBox){
      // tell server to delete customer
        fetch("/restservices/customer/del/"+id,delfetch)
        .then(function(response){
            if(response.ok){
                console.log("Customer deleted")
                // get the container div
                let parent = document.querySelector(".container");
                // tell it to unpack the row
                parent.removeChild(event.path[4]);
            }
            else if(response.status == 404){
                console.log("Customer not found!")
            }
            else console.log("cannot delete customer");
    })
    }
}


// this function just loads in the form to add a customer 
function addCustomer(event){
    console.log("add button clicked");
   
    let innerhtmlForm = ` <form id="newCustomerForm">
    <div class="form-row">
        <div class="form-group md-12">
            <label>CustomerID</label>
            <input class="form-control" type="number" value=${localStorage.getItem("customerID")} name="customerID" readonly="readonly"/>
        </div>
    </div>
    <div clas="form-row">
      <div class="form-group col-md-6">
        <label>Companyname</label>
        <input class="form-control" type = "text" placeholder="KPN" name="compname"/>
      </div>
      <div class="form-group col-md-6">
        <label for="kvknum">KvK Nummer</label> 
        <a target="_blank" href="https://www.kvk.nl/zoeken/handelsregister/?handelsnaam=&kvknummer=&straat=&postcode=&huisnummer=&plaats=&hoofdvestiging=1&rechtspersoon=1&nevenvestiging=1&zoekvervallen=0&zoekuitgeschreven=1&start=0">Search</a>
        <input class="form-control" type="number" id="kvknum" placeholder="27124701" name="kvknum">
      </div>
    </div>

    <div class="form-row">
      <div class="form-group col-md-2">
        <label>Emailadress</label>
        <input class="form-control" type="email" placeholder="kpn@kpn.nl" name="email"/>
      </div>
      
      <div class="form-group col-md-2">
        <label>Telefoonnummer</label>
        <input class="form-control" type="number" placeholder="08000402" name="phonenumber"/>
      </div>

      <div class="form-group col-md-2">
        <label>PackageID</label>
        <input class="form-control" type="number" value=${localStorage.getItem("packageID")} name="packageID" readonly="readonly"/>
      </div>
      
      <div class="form-group col-md-3">
        <label>Pakketnaam</label>
        <input class="form-control" type="text" placeholder="Dagelijks Pro" name="packagename"/>
      </div>
      
      <div class="form-group col-md-3">
        <label>Pakketprijs</label>
        <input class="form-control" type="number" placeholder="2300" name="price"/>
      </div>
    </div>

    <div class="form-row">
      <div class="form-group md-12">
        <label>OrderID</label>
        <input class="form-control" type="number" value=${localStorage.getItem("orderID")} name="orderID" readonly="readonly"/>
      </div>
    </div>

    <div class="form-row">
      <div class="form-group col-md-12">
        <input class="form-control" type="number" placeholder="1" name="service1">
        <input class="form-control" type="number" placeholder="2" name="service2">
        <input class="form-control" type="number" placeholder="3" name="service3">
        <input class="form-control" type="number" placeholder="4" name="service4">
        <input class="form-control" type="number" placeholder="5" name="service5">
        <input class="form-control" type="number" placeholder="6" name="service6">
        <label>Services vul a.u.b. alleen service ID's in</label>
      </div>

    </div>
  </form>
  <div id="newCusButtons">
    <button type="button" class="btn btn-success saveNewCus">Save</button>
    <button type="button" class="btn btn-danger cancNewCus">Cancel</button
  </div>`

  document.querySelector("#addcustomerformloadin").innerHTML = innerhtmlForm;

  addSaveEventNewCus();
  addCancelEventNewCus();

}

function addCancelEventNewCus(){
    document.querySelector(".cancNewCus").addEventListener("click",cancelCustomerFunction);
}

function addSaveEventNewCus(){
    document.querySelector(".saveNewCus").addEventListener("click",saveCustomerFunction);
}


function cancelCustomerFunction(){
    document.querySelector("#addcustomerformloadin").innerHTML = "";
}

// add the actual customer to the database
function saveCustomerFunction(event){
    let formData = new FormData(document.querySelector("#newCustomerForm"));
    console.log(formData);
    let encData = new URLSearchParams(formData.entries());

    let fetchoptions = {
      method: 'POST',
      headers : {
          'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
      },
      body : encData
  };

  console.log(formData.get("price").length);
  console.log(formData.get("packagename"));

  //validate userinput
  if(formData.get("service1") > 8 || formData.get("service2") > 8 || formData.get("service3") > 8 || formData.get("service4") > 8 
  || formData.get("service5") > 8 || formData.get("service6") > 8){
    alert("There are no services with an ID higher than 8 please check your input")
  }
  // check if a service is filled in
  // to prevent orderID from becomming corrupted
  else if(formData.get("service1").length == 0 && 
  formData.get("service2").length == 0 && formData.get("service3").length == 0 && formData.get("service4").length == 0 && formData.get("service5").length== 0 
  && formData.get("service6").length == 0){
    alert("Please fill in atleast 1 service");
  }

  //check if all fields contain a value
  else if(formData.get("price").length == 0 || formData.get("packagename").length == 0 || formData.get("phonenumber").length == 0 || 
  formData.get("email").length == 0 || formData.get("kvknum").length == 0 || formData.get("compname").length == 0)
  {
    alert("Please verify your input and try again")
  }
  else{
    // let user know server is processing by loading icon
    let imgload = document.createElement("img");
    imgload.setAttribute("src","img/landingpage/Spinner-1s-21px.svg");
    event.currentTarget.disabled = true;
    event.path[0].appendChild(imgload);
    fetch("restservices/customer", fetchoptions)
    .then(response => response.json())
    .then(function(response){
      if(response.ok){
      renderCustomer(response);
      cancelCustomerFunction();
      }})
    .catch(function(error){
        console.log(error);
        alert("Something has gone wrong please refresh the page and try again.")
        location.reload();
        console.log("something has gone wrong");
        window.location.href = "../";
     });    
}}



// edit a customers information
function edit(event){

  //set the old values in case the user cancels
    customername = event.path[2].childNodes[0].childNodes[0].textContent;
    customerEmail = event.path[2].childNodes[4].childNodes[0].textContent;
    customerPhone = event.path[2].childNodes[4].childNodes[1].textContent;

    // weird bug fix
    if(customerPhone.length == 5){
      customerPhone = event.path[2].childNodes[4].childNodes[2].textContent;
      // path[2].childNodes[4].childNodes[2]
    }

    // turn data fields into inputs
    event.path[2].childNodes[0].childNodes[0].innerHTML = `
      <form id="customerName">
        <input id="nameCus" type="text" placeholder="test" value="${customername}" name="customerName"/>
      </form>`;


    event.path[2].childNodes[4].innerHTML = `
      <form id="customerContact">
        <input id="email" type="email" placeholder="test" value="${customerEmail}" name="customerEmail"/>
        <br/>
        <input id="phone" type="phone" placeholder="testeserasdd" value="${customerPhone}" name="customerPhone"/>
      </form>`;


      event.path[1].innerHTML = `
      <button type="button" class="btn btn-danger cancel">Cancel</button>
      <button type="button" class="btn btn-info save">Save</button>
      ` 
        
    cancelEventListener();
    saveEventListener();
}

function saveEventListener(){
  document.querySelector(".save").addEventListener("click",saveEventHandler);
}

// save the new data 
function saveEventHandler(event){
  let formData = new FormData(document.querySelector("#customerName"),document.querySelector("#customerContact"));
  
  let name = document.querySelector("#nameCus").value;
  let email = document.querySelector("#email").value;
  let phone = document.querySelector("#phone").value;

  // add values to formdata
  formData.append("customerEmail",email);
  
  formData.append("customerPhone",phone);

  formData.append("customerID",event.path[2].childNodes[0].childNodes[1].textContent);
  
  
  let encData = new URLSearchParams(formData.entries());

  let fetchoptions = {
    method: 'PUT',
    headers : {
        'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
    },
    body : encData
};

// request backend
fetch("restservices/customer", fetchoptions)
.then(function(response){
  if(response.ok){
    // if response is ok edit row to display new data
    event.path[2].childNodes[0].childNodes[0].innerHTML = `<p>${name}</p>`;
    event.path[2].childNodes[4].innerHTML = `<p>${email}</p>
    <p>${phone}</p>`;

    // add back the old buttons
  
  event.path[1].innerHTML = `
    <button type="button" class="btn btn-danger delete">Delete</button>
    <button type="button" class="btn btn-info edit">Edit</button>` 
  }

  arrlistEditButtons = document.querySelectorAll(".edit")


  // add event listeners to newly packed buttons
  for(var j = 0; j<arrlistEditButtons.length; j++){
      arrlistEditButtons[j].addEventListener("click",edit);
  }

  arlistDelButtons = document.querySelectorAll(".delete")

  for(var l=0; l<arlistDelButtons.length; l++){
      arlistDelButtons[l].addEventListener("click",deleteCus);
  }
})

}


function cancelEventListener(){
  console.log("stuff");
  document.querySelector(".cancel").addEventListener("click",cancelEventHandler);
}


// if user cancels the edit customer
function cancelEventHandler(event){

  // set old values back
  event.path[2].childNodes[0].childNodes[0].innerHTML = `<p>${customername}</p>`;
  event.path[2].childNodes[4].innerHTML = `<p>${customerEmail}</p>
  <p>${customerPhone}</p>`;
  

// add buttons again
  event.path[1].innerHTML = `
  <button type="button" class="btn btn-danger delete">Delete</button>
  <button type="button" class="btn btn-info edit">Edit</button>
  ` 


  arrlistEditButtons = document.querySelectorAll(".edit")

// add event listeners to buttons
  for(var j = 0; j<arrlistEditButtons.length; j++){
      arrlistEditButtons[j].addEventListener("click",edit);
  }

  arlistDelButtons = document.querySelectorAll(".delete")

  for(var l=0; l<arlistDelButtons.length; l++){
      arlistDelButtons[l].addEventListener("click",deleteCus);
  }

}


function editEvent(){
    document.querySelector(".edit").addEventListener("click",edit);
}

document.querySelector(".addCus").addEventListener("click",addCustomer);

//edit package function
function editPackage(event){
  console.log(event);
  let iarray = document.querySelectorAll("i");

  //hide all the favicon buttons to prevent user from bugging out the system.
  for(let i =0; i<iarray.length; i++){
      iarray[i].style.visibility = "hidden";
    }

// show the default packages
  event.path[1].childNodes[0].innerHTML = `
  <select id="packageSelector">
    <option value="2147483645">Superpakket</option>
    <option value="2147483646">Ultrapakket</option>
    <option value="2147483647">Dagelijks Pro</option>
  </select>
  <button type="button" class="btn btn-dark editpackageSaveButton">Save</button>
  `
// add a save button to edit package
  document.querySelector(".editpackageSaveButton").addEventListener("click", function(event){
    // disable people from spamming the button to bug out the system
    event.currentTarget.disabled = true;
    let formData = new FormData();
    let packageid = event.path[2].childNodes[1].textContent;
    let orderid = event.path[3].childNodes[2].childNodes[1].textContent;
    let customerid = event.path[3].childNodes[0].childNodes[1].textContent;
    let defaultPackageId = document.getElementById("packageSelector").value;

    // get all needed info for backend

    let imgload = document.createElement("img");
    imgload.setAttribute("src","img/landingpage/Spinner-1s-21px.svg");

    event.path[0].appendChild(imgload)
    

    // add data to formdata element
    formData.append("defaultPackageID",defaultPackageId);
    formData.append("currentOrderID",orderid);
    formData.append("customerID",customerid);
    formData.append("currentPackageId",packageid);

    let encData = new URLSearchParams(formData.entries());

    let fetchoptions = {
      method: 'POST',
      headers : {
          'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
      },
      body : encData
    };

    // send data to backend
    fetch("/restservices/package/edit", fetchoptions)
    .then(function(response){
      if(response.ok){
        location.reload();
      }
    });   
  });
}


//add logout functionality
document.querySelector("#logoutButton").addEventListener("click",function(){
  let confirmbox = confirm("Are you sure you want to log out?");
  if(confirmbox){
    sessionStorage.removeItem("myJWT");
    window.location.href = "../";
  }
});


getData();


// function edit(event){
//     console.log(event);

//     oldName = document.querySelector("#nameText").textContent;
//     infoArray = document.querySelectorAll(".customerInfo p");
//     oldPackage = document.querySelector(".customerPackage").textContent;
//     oldPayment = document.querySelector(".customerPayment").textContent;
//     oldServicesArray = document.querySelectorAll(".customerServices ul li");
//     path = event.path[2];

//     let editContent = 
//     `
//     <form>
//         <div class="customerName"> <input type=text value="${oldName}"/> </div>
//         <div class="customerPackage">
//             <select name="packages">
//                 <option value="${oldPackage}" selected> ${oldPackage}</option>
//                 <option value="anders">anders</option>
//                 <option value="anders1">anders1</option>
//             </select>
//         </div>
//         <div class="customerPayment"><input type=text value="${oldPayment.trim()}"/></div>
//         <div class="customerInfo">
//             <input type=email value="${infoArray[0].textContent}" />
//             <br/>
//             <input type=tel value="${infoArray[1].textContent}" />
//         </div>
//         <div class="customerServices">
//             <input type=text value="${oldServicesArray[0].textContent}"/>
//             <br/>
//             <input type=text value="${oldServicesArray[1].textContent}"/>
//             <br/>
//             <input type=text value="${oldServicesArray[2].textContent}"/>
//             <br/>
//             <input type=text value="${oldServicesArray[3].textContent}"/>
//         </div>

//         <div class="customerButtons">
//             <button type="button" class="btn btn-danger cancel" onclick="cancelEvent()">Cancel</button>
//             <button type="button" class="btn btn-info">Save</button>

//         </div>
//     </form>
//     `;

//     //select the current row and edit it
//     path.innerHTML = editContent;


//     console.log(oldName);
// }

// function cancelEvent(){
//     document.querySelector(".cancel").addEventListener("click",cancel(path,oldName,infoArray,oldPackage,oldPayment,oldServicesArray));
// }


// function cancel(path,name,info,package,payment,services){

//     let restoreContent = `
//     <div class="customerName">
//         <p id="nameText">${name}</p>
//     </div>
//     <div class="customerPackage">
//         <p>${package}</p>
//     </div>
//     <div class="customerPayment">
//         <p>${payment}</p>
//     </div>
//     <br/>
//     <div class="customerInfo">
//         <p>${info[0].textContent}</p>
//         <p>${info[1].textContent}</p>
//     </div>
//     <div class="customerServices">
//         <ul>
//             <li>${services[0].textContent}</li>
//             <li>${services[1].textContent}</li>
//             <li>${services[2].textContent}</li>
//             <li>${services[3].textContent}</li>
//         </ul>
//     </div>

//     <div class="customerButtons">
//         <button type="button" class="btn btn-danger delete">Delete</button>
//         <button type="button" class="btn btn-info edit" onclick="editEvent()">Edit</button>
//     </div>
    
//     `;

//     path.innerHTML = restoreContent;


//     console.log(path,name,info,package,payment,services);
// }
