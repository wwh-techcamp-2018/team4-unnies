import {$, $all} from './lib/utils.js';
import UserActivity from "./class/UserActivity.js";
import ImageUploader from "./class/ImageUploader.js";

let userId = location.pathname.split("/").pop();

const userActivity = new UserActivity();
userActivity.load(userId, () => {
    initEvents();
    showTabContents(1);
});

function initEvents() {
    $('#mypage-modify').addEventListener('click', toggleUpdateMode);
    $('.nav.nav-tabs.nav-fill').addEventListener('click', moveToSelectedBar);
    $('#mypage-modify .info-change').addEventListener('click', changeProfile);
    $('#mypage-modify .info-cancel').addEventListener('click', restoreProfile);

    const imageUploader = new ImageUploader();
    imageUploader.setForm($('div.preview-pic'));
    imageUploader.setMultiple(false);
    imageUploader.setAccept('image/jpeg,image/png');
    imageUploader.setName('file');
    // imageUploader.setDelegate($('#mypage-upload-image'));
    imageUploader.setDelegate($('div.upload'));

    imageUploader.addAfterFileInputListener(() => {
        const fileInput = $('input[name=file]');
        const fileReader = new FileReader();

        fileReader.addEventListener('load', ({target: {result}}) => {
            $('div.preview-pic .img-thumb img').src = result;
        });

        fileReader.addEventListener('error', error => {
            console.info('error', error);
        });
        fileReader.addEventListener('abort', error => {
            console.info('abort', error);
        });

        fileReader.readAsDataURL(fileInput.files.item(0));
    });
}

function showTabContents(index) {
    changeTabIndex(index);

    switch (index) {
        case 1:
            userActivity.loadCreatedProducts(userId, 0);
            break;
        case 2:
            userActivity.loadReceivedProducts(userId, 0);
            break;
        case 3:
            userActivity.loadCreatedReviews(userId, 0);
            break;
        case 4:
            userActivity.loadReceivedReviews(userId, 0);
            break;
        default:
            break;
    }
}

function changeTabIndex(index) {
    $('.nav.nav-tabs.nav-fill .nav-item.nav-link.active').classList.remove('active');
    $(`.nav.nav-tabs.nav-fill .nav-item.nav-link:nth-child(${index})`).classList.add('active');
    $('.tab-content .tab-pane.fade.show').classList.remove('show');
    $(`.tab-content .tab-pane.fade:nth-child(${index})`).classList.add('show');
}

function moveToSelectedBar(event) {
    event.preventDefault();

    showTabContents(getElementParentIndex(event.target));
}

function changeProfile(event) {
    event.preventDefault();

    const formData = new FormData($('#mypage-form'));
    userActivity.save(formData, userId);
}

function restoreProfile(event) {
    event.preventDefault();

    userActivity.restore();
}

function toggleUpdateMode(event) {
    event.preventDefault();

    const buttonManager = event.target.parentNode;
    const imageButton = $('#mypage-image > div.upload');

    imageButton.hidden ? imageButton.hidden = false : imageButton.hidden = true;
    [...buttonManager.children].forEach(child => {
        child.hidden ? child.hidden = false : child.hidden = true;
    });

    const toggleMode = $all('.toggle-mode');

    toggleMode.forEach(element => {
        if (element.classList.contains('off')) {
            element.classList.remove('off');
            element.value = "";
            element.readOnly = false;
        } else {
            element.classList.add('off');
            element.readOnly = true;
        }
    });
}

function getElementParentIndex(element) {
    return [...element.parentElement.children].indexOf(element) + 1;
}
