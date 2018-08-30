import { translateDateTime } from '../lib/Translator.js';
import { ratingTemplate } from './DetailTemplate.js';
import { numberWithCommas } from '../lib/utils.js'

export function productTemplate({ distanceMeter, productId, productTitle, productName, productImgUrl, ownerName, ownerImgUrl, ownerRating, orderCnt, maxParticipant, expireDateTime, price }) {
    const formattedExpireDateTime = translateDateTime(expireDateTime);
    const numberOwnerRating = Number(ownerRating);

    return `
    <div class="card">
        <input type="hidden" name="product-id" value="${productId}">
        <div class="card-header">
            <img src="${ productImgUrl ? `${productImgUrl}` : `#` }" alt="${productName}">
            <span class="badge badge-info">${distanceMeter} m</span>
        </div>
        <div class="card-body">
            <h5 class="card-title font-weight-bold truncate">${productName}</h5>
            <div class="container-fluid mt-2 chef">
                <div class="row">
                    <div class="chef-img-container">
                        <img src="${ ownerImgUrl ? `${ownerImgUrl}` : `/images/blank-profile.png` }" alt=${ownerName}>
                    </div>
                    <div class="col text-right">
                        <p class="card-text">${ownerName}</p>
                        <dl class="rating-app text-right">
                            <dt></dt>
                            <dd class="rating" value="${numberOwnerRating}">
                                ${ratingTemplate(numberOwnerRating)}
                            </dd>
                        </dl>
                    </div>
                </div>
            </div>
            <dl class="row">
                <dt>모집인원</dt>
                <dd>${orderCnt} / ${maxParticipant}</dd>
            </dl>
            <dl class="row">
                <dt>모집기간</dt>
                <dd>${formattedExpireDateTime} <span class="text-muted">까지</span></dd>
            </dl>
            <h4 class="card-subtitle text-right font-weight-bold price">${numberWithCommas(price)} <span class="text-muted">원</span></h4>
        </div>
    </div>`;
}