import { $, $all } from './lib/utils.js';
import DaumMapSearch from './lib/DaumMapSearch.js';
import Category from './lib/Category.js';
import Product from './lib/Product.js';
import { productTemplate } from './template/ProductTemplate.js';


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

    loadNearProducts(longitude, latitude);
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

function loadNearProducts(longitude, latitude) {
    const categoryId = window.location.pathname.split('/').pop();
    if(categoryId) {
        category.loadNearProducts(categoryId, longitude, latitude, onLoadNearProducts, onLoadFailNearProducts);
    } else {
        product.loadNearAll(longitude, latitude, onLoadNearProducts, onLoadFailNearProducts);
    }
}

function onLoadNearProducts(data) {
    const cardContainer = $(".card-columns");
    cardContainer.innerHTML = '';

    if(!data.length) {
        showNotFoundNearProducts();
        return;
    }

    hideNotFoundNearProducts();
    cardContainer.insertAdjacentHTML('afterbegin', templateCards(data));
    $all('.card').forEach(card => attachCardEventListener(card));
}

function templateCards(data) {
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