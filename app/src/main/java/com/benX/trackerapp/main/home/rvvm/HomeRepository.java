package com.benX.trackerapp.main.home.rvvm;

import android.content.Context;

import com.benX.trackerapp.core.base.BaseRepository;
import com.benX.trackerapp.core.data.dao.CoordinatesDao;
import com.benX.trackerapp.core.data.entities.Coordinates;
import com.benX.trackerapp.core.data.manager.DatabaseManager;
import com.benX.trackerapp.core.helpers.MapHelper;
import com.benX.trackerapp.core.listeners.schedulers.AppRxSchedulers;
import com.benX.trackerapp.objects.payloads.CoordinatesPayload;
import com.benX.trackerapp.util.LogUtil;
import com.google.android.gms.location.FusedLocationProviderClient;

import org.reactivestreams.Publisher;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class HomeRepository implements BaseRepository<Coordinates, CoordinatesPayload> {

    public Context context;
    public CoordinatesDao coordinatesDao;


    @Inject
    public HomeRepository(Context context) {
        this.context = context;
        this.coordinatesDao = DatabaseManager.getInstance(context).coordinatesDao();
    }


    @Override
    public Flowable<Coordinates> create(CoordinatesPayload data, HashMap query) {
        return null;
    }

    @Override
    public Flowable<Coordinates> edit(String id, CoordinatesPayload data, HashMap query) {
        return null;
    }

    @Override
    public Flowable<Coordinates> get(String id, HashMap query) {
        return null;
    }

    @Override
    public Flowable<List<Coordinates>> find(HashMap query) {
        return null;
    }

    @Override
    public Flowable<Coordinates> delete(String id) {
        return null;
    }

//    private Flowable<Coordinates> executeRequest(Flowable<CoordinatesPayload> coordinatesPayloadFlowable) {
//        return coordinatesPayloadFlowable
//                .map()
//                .observeOn(Schedulers.io())
//                .subscribeOn(Schedulers.io())
//                .flatMap((Function<Coordinates, Publisher<Coordinates>>) coordinates -> {
//                    LogUtil.w("coordinate:" + coordinates);
//                    long saved = coordinatesDao.save(coordinates);
//                    LogUtil.w("saved coordinates:" + saved);
//                    return Flowable.just(coordinates);
//                });
//    }

}
