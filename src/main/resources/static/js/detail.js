import {$, $all} from './lib/utils.js';
import Product from './class/Product.js';
import Review from './class/Review.js';
import {closeModal, openModal} from './modal.js'

const productId = $('#productId').value;
let reviewPage = 0;
new Product().load(productId);
new Review().load(productId, reviewPage);

function moveToSelectedImage(event) {
    event.preventDefault();

    const index = getElementParentIndex(event.target);

    $('.preview-pic.tab-content .tab-pane.active').classList.remove('active');
    $(`.preview-pic.tab-content .tab-pane:nth-child(${index})`).classList.add('active');
}

function getElementParentIndex(element){
    element = element.parentElement;
    return [...element.parentElement.children].indexOf(element) + 1;
}

function registerShare(event){
    event.preventDefault();

    const checkRider = $('input[name="groupOfRadioGap"]:checked');
    if(checkRider == null)
        return;

    const deliveryType = checkRider.id === 'radio-riders' ? 'BAEMIN_RIDER' : 'PICKUP';

    fetch(`/api/products/${productId}/orders`, {
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

function registerReview(event){
    event.preventDefault();

    const comment = $('#comment').value;
    const rating = $all('.star.selected').length;

    fetch(`/api/products/${productId}/reviews`, {
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
                new Review().load(productId, 0);
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

    $('#show-review-prev').addEventListener('click', (event) => {
        event.preventDefault();
        reviewPage -= 1;
        new Review().load(productId, reviewPage);
    });

    $('#show-review-next').addEventListener('click', (event) => {
        event.preventDefault();
        reviewPage += 1;
        new Review().load(productId, reviewPage);
    });

});
