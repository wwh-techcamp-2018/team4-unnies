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

    loadNearAll(latitude, longitude, offset, limit, success, fail) {
        fetch(`/api/products?latitude=${latitude}&longitude=${longitude}&offset=${offset}&limit=${limit}`, {
            method: 'get'
        }).then(response => {
            if (!response.ok) {
                fail('잠시 후 다시 시도해주세요');
            }
            return response.json();
        }).then(({ data }) => {
            success(data);
        }).catch(error => {
            fail(error);
        });
    }

}

export default Product;