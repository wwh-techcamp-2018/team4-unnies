import { $ , $all } from './utils.js'
import { registerTemplate } from '../template/DetailTemplate.js'

export function openReviewModal(event){
    event.preventDefault();
    $('#overlay').classList.remove("is-hidden");
}

export function closeReviewModal(){
    $('#overlay').classList.add("is-hidden");
    $('#comment').value = '';

    const selectedStars = $all('#product1 .star');
    $('strong[name=invalid-review]').style.visibility = 'hidden';
    for (const selectedStar of selectedStars) {
        if(selectedStar.classList.contains('selected')){
            selectedStar.classList.remove('selected');
        }
    }
}

export function openOrderModal(data){
    $('#register-overlay').classList.remove("is-hidden");
    $('#register-data').innerHTML = registerTemplate(data);
}

export function closeOrderModal(){
    $('#register-overlay').classList.add("is-hidden");
    $('strong[name=invalid-register]').style.visibility = 'hidden';
    const radioList = $all('input[name=groupOfRadioGap]');
    radioList.forEach(radio => {
        radio.checked = false;
    })
}

export function openOrderListModal(data){
    $('#orders-overlay').classList.remove("is-hidden");
}

export function closeOrderListModal(){
    $('#orders-overlay').classList.add("is-hidden");
}