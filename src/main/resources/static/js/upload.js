import ImageUploader from './lib/ImageUploader.js';

new ImageUploader({
    templateSource: $('#template-thumbnail-image').innerHTML,
    input: $('input[name=files]'),
    preview: $('.image-preview'),
    dropzone: $('form')
})
    .delegate($('.btn.upload'))
    .draggable();