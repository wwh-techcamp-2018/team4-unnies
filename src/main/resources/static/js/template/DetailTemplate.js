export function reviewTemplate({writer, imgUrl, comment, product, rating}){
           return  `<li class='product-comment'>
                       <div class='product-comment-wrap'>
                           <div class='product-comment-profile'>
                               <img alt='${writer.name}-profile'
                                    src='${imgUrl}'>
                           </div>
                           <div class='product-comment-nickname'>
                               <a href='#'>${writer.name}</a>
                           </div>

                           <div class='product-comment-region-name'>${writer.address}</div>
                           <p class='product-comment-text'>${comment}</p>
                           <div>
                             <div class='product-comment-address'>
                               ${product.name} (${product.shareDateTime}) 후기
                            </div>
                            <div id='product-comment-rating'>
                                ${ratingTemplate(Number(rating))}
                            </div>
                           </div>
                       </div>
                   </li>`;
}

export function ratingTemplate(rating){
    let html = '';

    for(let i = 0; i < 5; i++){
        i < rating ? html += `<span class='fa fa-star checked'></span>`: html += `<span class='fa fa-star'></span>`;
    }

    return html;
}


