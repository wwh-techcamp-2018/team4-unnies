import { categorySelect } from '../template/UploadTemplate.js';

class Category {

    load(element) {
        fetch('/api/categories')
        .then(response => {
            if (!response.ok) {
                throw '잠시 후 다시 시도해주세요';
            }
            return response.json();
        })
        .then(({ data }) => {
            element.innerHTML = ''
            element.insertAdjacentHTML('afterbegin', categorySelect(data));
        })
        .catch(error => {
            const feedback = element.closest('.form-group').querySelector('.feedback');
            feedback.innerHTML = error;
            feedback.classList.add('on');
            feedback.focus();
        });
    }

}

export default Category;