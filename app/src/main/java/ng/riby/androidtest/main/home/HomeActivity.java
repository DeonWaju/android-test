package ng.riby.androidtest.main.home;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import ng.riby.androidtest.R;
import ng.riby.androidtest.core.base.BaseActivity;
import ng.riby.androidtest.core.helpers.MapHelper;
import ng.riby.androidtest.core.helpers.MarkerAnimationHelper;
import ng.riby.androidtest.core.helpers.UIHelper;
import ng.riby.androidtest.core.interpolators.common.Spherical;
import ng.riby.androidtest.core.listeners.schedulers.AppRxSchedulers;
import ng.riby.androidtest.databinding.ActivityHomeBinding;
import ng.riby.androidtest.main.home.rvm.HomeViewModel;
import ng.riby.androidtest.util.AppUtil;
import ng.riby.androidtest.util.LogUtil;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class HomeActivity extends BaseActivity<ActivityHomeBinding> implements View.OnClickListener {

    private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 2171;

    ActivityHomeBinding binding;

    @Inject
    MapHelper mapHelper;

    @Inject
    AppRxSchedulers appRxSchedulers;
//    private AppRxSchedulers appRxSchedulers = new AppRxSchedulers();

    @Inject
    UIHelper uiHelper;

    @Inject
    HomeViewModel viewModel;

    private Marker marker;

    private boolean isFirstTimeFlag = true;

    private GoogleMap googleMap;

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();

//        FusedLocationProviderClient providerClient = LocationServices.getFusedLocationProviderClient(this);
//        mapHelper = new MapHelper(getResources());
//        uiHelper = new UIHelper(this);
//        viewModel = new HomeViewModel(this, appRxSchedulers, uiHelper.getLocationRequest(), providerClient, mapHelper);

        binding.setHomeViewModel(viewModel);
        binding.executePendingBindings();
        binding.setLifecycleOwner(this);

        if (!uiHelper.checkPlayServiceAvailable()) {
            showToast("Can't find play service package");
            finish();
        } else {
            checkLocationPermission();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(googleMap -> {
            mapHelper.defaultMapSettings(googleMap);
            this.googleMap = googleMap;
            startListenNewLocation();
        });

        binding.startBtn.setOnClickListener(this);
        binding.currentLocationImageButton.setOnClickListener(this);
    }

    private void checkLocationPermission() {
        if (!uiHelper.checkLocationProvideEnabled()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_FINE_LOCATION);
            return;
        }
        if (!uiHelper.checkLocationProvideEnabled()) {
            AppUtil.showDialog(this, getResources().getString(R.string.need_location),
                    getResources().getString(R.string.location_content),
                    () -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)), "Switch On", false);

            viewModel.requestLocationUpdate();
        }


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.currentLocationImageButton:
                if (marker == null || googleMap == null)
                    return;
                mapHelper.animateCamera(marker.getPosition(), googleMap);
                break;
            case R.id.startBtn:
                if (binding.startBtn.getText().toString().equals("START")) {
                    startListenNewLocation();
                    binding.startBtn.setText("STOP");
                } else if (binding.startBtn.getText().toString().equals("STOP")){
                    binding.startBtn.setText("START");
                }
                break;
        }
    }

    private void startListenNewLocation() {
        viewModel.currentLocation()
                .observe(this, location -> {
                    assert location != null;
                    LogUtil.w("Location received:" + location.getLatitude() + " , " + location.getLongitude());
                    if (isFirstTimeFlag) {
                        isFirstTimeFlag = false;
                        mapHelper.animateCamera(new LatLng(location.getLatitude(), location.getLongitude()), googleMap);
                        startDistanceTracking();
                    }
                    showOrAnimateMaker(location);
                });
    }

    private void startDistanceTracking() {
        viewModel.beginLocationTracking();
        viewModel.getDistanceTracker()
                .observe(this, distance -> {
                    LogUtil.w("Total distance covered:" + distance);
                    binding.distanceCoveredTextView.setText(distance);
                    showToast("Total distance covered:" + distance);
                });
    }

    private void showOrAnimateMaker(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (marker == null) {
            marker = googleMap.addMarker(mapHelper.getCurrentMarkerOptions(latLng));
        } else {
            MarkerAnimationHelper.animateMarker(marker, latLng, new Spherical());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_FINE_LOCATION) {
            int val = grantResults[0];
            if (val == PackageManager.PERMISSION_DENIED) {
                showToast("Location permission was denied");
                finish();
            } else if (val == PackageManager.PERMISSION_GRANTED) {
                viewModel.requestLocationUpdate();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
