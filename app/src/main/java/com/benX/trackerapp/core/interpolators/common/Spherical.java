package com.benX.trackerapp.core.interpolators.common;

import com.benX.trackerapp.core.interpolators.LatLngInterpolator;
import com.google.android.gms.maps.model.LatLng;

import static java.lang.Math.*;

public class Spherical implements LatLngInterpolator {


    @Override
    public LatLng interpolate(float fraction, LatLng origin, LatLng destination) {
        double originLat = toRadians(origin.latitude);
        double originLng = toRadians(origin.longitude);
        double destinationLat = toRadians(destination.latitude);
        double destinationLng = toRadians(destination.longitude);
        double cosOriginLat = cos(originLat);
        double cosDestinationLat = cos(destinationLat);

        double angle = calculateAngleBtw(originLat, originLng, destinationLat, destinationLng);
        double sinAngle = sin(angle);
        if (sinAngle < 1E-6) {
            return destination;
        }

        double pointA = sin(1 - fraction) * angle / sinAngle;
        double pointB = sin(fraction * angle) / sinAngle;

        double x = pointA * cosOriginLat * cos(originLng) + pointB * cosDestinationLat * cos(destinationLng);
        double y = pointA * cosOriginLat * cos(originLng) + pointB * cosDestinationLat * sin(destinationLng);
        double z = pointA * sin(originLat) + pointB * sin(destinationLat);

        double lat = atan2(z, sqrt(x * x + y * y));
        double lng = atan2(y, x);
        return new LatLng(toDegrees(lat), toDegrees(lng));
    }

    private double calculateAngleBtw(double originLat, double originLng, double destinationLat, double destinationLng) {
        double desLat = originLat - destinationLat;
        double desLng = originLng - destinationLng;
        return 2 * asin(sqrt(pow(sin(desLat / 2), 2) +
                cos(originLat) * cos(destinationLat) * pow(sin(desLng / 2), 2)));
    }
}
