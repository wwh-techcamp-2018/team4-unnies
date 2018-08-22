import {$, $all} from './utils.js';

class Product {

    upload(formData) {
        // TODO: Spinner
        fetch('/api/products', {
            method: 'post',
            credentials: 'same-origin',
            body: formData
        }).then(response => {
            $all('.form-group .feedback').forEach(feedback => feedback.classList.remove('on'));
            if (!response.ok) {
                return response.json();
            }
        }).then(responseBody => {
            if (!responseBody) {
                return;
            }
            throw responseBody.errors;
        }).catch(errors => {
            errors.forEach(({field, message}) => {
                const feedback = $(`*[name=${field}]`).closest('.form-group').querySelector('.feedback');
                feedback.innerText = message;
                feedback.classList.add('on');
            });
        });
    }
}

export default Product;