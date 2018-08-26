import { $, $all } from './lib/utils.js';
import ImageUploader from './class/ImageUploader.js';
import ImageViewer from './class/ImageViewer.js';
import Category from './lib/Category.js';
import Product from './lib/Product.js';
import DaumMap from './lib/DaumMap.js';
import { uploadInput, mainView, thumbnailView, imageView } from './template/UploadTemplate.js';

new Category().load($('#category-section > .wrapper'));

const imageViewer = new ImageViewer();
imageViewer.setViewTemplate(mainView);
imageViewer.setImageViewTemplate(imageView);
imageViewer.setThumbnailViewTemplate(thumbnailView);

imageViewer.setView($('#image-viewer'));
imageViewer.setMainWidth('320px');
imageViewer.setMainHeight('320px');

imageViewer.setThumbnailsCount(5);
imageViewer.enableThumbnailMouseOver();
imageViewer.enableDrag();

const imageUploader = new ImageUploader();
imageUploader.setForm($('.form-group.upload'));
imageUploader.setInputTemplate(uploadInput);
imageUploader.setInputName('files');
imageUploader.setDropZone($('.form-group.upload'));
imageUploader.setDelegate($('button.upload'));

imageUploader.addAfterFileInputListener((event => {
    const fileInputs = $all('input[name=files]');
    imageViewer.renderImages([...fileInputs].reduce((files, input) => [...files, ...input.files], []));
}));

const ENTER_KEY = 13;
$('#upload-form').addEventListener('keydown', event => {
    if (event.keyCode === ENTER_KEY) {
        event.preventDefault();
    }
});

$('#upload-form').addEventListener('submit', e => {
    e.preventDefault();
    const formData = new FormData($('#upload-form'));
    new Product().upload(formData);
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