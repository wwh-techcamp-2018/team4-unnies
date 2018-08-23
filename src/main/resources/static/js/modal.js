import { $ , $all } from './lib/utils.js'

export function openModal(event){
    event.preventDefault();
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