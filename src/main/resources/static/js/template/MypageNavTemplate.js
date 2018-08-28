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
