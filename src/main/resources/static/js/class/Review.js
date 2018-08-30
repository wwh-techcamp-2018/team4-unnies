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
                }
//                throw response.json();
            })
            .then(({ data }) => {
                this.loadReviews(data);
            })
            .catch(error => {
                // TODOs : error handling...
            });
    }

    loadReviews({ content, first, last, totalElements, pageable, totalPages }){
        if(first){
            $('#show-review-prev').disabled = true;
            $('#show-review-prev').classList.add('disabled');
        }else{
            $('#show-review-prev').disabled = false;
            $('#show-review-prev').classList.remve('disabled')
        }

        if(last){
            $('#show-review-next').disabled = true;
            $('#show-review-next').classList.add('disabled');
        }else{
            $('#show-review-next').disabled = false;
            $('#show-review-next').classList.remve('disabled')
        }


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