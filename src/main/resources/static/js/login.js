import { $, $all } from './lib/utils.js';

$('#button').addEventListener('click',loginHandler);

const registerFlag = {
    'email':false,
    'password':false,
};

function loginHandler(evt){
    evt.preventDefault();

    const email = $('#email').value;
    const password = $('#password').value;

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
            return location.reload();
        }else if(response.status === 200){
            alert('로그인을 하였습니다.');
        }
    })
    .catch(error=>{
        location.reload();
    });
}

function validateError(response){
    response.json().then(({errors})=>{
        errors.forEach((error)=>{
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
            }
        });
    })
}


function validateCheck(value){
    switch(value){
        case 'email':
            validateEmail();
            break;
        case 'password':
            validatePassword();
            break;
    }

    if(monitorRegisterButton()){
        $('#button').disabled=false;
    }else{
        $('#button').disabled=true;
    }
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
    }
}

function validatePassword(){

    const regex_password = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/; // 아이디와 패스워드가 적합한지 검사할 정규식
    const password = $('#password').value;
    if(!password.match(regex_password)){
        $('#invalid-password').style.visibility='visible';
        registerFlag['password'] = false;
    }else{
        $('#invalid-password').style.visibility='hidden';
        registerFlag['password'] = true;
    }

}
