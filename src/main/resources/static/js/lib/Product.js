import {$} from './utils.js';

class Product {

    upload(formData) {
        // upload(formElement) {
        // const formData = new FormData(formElement);

        fetch('/api/products', {
            method: 'post',
            credentials: 'same-origin',
            body: formData
        }).then(response => {
            document.querySelectorAll('.form-group .feedback').forEach(feedback => feedback.classList.remove('on'));
            if (!response.ok) {
                return response.json();
            }
            // console.log("response.headers : ", response.headers);
            // return location.href = '/';// products/id 페이지로 변경필요
        }).then(responseBody => {
            if (!responseBody) {
                return;
            }
            responseBody.errors.forEach(({field, message}) => {
                const feedback = $(`*[name=${field}]`).closest('.form-group').querySelector('.feedback');
                feedback.innerText = message;
                feedback.classList.add('on');
            });

        })
    }
}

export default Product;