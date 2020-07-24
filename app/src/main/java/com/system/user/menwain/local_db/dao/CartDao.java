package com.system.user.menwain.local_db.dao;

import com.system.user.menwain.local_db.entity.Cart;

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

    @Query("update Cart set quantity =quantity + :c_quantity, per_unit_price = per_unit_price + :c_per_unit_price where p_id =:c_id")
    void updateCartQuantity(Integer c_id, Integer c_quantity, float c_per_unit_price);

    @Query("update Cart set quantity =  :quantity,per_unit_price =  :c_per_unit_price WHERE p_id =:id")
    void updateCartQuantityByPid(Integer id, Integer quantity,float c_per_unit_price);

    /**
     * Updating only quantity
     * By p_id
     */
    @Query("update Cart set quantity=:quantity WHERE p_id = :p_id")
    void update(int quantity, int p_id);


    @Delete
    public void delete(Cart cart);

    @Query("Delete FROM Cart")
    void deleteAllRecords();

    @Query("select sum(per_unit_price) from cart")
    LiveData<Float> getTotalCartPrice();

    @Query("select sum(quantity) from cart")
    LiveData<Integer> getTotalItemQuantity();
}
