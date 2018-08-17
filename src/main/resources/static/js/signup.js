$("#button").addEventListener("click",signupHandler);

const registerFlag = {
    "email":false,
    "name":false,
    "password":false,
    "confirmPassword":false,
    "phone":false,
};

function signupHandler(evt){
    evt.preventDefault();

    const email = `${$("#email").value}`;
    const name = `${$("#name").value}`;
    const password = `${$("#password").value}`;
    const confirmPassword = `${$("#confirm").value}`;
    const phoneNumber= `${$("#phone").value}`;
    const address=`${$("#address").value} ${$("#address_detail").value}`;

    validateConfirmPassword();

    fetch('/api/users',{
        method:'post',
        headers:{'content-type':'application/json'},
        credentials:"same-origin",
        body: JSON.stringify({
            email,
            name,
            password,
            confirmPassword,
            phoneNumber,
            address
        })
    })
    .then(response=>{
        console.log(response);
        if(response.status == 401){
            validateError(response);
            return location.reload();
        }
        alert("회원가입을 축하드립니다.");
    })
    .catch(error=>{
        console.log(error);
        location.reload();
    });
}

function validateError(response){
    response.json().then(({errors})=>{
        errors.forEach((error)=>{
            switch(error.field){
                case "email" :
                    $("#email").parentElement.nextElementSibling.style.visibility="visible";
                    $("#email").parentElement.nextElementSibling.innerText=error.message;
                    registerFlag["email"] = false;
                    break;
                case "password" :
                    $("#password").parentElement.nextElementSibling.style.visibility="visible";
                    $("#password").parentElement.nextElementSibling.innerText=error.message;
                    registerFlag["password"] = false;
                    break;
                case "confirmPassword" :
                    $("#confirm").parentElement.nextElementSibling.style.visibility="visible";
                    $("#confirm").parentElement.nextElementSibling.innerText=error.message;
                    registerFlag["confirmPassword"] = false;
                    break;
                case "name" :
                    $("#name").parentElement.nextElementSibling.style.visibility="visible";
                    $("#name").parentElement.nextElementSibling.innerText=error.message;
                    registerFlag["name"] = false;
                    break;
                case "phoneNumber" :
                    $("#phone").parentElement.nextElementSibling.style.visibility="visible";
                    $("#phone").parentElement.nextElementSibling.innerText=error.message;
                    registerFlag["phone"] = false;
                    break;
                case "address" :
                    $("#address").parentElement.nextElementSibling.style.visibility="visible";
                    $("#address").parentElement.nextElementSibling.innerText=error.message;
                    registerFlag["address"] = false;
                    break;
            }
        });
    })
}


function validateCheck(value){
    switch(value){
        case "email":
            validateEmail();
            break;
        case "name":
            validateName();
            break;
        case "password":
            validatePassword();
            break;
        case "confirmPassword":
            validateConfirmPassword();
            break;
        case "phone":
            validatePhone();
            break;
    }

    if(monitorRegisterButton()){
        $("#button").disabled=false;
    }else{
        $("#button").disabled=true;
    }
}

function monitorRegisterButton(){
    for(let key in registerFlag){
            if(registerFlag[key] === false){
                return false;
            }
     }
     return true;
}

function validateEmail(){

    const regex_email = /^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\w+\.)+\w+$/i;// 이메일이 적합한지 검사할 정규식
    const email = `${$("#email").value}`;

    if(!email.match(regex_email)){
        $("#email").parentElement.nextElementSibling.style.visibility="visible";
        registerFlag["email"] = false;
    }else{
        $("#email").parentElement.nextElementSibling.style.visibility="hidden";
        registerFlag["email"] = true;
    }
}

function validateName(){
    var regex_name = /[가-힣]{2,16}|[a-zA-Z]{2,16}/;

    const name= `${$("#name").value}`;
    if(!name.match(regex_name)){
        $("#name").parentElement.nextElementSibling.style.visibility="visible";
        registerFlag["name"] = false;
    }else{
        $("#name").parentElement.nextElementSibling.style.visibility="hidden";
        registerFlag["name"] = true;
    }

}

function validatePassword(){

    var regex_password = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/; // 아이디와 패스워드가 적합한지 검사할 정규식
    const password = `${$("#password").value}`;
    if(!password.match(regex_password)){
        $("#password").parentElement.nextElementSibling.style.visibility="visible";
        registerFlag["password"] = false;
    }else{
        $("#password").parentElement.nextElementSibling.style.visibility="hidden";
        registerFlag["password"] = true;
    }

}

function validateConfirmPassword(){

    const confirm = `${$("#confirm").value}`;
    const password = `${$("#password").value}`;
    if(password !== confirm){
        $("#confirm").parentElement.nextElementSibling.style.visibility="visible";
        registerFlag["confirmPassword"] = false;
    }else{
        $("#confirm").parentElement.nextElementSibling.style.visibility="hidden";
        registerFlag["confirmPassword"] = true;
    }

}
function validatePhone(){
    var regex_phone = /(01[016789])-(\d{3,4})-(\d{4})$/;
    const phone = `${$("#phone").value}`;
    if(!phone.match(regex_phone)){
        $("#phone").parentElement.nextElementSibling.style.visibility="visible";
        registerFlag["phone"] = false;
    }else{
        $("#phone").parentElement.nextElementSibling.style.visibility="hidden";
        registerFlag["phone"] = true;
    }
}