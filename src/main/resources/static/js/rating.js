document.addEventListener('submit', function (event) {
        if (!event.target.matches('.rating')) return;

        event.preventDefault();

        const selected = document.activeElement;

        if (!selected) return;

        const selectedIndex = parseInt(selected.getAttribute('data-star'), 10);
        const stars = Array.from(event.target.querySelectorAll('.star'));

        stars.forEach(function (star, index) {
            if (index < selectedIndex) {
                star.classList.add('selected');
            } else {
                star.classList.remove('selected');
            }
        });

        const previousRating = event.target.querySelector('.star[aria-pressed="true"]');

        if (previousRating) {
            previousRating.removeAttribute('aria-pressed');
        }
        selected.setAttribute('aria-pressed', true);

}, false);

const highlight = function (event) {
    const star = event.target.closest('.star');
    const form = event.target.closest('.rating');
    if (!star || !form) return;

    const selectedIndex = parseInt(star.getAttribute('data-star'), 10);
    const stars = Array.from(form.querySelectorAll('.star'));
    stars.forEach(function (star, index) {
        if (index < selectedIndex) {
            star.classList.add('selected');
        } else {
            star.classList.remove('selected');
        }
    });
};

document.addEventListener('mouseover', highlight, false);
document.addEventListener('focus', highlight, true);