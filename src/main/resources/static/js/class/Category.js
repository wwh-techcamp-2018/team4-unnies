
class Category {

    load(success, fail) {
        fetch('/api/categories')
        .then(response => {
            if (!response.ok) {
                fail('잠시 후 다시 시도해주세요');
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

    loadNearProducts(id, longitude, latitude, success, fail) {
        fetch(`/api/categories/${id}/products?latitude=${latitude}&longitude=${longitude}`, {
            method: 'get'
        }).then(response => {
            if (!response.ok) {
                fail('잠시 후 다시 시도해주세요');
            }
            return response.json();
        }).then(({ data }) => {
            success(data);
        }).catch(error => {
            fail(error);
        });
    }

}

export default Category;