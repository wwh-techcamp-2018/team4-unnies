export function thumbnailImage({ src, alt }) {
    return `<img class="img-thumb border" src="${src}" alt="${alt}" draggable="true">`;
}

export function thumbnailEmpty() {
    return `<span class="img-thumb border"></span>`;
}

export function categorySelect(data) {
    return `<select class="custom-select form-control" name="categoryId" id="product-category" required>
                <option value="">카테고리 선택</option>
                ${data && data.map(categoryOptions).join('')}
            </select>`;
}

export function categoryOptions({id, name}) {
    return `<option value="${id}">${name}</option>`;
}
