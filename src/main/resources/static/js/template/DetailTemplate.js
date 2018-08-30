import {translateDateTime} from '../lib/Translator.js';
import {numberWithCommas} from '../lib/utils.js';

export function reviewTemplate({writer, product, rating}) {
    const writerId = writer.id;
    const writerName = writer.name;
    const writerImageUrl = writer.imageUrl ? writer.imageUrl : "/images/blank-profile.png";
    const writerAddress = writer.address

    const productName = product.name;
    const productShareDateTime = translateDateTime(product.shareDateTime);
    const productRating = rating;

    return `<li class='product-comment'>
               <div class='product-comment-wrap'>
                   <div class='product-comment-profile'>
                   <img alt='${writerName}-profile' src='${writerImageUrl}'>
                   </div>
                   <div class='product-comment-nickname'>
                       <a href='/users/${writerId}'>${writerName}</a>
                   </div>
    
                   <div class='product-comment-region-name'>${writerAddress}</div>
                   <div class='product-comment-text'></div>
                   <div>
                     <div class='product-comment-address'>
                       ${productName} (${productShareDateTime}) 후기
                    </div>
                    <div id='product-comment-rating'>
                        ${ratingTemplate(Number(productRating))}
                    </div>
                   </div>
               </div>
           </li>`;
}

export function ratingTemplate(rating) {
    let html = '';
    for (let i = 0; i < 5; i++) {
        i < rating ? html += `<span class='fa fa-star checked'></span>` : html += `<span class='fa fa-star'></span>`;
    }
    return html;
}



export function registerTemplate({ product }) {
    let price = product.price ? `<strong>${product.price}</strong><span class="unit">원&nbsp;</span>`: '<strong>무료 나눔</strong>';
           return `<div class="details">
                    <h1 class="share-page">나눔 신청 페이지</h1>
                    <dl class="product-info">
                        <dt class="cook">요리사</dt>
                        <dd>${product.name}</dd>
                        <dt class="participate-number">모집현황</dt>
                        <dd>${product.ordersSize} / ${product.maxParticipant}</dd>
                        <dt class="participate-date">모집기간</dt>
                        <dd>${translateDateTime(product.expireDateTime)}</dd>
                        <dt class="give-time">나눔시각</dt>
                        <dd>${translateDateTime(product.shareDateTime)}</dd>
                        <dt class="give-place">나눔장소</dt>
                        <dd>${product.address} ${product.addressDetail}</dd>
                        <dt class="give-plate">나눔용기</dt>
                        <dd>${product.isBowlNeeded === true ? '용기 지참' : '용기 제공'}</dd>
                        <dd class="price">
                        ${numberWithCommas(price)}
                    </dl>
                    <div class="form-check">
                        <label class="form-check-label" for="radio-riders">
                            <input type="radio" class="form-check-input with-gap" id="radio-riders"
                                   name="groupOfRadioGap" required>
                            배민라이더스</label>
                        <label class="form-check-label" for="radio-self">
                            <input type="radio" class="form-check-input with-gap" id="radio-self"
                                   name="groupOfRadioGap">
                            직접방문</label>
                    </div>
                </div>`;

}

export function orderListTemplate(data){
    const deliveryType = data.deliveryType === 'BAEMIN_RIDER' ? '배민라이더스' : '직접수령';
    const writerImageUrl = data.participant.imageUrl ? data.participant.imageUrl : "/images/blank-profile.png";
return `<div style="display:inline-block; text-align:center; margin-left:65px; margin-bottom:30px;">
        <div>
           <div>
               <div>
                   <img class="chef-img" src="${writerImageUrl}" style="width:90px;height:90px;border:dotted 1px lightgray;border-radius:3px;">
                   <div>
                       <a href="/users/${data.participant.id}">${data.participant.name}</a>

                   </div>
                          <span class="badge badge-danger">${deliveryType}</span>

               </div>
           </div>
           <span class="order-phone-number">${data.participant.phoneNumber}</span>
       </div>
       ${shareButtonTemplate(data.completeSharing, data.sharedDateTime, data.id)}
   </div>`;
}

function shareButtonTemplate(completeSharing, sharedDateTime, id){
    let html = '';
    if(completeSharing){
        html = `<button type='button' class='order-button btn btn-secondary' data-order-id=${id} disabled='true'>
                나눔 완료
            </button>`;
    }else{
        html = `<button type='button' class='order-button btn btn-secondary' data-order-id=${id} disabled='${!sharedDateTime}'>
                ${sharedDateTime ? '나눔 중' : '나눔 전'}
            </button>`;
    }
    return html;
}

export function detailContentsTemplate(){
return `<div class="container">
    <div class="card">
        <div class="container-fliud">
            <div class="wrapper row">
                <div class="col-6">
                   <div id="image-viewer">

                   </div>
                </div>

                <div class="details col-6">
                    <h1 class="product-name"></h1>
                    <div class="product-title">
                        <p id="product-title"><strong></strong></p>
                    </div>

                    <dl class="product-info">
                        <dt class="cook">요리사</dt>
                        <dd id="cook"></dd>
                        <dt class="participate-number">모집현황</dt>
                        <dd id="participate-number"></dd>
                        <dt class="participate-date">모집기간</dt>
                        <dd id="participate-date"></dd>
                        <dt class="give-time">나눔시각</dt>
                        <dd id="give-time"></dd>
                        <dt class="give-place">나눔장소</dt>
                        <dd id="give-place"></dd>
                        <dt class="give-plate">나눔용기</dt>
                        <dd id="give-plate"></dd>
                        <dd class="price">
                        <strong id="price"></strong><span class="unit"> 원 &nbsp;</span>
                    </dl>

                    <div class="line-top" style="margin-top:12px; margin-bottom:12px;">
                    </div>
                    <button class="add-to-cart btn btn-default" type="button" id="register-button"
                            disabled="true"></button>

                </div>
            </div>
        </div>
    </div>

    <div class="row card">
        <div class="col-12">
            <section id="user-profile">
                <!--user 마이페이지로 이동하는 부분-->
                <a id="user-profile-link" href="#">
                    <div class="user-space">
                        <div>
                            <div id="user-profile-image">
                                <img id="user-image" alt=""
                                     src="">
                            </div>
                            <div id="user-profile-left">
                                <div id="nickname"></div>
                                <div id="region-name"></div>
                            </div>
                        </div>

                        <div id="user-profile-right">
                            <dl id="rating-app">
                                <dt>
                                </dt>
                                <dd id="user-rating">

                                </dd>
                            </dl>
                        </div>
                    </div>
                </a></section>
        </div>
        <div class="col-12">
            <div style="width:100%;border-top:1px solid #e9ecef">

                <section id="product-description" style="margin-bottom : 20px;">
                    <div class="status"></div>
                    <div style="display:flex;">
                            <h1 id="product-name"></h1>
                            <p id="product-category"></p>
                    </div>
                    <time id="product-create-time"></time>
                    <p class="product-content-title">
                    </p>
                    <div id="product-detail">

                    </div>
                </section>

                <div style="width:100%;border-top:1px solid #e9ecef">

                    <section id="product-comments">
                        <div class="review-and-register">
                            <div id="reviews-count">
                            </div>
                            <div id="review-register">
                                <a href="#" id="open-modal">리뷰 등록</a>
                            </div>

                        </div>

                        <ul id="product-comments-list">
                        </ul>
                        <div style="text-align:center;">
                            <button id="show-review-prev"  class="remove-button-shape" disabled='true'> 이전 </button>
                            &nbsp;&nbsp;&nbsp;
                            &nbsp;&nbsp;&nbsp;
                            <button id="show-review-next"  class="remove-button-shape" disabled='true'> 다음 </button>
                        </div>
                    </section>

                    <section id="product-order">

                    </section>


                    <div class="overlay is-hidden" id="overlay">
                            <div class="modal-content">
                                <span class="button-close" id="close-modal">&times;</span>
                                <h4>리뷰 등록</h4>
                                <div class="form-group line">
                                    <textarea class="form-control" id="comment" rows="5" placeholder="리뷰를 등록해주세요"
                                              style="margin-top:10px;" required></textarea>
                                </div>

                                <form class="rating" id="product1">
                                    <button type="submit" class="star" data-star="1">
                                        &#9733;
                                        <span class="screen-reader">1 Star</span>
                                    </button>

                                    <button type="submit" class="star" data-star="2">
                                        &#9733;
                                        <span class="screen-reader">2 Stars</span>
                                    </button>

                                    <button type="submit" class="star" data-star="3">
                                        &#9733;
                                        <span class="screen-reader">3 Stars</span>
                                    </button>

                                    <button type="submit" class="star" data-star="4">
                                        &#9733;
                                        <span class="screen-reader">4 Stars</span>
                                    </button>

                                    <button type="submit" class="star" data-star="5">
                                        &#9733;
                                        <span class="screen-reader">5 Stars</span>
                                    </button>
                                </form>
                                <div style="text-align:center;">
                                    <strong name="invalid-review" style="color:red; visibility:hidden;"></strong>
                                </div>
                                <button type="submit" id="register-review" class="btn btn-info" style="float:right; color:#fff;">등록
                                </button>
                            </div>
                    </div>


                    <div class="overlay is-hidden" id="register-overlay">
                        <div class="modal-content">
                            <span class="button-close" id="close-register">&times;</span>
                            <form action="post" id="register-share">
                                <div id="register-data">
                                </div>

                                <div class="action" style="text-align: center; margin-top: 20px;">
                                    <strong name="invalid-register" style="color:red; visibility:hidden; margin-right:-80px;"></strong>
                                    <button type="submit" id="register-order" class="btn btn-info" style="float:right;">
                                        나눔신청
                                    </button>
                                </div>

                            </form>
                        </div>
                    </div>

                    <div class="overlay is-hidden" id="orders-overlay">
                        <div class="modal-content">
                            <span class="button-close" id="close-orders">&times;</span>
                            <div id="orders-data">
                                <div id="orders-count">

                                </div>
                                <div id="product-order-list">

                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
`;
}