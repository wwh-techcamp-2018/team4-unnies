
function initProductForm() {
    document.querySelector('#product-address').addEventListener("click", showAddressFinder);
    getCategoryList();
}

function uploadProduct() {
    fetch('/api/products')
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

function renderCategoryList({ data }) {
    const select = document.querySelector('#product-category');
    data.forEach(category => {
        const option = document.createElement("option");
        option.value = category.id;
        option.text = category.name;
        select.add(option);
    });
}

function handleError(msg) {
    alert(msg);
}

function showAddressFinder() {
    daum.postcode.load(function() {
        new daum.Postcode({
            oncomplete: function (data) {
                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullAddr = data.roadAddress; // 최종 주소 변수
                var extraAddr = ''; // 조합형 주소 변수

                // 기본 주소가 도로명 타입일때 조합한다.
                if (data.addressType === 'R') {
                    //법정동명이 있을 경우 추가한다.
                    if (data.bname !== '') {
                        extraAddr += data.bname;
                    }
                    // 건물명이 있을 경우 추가한다.
                    if (data.buildingName !== '') {
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += (extraAddr !== '' ? ' (' + extraAddr + ')' : '');
                }

                // 주소 정보를 해당 필드에 넣는다.
                document.querySelector("#product-address").value = fullAddr;

            }
        }).open();
    });
}

document.addEventListener("DOMContentLoaded", initProductForm);