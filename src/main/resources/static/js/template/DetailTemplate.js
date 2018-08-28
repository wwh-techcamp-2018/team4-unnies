import {translateDateTime} from '../lib/Translator.js';

export function reviewTemplate({writer, product, rating}) {
    const writerId = writer.id;
    const writerName = writer.name;
    const writerImageUrl = writer.imageUrl ? writer.imageUrl : "/images/blank-profile.png";
    const writerAddress = writer.address;

    const productName = product.name;
    const productShareDateTime = translateDateTime(product.shareDateTime);
    const productRating = rating;

    return `<li class='product-comment'>
               <div class='product-comment-wrap'>
                   <div class='product-comment-profile'>
                   <img alt='${writerName}-profile' src='${writerImageUrl}' style="width:60px;height:60px;border:dotted 1px lightgray;border-radius:3px;">
                   </div>
                   <div class='product-comment-nickname'>
                       <a href='${writerId}'>${writerName}</a>
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



export function orderTemplate({ name, owner, expireDateTime, shareDateTime, bowlNeeded, price}) {
    return `<h3></h3>
                    <div>
                        <span><strong>요리명 : ${name}</strong></span>
                    </div>
                    <h4><strong>요리사 : ${owner.name}</strong><span></span></h4>
                    </h4>
                    <h4><strong>모집기간 : ${expireDateTime}</strong><span></span>

                    </h4>
                    <h4><strong>나눔시각 : ${shareDateTime}</strong><span
                            ><strong></strong></span></h4>
                    <h4><strong>나눔장소 : ${owner.address} ${owner.addressDetail}</strong><span></span></h4>
                    <h4><strong>나눔용기 : ${bowlNeeded === true ? '용기 지참' : '용기 제공'}</strong><span></span>
                    <h4><strong>나눔가격 : ${price}</strong><span></span>`;
}

export function orderListTemplate(data){
    console.log(data);
    const shareDateTime = data.product.shareDateTime.replace('T',' ');
    const isBeforeShareTime = Date.parse(shareDateTime) > Date.now();
    const deliveryType = data.deliveryType === 'BAEMIN_RIDER' ? '배민라이더스' : '직접수령';

    return `
        <li>
            ${data.participant.name}
            ${data.participant.phoneNumber}
            ${deliveryType}
            <input type='hidden' value='${data.id}'/>
            ${shareCompleteButtonTemplate(data.completeSharing, isBeforeShareTime)}
        </li>
    `;
}

function shareCompleteButtonTemplate(completeSharing, isBeforeShareTime){
    let html = '';
    if(completeSharing){
        html = `<button type='button' class='order-button' disabled='true'>
                나눔 완료
            </button>`;
    }else{
        html = `<button type='button' class='order-button' disabled='false'>
                ${isBeforeShareTime ? '나눔 시간 전' : '나눔 중'}
                <input type='hidden' value=''
            </button>`;
    }
    return html;
}
