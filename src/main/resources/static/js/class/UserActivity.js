import {$, $all} from '../lib/utils.js';
import {reviewTemplate} from '../template/DetailTemplate.js';
import {cardTemplate} from "../template/CardTemplate.js";

//todo : 에러처리
class UserActivity {

    constructor() {
        this.originData = {};
    }

    load(userId) {
        fetch(`/api/users/${userId}`)
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
            })
            .then(({data}) => {
                this.originData = data;
                // this.hideUserInfo();
                this.showUserActivity();
            })
            .catch(error => {
                console.log(error);
            });
    }

    restore() {
        this.showUserActivity();
    }

    hideUserInfo() {
        console.log("this.originData.isMine : ", this.originData.mine);
        if (this.originData.mine === false) {
            [...$all('.hide-private-info')].forEach(element => {
                console.log("element : ", element);
                element.hidden = true;
            });
        }
    }

    showUserActivity() {

        $('#mypage-name').innerText = this.originData.name + ' (' + this.originData.email + ')';
        $('#mypage-activity .col:nth-child(1) .fa').innerText = '나눔수 ' + this.originData.orderFromCount;
        $('#mypage-activity .col:nth-child(2) .fa').innerText = '받음수 ' + this.originData.orderToCount;
        $('#mypage-activity .col:nth-child(3) .fa').innerText = '남긴리뷰수 ' + this.originData.reviewToCount;
        $('#mypage-activity .col:nth-child(4) .fa').innerText = '받은리뷰수 ' + this.originData.reviewFromCount;
        $('#mypage-activity .col:nth-child(5) .fa').innerText = '평균평점 ' + this.originData.avgRating;

        $('#mypage-aboutme').firstElementChild.value = this.originData.aboutMe;
        $('#mypage-aboutme').firstElementChild.placeholder = '자기소개를 입력해보아요.';
        $('#mypage-image > .user-image').src = this.originData.imageUrl;
    }

    save(formData, userId) {
        fetch(`/api/users/${userId}`, {
            method: 'post',
            credentials: 'same-origin',
            body: formData
        }).then(response => {
            if (response.ok) {
                return response.json();
            }
        }).then(data => {
            if (data) {
                return this.modifyInfo(data);
            }
            throw errors;
        }).catch(({errors}) => {
            // console.log("errors : ", errors);
            //
            // errors.forEach(({field, message}) => {
            //     alert(message);
            //     //const feedback = $(`*[name=${field}]`).closest('.form-group').querySelector('.feedback');
            //     //feedback.innerText = message;
            //     //feedback.classList.add('on');
            // });
            this.restore();
        });
    }

    modifyInfo(data) {
        this.originData.name = data.name;
        this.originData.aboutMe = data.aboutMe;
        this.originData.imageUrl = data.imageUrl;

        this.showUserActivity();
    }

    loadReceivedReviews(userId, page) {
        fetch(`/api/users/${userId}/reviews?page=${page}&size=5`)
            .then(response => {
                if (!response.ok) {
                    console.log('error 발생');
                }
                return response.json();
            })
            .then(({data}) => {
                const {content, first, last, totalElements} = data;
                console.log("data : ", data.content);

                $('#show-received-review-prev').style.visibility = first ? 'hidden' : 'visible';
                $('#show-received-review-next').style.visibility = last ? 'hidden' : 'visible';

                $('#received-reviews-count').innerHTML = '리뷰 ' + totalElements;
                $('#received-comments-list').innerHTML = content.map(reviewTemplate).join('');

                console.log("@@@content : ", content);

                content.map(({comment}, index) => {
                    tui.Editor.factory({
                        el: $(`#nav-get-review li.product-comment:nth-child(${index + 1}) .product-comment-text`),
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

    loadGiveReviews(userId, page) {
        fetch(`/api/reviews/users/${userId}?page=${page}&size=5`)
            .then(response => {
                if (!response.ok) {
                    console.log('error 발생');
                }
                return response.json();
            })
            .then(({data}) => {
                const {content, first, last, totalElements} = data;

                $('#show-gived-review-prev').style.visibility = first ? 'hidden' : 'visible';
                $('#show-gived-review-next').style.visibility = last ? 'hidden' : 'visible';

                $('#gived-reviews-count').innerHTML = '리뷰 ' + totalElements;
                $('#gived-comments-list').innerHTML = content.map(reviewTemplate).join('');

                content.map(({comment}, index) => {
                    tui.Editor.factory({
                        el: $(`#nav-give-review li.product-comment:nth-child(${index + 1}) .product-comment-text`),
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

    loadReceivedProducts(userId, page) {
        fetch(`/api/orders/users/${userId}?page=${page}&size=5`)
            .then(response => {
                if (!response.ok) {
                    console.log('error 발생');
                }
                return response.json();
            })
            .then(({data}) => {
                const {content, first, last, totalElements} = data;

                $('#show-received-product-prev').style.visibility = first ? 'hidden' : 'visible';
                $('#show-received-product-next').style.visibility = last ? 'hidden' : 'visible';

                $('#received-products').innerHTML = content.map(cardTemplate).join('');
            })
            .catch(error => {
                // todo error 처리
                console.log(error);
            });
    }

    loadGivedProducts(userId, page) {
        fetch(`/api/users/${userId}/products?page=${page}&size=5`)
            .then(response => {
                if (!response.ok) {
                    console.log('error 발생');
                }
                return response.json();
            })
            .then(({data}) => {
                const {content, first, last, totalElements} = data;

                $('#show-gived-product-prev').style.visibility = first ? 'hidden' : 'visible';
                $('#show-gived-product-next').style.visibility = last ? 'hidden' : 'visible';


                $('#gived-products').innerHTML = content.map(cardTemplate).join('');
            })
            .catch(error => {
                // todo error 처리
                console.log(error);
            });
    }

}

export default UserActivity;