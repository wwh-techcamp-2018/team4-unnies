import { $ } from '../lib/utils.js';

class OrderList {
    load(productId, callback) {
        fetch(`/api/orders/products/${productId}`)
            .then(response => {
                return response.json();
            })
            .then(({ data }) => {
                callback(data)
            })
            .catch(error => {
                // TODOs : error handling...
            });
    }
}

export default OrderList;

