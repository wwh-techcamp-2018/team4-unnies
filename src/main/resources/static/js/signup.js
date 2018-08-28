import {$} from './lib/utils.js';
import DaumPostCode from './class/DaumPostCode.js';

const registerFlag = {
    'email': false,
    'name': false,
    'password': false,
    'confirmPassword': false,
    'phoneNumber': false,
    'addressDetail': false
};

const daumPostCode = new DaumPostCode();

$('input[name=find-postcode]').addEventListener('click', event => {
    event.preventDefault();
    daumPostCode.load(({address}) => $('input[name=address]').value = address);
})


function signupHandler(event) {
    event.preventDefault();

    validateConfirmPassword();
    if (!validateAddress()) {
        return;
    }

    const email = $('input[name=email]').value;
    const name = $('input[name=name]').value;
    const password = $('input[name=password]').value;
    const confirmPassword = $('input[name=confirm]').value;
    const phoneNumber = $('input[name=phoneNumber]').value;

    const address = $('input[name=address]').value;
    const addressDetail = $('input[name=address-detail]').value;

    fetch('/api/users', {
        method: 'post',
        headers: {'content-type': 'application/json'},
        credentials: 'same-origin',
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
            if (response.status >= 400 && response.status <= 404) {
                validateError(response);
            } else if (response.status === 201) {
                location.href = '/';
            }
        })
        .catch(error => {
            location.reload();
        });
}

function validateError(response) {
    response.json().then(({errors}) => {
        errors.forEach((error) => {
            $(`strong[name=invalid-${error.field}`).style.visibility = 'visible';
            $(`strong[name=invalid-${error.field}`).innerText = error.message;
            registerFlag[`${error.field}`] = false;
        });
    })
}


function validateCheck() {
    $('#button').disabled = !monitorRegisterButton();
}

function monitorRegisterButton() {
    for (const key in registerFlag) {
        if (registerFlag[key] === false) {
            return false;
        }
    }
    return true;
}

function validateEmail() {

    const regex_email = /^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\w+\.)+\w+$/i;// 이메일이 적합한지 검사할 정규식
    const email = $('input[name=email]').value;

    if (!email.match(regex_email)) {
        $('strong[name=invalid-email]').style.visibility = 'visible';
        registerFlag['email'] = false;
    } else {
        $('strong[name=invalid-email]').style.visibility = 'hidden';
        registerFlag['email'] = true;
        validateCheck();
    }
}

function validateName() {
    const regex_name = /[가-힣]{2,16}|[a-zA-Z]{2,16}/;
    const name = $('input[name=name]').value;
    if (!name.match(regex_name)) {
        $('strong[name=invalid-name]').style.visibility = 'visible';
        registerFlag['name'] = false;
    } else {
        $('strong[name=invalid-name]').style.visibility = 'hidden';
        registerFlag['name'] = true;
        validateCheck();
    }

}

function validatePassword() {
    const regex_password = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/;
    const password = $('input[name=password]').value;
    if (!password.match(regex_password)) {
        $('strong[name=invalid-password]').style.visibility = 'visible';
        registerFlag['password'] = false;
    } else {
        $('strong[name=invalid-password]').style.visibility = 'hidden';
        registerFlag['password'] = true;
        validateCheck();
    }

}

function validateConfirmPassword() {
    const confirm = $('input[name=confirm]').value;
    const password = $('input[name=password]').value;
    if (password !== confirm) {
        $('strong[name=invalid-confirmPassword]').style.visibility = 'visible';
        registerFlag['confirmPassword'] = false;
    } else {
        $('strong[name=invalid-confirmPassword]').style.visibility = 'hidden';
        registerFlag['confirmPassword'] = true;
        validateCheck();
    }

}

function validatePhone() {
    const regex_phone = /(01[016789])-(\d{3,4})-(\d{4})$/;
    const phone = $('input[name=phoneNumber]').value;
    if (!phone.match(regex_phone)) {
        $('strong[name=invalid-phoneNumber]').style.visibility = 'visible';
        registerFlag['phoneNumber'] = false;
    } else {
        $('strong[name=invalid-phoneNumber]').style.visibility = 'hidden';
        registerFlag['phoneNumber'] = true;
        validateCheck();
    }
}

function validateAddress() {
    const address = $('input[name=address]').value;
    if (address) {
        $('strong[name=invalid-address]').style.visibility = 'hidden';
        validateCheck();
        return true;
    } else {
        $('strong[name=invalid-address]').style.visibility = 'visible';
        $('strong[name=invalid-address]').innerText = '주소를 입력하세요';
        return false;
    }
}

function validateAddressDetail() {
    const addressDetail = $('input[name=address-detail]').value;
    if (!addressDetail) {
        $('strong[name=invalid-address]').style.visibility = 'visible';
        $('strong[name=invalid-address]').innerText = '상세주소를 입력해주세요';
        registerFlag['addressDetail'] = false;
    } else {
        $('strong[name=invalid-address]').style.visibility = 'hidden';
        registerFlag['addressDetail'] = true;
        validateCheck();
    }
}

$('#signupForm').addEventListener('submit', signupHandler);
$('#btnCloseLayer').addEventListener('click', () => {
    $('#layer').style.display = 'none';
});

$('input[name=email]').onchange = validateEmail;
$('input[name=name]').onchange = validateName;
$('input[name=password]').onchange = validatePassword;
$('input[name=confirm]').onchange = validateConfirmPassword;
$('input[name=phoneNumber]').onchange = validatePhone;
$('input[name=address-detail]').onchange = validateAddressDetail;