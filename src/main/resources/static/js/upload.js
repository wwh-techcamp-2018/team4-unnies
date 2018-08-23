import { $ } from './lib/utils.js';
import ImageUploader from './lib/ImageUploader.js';
import Category from './lib/Category.js';
import Product from './lib/Product.js';
import DaumMap from './lib/DaumMap.js';

new Category().load($('#category-section > .wrapper'));

const imageUploader = new ImageUploader({
    input: $('input[name=files]'),
    preview: $('.fluid-box'),
    dropzone: $('form')
})
    .delegate($('.btn.upload'))
    .draggable();


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

    if (e.target.checked) {
        price.setAttribute('readonly', '');
        price.required = false;
        price.value = 0;
    } else {
        price.removeAttribute('readonly');
        price.required = true;
        price.value = '';
    }
}

setDateTime();
$('#product-price').addEventListener('blur', floorPrice);
$('#price-free').addEventListener('change', setZeroPrice)

const daumMap = new DaumMap();
daumMap.setZoomable(false);
daumMap.setZoomControl();

daumMap.addMarkerListener('dragend', () => {
    daumMap.getAddress(address => {
        $('#product-address').value = address.address_name;
    });
});

$('#product-address').addEventListener('input', ({ target }) => {
    daumMap.addressSearch(target.value, latLng => {
        const latitude = latLng.getLat();
        const longitude = latLng.getLng();
        $('input[name=latitude]').value = latitude;
        $('input[name=longitude]').value = longitude;
        daumMap.setPosition(latitude, longitude);
    });
});

$('.location button.current').addEventListener('click', e => {
    e.preventDefault();
    markCurrentLocation();
});

// $('.location button.home').addEventListener('click', TODO);

function markCurrentLocation() {
    daumMap.getCurrentLocation(({ coords: { latitude, longitude }}) => {
        $('input[name=latitude]').value = latitude;
        $('input[name=longitude]').value = longitude;

        daumMap.setPosition(latitude, longitude);
        daumMap.getAddress(address => {
            $('#product-address').value = address.address_name;
        });
    });
}

const editor = new tui.Editor({
    el: $('#editSection'),
    initialEditType: 'wysiwyg',
    previewStyle: 'vertical',
    height: '500px',
    events: {
        change: () => {
            $('input[name=description]').value = editor.getValue();
        }
    },
    hooks: {
        addImageBlobHook: (blob, callback) => {
            const formData = new FormData();
            formData.set('file', blob);
            fetch('/api/products/images', {
                method: 'post',
                body: formData
            })
                .then(response => {
                    if (!response.ok) {
                        throw blob;
                    }
                    const url = response.headers.get('Location');
                    callback(url);
                })
                .catch(callback);
        }
    }
});

document.addEventListener('DOMContentLoaded', () => {
    markCurrentLocation();
});