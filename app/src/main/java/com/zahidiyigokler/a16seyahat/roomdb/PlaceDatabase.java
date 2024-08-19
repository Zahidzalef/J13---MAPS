package com.zahidiyigokler.a16seyahat.roomdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.zahidiyigokler.a16seyahat.model.Place;

@(entities = {Place.class}, version = 1)
public abstract class PlaceDatabase extends RoomDatabase {
    public abstract PlaceDao placeDao();
}