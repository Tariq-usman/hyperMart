package com.system.user.menwain.utils;

public class URLs {
    public static final String base_url = "http://oranje-tech.com/demo/hypermart/api/";
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
    public static String submit_product_review_url = base_url +"orderProductreviews";
    public static String get_store_time_slots_url = base_url +"storeslotslist/";
    public static String submit_delivery_date_url = base_url +"preferreddeliverydate/";
    public static String get_product_details_url = base_url +"productById/";
    public static String heighest_availability = base_url +"heighestAvailability";
}
