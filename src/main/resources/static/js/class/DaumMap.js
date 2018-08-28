import {$} from '../lib/utils.js';
import '../lib/underscore.min.js';

class DaumMap {

    constructor() {
        this.DEFAULT_LATLNG = new daum.maps.LatLng(37.56708, 126.97868);

        const mapContainer = $('#map');
        const mapOption = {
            center: this.DEFAULT_LATLNG,
            level: 3,
            mapTypeId: daum.maps.MapTypeId.ROADMAP
        };

        this.map = new daum.maps.Map(mapContainer, mapOption);
        this.marker = new daum.maps.Marker({
            position: this.DEFAULT_LATLNG,
            draggable: true,
            map: this.map
        });
        this.geocoder = new daum.maps.services.Geocoder();

        this.geocoder.addressSearch = _.debounce(this.geocoder.addressSearch, 500);
    }

    setZoomable(b) {
        this.map.setZoomable(b)
    }

    setZoomControl() {
        const zoomControl = new daum.maps.ZoomControl();
        this.map.addControl(zoomControl, daum.maps.ControlPosition.RIGHT);
    }

    addMarkerListener(event, callback) {
        daum.maps.event.addListener(this.marker, event, callback);
    }

    getPosition() {
        return this.marker.getPosition();
    }

    setPosition(latitude, longitude) {
        const latLng = new daum.maps.LatLng(latitude, longitude);
        this.marker.setPosition(latLng);
        this.map.setCenter(latLng);
    }

    getAddress(callback) {
        const position = this.getPosition();
        this.geocoder.coord2Address(position.getLng(), position.getLat(), (result, status) => {
            if (status === daum.maps.services.Status.OK) {
                const address = result[0].road_address || result[0].address;
                callback(address);
            }
        });
    }

    addressSearch(address, callback) {
        this.geocoder.addressSearch(address, (result, status) => {
            if (status === daum.maps.services.Status.OK) {
                const latLng = new daum.maps.LatLng(result[0].y, result[0].x);
                callback(latLng);
            }
        });
    }

    getCurrentLocation(callback) {
        navigator.geolocation.getCurrentPosition(callback);
    }
}

export default DaumMap;











