import { $ , $all } from './lib/utils.js'

export function openModal(event){
    event.preventDefault();
    $('#overlay').classList.remove("is-hidden");
}

export function closeModal(){
    $('#overlay').classList.add("is-hidden");
    $('#comment').value = '';

    const selectedStars = $('#product1').getElementsByClassName('star');

    for (let selectedStar of selectedStars) {
        if(selectedStar.className.indexOf('selected') > -1){
            selectedStar.classList.remove('selected');
        }
    }
}