function initUploadProduct() {
    document.querySelector('#upload-button').addEventListener("click", uploadProduct)
}


function uploadProduct(e) {
    e.preventDefault();

    const form = document.querySelector('#upload-form');
    const formData = new FormData(form);

    for (var value of formData.values()) {
        console.log('what : ' + value);
    }

    fetch('/api/products', {
        method: 'post',
        credentials: 'same-origin',
        body: formData
    }).then(response => {
        if (response.ok) {
            return location.href = '/';// products/id 페이지로 변경필요
        }
        handleError("업로드 실패했습니다.");
    }).catch(error => {
        handleError(error.toString());
    });
}

function getCategoryList() {
    fetch('/api/categories', {
        method: 'get',
    }).then(response => {
        if (response.ok) {
            return response.json();
        }
        handleError("카테고리 리스트를 가져오는데 실패했습니다.");
    }).then(renderCategoryList)
        .catch(error => {
            handleError(error.toString());
        });
}

document.addEventListener("DOMContentLoaded", initUploadProduct);