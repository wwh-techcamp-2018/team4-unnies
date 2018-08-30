import {translateStatus, translateDateTime} from '../lib/Translator.js';
import {ratingTemplate} from "../template/DetailTemplate.js";
import {numberWithCommas} from '../lib/utils.js';

export function cardTemplate({product, orderCount, status, ownerRating}) {
    //TODO : 클래스 처리
    const ownerId = product.owner.id;
    const ownerName = product.owner.name;
    const ownerImageUrl = product.owner.imageUrl ? product.owner.imageUrl : "/images/blank-profile.png";

    const productId = product.id;
    const productName = product.name;
    const productImageUrl = product.productImages.length && product.productImages[0];
    const productStatus = translateStatus(product.status);
    const productOrderCount = orderCount ? orderCount : 0;
    const productOwnerRating = ownerRating;
    const productMaxParticipant = product.maxParticipant;
    const productExpireDateTime = translateDateTime(product.expireDateTime);
    const productPrice = product.price;

    return `
    <div class="card item">
        <input type="hidden" name="product-id" value="${productId}">
        <div class="card-header">
            <img src="${productImageUrl}" alt="${productName}">
        </div>
        <div class="card-body">
            <h5 class="card-title font-weight-bold">${productName}</h5>
            <div class="container-fluid mt-2 chef">
                <div class="row">
                    <div class="chef-img-container">
                        <img src="${ownerImageUrl}" alt=${ownerName}>
                    </div>
                    <div class="col text-right">
                        <p class="card-text">${ownerName}</p>
                        <span class="badge badge-danger">${productStatus}</span>
                        <dl class="rating-app text-right">
                            <dt></dt>
                            <dd class="rating" value="${productOwnerRating}">
                                ${ratingTemplate(productOwnerRating)}
                            </dd>
                        </dl>
                    </div>
                </div>
            </div>
            <dl class="row">
                <dt>모집인원</dt>
                <dd>${productOrderCount} / ${productMaxParticipant}</dd>
            </dl>
            <dl class="row">
                <dt>모집기간</dt>
                <dd>${productExpireDateTime} <span class="text-muted">까지</span></dd>
            </dl>
            <h4 class="card-subtitle text-right font-weight-bold price">${numberWithCommas(productPrice)} <span class="text-muted">원</span></h4>
        </div>
    </div>`;

    //
    // return `
    //     <div class="card" id="mypage-card">
    //     <a href="../products/${productId}">
    //     <div class="card-header">
    //         <img src="${productImageUrl}" alt="${productName}">
    //     </div>
    //     <div class="card-body">
    //         <h5 class="card-title font-weight-bold">${productName}</h5>
    //         <!-- chef -->
    //         <div class="container-fluid mt-2">
    //             <div class="row">
    //                 <img class="chef-img" src="${ownerImageUrl}" style="width:80px;height:80px;border:dotted 1px lightgray;border-radius:3px;">
    //                 <div class="col text-right">
    //                     <a href="${ownerId}">${ownerName}</a>
    //                     <span class="badge badge-danger">${productStatus}</span>
    //                 </div>
    //             </div>
    //             <dl class="rating-app text-right">
    //                 <dt></dt>
    //                 <dd class="rating">
    //                    ${ratingTemplate(Number(productOwnerRating))}
    //                 </dd>
    //             </dl>
    //         </div>
    //         <dl class="row">
    //             <dt class="col">모집인원</dt>
    //             <dd class="col">${productOrderCount} / ${productMaxParticipant}</dd>
    //         </dl>
    //         <dl class="row">
    //             <dt class="col">모집기간</dt>
    //             <dd class="col">${productExpireDateTime} <span class="text-muted">까지</span></dd>
    //         </dl>
    //         <h4 class="card-subtitle text-right font-weight-bold">${productPrice} <span class="text-muted">원</span></h4>
    //     </div>
    // </div>`;


}











