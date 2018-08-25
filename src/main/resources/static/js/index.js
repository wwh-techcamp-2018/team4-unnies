import { $ } from "./lib/utils.js";
import DaumMapSearch from "./lib/DaumMapSearch.js";


const LOADING_TEXT = '(loading...)';

const daumMap = new DaumMapSearch(onSetAddress, onErrorSearchAddress);
daumMap.setZoomable(false);
daumMap.setZoomControl();

hideMap();
getCurrentLocation();

$('.location .current').addEventListener('click', getCurrentLocation);
$('.location .modification').addEventListener('click', modifyLocation);
$('.map_wrap .input-group button.search').addEventListener('click', searchAddress);


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



function onDOMContentLoaded() {
    $(".card-columns").insertAdjacentHTML('afterbegin', templateCard());
}

function templateCard() {
    return `
    <!-- product template -->
    <div class="card">
        <img class="card-img-top" src="https://cdn.bmf.kr/_data/product/I21A3/b3b7ce9d8cf1ee91166fe97785d51d8a.jpg" alt="연잎수육">
        <div class="card-body">
            <h5 class="card-title font-weight-bold">진리의 탕수육</h5>
            <!-- chef -->
            <div class="container-fluid mt-2">
                <div class="row">
                    <div class="chef-img" style="width:80px;height:80px;border:dotted 1px lightgray;border-radius:3px;"></div>
                    <div class="col text-right">
                        <p class="card-text">강석윤</p>
                        <span class="badge badge-danger">나대는 사람</span>
                    </div>
                </div>
                <dl class="rating-app text-right">
                    <dt></dt>
                    <dd class="rating">
                        <span class="fa fa-star checked"></span>
                        <span class="fa fa-star checked"></span>
                        <span class="fa fa-star checked"></span>
                        <span class="fa fa-star checked"></span>
                        <span class="fa fa-star"></span>
                    </dd>
                </dl>
            </div>
            <dl class="row">
                <dt class="col">모집인원</dt>
                <dd class="col">3 / 4</dd>
            </dl>
            <dl class="row">
                <dt class="col">모집기간</dt>
                <dd class="col">2018.08.24 17:22:20 <span class="text-muted">까지</span></dd>
            </dl>
            <h4 class="card-subtitle text-right font-weight-bold">7000 <span class="text-muted">원</span></h4>
        </div>
        <a href="/detail.html" class="btn btn-primary">나눔신청</a>
    </div>`;
}

document.addEventListener('DOMContentLoaded', onDOMContentLoaded);