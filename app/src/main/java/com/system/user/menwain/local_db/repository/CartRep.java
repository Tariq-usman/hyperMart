package com.system.user.menwain.local_db.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.system.user.menwain.local_db.dao.CartDao;
import com.system.user.menwain.local_db.database.HyperMart;
import com.system.user.menwain.local_db.entity.Cart;
import com.system.user.menwain.local_db.model.UpdateCartQuantity;
import com.system.user.menwain.local_db.model.UpdateCartQuantityByPid;

import java.util.List;

import androidx.lifecycle.LiveData;

public class CartRep {

    private CartDao cartDao;
    private LiveData<List<Cart>> cartData;


    public CartRep(Application application) {
        HyperMart database = HyperMart.getInstance(application);
        cartDao = database.cartDao();
        cartData = cartDao.getCartData();
    }

    public void insertCart(Cart cart) {
        new InsertCartDataAsyncTask(cartDao).execute(cart);
    }

    public LiveData<List<Cart>> getCartData() {
        return cartData;
    }

    public void updateCart(Cart cart) {
        new UpdateCartDataAsyncTask(cartDao).execute(cart);
    }

    public void deleteCart(Cart cart) {
        new DeleteCartDataAsyncTask(cartDao).execute(cart);
    }

    public void UpdateCartQuantiy(UpdateCartQuantity updateCartQuantity) {
        new UpdateCartQuantityAsyncTask(cartDao).execute(updateCartQuantity);
    }

    public LiveData<Float> getTotalCartPrice() {
        return cartDao.getTotalCartPrice();
    }

    public LiveData<Integer> getTotalItemQuantity() {
        return cartDao.getTotalItemQuantity();
    }

    public void deleteAllRecords() {
        new DeleteAllRecordsAsyncTask(cartDao).execute();
    }


    public void UpdateCartQuantiyByPid(UpdateCartQuantityByPid updateCartQuantityByPid) {
        new UpdateCartQuantityByPidAsyncTask(cartDao).execute(updateCartQuantityByPid);
    }

    private static class InsertCartDataAsyncTask extends AsyncTask<Cart, Void, Void> {

        private CartDao cartDao;

        private InsertCartDataAsyncTask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(Cart... cart) {
            cartDao.insert(cart[0]);
            return null;
        }
    }

    private static class UpdateCartDataAsyncTask extends AsyncTask<Cart, Void, Void> {

        private CartDao cartDao;

        private UpdateCartDataAsyncTask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(Cart... cart) {
            cartDao.update(cart[0]);
            return null;
        }
    }


    private static class DeleteCartDataAsyncTask extends AsyncTask<Cart, Void, Void> {

        private CartDao cartDao;

        private DeleteCartDataAsyncTask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(Cart... cart) {
            cartDao.delete(cart[0]);
            return null;
        }
    }

    private static class UpdateCartQuantityAsyncTask extends AsyncTask<UpdateCartQuantity, Void, Void> {

        private CartDao cartDao;

        private UpdateCartQuantityAsyncTask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(UpdateCartQuantity... updateCartQuantities) {
            cartDao.updateCartQuantity(
                    updateCartQuantities[0].getC_id(),
                    updateCartQuantities[0].getC_quantity(),
                    updateCartQuantities[0].getPer_unit_price());
            return null;
        }
    }

    private static class DeleteAllRecordsAsyncTask extends AsyncTask<Void, Void, Void> {

        private CartDao cartDao;

        private DeleteAllRecordsAsyncTask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cartDao.deleteAllRecords();
            return null;
        }
    }

    private static class UpdateCartQuantityByPidAsyncTask extends AsyncTask<UpdateCartQuantityByPid, Void, Void> {

        private CartDao cartDao;

        private UpdateCartQuantityByPidAsyncTask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(UpdateCartQuantityByPid... cart_apis) {
            cartDao.updateCartQuantityByPid(
                    cart_apis[0].getC_id(),
                    cart_apis[0].getC_quantity(),
                    cart_apis[0].getPer_unit_price()
            );
            return null;
        }
    }

}
