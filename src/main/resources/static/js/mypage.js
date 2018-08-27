import {$, $all} from './lib/utils.js';
import UserActivity from "./class/UserActivity.js";

const userActivity = new UserActivity();;
let userID = '1';   //임시 테스트
//userId = location.pathname.split("/").pop();  //todo 변경필요


function initEvent(){
    $('#mypage-modify').addEventListener('click', toggleUpdateMode);
    $('.nav.nav-tabs.nav-fill').addEventListener('click', moveToSelectedBar);
    $('#mypage-modify .info-change').addEventListener('click', changeProfile);
    $('#mypage-modify .info-cancel').addEventListener('click', restoreProfile);

    getMypageData(location.pathname);
}

function getMypageData(pathName){
    const path = pathName.split("/").pop();

    userActivity.load(userID);
    showContents(1);
}

function showContents(index){
    $('.nav.nav-tabs.nav-fill .nav-item.nav-link.active').classList.remove('active');
    $(`.nav.nav-tabs.nav-fill .nav-item.nav-link:nth-child(${index})`).classList.add('active');

    $('.tab-content.py-3.px-3.px-sm-0 .tab-pane.fade.show').classList.remove('show');
    $(`.tab-content.py-3.px-3.px-sm-0 .tab-pane.fade:nth-child(${index})`).classList.add('show');

    switch (index) {
        case 1:
            userActivity.loadGivedProducts(userID, 0);
            break;
        case 2:
            userActivity.loadReceivedProducts(userID, 0);
            break;
        case 3:
            userActivity.loadGiveReviews(userID,0);
            break;
        case 4:
            userActivity.loadReceivedReviews(userID,0);
            break;
        default:
            break;
    }

}

function moveToSelectedBar(event) {
    event.preventDefault();

    showContents(getElementParentIndex(event.target));
}

function changeProfile(event) {
    event.preventDefault();

    const formData = new FormData($('#mypage-form'));
    userActivity.save(formData, userID);
}

function restoreProfile(event) {
    event.preventDefault();

    userActivity.restore();
}

function toggleUpdateMode(event){
    event.preventDefault();

    const buttonManager = event.target.parentNode;
    const imageInfoText = $('#mypage-image').firstElementChild;

    imageInfoText.hidden ? imageInfoText.hidden = false : imageInfoText.hidden = true;
    [...buttonManager.children].forEach(child => {
        child.hidden ? child.hidden = false : child.hidden = true;
    });

    const toggleMode = $all('.toggle-mode');

    toggleMode.forEach(element => {
        if(element.classList.contains('off')) {
            element.classList.remove('off');
            element.value = "";
            element.readOnly = false;
        } else {
            element.classList.add('off');
            element.readOnly = true;
        }
    });
}

function getElementParentIndex(element){
    return [...element.parentElement.children].indexOf(element) + 1;
}

document.addEventListener('DOMContentLoaded', initEvent)