export function translateStatus (status) {
    switch (status) {
        case 'ON_SHARING':
            return '나눔중';
        case 'COMPLETE_SHARING':
            return '나눔완료';
        case 'EXPIRED':
            return '기간만료';
        case 'FULL_PARTICIPANTS':
            return '모집완료';
        case 'ON_PARTICIPATING':
            return '모집중';
        default:
            return '';
    }
}

export function translateDateTime (dateTime) {
    const date = new Date(dateTime);
    return date.getFullYear() + '년 '
        + (date.getMonth() + 1) + '월 '
        + date.getDate() + '일 '
        + date.getHours() + '시 '
        + date.getMinutes() + '분';
}