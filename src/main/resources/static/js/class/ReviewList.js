import { $ } from '../lib/utils.js';
import {reviewTemplate} from '../template/DetailTemplate.js';

class ReviewList {
    load(productId,page) {
        fetch(`/api/products/${productId}/reviews?page=${page}&size=5`)
            .then(response => {
                if (!response.ok) {
                    console.log('error 발생');
                }
                return response.json();
            })
            .then(({ data }) => {
                const { content, first, last, totalElements } = data;

                $('#show-review-prev').style.visibility = first ?  'hidden' :  'visible';
                $('#show-review-next').style.visibility = last ? 'hidden' : 'visible';

                $('#reviews-count').innerHTML = '리뷰 '+totalElements;
                $('#product-comments-list').innerHTML = content.map(reviewTemplate).join('');
            })
            .catch(error => {
                // todo error 처리
                console.log(error);
            });
    }
}

export default ReviewList;