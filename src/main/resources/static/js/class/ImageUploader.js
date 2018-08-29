class ImageUploader {

    constructor(...args) {
        this.FILE_LIMIT = args.limit || 5;
        this.filesLength = 0;

        args.form && this.setForm(args.form);
        args.name && this.setName(args.name);
        args.accept && this.setAccept(args.accept);
        args.multiple && this.setMultiple(args.multiple);
        args.dropzone && this.setDropZone(args.dropzone);
        args.delegate && this.setDelegate(args.delegate);
    }

    setForm(element) {
        this.form = element;
    }

    setName(name) {
        this.name = name;
    }

    setAccept(accept) {
        this.accept = accept;
    }

    setMultiple(boolean) {
        this.multiple = boolean;
    }

    setMultiple(boolean) {
        this.multiple = boolean;
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

        let input = this.form.querySelector(`input[name=${this.name}]`);
        if (input && !this.multiple) {
            return input.click();
        }

        input = document.createElement('input');
        input.name = this.name || input.name;
        input.accept = this.accept || input.accept;
        input.multiple = this.multiple || input.multiple;
        input.type = 'file';
        input.style.display = 'none';
        input.addEventListener('input', event => {
            event.preventDefault();
            event.stopPropagation();
            const { files } = event.target;
            if (!files.length || this.FILE_LIMIT < this.filesLength + files.length) {
                return;
            }
            this.form.appendChild(input);
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