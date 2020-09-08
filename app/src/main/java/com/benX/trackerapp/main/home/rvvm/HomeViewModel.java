package com.benX.trackerapp.main.home.rvvm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.benX.trackerapp.core.data.dao.CoordinatesDao;
import com.benX.trackerapp.core.data.entities.Coordinates;
import com.benX.trackerapp.core.data.manager.DatabaseManager;
import com.benX.trackerapp.core.helpers.MapHelper;
import com.benX.trackerapp.core.listeners.schedulers.AppRxSchedulers;
import com.benX.trackerapp.util.AppUtil;
import com.benX.trackerapp.util.LogUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.PendingResult;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class HomeViewModel extends ViewModel {

    private static final String TAG = HomeViewModel.class.getSimpleName();

    private final AppRxSchedulers appRxSchedulers;
    private final LocationRequest locationRequest;
    private final FusedLocationProviderClient locationProviderClient;
    private final MapHelper mapHelper;

    public Context context;
    private CoordinatesDao coordinatesDao;

    private LocationCallback locationCallback;
    private Location locationTrackingCoordinates;
    private Location currentLocation;

    private MediatorLiveData<Location> locationLiveData = new MediatorLiveData<>();
    private MediatorLiveData<String> distanceTracker = new MediatorLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private boolean locationFirstTimeFlag = true;
    private long totalDistanceCovered = 0L;


    //    @Inject
    public HomeViewModel(Context context, AppRxSchedulers appRxSchedulers, LocationRequest locationRequest,
                         FusedLocationProviderClient locationProviderClient, MapHelper mapHelper) {
        this.context = context;
        this.coordinatesDao = DatabaseManager.getInstance(context).coordinatesDao();
        this.appRxSchedulers = appRxSchedulers;
        this.locationProviderClient = locationProviderClient;
        this.locationRequest = locationRequest;
        this.mapHelper = mapHelper;

        provideLocationCallback();
        getLastKnown();
    }

    public LiveData<Location> currentLocation() {
        return locationLiveData;
    }

    public LiveData<String> getDistanceTracker() {
        return distanceTracker;
    }

    private void provideLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                if (locationFirstTimeFlag) {
                    currentLocation = location;
                    locationLiveData.setValue(currentLocation);
                }
                float accuracy = location.getAccuracy();
                if (!currentLocation.hasAccuracy() || accuracy > 10f)
                    return;
                currentLocation = location;
                locationLiveData.setValue(currentLocation);
            }
        };
    }

    private void getLastKnown() {
        FusedLocationProviderClient locationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        locationProviderClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        LogUtil.w("Lastkno:---->", location.toString());
                        currentLocation = location;
                        locationLiveData.setValue(currentLocation);
                    } else {
                        LogUtil.w("IS NULL");
                    }
                })
                .addOnFailureListener(e -> {
                    LogUtil.w(TAG, "Error trying to get last GPS location");
                    e.printStackTrace();
                });
    }


    @SuppressLint("MissingPermission")
    public void requestLocationUpdate() {
        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    public void beginLocationTracking() {
        locationTrackingCoordinates = currentLocation;
        compositeDisposable.add(Observable.interval(10, TimeUnit.SECONDS)
                .subscribeOn(appRxSchedulers.threadPoolSchedulers())
                .subscribe(__ -> performDistanceCalculationCall()
                        , throwable -> {
                            LogUtil.w("location beginError:" + throwable);
                            beginLocationTracking();
                        }));

    }

    public void onStart() {
        beginLocationTracking();
    }

    private void performDistanceCalculationCall() {
        Location tempLocation = currentLocation;
        String[] destination = {tempLocation.getLatitude() + ",".concat(String.valueOf(tempLocation.getLongitude()))};
        String[] origins = {locationTrackingCoordinates.getLongitude() + ",".concat(String.valueOf(locationTrackingCoordinates.getLongitude()))};
        long id = 0;
        if (tempLocation != null && locationTrackingCoordinates != null) {
            Coordinates coordinates = new Coordinates();
            coordinates.setOriginLat(locationTrackingCoordinates.getLatitude());
            coordinates.setOriginLng(locationTrackingCoordinates.getLongitude());
            coordinates.setDestinationLat(tempLocation.getLatitude());
            coordinates.setDestinationLng(tempLocation.getLongitude());
            coordinates.setCreatedAt(new Date());
            id = coordinatesDao.save(coordinates);
            if (id > 0) {
                LogUtil.w("saved coord: --> " + id);
            }
        }
        double distanceCovered = 0;
        Coordinates coordinates = coordinatesDao.findById(id);
        if (coordinates != null && id != 0) {
            distanceCovered = AppUtil.distanceBetweenTwoPoint(coordinates.getOriginLat(),
                    coordinates.getOriginLng(), coordinates.getDestinationLat(), coordinates.getDestinationLng());
            distanceTracker.postValue(distanceCovered + " meters");
        } else {
            distanceCovered = AppUtil.distanceBetweenTwoPoint(locationTrackingCoordinates.getLatitude(),
                    locationTrackingCoordinates.getLongitude(), tempLocation.getLatitude(), tempLocation.getLongitude());
            distanceTracker.postValue(distanceCovered + " meters");
        }


        /**
         * You use this to calculate the distance too, I comment it because I need pay for
         * cloud google billing
         * */
//        DistanceMatrixApi.getDistanceMatrix(mapHelper.geoContextDistanceApi(), origins, destination)
//                .mode(TravelMode.WALKING)
//                .setCallback(new PendingResult.Callback<DistanceMatrix>() {
//                    @Override
//                    public void onResult(DistanceMatrix result) {
//                        locationTrackingCoordinates = tempLocation;
//                        long temp = result.rows[0].elements[0].distance.inMeters;
//                        totalDistanceCovered += temp;
//                        distanceTracker.postValue(getDistance());
//                    }
//
//                    @Override
//                    public void onFailure(Throwable e) {
//                        e.printStackTrace();
//                    }
//                });
    }

    private String getDistance() {
        return mapHelper.getDistanceInKiloMeters(totalDistanceCovered);
    }
}
