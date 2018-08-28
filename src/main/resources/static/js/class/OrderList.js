import { $ } from '../lib/utils.js';

class OrderList {
    load(productId, callback) {
        fetch(`/api/products/${productId}/orders/user`)
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                if (response.status === 404){
                    return;
                }
//                throw response.json();
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

