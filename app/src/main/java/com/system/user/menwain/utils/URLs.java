package com.system.user.menwain.utils;

public class URLs {
    public static final String base_url = "http://oranje-tech.com/demo/hypermart_menwain/api/";
    public static String user_log_in_url = base_url + "customerlogin";
    public static String user_register_url = base_url + "registerCustomer";
    public static String pass_recover_through_num_url = base_url + "customerRecvPassNumb";
    public static String get_current_user_profile_url = base_url + "getcustomerdetail";
    public static String get_explore_and_shop_url = base_url + "products";
    public static String update_current_user_profile_url = base_url + "customerupdatepro";
    public static String get_explore_url = base_url + "exploreproducts";
    public static String get_banner_url = base_url + "homebanner";
    public static String over_all_rating_url = base_url + "orderrating";
    public static String get_super_category_url = base_url + "getsupercats";
    public static String get_user_address_url = base_url + "useraddresslist";
    public static String get_category_url = base_url + "getcats/";
    public static String add_user_address_url = base_url + "addadress";
    public static String update_user_address_url = base_url + "useraddressupdate/";
    public static String get_all_stores_data_url = base_url + "getStoreMainBranch";
    public static String get_selected_store_data_url = base_url + "getStoreProducts/";
    public static String get_category_products_data_url = base_url + "store_prods/";

    /*    Orders Urls    */
    public static String get_all_orders_url = base_url + "allorders";
    public static String get_processing_orders_url = base_url + "Processingorders";
    public static String get_delivered_orders_url = base_url + "Deliveredorders";
    public static String get_cancelled_orders_url = base_url + "cancelledorders";
    public static String get_orders_details_url = base_url + "openorder/";
    public static String submit_product_review_url = base_url + "orderProductreviews";
    public static String get_store_time_slots_url = base_url + "storeslotslist/";
    public static String submit_delivery_date_url = base_url + "preferreddeliverydate/";
    public static String get_product_details_url = base_url + "productById/";
    public static String heighest_availability = base_url + "heighestAvailability";
    public static String place_order_add_wish_list = base_url + "placeorder";
    public static String calcualte_shipping_cost = base_url + "calculateshipping";
    public static String get_user_wish_list = base_url + "getUserWishlistProducts";
    public static String get_user_wish_list_by_id = base_url + "WishlistProducts/";
    public static String delete_wish_list = base_url + "deletewishlist/";
    public static String see_all_explore_shop = base_url + "productsseeall";
    public static String see_all_explore = base_url + "exploreProductsSeeAll";
    public static String see_all_shop = base_url + "exploreProductsSeeAll";
    public static String search_product_by_name_url = base_url + "searchprdctbyname";
    public static String reviews_list_url = base_url + "reviewslist/";
    public static String post_preffered_date_url = base_url + "storeslotslist/";
    public static String payments_ypes_url = base_url + "PaymentsTypes";
    public static String get_super_category_products_url = base_url + "subcategoryProducts/";
    public static String get_sub_category_and_products_url = base_url + "getcatwiseproduct/";
    public static String get_sub_category_products_url = base_url + "subcategoryProducts/";
    public static String add_card_url = base_url + "addcustomerCard";
    public static String delete_address_url = base_url + "deladress/";
    public static String user_card_url = base_url + "customerCardlist";
    public static String delete_card_url = base_url + "deletecustomerCard/";
    public static String update_user_card_url = base_url + "updatecustomerCard/";
    public static String search_product_by_bar_code_url = base_url + "searchproductbybarcode";
    public static String heighest_availability_by_radius = base_url +"hAByRadious";
    public static String radious_stores_list = base_url+"radiousStoresList";
    public static String get_selected_store_selected_cat_url = base_url+"storeProdbycat/";
}
