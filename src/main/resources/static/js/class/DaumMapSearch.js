import {$} from '../lib/utils.js';
import DaumMap from "./DaumMap.js";

/**
 * reference : http://apis.map.daum.net/web/sample/keywordList/
 * WARNING : refactoring NOT AT ALL...
 */

class DaumMapSearch extends DaumMap {

    constructor(onSetAddress, onError) {
        super();

        this.markers = [];
        this.places = new daum.maps.services.Places();

        this.imgMarker = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png';

//         var imageSrc = '/images/map-marker.png',
//             imageSize = new daum.maps.Size(40, 40), // 마커이미지의 크기입니다
//             imageOption = {offset: new daum.maps.Point(20, 40)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
//
//         // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
//         var markerImage = new daum.maps.MarkerImage(imageSrc, imageSize, imageOption);
//
// // 마커를 생성합니다
//         this.marker = new daum.maps.Marker({
//             image: markerImage // 마커이미지 설정
//         });
//
//         this.marker.setMap(this.map);


        this.onSetAddress = onSetAddress;
        this.onError = onError;
    }

    searchPlaces(keyword) {
        if (!keyword.replace(/^\s+|\s+$/g, '')) {
            this.onError.call('키워드를 입력해주세요!');
            return false;
        }

        this.places.keywordSearch(keyword, this.onSearchPlaces.bind(this));
    }

    onSearchPlaces(data, status, pagination) {
        if (status === daum.maps.services.Status.OK) {
            this.showSearchAddressList();
            this.displayPlaces(data);
            this.displayPagination(pagination);
        } else if (status === daum.maps.services.Status.ZERO_RESULT) {
            this.hideSearchAddressList();
            this.onError.call('검색 결과가 존재하지 않습니다.');
            return;
        } else if (status === daum.maps.services.Status.ERROR) {
            this.hideSearchAddressList();
            this.onError.call('검색 결과 중 오류가 발생했습니다.');
            return;
        }
    }

    showSearchAddressList() {
        $('#menu_wrap').style.display = 'block';
    }

    hideSearchAddressList() {
        $('#menu_wrap').style.display = 'none';
    }

    displayPlaces(places) {
        let listEl = $('#placesList');
        let menuEl = $('#menu_wrap');
        let fragment = document.createDocumentFragment();
        let bounds = new daum.maps.LatLngBounds();

        this.removeAllChildNodes(listEl);
        this.removeMarker();

        for (let i = 0; i < places.length; i++) {
            const placePosition = new daum.maps.LatLng(places[i].y, places[i].x);
            const marker = this.addMarker(placePosition, i);
            const itemEl = this.getListItem(i, places[i]);

            // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
            // LatLngBounds 객체에 좌표를 추가합니다
            bounds.extend(placePosition);

            daum.maps.event.addListener(marker, 'click', this.onSetAddress.bind(places[i]));
            itemEl.addEventListener('click', this.onSetAddress.bind(places[i]));

            fragment.appendChild(itemEl);
        }

        listEl.appendChild(fragment);
        menuEl.scrollTop = 0;

        this.map.setBounds(bounds);
    }

    removeAllChildNodes(el) {
        el.innerHTML = '';
    }

    removeMarker() {
        this.markers.forEach(marker => { marker.setMap(null); });
        this.markers = [];
    }

    addMarker(position, idx, title) {
        const imageSize = new daum.maps.Size(36, 37);  // 마커 이미지의 크기
        const imgOptions = {
            spriteSize : new daum.maps.Size(36, 691), // 스프라이트 이미지의 크기
            spriteOrigin : new daum.maps.Point(0, (idx * 46) + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new daum.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        };
        const markerImage = new daum.maps.MarkerImage(this.imgMarker, imageSize, imgOptions);

        const marker = new daum.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage
        });

        marker.setMap(this.map); // 지도 위에 마커를 표출합니다
        this.markers.push(marker);  // 배열에 생성된 마커를 추가합니다

        return marker;
    }

    getListItem(index, places) {
        const el = document.createElement('li');
        el.innerHTML = this.templateSearchItem(index, places);
        el.className = 'item';
        return el;
    }

    templateSearchItem(index, places) {
        return `<span class='markerbg marker_${index+1}'></span>
                <div class='info'>
                    <h5>${places.place_name}</h5>
                    ${ places.road_address_name ?
                        `<span>${places.road_address_name}</span>
                        <span class='jibun gray'>${places.address_name}</span>`
                        : `<span>${places.address_name}</span>`
                    }
                    <span class='tel'>${places.phone}</span>
                </div>
                `;
    }

    displayPagination(pagination) {
        let paginationEl = $('#pagination');
        let fragment = document.createDocumentFragment();

        while (paginationEl.hasChildNodes()) {
            paginationEl.removeChild (paginationEl.lastChild);
        }

        for (let i = 1; i <= pagination.last; i++) {
            const el = document.createElement('a');
            el.href = "#";
            el.innerHTML = i;

            if (i === pagination.current) {
                el.className = 'on';
            } else {
                el.onclick = (i => {
                    return function() {
                        pagination.gotoPage(i);
                    }
                })(i);
            }

            fragment.appendChild(el);
        }
        paginationEl.appendChild(fragment);
    }
}

export default DaumMapSearch;