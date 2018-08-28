import {$, $all} from '../lib/utils.js';
import {ratingTemplate} from "../template/DetailTemplate.js";

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

    load(productId) {
        fetch(`/api/products/${productId}`)
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                this.loadProductError();
                return;
            })
            .then(({data}) => {
                this.loadProductDetail(data);
            })
            .catch(error => {
                // TODOs : error handling...
            });
    }

    loadProductDetail(data) {
        const {product, orderCount, status, ownerRating} = data;
        const {owner} = product;
        $('.product-name').innerText = product.name;
        $('#product-name').innerText = product.name;
        $('#product-title').innerText = product.title;
        $('#cook').innerText = owner.name;
        $('#participate-number').innerText = orderCount + '/' + product.maxParticipant;
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

        this.loadStatus(status);
        const userRating = Math.round(data.ownerRating)
        $('#user-rating').innerHTML = userRating > 0 ? ratingTemplate(userRating) : '';

        const currentStatus = $('.status');
        const registerShareBtn = $('#register-button');
        if(status === 'ON_PARTICIPATING') {
            currentStatus.innerText = '모집중';
            currentStatus.classList.add('on');
            registerShareBtn.innerText = '나눔신청';
            registerShareBtn.classList.add('on');
            registerShareBtn.disabled = false;
        } else if (status === 'FULL_PARTICIPANTS') {
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
            $('.product-name').innerText = '존재 하지 않은 데이터 입니다';
            $('#product-name').innerText = '존재 하지 않은 데이터 입니다';
            $('#product-title').innerText = '존재 하지 않은 데이터 입니다';
            $('#cook').innerText = '존재 하지 않은 데이터 입니다';
            $('#participate-number').innerText = '존재 하지 않은 데이터 입니다';
            $('#participate-date').innerText = '존재 하지 않은 데이터 입니다';
            $('#give-time').innerText = '존재 하지 않은 데이터 입니다';
            $('#give-place').innerText = '존재 하지 않은 데이터 입니다';
            $('#give-plate').innerText = '존재 하지 않은 데이터 입니다';
            $('#price').innerText = '존재 하지 않은 데이터 입니다';
            $('#nickname').innerText = '존재 하지 않은 데이터 입니다';
            $('#region-name').innerText = '존재 하지 않은 데이터 입니다';
            $('#product-name').innerText = '존재 하지 않은 데이터 입니다';
            $('#product-category').innerText = '존재 하지 않은 데이터 입니다';
            $('#product-create-time').innerText = '존재 하지 않은 데이터 입니다';
            $('#product-price').innerText = '존재 하지 않은 데이터 입니다';

            const userRating = '존재 하지 않은 데이터 입니다';
        }
}

export default Product;