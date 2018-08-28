export function contentsTemplate() {
    return `
    <h1 class="form-upload-heading mt-3 mb-5">마이 페이지</h1>
    <div class="card">
        <div class="container-fluid">
            <form class="form-horizontal" id="mypage-form" enctype="multipart/form-data">
                <div class="wrapper row">
                    <div class="form-group">
                        <div class="col-4">
                            <div class="preview-pic">
                                <div class="img-thumb border" id="mypage-image">
                                    <button id="mypage-upload-image" type="button" class="btn btn-secondary centered" hidden>이미지변경</button>
                                    <img src="">
                                </div>
                                <span class="feedback"></span>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col">
                        <h6 id="mypage-name"></h6>
                        <hr>
                        <div class="row pl-2" id="mypage-activity">
                            <div class="col"><i class="fa fa-heart mr-1"></i></div>
                            <div class="col"><i class="fa fa-heart-o mr-1"></i></div>
                            <div class="col"><i class="fa fa-comment mr-1"></i></div>
                            <div class="col"><i class="fa fa-comment-o mr-1"></i></div>
                            <div class="col"><i class="fa fa-star mr-1"></i></div>
                        </div>
                        <hr>
                        <div class="form-group" id="mypage-aboutme">
                                <textarea class="form-control off toggle-mode" rows="3" name="aboutMe"
                                          placeholder="자기소개를 입력해보아요." readonly></textarea>
                            <span class="feedback"></span>
                        </div>

                        <div id="mypage-modify" class="hide-private-info" hidden>
                            <button hidden type="button" class="btn btn-primary float-right ml-2 info-change">변경</button>
                            <button hidden type="button" class="btn btn-primary float-right ml-2 info-cancel">취소</button>
                            <button type="button" class="btn btn-primary float-right ml-2">프로필 수정</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <section id="tabs">
        <div class="container5">
            <div class="row">
                <div class="col-12 nav-template-area">
                    <nav>
                        <div class="nav nav-tabs nav-fill" id="nav-tab" role="tablist">

                        </div>
                    </nav>
                    <div class="tab-content py-3 px-3 px-sm-0" id="nav-tabContent">
                        <div class="tab-pane fade show active" id="nav-give" role="tabpanel"
                             aria-labelledby="nav-give-tab">
                            <div class="py-5">
                                <div class="container">
                                    <a href="#" id="show-gived-product-prev">이전 보기</a>
                                    <a href="#" id="show-gived-product-next">다음 보기</a>
                                    <div class="row hidden-md-up" id="gived-products">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="nav-take" role="tabpanel" aria-labelledby="nav-take-tab">
                            <div class="py-5">
                                <div class="container">
                                    <a href="#" id="show-received-product-prev">이전 보기</a>
                                    <a href="#" id="show-received-product-next">다음 보기</a>
                                    <div class="row hidden-md-up" id="received-products">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="nav-give-review" role="tabpanel"
                             aria-labelledby="nav-give-review-tab">
                            <section id="gived-comments">
                                <div class="review-and-register">
                                    <div id="gived-reviews-count">
                                    </div>
                                </div>
                                <a href="#" id="show-gived-review-prev">이전 보기</a>
                                <a href="#" id="show-gived-review-next">다음 보기</a>
                                <ul id="gived-comments-list">
                                </ul>
                            </section>
                        </div>
                        <div class="tab-pane fade" id="nav-get-review" role="tabpanel"
                             aria-labelledby="nav-get-review-tab">
                            <section id="received-comments">
                                <div class="review-and-register">
                                    <div id="received-reviews-count">
                                    </div>
                                </div>
                                <a href="#" id="show-received-review-prev">이전 보기</a>
                                <a href="#" id="show-received-review-next">다음 보기</a>
                                <ul id="received-comments-list">
                                </ul>
                            </section>
                        </div>
                    </div>

                </div>
            </div>
        </div>
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