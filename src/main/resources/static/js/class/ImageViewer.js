class ImageViewer {

    constructor(...args) {
        this.template = {};

        args.viewTemplate && this.setViewTemplate(args.viewTemplate);
        args.imageViewTemplate && this.setImageViewTemplate(args.imageViewTemplate);
        args.thumbnailViewTemplate && this.setThumbnailViewTemplate(args.thumbnailViewTemplate);
        args.view && this.setViewer(args.view);
        args.width && this.setWidth(args.width);
        args.height && this.setHeight(args.height);
    }

    setViewTemplate(template) {
        this.template.view = template;
    }

    setImageViewTemplate(template) {
        this.template.imageView = template;
    }

    setThumbnailViewTemplate(template) {
        this.template.thumbnailView = template;
    }

    setView(element) {
        this.view = element;
        this.view.innerHTML = '';
        this.view.insertAdjacentHTML('afterbegin', this.template.view());
        this.view.main = this.view.querySelector('.image-viewer-main');
    }

    getMainWidth() {
        return this.view.main.offsetWidth;
    }

    setMainWidth(width) {
        this.view.main.style.width = width;
    }

    getMainHeight() {
        return this.view.main.offsetHeight;
    }

    setMainHeight(height) {
        this.view.main.style.height = height;
    }

    setThumbnailsCount(count) {
        const width = parseInt(this.getMainWidth() * 0.9 / count) + 'px;';
        const height = width;
        const marginRight = parseInt((this.getMainWidth() * 0.1 / (count - 1))) + 'px;';

        this.view.main.classList.add('mb-2');
        this.view.thumbnails = this.view.querySelector('.image-viewer-thumbnails');
        this.view.thumbnails.style.display = 'block';
        this.view.thumbnails.insertAdjacentHTML('beforeend', this.template.thumbnailView({
            width,
            height,
            'margin-right': marginRight
        }).repeat(count));
    }

    enableThumbnailMouseOver() {
        this.view.thumbnails.addEventListener('mouseover', this._defaultMouseOverHandler.bind(this));
    }

    _defaultMouseOverHandler(event) {
        event.preventDefault();
        event.stopPropagation();

        const {target} = event;
        if (target.tagName !== 'IMG') {
            return;
        }
        const {src, alt} = target;
        this.setImage(src, alt);
    }

    enableDrag() {
        this.view.thumbnails.addEventListener('dragstart', this._defaultDragStartHandler.bind(this));
        this.view.thumbnails.addEventListener('dragover', this._defaultDragOverHandler);
        this.view.thumbnails.addEventListener('drop', this._defaultDropHandler.bind(this));
    }

    _defaultDragStartHandler(event) {
        event.stopPropagation();
        event.dataTransfer.dropEffect = 'move';

        const {target} = event;
        if (target.tagName !== 'IMG') {
            return;
        }

        event.dataTransfer.setData('index', this._thumbnailIndexOf.call(this, target));
    }

    _defaultDragOverHandler(event) {
        event.preventDefault();
        event.stopPropagation();
        event.dataTransfer.dropEffect = 'move';
    }

    _defaultDropHandler(event) {
        event.preventDefault();
        event.stopPropagation();
        event.dataTransfer.dropEffect = 'move';

        const {target, dataTransfer} = event;
        if (target.tagName !== 'IMG') {
            return;
        }

        const dragIndex = parseInt(dataTransfer.getData('index'));
        const dropIndex = this._thumbnailIndexOf.call(this, target);
        this._swapThumbnail.call(this, dragIndex, dropIndex);
    }

    _thumbnailIndexOf(target) {
        return [...this.view.thumbnails.querySelectorAll('img')].indexOf(target);
    }

    _swapThumbnail(dragIndex, dropIndex) {
        const thumbnails = this.view.thumbnails.querySelectorAll('img');
        const {src: dragSrc, alt: dragAlt} = thumbnails[dragIndex];
        const {src: dropSrc, alt: dropAlt} = thumbnails[dropIndex];

        thumbnails[dragIndex].src = dropSrc;
        thumbnails[dragIndex].alt = dropAlt;
        thumbnails[dropIndex].src = dragSrc;
        thumbnails[dropIndex].alt = dragAlt;
    }

    renderImages(files) {
        Promise
            .all(files.map(this._renderImageFile))
            .then(this._renderThumbnail.bind(this));
    }

    _renderImageFile(file) {
        return new Promise(resolve => {
            const fileReader = new FileReader();
            fileReader.addEventListener('load', ({target: {result}}) => {
                resolve({src: result, alt: file.name});
            });
            fileReader.readAsDataURL(file);
        });
    }

    _renderThumbnail(images) {
        Array(this.view.thumbnails.children.length).fill().map((_, index) => {
            const {src, alt} = images[index] || {};
            this.setThumbnailImage(index, src, alt);
        });
        this.setImage(images[0].src, images[0].alt);
    }

    setImage(src, alt) {
        const img = this.view.main.querySelector('img');
        if (!src) {
            img && img.remove();
            return;
        }
        if (!img) {
            this.view.main.insertAdjacentHTML('afterbegin', this.template.imageView(src, alt));
            return;
        }
        img.src = src;
        img.alt = alt;
    }

    setThumbnailImage(index, src, alt) {
        const thumbnail = this.view.thumbnails.children[index];
        const thumbnailImg = thumbnail.querySelector('img');
        if (!src) {
            thumbnailImg && thumbnailImg.remove();
            return;
        }
        if (!thumbnailImg) {
            thumbnail.insertAdjacentHTML('afterbegin', this.template.imageView(src, alt));
            return;
        }
        thumbnailImg.src = src;
        thumbnailImg.alt = alt;
    }

}

export default ImageViewer;