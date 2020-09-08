package com.benX.trackerapp.core.listeners;

public interface DialogListener {

    void onPositiveClick();

    default void onNegativeClick() {
    }
}
