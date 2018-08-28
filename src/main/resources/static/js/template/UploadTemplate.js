export function mainView() {
    return `<div class="image-viewer-main"></div>
            <div class="image-viewer-thumbnails" style="display: none;"></div>`;
}

export function thumbnailView(style) {
    style = Object.keys(style).map(property => `${property}:${style[property]}`).join(';');
    return `<div class="image-viewer-thumbnail" style="${style}"></div>`;
}

export function imageView(src, alt) {
    return `<img src="${src}" alt="${alt}" draggable="true">`;
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