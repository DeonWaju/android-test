package com.benX.trackerapp.core.base;

import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;

public class CoreViewModel<S extends BaseRepository, T, P, V> extends BaseViewModel {

    public MutableLiveData<V> validation = new MutableLiveData<>();

    protected MutableLiveData<List<T>> currentListData = new MutableLiveData<>();
    protected MutableLiveData<T> currentData = new MutableLiveData<>();
    protected HashMap<String, Object> query = new HashMap<>();

    protected V currentValidation;
    protected S repository;
    protected T current;
    protected String error;


    @Bindable
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
//        notifyPropertyChanges(com.benX.trackerapp.BR.error);
    }

    @Bindable
    public T getCurrent() {
        return current;
    }

    public void setCurrent(T current) {
        this.current = current;
//        notifyPropertyChanges(com.benX.trackerapp.BR.current);
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.remove(callback);
    }

    public void notifyChange() {
        callbacks.notifyCallbacks(this, 0, null);
    }

    public MutableLiveData<List<T>> getCurrentListData(){
        return currentListData;
    }

    public void setCurrentData(MutableLiveData<T> currentData) {
        this.currentData = currentData;
    }

    public MutableLiveData<T> getCurrentData() {
        return currentData;
    }

    public void setCurrentListData(MutableLiveData<List<T>> currentListData) {
        this.currentListData = currentListData;
    }

    public void get(String id) {

    }

    public void performAction(Flowable<T> flowable) {
        disposeDisposable();
    }


}
