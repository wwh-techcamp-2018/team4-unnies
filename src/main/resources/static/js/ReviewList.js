import { $, $all } from './lib/utils.js';
import {reviewTemplate} from './template/DetailTemplate.js';

class ReviewList {
    load(productId,page) {
        fetch(`/api/products/${productId}/reviews?page=${page}&size=5`)
        .then(response => {
                if (response.ok) {
                    return response.json();
                }
            }).then(({ data }) => {

                if(data.length == 0){
                    $('#show-review-next').style.visibility = 'hidden';
                    alert("다음 리뷰가 존재하지 않습니다.");
                    return;
                }

                $('#reviews-count').innerHTML = '리뷰 '+data.length;
                $('#product-comments-list').innerHTML = data.map(review => reviewTemplate(review)).join('');
                $('#show-review-prev').style.visibility = 'display';

            })
            .catch(error => {
                // todo error 처리
                console.log(error);
            });
    }
}

export default ReviewList;