import { $ } from "./lib/utils.js";
import Category from "./lib/Category.js";

$('.logout') && $('.logout').addEventListener('click', logout);

function logout(){
    fetch('/api/users/logout')
        .then(response => {
            if(!response.ok) {
                throw '로그아웃에 실패하였습니다.'
            }
            location.href = '/';
        })
        .catch(error => {
            console.error(error);
        })
}

new Category().load(onLoadSuccessCategory, onLoadFailCategory);

function onLoadSuccessCategory(data) {
    insertIntoCategoryContainer(addCategories(data));
}

function onLoadFailCategory(error) {
    insertIntoCategoryContainer(templateCategoryError(error));
}

function insertIntoCategoryContainer(template) {
    const container = $('.categories');
    container.innerHTML = '';
    container.insertAdjacentHTML('afterbegin', template);
}

function addCategories(data) {
    return `${data && data.map(templateCategory).join('')}`;
}

function templateCategory({ id, name }) {
    // TODO: category href 연결 필요
    return `<a class="nav-item nav-link" href="#">${name}</a>`;
}

function templateCategoryError(error) {
    return `<span class="category-error text-muted">카테고리를 가져오는데 실패했습니다.(${error})</span>`;
}