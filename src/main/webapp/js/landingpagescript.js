var oldName;
var infoArray;
var oldPackage;
var oldPayment;
var oldServicesArray;
var path;

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

function edit(event){
    console.log(event);

    oldName = document.querySelector("#nameText").textContent;
    infoArray = document.querySelectorAll(".customerInfo p");
    oldPackage = document.querySelector(".customerPackage").textContent;
    oldPayment = document.querySelector(".customerPayment").textContent;
    oldServicesArray = document.querySelectorAll(".customerServices ul li");
    path = event.path[2];

    let editContent = 
    `
    <form>
        <div class="customerName"> <input type=text value="${oldName}"/> </div>
        <div class="customerPackage">
            <select name="packages">
                <option value="${oldPackage}" selected> ${oldPackage}</option>
                <option value="anders">anders</option>
                <option value="anders1">anders1</option>
            </select>
        </div>
        <div class="customerPayment"><input type=text value="${oldPayment.trim()}"/></div>
        <div class="customerInfo">
            <input type=email value="${infoArray[0].textContent}" />
            <br/>
            <input type=tel value="${infoArray[1].textContent}" />
        </div>
        <div class="customerServices">
            <input type=text value="${oldServicesArray[0].textContent}"/>
            <br/>
            <input type=text value="${oldServicesArray[1].textContent}"/>
            <br/>
            <input type=text value="${oldServicesArray[2].textContent}"/>
            <br/>
            <input type=text value="${oldServicesArray[3].textContent}"/>
        </div>

        <div class="customerButtons">
            <button type="button" class="btn btn-danger cancel" onclick="cancelEvent()">Cancel</button>
            <button type="button" class="btn btn-info">Save</button>

        </div>
    </form>
    `;

    //select the current row and edit it
    path.innerHTML = editContent;


    console.log(oldName);
}

function cancelEvent(){
    document.querySelector(".cancel").addEventListener("click",cancel(path,oldName,infoArray,oldPackage,oldPayment,oldServicesArray));
}


function cancel(path,name,info,package,payment,services){

    let restoreContent = `
    <div class="customerName">
        <p id="nameText">${name}</p>
    </div>
    <div class="customerPackage">
        <p>${package}</p>
    </div>
    <div class="customerPayment">
        <p>${payment}</p>
    </div>
    <br/>
    <div class="customerInfo">
        <p>${info[0].textContent}</p>
        <p>${info[1].textContent}</p>
    </div>
    <div class="customerServices">
        <ul>
            <li>${services[0].textContent}</li>
            <li>${services[1].textContent}</li>
            <li>${services[2].textContent}</li>
            <li>${services[3].textContent}</li>
        </ul>
    </div>

    <div class="customerButtons">
        <button type="button" class="btn btn-danger delete">Delete</button>
        <button type="button" class="btn btn-info edit" onclick="editEvent()">Edit</button>
    </div>
    
    `;

    path.innerHTML = restoreContent;


    console.log(path,name,info,package,payment,services);
}


function editEvent(){
    document.querySelector(".edit").addEventListener("click",edit);
}

document.querySelector(".edit").addEventListener("click",edit);