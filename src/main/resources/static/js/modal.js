import { $ , $all } from './lib/utils.js'
import { registerTemplate } from './template/DetailTemplate.js'

export function openReviewModal(){
    $('#overlay').classList.remove("is-hidden");
}

export function closeReviewModal(){
    $('#overlay').classList.add("is-hidden");
    $('#comment').value = '';

    const selectedStars = $all('#product1 .star');

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
    const radioList = $all('input[name=groupOfRadioGap]');
    radioList.forEach(radio => {
        radio.checked = false;
    })
}

