import {$, $all} from '../lib/utils.js';
import {ratingTemplate} from "../template/DetailTemplate.js";
import {errorPageTemplate} from '../template/ErrorPageTemplate.js';

class Product {

    upload(formData, callback) {
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
            callback(response);
        }).then(responseBody => {
            if (responseBody.errors) {
                throw responseBody.errors;
            }
        }).catch(errors => {
            errors.forEach(({field, message}) => {
                const feedback = $(`*[name=${field}]`).closest('.form-group').querySelector('.feedback');
                feedback.innerText = message;
                feedback.classList.add('on');
            });
            $('.feedback.on:first-child').focus();
        });
    }

    load(productId, callback){
        fetch(`/api/products/${productId}`)
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                this.loadProductError();
                return;
            })
            .then(({ data }) => {
                console.log(data);
                callback(data);
            })
            .catch(error => {
                // TODOs : error handling...
            });
    }

    loadProduct(data) {
        const {product, ownerRating} = data;
        const {owner, status} = product;
        $('.product-name').innerText = product.name;
        $('#product-name').innerText = product.name;
        $('#product-title').innerText = product.title;
        $('#cook').innerText = owner.name;
        $('#participate-number').innerText = product.ordersSize + '/' + product.maxParticipant;
        $('#participate-date').innerText = product.expireDateTime;
        $('#give-time').innerText = product.shareDateTime;
        $('#give-place').innerText = owner.address;
        $('#give-plate').innerText = product.isBowlNeeded === true ? '개인 용기 지참' : '나눔 용기 제공';
        $('#price').innerText = product.price;
        $('#nickname').innerText = owner.name;
        $('#region-name').innerText = owner.address;
        $('#product-name').innerText = product.name;
        $('#product-category').innerText = product.category.name;
        $('#product-create-time').innerText = product.shareDateTime;
        $('#product-price').innerText = product.price == 0 ? '무료 나눔' : product.price;

        tui.Editor.factory({
            el: $('#product-detail'),
            height: 'auto',
            viewer: true,
            initialValue: product.description
        });

        const userRating = Math.round(ownerRating)
        $('#user-rating').innerHTML = userRating > 0 ? ratingTemplate(userRating) : '';

        const currentStatus = $('.status');
        const registerShareBtn = $('#register-button');
        if(status === 'ON_PARTICIPATING') {
            currentStatus.innerText = '모집중';
            currentStatus.classList.add('on');
            registerShareBtn.innerText = '나눔신청';
            registerShareBtn.classList.add('on');
            registerShareBtn.disabled = false;
        } else if(status === 'FULL_PARTICIPANTS') {
            currentStatus.innerText = '모집완료';
            currentStatus.classList.add('full');
            registerShareBtn.innerText = '모집완료';
            registerShareBtn.classList.add('full');
            registerShareBtn.disabled = true;
        } else {
            currentStatus.innerText = '기간만료';
            currentStatus.classList.add('expired');
            registerShareBtn.innerText = '기간만료';
            registerShareBtn.classList.add('expired');
            registerShareBtn.disabled = true;
        }

    }

    loadProductError(){
        $('.container').insertAdjacentHTML('afterbegin', errorPageTemplate());
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