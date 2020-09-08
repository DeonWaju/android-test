package com.benX.trackerapp.core.interpolators.common;

import com.benX.trackerapp.core.interpolators.LatLngInterpolator;
import com.google.android.gms.maps.model.LatLng;

public class Linear implements LatLngInterpolator {

    @Override
    public LatLng interpolate(float fraction, LatLng origin, LatLng destination) {
        double lat = (destination.latitude - origin.latitude) * fraction + origin.latitude;
        double lng = (destination.longitude - origin.longitude) * fraction + origin.longitude;
        return new LatLng(lat, lng);
    }
}
