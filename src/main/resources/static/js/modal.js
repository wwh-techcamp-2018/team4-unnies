import { $ , $all } from './lib/utils.js'
import { orderTemplate } from './template/DetailTemplate.js'

export function openModal(){
    $('#overlay').classList.remove("is-hidden");
}

export function closeModal(){
    $('#overlay').classList.add("is-hidden");
    $('#comment').value = '';

    const selectedStars = $all('#product1 .star');

    for (const selectedStar of selectedStars) {
        if(selectedStar.classList.contains('selected')){
            selectedStar.classList.remove('selected');
        }
    }
}

export function openRegisterModal({ data }){
    $('#register-overlay').classList.remove("is-hidden");
    $('#register-data').innerHTML = orderTemplate(data);
}

export function closeRegisterModal(){
    $('#register-overlay').classList.add("is-hidden");
    const radioList = $all('input[name=groupOfRadioGap]');
    radioList.forEach(radio => {
        radio.checked = false;
    })
}

