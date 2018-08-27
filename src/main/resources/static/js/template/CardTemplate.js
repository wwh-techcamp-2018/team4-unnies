export function cardTemplate({product, orderCount, status, ownerRating}) {

    const imageUrl = product.productImages.length && product.productImages[0];
    const name = product.name;
    const ownerName = product.owner.name;
    const productStatus = status;//추가
    const productOrderCount = orderCount ? orderCount : 0 ;//추가
    const productOwnerRatting = ownerRating;
    const maxParticipant = product.maxParticipant;
    const expireDateTime = product.expireDateTime;
    const price = product.price;

    return `<div class="card" id="mypage-card">
        <img class="card-img-top" src="https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg" alt="${name}">
        <div class="card-body">
            <h5 class="card-title font-weight-bold">${name}</h5>
            <!-- chef -->
            <div class="container-fluid mt-2">
                <div class="row">
                    <div class="chef-img" style="width:80px;height:80px;border:dotted 1px lightgray;border-radius:3px;"></div>
                    <div class="col text-right">
                        <p class="card-text">${ownerName}</p>
                        <span class="badge badge-danger">${productStatus}</span>
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
                <dd class="col">${productOrderCount} / ${maxParticipant}</dd>
            </dl>
            <dl class="row">
                <dt class="col">모집기간</dt>
                <dd class="col">${expireDateTime} <span class="text-muted">까지</span></dd>
            </dl>
            <h4 class="card-subtitle text-right font-weight-bold">${price} <span class="text-muted">원</span></h4>
        </div>
        <!--<a href="/detail.html" class="btn btn-primary">나눔신청</a>-->
    </div>`;
}