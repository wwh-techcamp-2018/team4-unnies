class Category {

    load(success, fail) {
        fetch('/api/categories')
        .then(response => {
            if (!response.ok) {
                throw '잠시 후 다시 시도해주세요';
            }
            return response.json();
        })
        .then(({ data }) => {
            success(data);
        })
        .catch(error => {
            fail(error);
        });
    }

}

export default Category;