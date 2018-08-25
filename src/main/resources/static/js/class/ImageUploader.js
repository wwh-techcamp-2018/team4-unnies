class ImageUploader {

    constructor(...args) {
        this.FILE_LIMIT = args.limit || 5;
        this.filesLength = 0;

        args.form && this.setForm(args.form);
        args.inputTemplate && this.setInputTemplate(args.inputTemplate);
        args.inputName && this.setInputName(args.inputName);
        args.dropzone && this.setDropZone(args.dropzone);
        args.delegate && this.setDelegate(args.delegate);
    }

    setForm(element) {
        this.form = element;
    }

    setInputTemplate(template) {
        this.inputTemplate = template;
    }

    setInputName(name) {
        this.name = name;
    }

    setDropZone(element) {
        this.dropzone = element;
        this.dropzone.addEventListener('dragover', this._defaultDragOverHandler.bind(this));
        this.dropzone.addEventListener('drop', this._defaultDropHandler.bind(this));
    }

    _defaultDragOverHandler(event) {
        event.preventDefault();
        event.stopPropagation();
    }

    _defaultDropHandler(event) {
        event.preventDefault();
        event.stopPropagation();

        const { files, types } = event.dataTransfer;
        if (!types.includes('Files')) {
            return;
        }
        if (this.FILE_LIMIT < this.filesLength + files.length) {
            return;
        }
        if ([...files].filter(file => !/image\/(jpeg|png)/.test(file.type)).length) {
            return;
        }

        this.filesLength += files.length;
        this.form.insertAdjacentHTML('beforeend', this.inputTemplate(this.name));
        this.form.querySelector(`input[name=${this.name}]:last-child`).files = files;
        this._afterFileInputHandler && this._afterFileInputHandler(event);
    }

    setDelegate(element) {
        this.button = element;
        this.button.addEventListener('click', this._defaultUploadHandler.bind(this));
    }

    _defaultUploadHandler(event) {
        event.preventDefault();
        this.form.insertAdjacentHTML('beforeend', this.inputTemplate(this.name));
        const input = this.form.querySelector(`input[name=${this.name}]:last-child`)
        input.addEventListener('input', event => {
            const { files } = event.target;
            if (this.FILE_LIMIT < this.filesLength + files.length) {
                input.remove();
                return;
            }
            this.filesLength += files.length;
            this._afterFileInputHandler && this._afterFileInputHandler(event);
        });
        input.click();
    }

    addAfterFileInputListener(handler) {
        this._afterFileInputHandler = handler;
    }

}

export default ImageUploader;