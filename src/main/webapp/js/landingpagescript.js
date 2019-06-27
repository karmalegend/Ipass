// var oldName;
// var infoArray;
// var oldPackage;
// var oldPayment;
// var oldServicesArray;
// var path;

/*
* 
* TODO: MAKE THEM DYNAMIC!
* make all selections based on event.
* rather than queryselector.
*  
* */


/*
* TODO:
* 
* Still need to add save functionality and communication to back-end;
* 
* CURRENTLY ONLY A MOCK-UP!
* */



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
       window.location.href = "http://localhost:8080/";
    });
}


function initialLoadIn(response){
    console.log("This is the initialLoadIn function with response ");
    console.log(response);
    
    for(var i = 0; i<response.length; i++){
        renderCustomer(response[i]);
    }
}


//in hinesight i should've just made 1 var for <div> and <p> rather than create a new one everytime.

function renderCustomer(customer){
    console.log(customer);
    //append all this to div class = container to add it to body
    console.log("running function render customer");

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

    var customerIdP = document.createElement("P");
    customerIdP.textContent = customer.customerID;

    customerNameP.appendChild(customerNamePTextNode);
    customerName.appendChild(customerNameP);
    customerName.appendChild(customerIdP);
    customerRow.appendChild(customerName);

    //customerPackage
    var customerPackage = document.createElement("div");
    customerPackage.className = "customerPackage";

    var customerPackageP = document.createElement("P");
    var customerPackagePTextNode = document.createTextNode(customer.pakket.packageName);

    var customerPackageIdP = document.createElement("P");
    customerPackageIdP.textContent = customer.pakket.packageID;

    customerPackageP.appendChild(customerPackagePTextNode);
    customerPackage.appendChild(customerPackageP);
    customerPackage.appendChild(customerPackageIdP);
    customerRow.appendChild(customerPackage);

    //packagePrice
    var packagePrice = document.createElement("div");
    packagePrice.className = "customerPayment";

    var packagePriceP = document.createElement("P");
    var packagePricePTextNode = document.createTextNode(customer.pakket.pagckagePrice);

    packagePriceP.appendChild(packagePricePTextNode);
    packagePrice.appendChild(packagePriceP);
    customerRow.append(packagePrice);


    //breakrule

    var br = document.createElement("BR")
    customerRow.appendChild(br);


    //customerInfo

    var customerInfo = document.createElement("div");
    customerInfo.className = "customerInfo";

    var customerInfoP = document.createElement("p");
    customerInfoPTextNode = document.createTextNode(customer.emailadress);
    
    customerInfoP.appendChild(customerInfoPTextNode);
    customerInfo.appendChild(customerInfoP);


    var customerInfoP1 = document.createElement("p");
    customerInfoP1TextNode = document.createTextNode(customer.phonenumber);

    
    customerInfoP1.appendChild(customerInfoP1TextNode);
    customerInfo.appendChild(customerInfoP1);

    customerRow.appendChild(customerInfo);


    //packageServices
    var customerServices = document.createElement("div");
    customerServices.className= "customerServices";
    var ul = document.createElement("ul");

    for(var i = 0; i<customer.pakket.orders[0].services.length; i++){
        var li = document.createElement("li");
        var liTextNode = document.createTextNode(customer.pakket.orders[0].services[i].serviceName);
        
        li.appendChild(liTextNode);
        ul.appendChild(li);
    }

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
    
    customerButtons.appendChild(delButton);
    customerButtons.appendChild(editButton);
    customerRow.appendChild(customerButtons);




    mainCol.appendChild(customerRow);

    row.appendChild(mainCol);

    document.querySelector(".container").appendChild(row);

    arrlistEditButtons = document.querySelectorAll(".edit")

    console.log(arrlistEditButtons);

    for(var j = 0; j<arrlistEditButtons.length; j++){
        arrlistEditButtons[j].addEventListener("click",edit);
    }

    arlistDelButtons = document.querySelectorAll(".delete")

    for(var l=0; l<arlistDelButtons.length; l++){
        arlistDelButtons[l].addEventListener("click",deleteCus);
    }

    localStorage.setItem("orderID",customer.pakket.orders[0].orderID+1);
    localStorage.setItem("customerID",customer.customerID+1);
    localStorage.setItem("packageID",customer.pakket.packageID+1);


}


function deleteCus(event){
    let id = event.path[2].childNodes[0].children[1].innerText;
    console.log("customer id = " + id);

    let delfetch = {
        method: 'DELETE',
        headers : {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }

    };

    let cfBox = confirm("Are you sure? This action will permanently remove a customer and is non-reversible")
    if(cfBox){
        fetch("/restservices/customer/del/"+id,delfetch)
        .then(function(response){
            if(response.ok){
                console.log("Customer deleted")
                let parent = document.querySelector(".container");
                parent.removeChild(event.path[4]);
            }
            else if(response.status == 404){
                console.log("Customer not found!")
            }
            else console.log("cannot delete customer");
    })
    }

    
    console.log("detele button");
    console.log(event);
}



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



    fetch("restservices/customer", fetchoptions)
    console.log(encData);
    console.log("save customer clicked");
    console.log(event);
}


function edit(event){
    console.log("Edit button");
    console.log(event);
}


function editEvent(){
    document.querySelector(".edit").addEventListener("click",edit);
}

document.querySelector(".addCus").addEventListener("click",addCustomer);

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
