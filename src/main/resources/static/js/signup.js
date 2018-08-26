import { $ } from './lib/utils.js';
import { closeDaumPostcode, execDaumPostcode } from './address.js';
const registerFlag = {
    'email':false,
    'name':false,
    'password':false,
    'confirmPassword':false,
    'phone':false,
    'addressDetail':false
};

function signupHandler(event){
    event.preventDefault();

    validateConfirmPassword();
    if(!validateAddress()){
        return;
    }

    const email = $('#email').value;
    const name = $('#name').value;
    const password = $('#password').value;
    const confirmPassword = $('#confirm').value;
    const phoneNumber= $('#phone').value;

    const address= $('#address').value;
    const addressDetail = $('#address-detail').value;

    fetch('/api/users',{
        method:'post',
        headers:{'content-type':'application/json'},
        credentials:'same-origin',
        body: JSON.stringify({
            email,
            name,
            password,
            confirmPassword,
            phoneNumber,
            address,
            addressDetail
        })
    })
    .then(response => {
        if(response.status >= 400 && response.status <= 405){
            validateError(response);
        }else if(response.status === 201){
            location.href = '/';
        }
    })
    .catch(error=>{
        location.reload();
    });
}

function validateError(response){
    response.json().then(({errors}) => {
        errors.forEach((error) => {
            switch(error.field){
                case 'email' :
                    $('#invalid-email').style.visibility='visible';
                    $('#invalid-email').innerText=error.message;
                    registerFlag['email'] = false;
                    break;
                case 'password' :
                    $('#invalid-password').style.visibility='visible';
                    $('#invalid-password').innerText=error.message;
                    registerFlag['password'] = false;
                    break;
                case 'confirmPassword' :
                    $('#invalid-confirmPassword').style.visibility='visible';
                    $('#invalid-confirmPassword').innerText=error.message;
                    registerFlag['confirmPassword'] = false;
                    break;
                case 'name' :
                    $('#invalid-name').style.visibility='visible';
                    $('#invalid-name').innerText=error.message;
                    registerFlag['name'] = false;
                    break;
                case 'phoneNumber' :
                    $('#invalid-phone').style.visibility='visible';
                    $('#invalid-phone').innerText=error.message;
                    registerFlag['phone'] = false;
                    break;
                case 'address' :
                    $('#invalid-address').style.visibility='visible';
                    $('#invalid-address').innerText=error.message;
                    registerFlag['address'] = false;
                    break;
            }
        });
    })
}


function validateCheck(){
    $('#button').disabled = monitorRegisterButton() ? false : true ;
}

function monitorRegisterButton(){
    for(const key in registerFlag){
            if(registerFlag[key] === false){
                return false;
            }
     }
     return true;
}

function validateEmail(){

    const regex_email = /^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\w+\.)+\w+$/i;// 이메일이 적합한지 검사할 정규식
    const email = $('#email').value;

    if(!email.match(regex_email)){
        $('#invalid-email').style.visibility='visible';
        registerFlag['email'] = false;
    }else{
        $('#invalid-email').style.visibility='hidden';
        registerFlag['email'] = true;
        validateCheck();
    }
}

function validateName(){
    const regex_name = /[가-힣]{2,16}|[a-zA-Z]{2,16}/;
    const name = $('#name').value;
    if(!name.match(regex_name)){
        $('#invalid-name').style.visibility='visible';
        registerFlag['name'] = false;
    }else{
        $('#invalid-name').style.visibility='hidden';
        registerFlag['name'] = true;
        validateCheck();
    }

}

function validatePassword(){
    const regex_password = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/;
    const password = $('#password').value;
    if(!password.match(regex_password)){
        $('#invalid-password').style.visibility='visible';
        registerFlag['password'] = false;
    }else{
        $('#invalid-password').style.visibility='hidden';
        registerFlag['password'] = true;
        validateCheck();
    }

}

function validateConfirmPassword(){
    const confirm = $('#confirm').value;
    const password = $('#password').value;
    if(password !== confirm){
        $('#invalid-confirmPassword').style.visibility='visible';
        registerFlag['confirmPassword'] = false;
    }else{
        $('#invalid-confirmPassword').style.visibility='hidden';
        registerFlag['confirmPassword'] = true;
        validateCheck();
    }

}
function validatePhone(){
    const regex_phone = /(01[016789])-(\d{3,4})-(\d{4})$/;
    const phone = $('#phone').value;
    if(!phone.match(regex_phone)){
        $('#invalid-phone').style.visibility='visible';
        registerFlag['phone'] = false;
    }else{
        $('#invalid-phone').style.visibility='hidden';
        registerFlag['phone'] = true;
        validateCheck();
    }
}

function validateAddress(){
    const address = $('#address').value;
    const postcode = $('#postcode').value;
    if(address && postcode){
        $('#invalid-address').style.visibility='hidden';
        validateCheck();
        return true;
    }else{
        $('#invalid-address').style.visibility='visible';
        $('#invalid-address').innerText='주소를 입력하세요';
        return false;
    }
}

function validateAddressDetail(){
    const addressDetail = $('#address-detail').value;
    if(!addressDetail){
        $('#invalid-address').style.visibility='visible';
        $('#invalid-address').innerText='상세주소를 입력해주세요';
        registerFlag['addressDetail'] = false;
    }else{
        $('#invalid-address').style.visibility='hidden';
        registerFlag['addressDetail'] = true;
        validateCheck();
    }
}

document.addEventListener('DOMContentLoaded', () => {
    $('#signupForm').addEventListener('submit',signupHandler);
    $('#find-postcode').addEventListener('click',execDaumPostcode);
    $('#btnCloseLayer').addEventListener('click',closeDaumPostcode);
    $('#email').onchange = validateEmail;
    $('#name').onchange = validateName;
    $('#password').onchange = validatePassword;
    $('#confirm').onchange = validateConfirmPassword;
    $('#phone').onchange = validatePhone;
    $('#address-detail').onchange = validateAddressDetail;
});