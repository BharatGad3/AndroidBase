package com.base.maps;

/**
 * Notification by the view that the map is ready.
 */
public interface IMapPresenter {
    void onMapReady();
    void onMapDestroyed();
}