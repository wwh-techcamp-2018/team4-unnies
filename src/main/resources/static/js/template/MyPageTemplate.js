export function contentsTemplate() {
    return `
    <div class="card mb-5">
        <form id="mypage-form" enctype="multipart/form-data">
            <div class="form-group">
                <div class="preview-pic">
                    <div class="img-thumb border" id="mypage-image">
                        <img src="">
                        <div id="mypage-upload-image" class="upload" hidden>+</div>
                    </div>
                    <span class="feedback"></span>
                </div>
            </div>
            
            <div class="form-group">
                <h6 id="mypage-name"></h6>
                <hr>
                <nav class="nav nav-fill justify-content-center" id="mypage-activity">
                    <span class="nav-item"><i class="fa fa-heart"></i></span>
                    <span class="nav-item"><i class="fa fa-heart-o"></i></span>
                    <span class="nav-item"><i class="fa fa-comment"></i></span>
                    <span class="nav-item"><i class="fa fa-comment-o"></i></span>
                    <span class="nav-item"><i class="fa fa-star"></i></span>
                </nav>
                
                <hr>
                <div id="mypage-aboutme">
                    <textarea class="form-control off toggle-mode" rows="3" name="aboutMe"
                              placeholder="자기소개를 입력해보아요." readonly></textarea>
                    <span class="feedback"></span>
                </div>

                <div id="mypage-modify" hidden>
                    <button hidden type="button" class="btn btn-danger info-cancel">취소</button>
                    <button hidden type="button" class="btn btn-primary ml-2 info-change">변경</button>
                    <button type="button" class="btn btn-primary">프로필 수정</button>
                </div>
            </div>
        </form>
    </div>

    <section>
        <!--<div class="row">-->
            <!--<div class="col-12 nav-template-area">-->
            <div class="nav-template-area">
                <nav>
                    <div class="nav nav-tabs nav-fill" id="nav-tab" role="tablist">

                    </div>
                </nav>
                <div class="tab-content  px-5" id="nav-tabContent">
                    <div class="tab-pane fade show active" id="nav-give" role="tabpanel"
                         aria-labelledby="nav-give-tab">
                        <!--<div class="py-5">-->
                            <!--<div class="container">-->
                                <div class="row hidden-md-up pl-4" id="gived-products">
                                </div>
                                <a href="#" id="show-gived-product-prev">이전 보기</a>
                                <a href="#" id="show-gived-product-next">다음 보기</a>
                            <!--</div>-->
                        <!--</div>-->
                    </div>
                    <div class="tab-pane fade" id="nav-take" role="tabpanel" aria-labelledby="nav-take-tab">
                        <!--<div class="py-5">-->
                            <!--<div class="container">-->
                                <div class="row hidden-md-up pl-4" id="received-products">
                                </div>
                                <a href="#" id="show-received-product-prev">이전 보기</a>
                                <a href="#" id="show-received-product-next">다음 보기</a>
                            <!--</div>-->
                        <!--</div>-->
                    </div>
                    <div class="tab-pane fade" id="nav-give-review" role="tabpanel"
                         aria-labelledby="nav-give-review-tab">
                        <section id="gived-comments">
                            <div class="review-and-register">
                                <div id="gived-reviews-count">
                                </div>
                            </div>
                            <ul id="gived-comments-list">
                            </ul>
                            <a href="#" id="show-gived-review-prev">이전 보기</a>
                            <a href="#" id="show-gived-review-next">다음 보기</a>
                        </section>
                    </div>
                    <div class="tab-pane fade" id="nav-get-review" role="tabpanel"
                         aria-labelledby="nav-get-review-tab">
                        <section id="received-comments">
                            <div class="review-and-register">
                                <div id="received-reviews-count">
                                </div>
                            </div>
                            <ul id="received-comments-list">
                            </ul>
                            <a href="#" id="show-received-review-prev">이전 보기</a>
                            <a href="#" id="show-received-review-next">다음 보기</a>
                        </section>
                    </div>
                </div>

            </div>
        <!--</div>-->
    </section>
    `;
}

export function tabsTemplate(isMine) {
    return `
        <a class="nav-item nav-link active" id="nav-give-tab" data-toggle="tab" href="#nav-give" role="tab"
           aria-controls="nav-give" aria-selected="true">나눔</a>
        <a class="nav-item nav-link hide-private-info" id="nav-take-tab" data-toggle="tab" href="#nav-take"
           role="tab" aria-controls="nav-take" aria-selected="false" ${isMine ? "" : 'hidden'} >받음</a>
        <a class="nav-item nav-link hide-private-info" id="nav-give-review-tab" data-toggle="tab"
           href="#nav-give-review" role="tab" aria-controls="nav-give-review" aria-selected="false" ${isMine ? "" : 'hidden'}>남긴
            리뷰</a>
        <a class="nav-item nav-link" id="nav-get-review-tab" data-toggle="tab"
           href="#nav-get-review" role="tab" aria-controls="nav-get-review" aria-selected="false">받은
            리뷰</a>
    `;
}