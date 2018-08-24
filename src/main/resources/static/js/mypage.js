import { $ } from './lib/utils.js';

function changeToModify(e){
    e.preventDefault();

    console.log('changeToModify');
}


function initEvent(){
   $('#mypage-modify').addEventListener('click', changeToModify);
   $('.nav.nav-tabs.nav-fill').addEventListener('click', moveToSelectedBar);
}

function moveToSelectedBar(event) {
   event.preventDefault();

   const index = getElementParentIndex(event.target);

   $('.nav.nav-tabs.nav-fill .nav-item.nav-link.active').classList.remove('active');
   $(`.nav.nav-tabs.nav-fill .nav-item.nav-link:nth-child(${index})`).classList.add('active');

   showContents(index);
}

function showContents(index){
   $('.tab-content.py-3.px-3.px-sm-0 .tab-pane.fade.show').classList.remove('show');
   //console.log($(`.tab-content.py-3.px-3.px-sm-0 .tab-pane.fade:nth-child(${index})`));
   $(`.tab-content.py-3.px-3.px-sm-0 .tab-pane.fade:nth-child(${index})`).classList.add('show');
}

function getElementParentIndex(element){
   console.log(element);
   console.log([...element.parentElement.children].indexOf(element));

   return [...element.parentElement.children].indexOf(element) + 1;
}

document.addEventListener('DOMContentLoaded', initEvent)
