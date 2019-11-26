package com.system.user.menwain.dao;

import com.system.user.menwain.entity.Cart;
import com.system.user.menwain.model.UpdateCartQuantity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CartDao {

    @Insert
    Long insert(Cart cart);

    @Query("select * from Cart ")
    public LiveData<List<Cart>> getCartData();

    @Query("select * from Cart where id =:id")
    public LiveData<List<Cart>> getCart(int id);

    @Update
    void update(Cart cart);

    @Query("update Cart set quantity =:c_quantity where id =:c_id")
    public void updateCartQuantity(Integer c_id, Integer c_quantity);

    @Query("Update Cart set quantity = quantity + :quantity WHERE id =:id")
    void updateCartQuantityByPid(Integer id, Integer quantity);

    @Delete
    public void delete(Cart cart);

    @Query("Delete FROM Cart")
    void deleteAllRecords();

    @Query("select sum(per_unit_price) from cart")
    LiveData<Float> getTotalCartPrice();
}