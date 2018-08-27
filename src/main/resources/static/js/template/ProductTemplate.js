import { ratingTemplate } from "./DetailTemplate.js";

export function productTemplate({ productId, productTitle, productImgUrl, distanceMeter, ownerName, ownerImgUrl, ownerRating, orderCnt, maxParticipant, expireDateTime, price }) {
    return `
    <div class="card">
        <input type="hidden" name="product-id" value="${productId}">
        <div class="card-header">
            <img src="${productImgUrl}" alt="${productTitle}">
            <span class="badge badge-info">${distanceMeter} m</span>
        </div>
        <div class="card-body">
            <h5 class="card-title font-weight-bold">${productTitle}</h5>
            <div class="container-fluid mt-2 chef">
                <div class="row">
                    <div class="chef-img">
                        ${ ownerImgUrl ? `<img src="${ownerImgUrl} alt=${ownerName}">` : `` }
                    </div>
                    <div class="col text-right">
                        <p class="card-text">${ownerName}</p>
                        <dl class="rating-app text-right">
                            <dt></dt>
                            <dd class="rating" value="${Number(ownerRating)}">
                                ${ratingTemplate(Number(ownerRating))}
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
                <dd>${expireDateTime} <span class="text-muted">까지</span></dd>
            </dl>
            <h4 class="card-subtitle text-right font-weight-bold price">${price} <span class="text-muted">원</span></h4>
        </div>
    </div>`;
}