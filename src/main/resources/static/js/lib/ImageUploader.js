class ImageUploader {

    constructor(args) {
        this.FILE_LIMITS = 5;
        this.files = [];

        args.templateSource && this.setTemplateSource(args.templateSource);
        args.input && this.setInput(args.input);
        args.preview && this.setPreview(args.preview);
        args.dropzone && this.setDropZone(args.dropzone);
    }

    setTemplateSource(templateSource) {
        this.template = Handlebars.compile(templateSource);
    }

    setInput(element) {
        this.input = element;
        this.input.addEventListener('input', validateInputFiles.bind(this));

        function validateInputFiles({ target: { files }}) {
            const feedback = this.input.closest('form').querySelector('.feedback');
            feedback.classList.remove('on');
            if (this.FILE_LIMITS < this.files.length + files.length) {
                feedback.classList.add('on');
                return;
            }
            this.files = [...this.files, ...files];
            this.renderPreview.bind(this)(this.files);
            this.input.value = '';
        }
    }

    setPreview(element) {
        this.preview = element;
        this.preview.addEventListener('mouseover', updateMainThumbnail.bind(this));

        function updateMainThumbnail({ target }) {
            const sub = target.closest('.sub');
            if (!sub) {
                return;
            }

            const index = [...sub.children].indexOf(target);
            if (-1 < index && target.tagName === 'IMG') {
                const main = this.preview.querySelector('.main');
                main.innerHTML = '';
                main.appendChild(target.cloneNode());
            }
        }
    }

    setDropZone(element) {
        this.dropzone = element;
        this.dropzone.addEventListener('dragover', e => {
            e.preventDefault();
            e.stopPropagation();
        });
        this.dropzone.addEventListener('drop', dropImageFileInputHandler.bind(this));

        function dropImageFileInputHandler(e) {
            e.preventDefault();
            e.stopPropagation();
            const files = e.dataTransfer.files;
            const feedback = this.input.closest('form').querySelector('.feedback');
            feedback.classList.remove('on');
            if (this.FILE_LIMITS < this.files.length + files.length) {
                feedback.classList.add('on');
                return;
            }
            this.files = [...this.files, ...files];
            this.renderPreview.bind(this)(this.files);
        }

        return this;
    }

    delegate(element) {
        this.input.style.display = 'none';
        this.button = element;
        this.button.addEventListener('click', clickFileInput.bind(this));

        function clickFileInput(e) {
            e.preventDefault();
            this.input.click();
        }

        return this;
    }

    draggable() {
        const sub = this.preview.querySelector('.sub');
        sub.addEventListener('dragstart', prepareDragData);
        sub.addEventListener('dragover', dragoverHandler);
        sub.addEventListener('drop', dropHandler.bind(this));

        function prepareDragData(e) {
            if (e.target.tagName !== 'IMG') {
                return;
            }
            e.dataTransfer.setData("index", indexOf(e.target));
            e.dropEffect = "move";
        }

        function dragoverHandler(e) {
            e.preventDefault();
            e.stopPropagation();
            e.dataTransfer.dropEffect = "move";
        }

        function dropHandler(e) {
            e.preventDefault();
            e.stopPropagation();
            if (!e.dataTransfer.getData("index")) {
                return;
            }
            if (e.target.tagName !== 'IMG') {
                return;
            }
            const dropIndex = indexOf(e.target);
            const dragIndex = parseInt(e.dataTransfer.getData("index"));
            this.files = swap(this.files, dropIndex, dragIndex);
            this.renderPreview(this.files);
        }

        function indexOf(target) {
            return [...sub.children].indexOf(target);
        }

        function swap(array, i, j) {
            const temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            return array;
        }

        return this;
    }

    renderPreview(files) {
        const main = this.preview.querySelector('.main');
        const sub = this.preview.querySelector('.sub');
        main.innerHTML = '';
        sub.innerHTML = '';

        Promise
            .all(files.map(this.renderImageFile.bind(this)))
            .then(renderThumbnail.bind(this));

        function renderThumbnail(images) {
            const defaultThumbnail = Array(this.FILE_LIMITS - images.length).fill(this.template());
            sub.insertAdjacentHTML('afterbegin', [...images, ...defaultThumbnail].join(''));
            main.insertAdjacentHTML('afterbegin', images[0]);
        }
    }

    renderImageFile(file) {
        return new Promise(resolve => {
            function renderTemplate({ target: { result }}) {
                resolve(this.template({
                   src: result,
                   alt: file.name
                }));
            }

            const fileReader = new FileReader();
            fileReader.addEventListener('load', renderTemplate.bind(this));
            fileReader.readAsDataURL(file);
        });
    }

}

export default ImageUploader;