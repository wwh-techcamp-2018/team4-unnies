package com.baemin.nanumchan.utils;

public class DistanceUtils {

    private final static double EARTH_RADIUS = 6370986; // meters

    /**
     * 두 지점간의 거리 계산
     *
     * @param lat1 지점 1 위도
     * @param lon1 지점 1 경도
     * @param lat2 지점 2 위도
     * @param lon2 지점 2 경도
     * @return
     */
    public static double distanceInMeter(double lat1, double lon1, double lat2, double lon2) {
        double lat = Math.toRadians(lat2 - lat1);
        double lon = Math.toRadians(lon2 - lon1);

        double interim = Math.sin(lat / 2) * Math.sin(lat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lon / 2) * Math.sin(lon / 2);
        interim = 2 * Math.atan2(Math.sqrt(interim), Math.sqrt(1 - interim));

        return (float) (EARTH_RADIUS * interim);
    }
}
