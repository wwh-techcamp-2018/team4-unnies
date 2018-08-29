export function templateCategory({ id, name }) {
    return `<a class="nav-item nav-link category-id" href="/categories/${id}">${name}</a>`;
}