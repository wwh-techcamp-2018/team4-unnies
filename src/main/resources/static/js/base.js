import { $ } from "./lib/utils.js";

function onDOMContentLoaded() {
    fetchCategories($('.categories'));
}

function fetchCategories(container) {
    fetch('/api/categories')
        .then(response => {
            if (!response.ok) {
                throw '잠시 후 다시 시도해주세요';
            }
            return response.json();
        })
        .then(({ data }) => {
            container.innerHTML = '';
            container.insertAdjacentHTML('afterbegin', renderCategories(data));
        })
        .catch(error => {
            container.innerHTML = '';
            container.insertAdjacentHTML('afterbegin', templateCategoryError(error));
        });
}

function renderCategories(data) {
    return `${data && data.map(templateCategory).join('')}`;
}

function templateCategory({ name }) {
    // TODO: category href 연결 필요
    return `<a class="nav-item nav-link" href="#">${name}</a>`;
}

function templateCategoryError(error) {
    return `<span class="category-error text-muted">${error}</span>`;
}

document.addEventListener('DOMContentLoaded', onDOMContentLoaded);