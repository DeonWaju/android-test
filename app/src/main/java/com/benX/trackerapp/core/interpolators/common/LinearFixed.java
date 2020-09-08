package com.benX.trackerapp.core.interpolators.common;

import com.benX.trackerapp.core.interpolators.LatLngInterpolator;
import com.google.android.gms.maps.model.LatLng;

public class LinearFixed implements LatLngInterpolator {


    @Override
    public LatLng interpolate(float fraction, LatLng origin, LatLng destination) {
        double lat = (destination.latitude - origin.latitude) * fraction + origin.latitude;
        double lngDelta = destination.longitude - origin.longitude;
        if (Math.abs(lngDelta) > 180) {
            lngDelta -= Math.signum(lngDelta) * 360;
        }
        double lng = lngDelta * fraction + origin.longitude;
        return new LatLng(lat, lng);
    }
}
