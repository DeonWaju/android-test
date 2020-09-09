package ng.riby.androidtest.main.home.rvvm;

import android.content.Context;

import ng.riby.androidtest.core.base.BaseRepository;
import ng.riby.androidtest.core.data.dao.CoordinatesDao;
import ng.riby.androidtest.core.data.entities.Coordinates;
import ng.riby.androidtest.core.data.manager.DatabaseManager;
import ng.riby.androidtest.objects.payloads.CoordinatesPayload;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

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

}
