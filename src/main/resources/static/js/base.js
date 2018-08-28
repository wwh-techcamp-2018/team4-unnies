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
    insertIntoCategoryContainer(templateCategories(data));
    const categoryId = window.location.pathname.split('/').pop();
    categoryId && setActiveCategory(categoryId);
}

function onLoadFailCategory(error) {
    insertIntoCategoryContainer(templateCategoryError(error));
}

function insertIntoCategoryContainer(template) {
    const container = $('.categories');
    container.innerHTML = '';
    container.insertAdjacentHTML('afterbegin', template);
}

function templateCategories(data) {
    return `${data && data.map(templateCategory).join('')}`;
}

function templateCategory({ id, name }) {
    return `<a class="nav-item nav-link category-id" href="/categories/${id}">${name}</a>`;
}

function templateCategoryError(error) {
    return `<span class="category-error text-muted">카테고리를 가져오는데 실패했습니다.(${error})</span>`;
}

function setActiveCategory(index) {
    const activeItem = $('.categories > .nav-item.active');
    activeItem && activeItem.classList.remove('active');
    $(`.categories > .nav-item:nth-child(${index})`).classList.add('active');
}