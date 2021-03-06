package ng.riby.androidtest.core.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import ng.riby.androidtest.core.data.entities.Coordinates;

import java.util.List;

@Dao
public interface CoordinatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long save(Coordinates coordinates);

    @Query("SELECT * from coordinates WHERE _id = :id ORDER BY createdAt LIMIT 1")
    Coordinates findById(long id);

    @Query("SELECT * from coordinates")
    LiveData<List<Coordinates>> getAll();

    @Delete
    void delete(Coordinates coordinates);

    @Query("DELETE FROM coordinates")
    void deleteAll();
}
