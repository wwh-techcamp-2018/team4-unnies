import { $ } from './lib/utils.js';
import ImageUploader from './lib/ImageUploader.js';
import DaumPostCode from './lib/DaumPostCode.js';
import Category from './lib/Category.js';
import { categorySelect } from './template/UploadTemplate.js';
import Product from "./lib/Product.js";

new Category()
.getList()
.then(({ data }) => {
    const view = $('#category-section > div');
    view.innerHTML = ''
    view.insertAdjacentHTML('afterbegin', categorySelect(data));
})

const imageUploader = new ImageUploader({
    templateSource: $('#template-thumbnail-image').innerHTML,
    input: $('input[name=files]'),
    preview: $('.fluid-box'),
    dropzone: $('form')
})
    .delegate($('.btn.upload'))
    .draggable();

new DaumPostCode({
    input: $('#product-address'),
    searchButton: $("#product-address-search")
}).load();

$('#upload-form').addEventListener('submit', e => {
    e.preventDefault();
    const formData = new FormData($('#upload-form'));


    // console.log(formData.getAll('files'), imageUploader.files);
    // formData.set('files', imageUploader.files);
    new Product().upload(formData);
    // new Product().upload($('#upload-form'));
});

function setDateTime() {
    const expireDate = document.querySelector('#product-expire');
    const shareDate = document.querySelector('#product-share');
    const minDate = moment(new Date()).ceil(10, 'm').add(2, 'h');

    expireDate.value = minDate.format("YYYY-MM-DDTHH:mm");
    expireDate.min = minDate.format("YYYY-MM-DDTHH:mm");
    expireDate.max = minDate.add(2, 'w').format("YYYY-MM-DDTHH:mm");

    shareDate.value = expireDate.value;
    shareDate.min = expireDate.min;
    shareDate.max = expireDate.max

    expireDate.addEventListener('change', validateShareDateTime);
    shareDate.addEventListener('change', validateShareDateTime);
}

function validateShareDateTime(e) {
    const shareDate = document.querySelector('#product-share');
    const expireDate = document.querySelector('#product-expire');

    if(moment(shareDate.value).isBefore(expireDate.value)) {
        shareDate.value = expireDate.value;
    }
}

function floorPrice(e) {
    const price = $('#product-price');
    price.value -= price.value % 1000;
}

function setZeroPrice(e) {
    const price = $('#product-price');

    console.log("funck : ", price);

    if (e.target.checked) {
        price.required = false;
        price.setAttribute('readonly', '');
        price.value = 0;
    }
    else {
        price.removeAttribute('readonly')
        price.required = true;
        price.value = '';
    }

}

document.addEventListener('DOMContentLoaded', () => {
    setDateTime();

    $('#product-price').addEventListener('blur', floorPrice);
    $('#price-free').addEventListener('change', setZeroPrice)
});