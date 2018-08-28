import { $, $all } from './lib/utils.js';
import ProductDetail from './class/ProductDetail.js';
import ReviewList from './class/ReviewList.js';
import OrderList from './class/OrderList.js';
import { orderListTemplate } from './template/DetailTemplate.js';
import { closeModal, openModal, openRegisterModal, closeRegisterModal } from './modal.js'


//const loginUserId = $('#login-user-id') ? $('#login-user-id').value : null;
const pathname = window.location.pathname;
const productId = pathname.substring(pathname.lastIndexOf('/')+1);
let reviewPage = 0;


const productDetail = new ProductDetail()
const reviewList = new ReviewList()
const orderList = new OrderList();

productDetail.load(productId);
reviewList.load(productId, reviewPage);
orderList.load(productId, loadOrderList);

function loadOrderList(data){
    if(data.length == 0){
        return;
    }

    $('#orders-count').innerHTML = '신청자 ' + data.length;
    $('#product-order-list').innerHTML = data.map(orderListTemplate).join('');

    const buttonList = $all('#product-order-list li .order-button');
    buttonList.forEach(button => {
        if(button.innerText === '나눔 중'){
            button.disabled = false;
            button.addEventListener("click",pressShareComplete);
        }
    });
}

function pressShareComplete(event){
    event.preventDefault();

    const { target } = event;
    const orderId = target.previousElementSibling.value;

    fetch(`/api/products/${productId}/orders/user`, {
        method:'post',
        headers:{'content-type':'application/json'},
        credentials:'same-origin',
        body: JSON.stringify(
            orderId
        )
    }).then(response => {
        if(response.status === 200 || (response.status >= 401 && response.status <= 404)){
            return response.json();
        }
    })
    .then(data => {
        if(data.errors){

        }else{

            loadOrderList(data.data);
        }
    })
    .catch(error => {
        // TODOs : error handling...
    })
}
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

function checkRegisterPossible(event){
    event.preventDefault();

    fetch(`/api/products/${productId}/orders/check`)
    .then(response => {
        if(response.status === 200 || (response.status >= 401 && response.status <= 404)){
            return response.json();
        }
//        throw response.json();
    })
    .then(data => {
        if(data.errors){
            $('strong[name=invalid-register]').innerText = data.errors[0].message;
            $('strong[name=invalid-register]').style.visibility = 'visible';
        }else{
            openRegisterModal(data);
        }
    })
    .catch(error => {
        // TODOs : error handling...
    })
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
    })
    .then(response => {
        closeRegisterModal();
        if(response.status === 201 || (response.status >= 401 && response.status <= 404)){
            return response.json();
        }
//        throw response.json();
    })
    .then(data => {
        if(data.errors){
            $('strong[name=invalid-register]').innerText = data.errors[0].message;
            $('strong[name=invalid-register]').style.visibility = 'visible';
        }else{
            productDetail.loadProductDetail(data.data);
            $('strong[name=invalid-register]').style.visibility = 'hidden';
        }
    })
    .catch(error => {
        // TODOs : error handling...
    })
}

function checkReviewPossible(event){
    event.preventDefault();

    fetch(`/api/products/${productId}/reviews/check`)
        .then(response => {
            if (response.ok) {
                $('strong[name=invalid-review]').style.visibility = 'hidden';
                openModal();
                return;
            }
            if (response.status >= 401 && response.status <= 404){
                return response.json();
            }
//                throw response.json();
        })
        .then(({ errors }) => {
            $('strong[name=invalid-review]').innerText = errors[0].message;
            $('strong[name=invalid-review]').style.visibility = 'visible';
        })
        .catch(error => {
            // TODOs : error handling...
        });
}

function registerReview(event){
    event.preventDefault();

    const comment = $('#comment').value;
    const rating = $all('.star.selected').length;

    fetch(`/api/products/${productId}/reviews?page=0&size=5`, {
            method:'post',
            headers:{'content-type':'application/json'},
            credentials:'same-origin',
            body: JSON.stringify({
                comment,
                rating
            })
        }).then(response => {
            closeModal();
            return response.json();
            }
        })
        .then(({ data, errors}) => {
            if(data){
                $('strong[name=invalid-review]').style.visibility = 'hidden';
                reviewList.loadReviewList(data);
                return;
            }
            errors.forEach(({ message }) => {
                $('strong[name=invalid-review]').innerText = data.errors[0].message;
            })
            $('strong[name=invalid-review]').style.visibility = 'visible';
        })
        .catch(error => {
            // TODOs : error handling...
        })
}

$('.preview-thumbnail.nav.nav-tabs').addEventListener('click', moveToSelectedImage);

$('#register-button').addEventListener('click', checkRegisterPossible);
$('#register-share').addEventListener('submit',registerShare);
$('#close-register').addEventListener('click',closeRegisterModal);

$('#open-modal').addEventListener('click',checkReviewPossible);
$('#register-review').addEventListener('click', registerReview);
$('#close-modal').addEventListener('click',closeModal);

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