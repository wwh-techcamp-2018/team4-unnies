import { $, $all } from './lib/utils.js';
import { reviewTemplate } from './template/DetailTemplate.js';
import ProductDetail from './ProductDetail.js';
import ReviewList from './ReviewList.js';
import { openModal, closeModal } from './modal.js'

// for Test!
const productId = 1;
let reviewPage = 0;
new ProductDetail().load(productId);
new ReviewList().load(productId, reviewPage);

function moveToSelectedImage(e) {
    e.preventDefault();

    const index = getElementParentIndex(e.target);

    $('.preview-pic.tab-content .tab-pane.active').classList.remove('active');
    $(`.preview-pic.tab-content .tab-pane:nth-child(${index})`).classList.add('active');
}

function getElementParentIndex(element){
    element = element.parentElement;
    return [...element.parentElement.children].indexOf(element) + 1;
}

function registerShare(e){
    e.preventDefault();

    let deliveryType = $('#radio-riders').value === '배민라이더스' ? 'BAEMIN_RIDER' : 'PICKUP';

    fetch(`/api/products/${productId}/order`, {
        method:'post',
        headers:{'content-type':'application/json'},
        credentials:'same-origin',
        body: JSON.stringify({
            deliveryType
        })
    }).then(response => {
        if(response.status === 201){
            return;
        }
    })
    .catch(error => {
        console.log(error);
    })
}

function registerReview(e){
    e.preventDefault();

    const comment = $('#comment').value;
    const rating = $all('.star.selected').length;

    fetch(`/api/products/${productId}/review`, {
            method:'post',
            headers:{'content-type':'application/json'},
            credentials:'same-origin',
            body: JSON.stringify({
                comment,
                rating
            })
        }).then(response => {
            closeModal();
            if(response.status === 201){
                new ReviewList().load(productId, 0);
            }
            if(response.status >= 400 && response.status <= 404){
                alert('권한이 없습니다.')
                return;
            }
        })
        .catch(error => {
            console.log(error);
        })

}

document.addEventListener('DOMContentLoaded', () => {
    $('.preview-thumbnail.nav.nav-tabs').addEventListener('click', moveToSelectedImage);
    $('.add-to-cart.btn.btn-default').addEventListener('click', registerShare);
    $('#open-modal').addEventListener('click',openModal);
    $('#close-modal').addEventListener('click',closeModal);
    $('#register-review').addEventListener('click', registerReview);
    $('#show-review-prev').addEventListener('click', (e) => {
        e.preventDefault();
        reviewPage -= 1;
        if(reviewPage == 0){
            $('#show-review-prev').style.visibility = 'hidden';
        }
        new ReviewList().load(productId, reviewPage);
    });
    $('#show-review-next').addEventListener('click', (e) => {
        e.preventDefault();
        reviewPage += 1;
        new ReviewList().load(productId, reviewPage);
        $('#show-review-prev').style.visibility = 'display';
    });
});
