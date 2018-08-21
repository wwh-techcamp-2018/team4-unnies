export function categorySelect(data) {
    return `<select class="custom-select form-control" name="categoryId" id="product-category" required>
                <option value="">카테고리 선택</option>
                ${data && data.map(categoryOptions).join('')}
            </select>`;
}

export function categoryOptions({id, name}) {
    return `<option value="${id}">${name}</option>`;
}

