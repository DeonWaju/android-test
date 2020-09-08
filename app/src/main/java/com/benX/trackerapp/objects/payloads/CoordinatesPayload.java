package com.benX.trackerapp.objects.payloads;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.benX.trackerapp.core.data.converters.TimeConverter;
import com.benX.trackerapp.core.data.entities.Coordinates;

import java.util.Date;

public class CoordinatesPayload {



    private double originLat;

    private double originLng;

    private double destinationLat;

    private double destinationLng;


    public static CoordinatesPayload create(Coordinates coordinates) {
        CoordinatesPayload payload = new CoordinatesPayload();
        payload.originLat = coordinates.getOriginLat();
        payload.destinationLat = coordinates.getDestinationLat();
        payload.originLng = coordinates.getOriginLng();
        payload.destinationLng = coordinates.getDestinationLng();
        return payload;
    }

    @NonNull
    @Override
    public String toString() {
        return "CoordinatesPayload{" +
                "originLat=" + originLat + '\'' +
                "destinationLat=" + destinationLat + '\'' +
                "originLng=" + originLng + '\'' +
                "destinationLng=" + destinationLng + '\'' +
                '}';
    }
}
