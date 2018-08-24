import { $ } from "./lib/utils.js";

function onDOMContentLoaded() {
    $(".card-columns").insertAdjacentHTML('afterbegin', templateCard());
}

function templateCard() {
    return `
    <!-- product template -->
    <div class="card">
        <img class="card-img-top" src="https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg" alt="연잎수육">
        <div class="card-body">
            <h5 class="card-title font-weight-bold">진리의 탕수육</h5>
            <!-- chef -->
            <div class="container-fluid mt-2">
                <div class="row">
                    <div class="chef-img" style="width:80px;height:80px;border:dotted 1px lightgray;border-radius:3px;"></div>
                    <div class="col text-right">
                        <p class="card-text">강석윤</p>
                        <span class="badge badge-danger">나대는 사람</span>
                    </div>
                </div>
                <dl class="rating-app text-right">
                    <dt></dt>
                    <dd class="rating">
                        <span class="fa fa-star checked"></span>
                        <span class="fa fa-star checked"></span>
                        <span class="fa fa-star checked"></span>
                        <span class="fa fa-star checked"></span>
                        <span class="fa fa-star"></span>
                    </dd>
                </dl>
            </div>
            <dl class="row">
                <dt class="col">모집인원</dt>
                <dd class="col">3 / 4</dd>
            </dl>
            <dl class="row">
                <dt class="col">모집기간</dt>
                <dd class="col">2018.08.24 17:22:20 <span class="text-muted">까지</span></dd>
            </dl>
            <h4 class="card-subtitle text-right font-weight-bold">7000 <span class="text-muted">원</span></h4>
        </div>
        <a href="/detail.html" class="btn btn-primary">나눔신청</a>
    </div>`;
}

document.addEventListener('DOMContentLoaded', onDOMContentLoaded);