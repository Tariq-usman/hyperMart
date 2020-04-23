package com.system.user.menwain.local_db.viewmodel;

import android.app.Application;

import com.system.user.menwain.local_db.entity.Cart;
import com.system.user.menwain.local_db.model.UpdateCartQuantity;
import com.system.user.menwain.local_db.model.UpdateProductQuantity;
import com.system.user.menwain.local_db.repository.CartRep;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CartViewModel extends AndroidViewModel
{
    private CartRep cartRep;
    private LiveData<List<Cart>> cartDataList;

    public CartViewModel(@NonNull Application application) {
        super(application);

        cartRep = new CartRep(application);
        cartDataList = cartRep.getCartData();
    }

    public void insertCart(Cart cart) {
        cartRep.insertCart(cart);
    }

    public void insertAllCart(Cart cart, UpdateCartQuantity updateCartQuantity) {
        cartRep.UpdateCartQuantiyByPid(updateCartQuantity,cart);
    }
    public void updateCart(Cart cart) {
        cartRep.updateCart(cart);
    }
    public void deleteCart( Cart cart) {
        cartRep.deleteCart(cart);
    }

    public void deleteAllRecord(Cart cart){
        cartRep.deleteAllRecords();
    }
    public LiveData<List<Cart>> getCartDataList() {
        return cartDataList;
    }

    public void updateCartQuantity(UpdateCartQuantity updateCartQuantity) {
        cartRep.UpdateCartQuantiy(updateCartQuantity);
    }

    public void updateCartQuantityByPid(UpdateCartQuantity updateCartQuantity, Cart cart) {
        cartRep.UpdateCartQuantiyByPid(updateCartQuantity, cart);
    }


    public void deleteAllCartRecords() {
        cartRep.deleteAllRecords();
    }

    public LiveData<Float> getTotalCartPrice()
    {
        return cartRep.getTotalCartPrice();
    }

    public LiveData<Integer> getTotalItemQuantity()
    {
        return cartRep.getTotalItemQuantity();
    }

}
