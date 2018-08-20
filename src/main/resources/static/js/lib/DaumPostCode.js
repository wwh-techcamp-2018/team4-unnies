class DaumPostCode {

    constructor(args) {
        args.input && this.setInput(args.input);
        args.searchButton && this.setSearchButton(args.searchButton);
    }

    setInput(element) {
        this.input = element;
    }

    setSearchButton(element) {
        this.searchButton = element;
    }

    load() {
        daum.postcode.load(() => {
            this.postcode = new daum.Postcode({
                oncomplete: ({ address }) => this.input.value = address
            });

            this.searchButton.addEventListener('click', () => {
                this.postcode.open();
            });
        });
    }

}

export default DaumPostCode;