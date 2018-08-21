class Category {
    constructor() {

    }

    getList() {
        return fetch('/api/categories')
        .then(response => {
            if (!response.ok) {
                // error
                return console.log("에러 " , response);
            }
            return response.json();
        })
        .catch();
    }

}

export default Category;