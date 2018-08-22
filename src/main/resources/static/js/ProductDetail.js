import { $, $all } from './lib/utils.js';
import { ratingTemplate } from './template/DetailTemplate.js';
class ProductDetail {

    load(productId){
        fetch(`/api/products/${productId}`)
            .then(response => {
                if (response.ok) {
                    return response.json();
                }

            }).then(({ data }) => {
                this.loadProductDetail(data);
            })
            .catch(error => {
                console.log(error);
            });
    }

    loadProductDetail(data){
        const {product, orderCount, status, ownerRating} = data;
        const {owner} = product;
        $('.product-name').innerText = product.name;
        $('#product-name').innerText = product.name;
        $('#product-title').innerText = product.title;
        $('#cook').innerText = owner.name;
        $('#participate-number').innerText = orderCount +'/'+ product.maxParticipant;
        $('#participate-date').innerText = product.expireDateTime;
        $('#give-time').innerText = product.shareDateTime;
        $('#give-place').innerText = owner.address;
        product.isBowlNeeded === true ? $('#give-plate').innerText = '개인 용기 지참' : $('#give-plate').innerText = '나눔 용기 제공';
        $('#price').innerText = product.price;
        $('#nickname').innerText = owner.name;
        $('#region-name').innerText = owner.address;
        $('#product-name').innerText = product.name;
        $('#product-category').innerText = product.category.name;
        $('#product-create-time').innerText = product.shareDateTime;
        $('#product-price').innerText = product.price;
        $('#product-detail').innerText = product.description;

        this.loadStatus(status);
        const userRating = Math.round(data.ownerRating)
        $('#user-rating').innerHTML = userRating > 0 ? ratingTemplate(userRating) : '';
    }

    loadStatus(status){
        // status type : EXPIRED, FULL_PARTICIPANTS, ON_PARTICIPATING
        const currentStatus = $('.status');
        const registerShareBtn = $('#register-share');
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

}

export default ProductDetail;