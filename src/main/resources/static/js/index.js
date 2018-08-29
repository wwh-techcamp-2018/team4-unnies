import { $, $all } from './lib/utils.js';
import DaumMapSearch from './class/DaumMapSearch.js';
import Category from './class/Category.js';
import Product from './class/Product.js';
import { productTemplate } from './template/ProductTemplate.js';


const PAGE_MIN_OFFSET = 0;
const PAGE_SIZE = 6;
let pageOffset = PAGE_MIN_OFFSET;

const RATIO_SCROLL_DETECTION = 0.4;
const WAIT_TIME_MS = 50;
window.addEventListener('scroll', throttle(loadMoreNearProducts, WAIT_TIME_MS));
window.addEventListener('resize', throttle(loadMoreNearProducts, WAIT_TIME_MS));

const LOADING_TEXT = '(loading...)';

const category = new Category();
const product = new Product();

const daumMap = new DaumMapSearch(onSetAddress, onErrorSearchAddress);
daumMap.setZoomable(false);
daumMap.setZoomControl();

hideMap();
hideNotFoundNearProducts();

loadGIS();

$('.location .current').addEventListener('click', getCurrentLocation);
$('.location .modification').addEventListener('click', modifyLocation);
$('.map_wrap .input-group button.search').addEventListener('click', searchAddress);


function loadGIS() {
    const longitude = sessionStorage.getItem('longitude');
    const latitude = sessionStorage.getItem('latitude');

    if (longitude && latitude) {
        setGIS(latitude, longitude);
        return;
    }

    getCurrentLocation();
}

function onSetAddress() {
    hideMap();
    daumMap.addressSearch(this.address_name, onSearchAddress);
}

function onErrorSearchAddress() {
    setErrorMessageSearchAddress(this);
}

function setErrorMessageSearchAddress(error) {
    $('.map_wrap .error').innerHTML = error;
}

function onSearchAddress(latLng) {
    const latitude = latLng.getLat();
    const longitude = latLng.getLng();
    setGIS(latitude, longitude);
}

function setGIS(latitude, longitude) {
    sessionStorage.setItem('longitude', longitude);
    sessionStorage.setItem('latitude', latitude);

    daumMap.setPosition(latitude, longitude);

    setLocation(LOADING_TEXT);
    daumMap.getAddress(address => onGetAddress(address));

    pageOffset = PAGE_MIN_OFFSET;
    loadNearProducts(latitude, longitude, pageOffset, PAGE_SIZE);
}

function getCurrentLocation() {
    hideMap();
    setLocation(LOADING_TEXT);
    daumMap.getCurrentLocation(({ coords: { latitude, longitude }}) => {
        setGIS(latitude, longitude);
    });
}

function onGetAddress(address) {
    address && address.address_name && setLocation(address.address_name);
}

function modifyLocation() {
    showMap();
}

function setLocation(address) {
    $('.location .settings .address').innerHTML = address;
}

function showMap() {
    clearAddressSearchKeyword();

    $('.map_wrap').style.display = 'block';
    daumMap.relayout();

    const longitude = sessionStorage.getItem('longitude');
    const latitude = sessionStorage.getItem('latitude');
    daumMap.setPosition(latitude, longitude);
}

function hideMap() {
    $('.map_wrap').style.display = 'none';
}

function clearAddressSearchKeyword() {
    $('#keyword').value = '';
    $('#pagination').innerHTML = '';
    $('#placesList').innerHTML = '';
    setErrorMessageSearchAddress('');
}

function searchAddress() {
    setErrorMessageSearchAddress('');
    daumMap.searchPlaces();
}

function loadNearProducts(latitude, longitude, offset, limit) {
    const categoryId = window.location.pathname.split('/').pop();
    if (categoryId) {
        category.loadNearProducts(categoryId, latitude, longitude, offset, limit, onLoadNearProducts, onLoadFailNearProducts);
    } else {
        product.loadNearAll(latitude, longitude, offset, limit, onLoadNearProducts, onLoadFailNearProducts);
    }
}

function onLoadNearProducts(data) {
    const productsContainer = $(".card-columns");

    if(!pageOffset)
        productsContainer.innerHTML = '';

    if (!data.length && !$('.container.card-columns').children.length) {
        showNotFoundNearProducts();
        return;
    }

    hideNotFoundNearProducts();
    productsContainer.insertAdjacentHTML('beforeend', templateProducts(data));
    $all('.card').forEach(card => attachCardEventListener(card));

    pageOffset += data.length;
}

function templateProducts(data) {
    return `${data && data.map(productTemplate).join('')}`;
}

function attachCardEventListener(card) {
    const productId = $('input[name=product-id]').value;
    card && card.addEventListener('click', () => {
        location.href = `/products/${productId}`;
    });
}

function onLoadFailNearProducts(error) {
    showNotFoundNearProducts();
}

function showNotFoundNearProducts() {
    $('.container.not-found').style.display = 'block';
}

function hideNotFoundNearProducts() {
    $('.container.not-found').style.display = 'none';
}

function throttle(callback, wait) {
    let time = Date.now();
    return function() {
        if ((time + wait) < Date.now()) {
            callback();
            time = Date.now();
        }
    }
}

function isOverScrollThreshold() {
    const documentHeight = Math.max(document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight);
    const windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;

    const scrollY = document.documentElement.scrollTop + (windowHeight / 2);
    const documentYHalf = documentHeight * RATIO_SCROLL_DETECTION;

    if (scrollY < documentYHalf)
        return false;
    else
        return true;
}

function loadMoreNearProducts() {
    if(!isOverScrollThreshold()) {
        return;
    }

    if (pageOffset % PAGE_SIZE != 0) {
        return;
    }

    const longitude = sessionStorage.getItem('longitude');
    const latitude = sessionStorage.getItem('latitude');
    loadNearProducts(latitude, longitude, pageOffset, PAGE_SIZE);
}