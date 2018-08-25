export const $ = selector => document.querySelector(selector);

export const $all = selector => document.querySelectorAll(selector);

export const $index = element => [...element.parentElement.children].indexOf(element) + 1;