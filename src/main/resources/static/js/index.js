import { $, $all } from './lib/utils.js';
import DaumMapSearch from './lib/DaumMapSearch.js';
import { productTemplate } from './template/ProductTemplate.js';


const LOADING_TEXT = '(loading...)';

const daumMap = new DaumMapSearch(onSetAddress, onErrorSearchAddress);
daumMap.setZoomable(false);
daumMap.setZoomControl();

hideMap();
getCurrentLocation();

$('.location .current').addEventListener('click', getCurrentLocation);
$('.location .modification').addEventListener('click', modifyLocation);
$('.map_wrap .input-group button.search').addEventListener('click', searchAddress);


function loadNearProducts() {
    // TODO: spinner

    const latitude = $('input[name=latitude]').value;
    const longitude = $('input[name=longitude]').value;
    const offset = 0; // TODO: page.offset for infinite scrolling

    fetch(`/api/products?latitude=${latitude}&longitude=${longitude}&offset=${offset}`, {
        method: 'get'
    }).then(response => {
        if (!response.ok) {
            console.error('failed to load near products');
        }
        return response.json();
    }).then(({ data }) => {
        console.log(data);
        onLoadProducts(data);
    }).catch(error => {
       console.log(error);
    });
}

function onSetAddress() {
    hideMap();
    daumMap.addressSearch(this.address_name, onSearchAddress);
    setLocation(this.address_name);
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
    $('input[name=latitude]').value = latitude;
    $('input[name=longitude]').value = longitude;
    daumMap.setPosition(latitude, longitude);
    loadNearProducts();
}

function getCurrentLocation() {
    hideMap();
    setLocation(LOADING_TEXT);
    daumMap.getCurrentLocation(({ coords: { latitude, longitude }}) => {
        setGIS(latitude, longitude);
        daumMap.getAddress(address => onGetAddress(address));
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
    $('#map').style.width = "900px";
    $('#map').style.height = "500px";

    daumMap.relayout();

    const latitude = $('input[name=latitude]').value;
    const longitude = $('input[name=longitude]').value;
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

function searchAddress(event) {
    event.preventDefault();
    setErrorMessageSearchAddress('');
    daumMap.searchPlaces();
}

function onLoadProducts(data) {
    const cardContainer = $(".card-columns");
    cardContainer.innerHTML = '';
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