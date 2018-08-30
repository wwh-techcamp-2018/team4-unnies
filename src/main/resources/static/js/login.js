import { $, $all } from './lib/utils.js';

const registerFlag = {
    'email':false,
    'password':false,
};


function loginHandler(event){
    event.preventDefault();

    if(!monitorRegisterButton()){
        return;
    }

    const email = $('input[name=email]').value;
    const password = $('input[name=password]').value;

    fetch('/api/users/login',{
        method:'post',
        headers:{'content-type':'application/json'},
        credentials:'same-origin',
        body: JSON.stringify({
            email,
            password
        })
    })
    .then(response=>{
        if(response.status >= 400 && response.status <= 404){
            validateError(response);
        }else if(response.status === 200){
            location.href = '/';
        }
    })
    .catch(error=>{
        location.reload();
    });
}

function validateError(response) {
    response.json().then(({errors}) => {
        errors.forEach((error) => {
            $(`strong[name=invalid-${error.field}`).style.visibility = 'visible';
            $(`strong[name=invalid-${error.field}`).innerText = error.message;
            registerFlag[`${error.field}`] = false;
            })
    })
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
    const email = $('input[name=email]').value;

    if(!email.match(regex_email)){
        $('strong[name=invalid-email]').style.visibility='visible';
        registerFlag['email'] = false;
    }else{
        $('strong[name=invalid-email]').style.visibility='hidden';
        registerFlag['email'] = true;
    }
}

function validatePassword(){

    const regex_password = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/; // 아이디와 패스워드가 적합한지 검사할 정규식
    const password = $('input[name=password]').value;
    if(!password.match(regex_password)){
        $('strong[name=invalid-password]').style.visibility='visible';
        registerFlag['password'] = false;
    }else{
        $('strong[name=invalid-password]').style.visibility='hidden';
        registerFlag['password'] = true;
    }

}

$('#loginForm').addEventListener('submit',loginHandler);
$('input[name=email]').onchange = validateEmail;
$('input[name=password]').onchange = validatePassword;
