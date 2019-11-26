package com.system.user.menwain.database;

import android.content.Context;

import com.system.user.menwain.dao.CartDao;
import com.system.user.menwain.entity.Cart;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Cart.class}, version = 1,exportSchema = false)
public abstract class HyperMart extends RoomDatabase {

    private static HyperMart INSTANCE;
    public abstract CartDao cartDao();

    public static synchronized HyperMart getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    HyperMart.class, HyperMart.class.getName())
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }


}
