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


