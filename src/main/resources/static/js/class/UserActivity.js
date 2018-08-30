import {$, $all} from '../lib/utils.js';
import {reviewTemplate} from '../template/DetailTemplate.js';
import {cardTemplate} from '../template/CardTemplate.js';
import {contentsTemplate, tabsTemplate} from '../template/MyPageTemplate.js';
import {errorPageTemplate} from '../template/ErrorPageTemplate.js';

//todo : 에러처리
class UserActivity {

    constructor() {
        this.userId;
        this.originData = {};
    }

    load(userId, callback) {
        this.userId = userId;

        fetch(`/api/users/${userId}`)
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                if (response.status === 404) {
                    this.setErrorPageTemplate();
                }
            })
            .then(({data}) => {
                this.originData = data;
                this.setMypageTemplate();
                this.setEditMode();
                this.showUserActivity();
                callback();
            })
            .catch(error => {
                console.log(error);
            });
    }

    setErrorPageTemplate() {
        $('.mypage-contents').insertAdjacentHTML('afterbegin', errorPageTemplate());
    }

    setMypageTemplate() {
        $('.mypage-contents').insertAdjacentHTML('afterbegin', contentsTemplate());
    }

    restore() {
        this.showUserActivity();
    }

    setEditMode() {
        $('.nav.nav-tabs.nav-fill').insertAdjacentHTML('afterbegin', tabsTemplate(this.originData.mine));
        $('#mypage-modify').hidden = !this.originData.mine;
    }

    showUserActivity() {
        $('#mypage-name').innerText = this.originData.name + ' (' + this.originData.email + ')';
        $('#mypage-activity .nav-item:nth-child(1) .fa').innerText = ' 나눔수 ' + this.originData.createdProductsCount;
        $('#mypage-activity .nav-item:nth-child(2) .fa').innerText = ' 받음수 ' + this.originData.receivedProductsCount;
        $('#mypage-activity .nav-item:nth-child(3) .fa').innerText = ' 남긴리뷰수 ' + this.originData.createdReviewsCount;
        $('#mypage-activity .nav-item:nth-child(4) .fa').innerText = ' 받은리뷰수 ' + this.originData.receivedReviewsCount;
        $('#mypage-activity .nav-item:nth-child(5) .fa').innerText = ' 평균평점 ' + this.originData.avgRating;

        $('#mypage-aboutme').firstElementChild.value = this.originData.aboutMe;
        $('#mypage-aboutme').firstElementChild.placeholder = '자기소개를 입력해보아요.';
        $('div.preview-pic .img-thumb img').src = this.originData.imageUrl ? this.originData.imageUrl : "/images/blank-profile.png";
    }

    save(formData, userId) {
        fetch(`/api/users/${userId}`, {
            method: 'post',
            credentials: 'same-origin',
            body: formData
        }).then(response => {
            return response.json();
        }).then(({data, errors}) => {
            if (data) {
                $all('.form-group .feedback').forEach(feedback => feedback.classList.remove('on'));
                return this.modifyInfo(data);
            }
            errors.forEach(({field, message}) => {
                const feedback = $(`*[name=${field}]`).closest('.form-group').querySelector('.feedback');
                feedback.innerText = message;
                feedback.classList.add('on');
            });

            this.restore();
        }).catch(error => {
            // todo error 처리
            this.restore();
        });
    }

    modifyInfo(data) {
        this.originData.aboutMe = data.aboutMe;
        this.originData.imageUrl = data.imageUrl;

        this.showUserActivity();
        this.refreshTab();
    }

    refreshTab() {
        $('.nav.nav-tabs.nav-fill .nav-item.nav-link.active').classList.remove('active');
        $(`.nav.nav-tabs.nav-fill .nav-item.nav-link:first-child`).classList.add('active');
        $('.tab-content.py-3.px-3.px-sm-0 .tab-pane.fade.show').classList.remove('show');
        $(`.tab-content.py-3.px-3.px-sm-0 .tab-pane.fade:first-child`).classList.add('show');

        this.loadCreatedProducts(this.userId, 0);
    }

    loadReceivedReviews(userId, page) {
        fetch(`/api/reviews/users/${userId}?page=${page}&size=5`)
            .then(response => {
                if (!response.ok) {
                    console.log('error 발생');
                }
                return response.json();
            })
            .then(({data}) => {
                const {content, first, last, totalElements} = data;

                $('#show-received-review-prev').disabled = first ? true : false;
                $('#show-received-review-next').disabled = last ? true : false;

                $('#received-reviews-count').innerHTML = '리뷰 ' + totalElements;
                $('#received-comments-list').innerHTML = content.map(reviewTemplate).join('');

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

    loadCreatedReviews(userId, page) {
        fetch(`/api/users/${userId}/reviews?page=${page}&size=5`)
            .then(response => {
                if (!response.ok) {
                    console.log('error 발생');
                }
                return response.json();
            })
            .then(({data}) => {
                const {content, first, last, totalElements} = data;

                $('#show-gived-review-prev').disabled = first ? true : false;
                $('#show-gived-review-next').disabled = last ? true : false;

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

                $('#show-received-product-prev').disabled = first ? true : false;
                $('#show-received-product-next').disabled = last ? true : false;
                $('#received-products').innerHTML = content.map(cardTemplate).join('');
                $all('.card.item').forEach(card => attachCardEventListener(card));
            })
            .catch(error => {
                // todo error 처리
                console.log(error);
            });
    }

    loadCreatedProducts(userId, page) {
        fetch(`/api/users/${userId}/products?page=${page}&size=5`)
            .then(response => {
                if (!response.ok) {
                    console.log('error 발생');
                }
                return response.json();
            })
            .then(({data}) => {
                const {content, first, last, totalElements} = data;

                $('#show-gived-product-prev').disabled = first ? true : false;
                $('#show-gived-product-next').disabled = last ? true : false;
                $('#gived-products').innerHTML = content.map(cardTemplate).join('');
                $all('.card.item').forEach(card => attachCardEventListener(card));
            })
            .catch(error => {
                // todo error 처리
                console.log(error);
            });
    }

}

function attachCardEventListener(card) {
    const productId = card.querySelector('input[name=product-id]').value;
    card && card.addEventListener('click', () => {
        location.href = `/products/${productId}`;
    });
}

export default UserActivity;