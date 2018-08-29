import { $, $all } from './lib/utils.js';
import Product from './class/Product.js';
import Review from './class/Review.js';
import OrderList from './class/OrderList.js';
import { orderListTemplate } from './template/DetailTemplate.js';
import { openReviewModal, closeReviewModal, openOrderModal, closeOrderModal } from './modal.js'

const pathname = window.location.pathname;
const productId = pathname.substring(pathname.lastIndexOf('/')+1);
let reviewPage = 0;


const product = new Product();
const review = new Review();
const orderList = new OrderList();

product.load(productId, product.loadProduct);
review.load(productId, reviewPage);
orderList.load(productId, loadOrderList);

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

function loadOrderList(data){
    if(!data.length){
        return;
    }

    $('#orders-count').innerHTML = '신청자 ' + data.length;
    $('#product-order-list').innerHTML = data.map(orderListTemplate).join('');

    const buttonList = $all('#product-order-list li .order-button');
    buttonList.forEach(button => {
        if(button.innerText === '나눔 중'){
            button.disabled = false;
            button.addEventListener('click', changeOrderStatus);
        }
    });
}

function loadOrder(data){
    if(!data){
        return;
    }
    $(`[data-order-id='${data.id}']`).disabled = true;
    $(`[data-order-id='${data.id}']`).innerText = '나눔 완료';
}

function changeOrderStatus(event){
    event.preventDefault();

    const { target } = event;
    const orderId = target.dataset.orderId;
    const status = 'COMPLETE_SHARING';

    fetch(`/api/orders/${orderId}`, {
        method:'PUT',
        headers:{'content-type':'application/json'},
        credentials:'same-origin',
        body:JSON.stringify({
            status
        })
    }).then(response => {
        return response.json();
    })
    .then(({ data, errors }) => {
        if(data){
            loadOrder(data);
            return;
        }

        let htmlOrderError = `<strong style='color:red;'>`;
        errors.forEach(({ message }) => {
            htmlOrderError += message;
        })
        htmlOrderError += `</strong>`;
        target.insertAdjacentHTML("afterend", htmlOrderError);
    })
    .catch(error => {
        // TODOs : error handling...
    })
}

function createOrder(event){
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
    })
    .then(response => {
        closeOrderModal();
        return response.json();
    })
    .then(({ data, errors }) => {
        console.log(data);
        if(data){
            product.loadProduct(data);
            $('strong[name=invalid-register]').style.visibility = 'hidden';
            return;
        }

        errors.forEach(({ message }) => {
            $('strong[name=invalid-register]').innerText += message;
        });
        $('strong[name=invalid-register]').style.visibility = 'visible';

    })
    .catch(error => {
        // TODOs : error handling...
    })
}

function createReview(event){
    event.preventDefault();

    const comment = $('#comment').value;
    const rating = $all('.star.selected').length;

    console.log(comment);

    fetch(`/api/products/${productId}/reviews?page=0&size=5`, {
            method:'post',
            headers:{'content-type':'application/json'},
            credentials:'same-origin',
            body: JSON.stringify({
                comment,
                rating
            })
        }).then(response => {
            closeReviewModal();
            return response.json();
        })
        .then(({ data, errors }) => {
            if(data){
                $('strong[name=invalid-review]').style.visibility = 'hidden';
                review.loadReviews(data);
                return;
            }
            errors.forEach(({ message }) => {
                $('strong[name=invalid-review]').innerText += message;
            });
            $('strong[name=invalid-review]').style.visibility = 'visible';
        })
        .catch(error => {
            // TODOs : error handling...
        })
}

function registerOrderModal(){
    product.load(productId, openOrderModal);
}

$('.preview-thumbnail.nav.nav-tabs').addEventListener('click', moveToSelectedImage);

$('#register-button').addEventListener('click',registerOrderModal);
$('#register-share').addEventListener('submit',createOrder);
$('#close-register').addEventListener('click',closeOrderModal);

$('#open-modal').addEventListener('click',openReviewModal);
$('#register-review').addEventListener('click', createReview);
$('#close-modal').addEventListener('click',closeReviewModal);

$('#show-review-prev').addEventListener('click', (event) => {
    event.preventDefault();
    reviewPage -= 1;
    reviewList.load(productId, reviewPage);
});

$('#show-review-next').addEventListener('click', (event) => {
    event.preventDefault();
    reviewPage += 1;
    reviewList.load(productId, reviewPage);
});