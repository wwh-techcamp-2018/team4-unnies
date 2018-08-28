import {$} from '../lib/utils.js';
import {reviewTemplate} from '../template/DetailTemplate.js';

class Review {
    load(productId, page) {
        fetch(`/api/products/${productId}/reviews?page=${page}&size=5`)
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                if (response.status === 404){
                    this.loadReviewError();
                    return;
//                    location.href = window.location.origin +'/error-404';
                }
//                throw response.json();
            })
            .then(({ data }) => {
                this.loadReviewList(data);
            })
            .catch(error => {
                // TODOs : error handling...
            });
    }

    loadReviewList({ content, first, last, totalElements }){
        $('#show-review-prev').style.visibility = first ?  'hidden' :  'visible';
        $('#show-review-next').style.visibility = last ? 'hidden' : 'visible';

        $('#reviews-count').innerHTML = '리뷰 '+totalElements;
        $('#product-comments-list').innerHTML = content.map(reviewTemplate).join('');
        content.map(({ comment }, index) => {
            tui.Editor.factory({
                el: $(`li.product-comment:nth-child(${index + 1}) .product-comment-text`),
                height: 'auto',
                viewer: true,
                initialValue: comment
            });
        });
    }

    loadReviewError(){
        $('#show-review-prev').style.visibility = 'hidden';
        $('#show-review-next').style.visibility = 'hidden';
        $('#open-modal').style.visibility = 'hidden';
    }
}

export default Review;