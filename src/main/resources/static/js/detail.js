import { $, $all } from './lib/utils.js';
import Product from './class/Product.js';
import Review from './class/Review.js';
import OrderList from './class/OrderList.js';
import { orderListTemplate, detailContentsTemplate } from './template/DetailTemplate.js';
import { openReviewModal, closeReviewModal, openOrderModal, closeOrderModal, openOrderListModal, closeOrderListModal } from './lib/modal.js'
import ImageViewer from './class/ImageViewer.js';
import { mainView, thumbnailView, imageView, categorySelect } from './template/UploadTemplate.js';

const pathname = window.location.pathname;
const productId = pathname.substring(pathname.lastIndexOf('/') + 1 );
let reviewPage = 0;

const product = new Product();
const review = new Review();
const orderList = new OrderList();
const imageViewer = new ImageViewer();

function loadDetailContent(callback){
    $('.detail-contents').insertAdjacentHTML('afterbegin', detailContentsTemplate());
    callback();
}

loadDetailContent(function(){

    imageViewer.setViewTemplate(mainView);
    imageViewer.setImageViewTemplate(imageView);
    imageViewer.setThumbnailViewTemplate(thumbnailView);

    imageViewer.setView($('#image-viewer'));
    imageViewer.setMainWidth('399px');
    imageViewer.setMainHeight('399px');

    imageViewer.setThumbnailsCount(5);
    imageViewer.enableThumbnailMouseOver();
    product.load(productId)
            .then(data => {
                data.product.productImages.forEach((productImage, index) => {
                    imageViewer.setThumbnailImage(index, productImage, productImage);
                });
                imageViewer.setImage(data.product.productImages[0], data.product.productImages[0]);
                product.loadProduct(data);
                return review.load(productId, reviewPage);
            })
            .then(()=>orderList.load(productId,loadOrderList))
})



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
    if(!data){
        $('#register-button').addEventListener('click',registerOrderModal);
        return;
    }

    $('#register-button').innerText = '신청내역';
    $('#register-button').classList.add('on');
    $('#register-button').classList.remove('expired');
    $('#register-button').classList.remove('full');
    $('#register-button').disabled = false;
    $('#register-button').addEventListener('click',openOrderListModal);


    if(!data.length){
        $('#orders-count').innerHTML = '신청자가 존재하지 않습니다';
        return;
    }

    $('#orders-count').innerHTML = '신청자 ' + data.length + '명';

//    const test = data.map(orderListTemplate).join('');
    $('#product-order-list').innerHTML = data.map(orderListTemplate).join('');
    const buttonList = $all('#product-order-list li .order-button');
    buttonList.forEach(button => {
        if(button.innerText.trim() == '나눔 중'){
            button.disabled = false;
            button.classList.remove('btn-secondary');
            button.classList.add('btn-primary');
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
        return response.json();
    })
    .then(({ data, errors }) => {
        if(data){
            product.loadProduct(data);
            closeOrderModal();
            return;
        }

        errors.forEach(({ message }) => {
            $('strong[name=invalid-register]').innerText = message;
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

    fetch(`/api/products/${productId}/reviews`, {
            method:'post',
            headers:{'content-type':'application/json'},
            credentials:'same-origin',
            body: JSON.stringify({
                comment,
                rating
            })
        }).then(response => {
            return response.json();
        })
        .then(({ data, errors }) => {
            if(data){
                closeReviewModal();
                $('strong[name=invalid-review]').style.visibility = 'hidden';
                review.loadReviews(data);
                return;
            }
            errors.forEach(({ message }) => {
                $('strong[name=invalid-review]').innerText = message;
            });
            $('strong[name=invalid-review]').style.visibility = 'visible';
        })
        .catch(error => {
            // TODOs : error handling...
        })
}

function registerOrderModal(){
    product.load(productId)
                .then(openOrderModal);
}

//$('.preview-thumbnail.nav.nav-tabs').addEventListener('click', moveToSelectedImage);

$('#register-share').addEventListener('submit',createOrder);
$('#close-register').addEventListener('click',closeOrderModal);

$('#open-modal').addEventListener('click',openReviewModal);
$('#register-review').addEventListener('click', createReview);
$('#close-modal').addEventListener('click',closeReviewModal);

$('#close-orders').addEventListener('click', closeOrderListModal);

$('#show-review-prev').addEventListener('click', (event) => {
    console.log(event.target);
    event.preventDefault();
    reviewPage -= 1;
    review.load(productId, reviewPage);
});

$('#show-review-next').addEventListener('click', (event) => {
    event.preventDefault();
    console.log("asdasd");
    reviewPage += 1;
    review.load(productId, reviewPage);
});