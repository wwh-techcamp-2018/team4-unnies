class DaumPostCode {

    load(callback) {
        daum.postcode.load(() => {
            this.postcode = new daum.Postcode({
                oncomplete: callback
            });
            this.postcode.open();
        });
    }

}

export default DaumPostCode;