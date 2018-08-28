import {$} from '../lib/utils.js';
import {reviewTemplate} from '../template/DetailTemplate.js';

class Review {
    load(productId, page) {
        fetch(`/api/products/${productId}/reviews?page=${page}&size=5`)
            .then(response => {
                if (!response.ok) {
                    console.log('error 발생');
                }
                return response.json();
            })
            .then(({data}) => {
                const {content, first, last, totalElements} = data;

                $('#show-review-prev').style.visibility = first ? 'hidden' : 'visible';
                $('#show-review-next').style.visibility = last ? 'hidden' : 'visible';

                $('#reviews-count').innerHTML = '리뷰 ' + totalElements;
                $('#product-comments-list').innerHTML = content.map(reviewTemplate).join('');
                content.map(({comment}, index) => {
                    tui.Editor.factory({
                        el: $(`li.product-comment:nth-child(${index + 1}) .product-comment-text`),
                        height: 'auto',
                        viewer: true,
                        initialValue: comment
                    });
                });
            })
            .catch(error => {
                // todo error 처리
                console.log(error);
            });
    }
}

export default Review;