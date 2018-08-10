package com.example.aditya.retailerclient;

import com.example.aditya.retailerclient.Model.BrochureDisplay;
import com.example.aditya.retailerclient.Model.CartDisplay;
import com.example.aditya.retailerclient.Model.MyOrder;
import com.example.aditya.retailerclient.Model.Offers;
import com.example.aditya.retailerclient.Model.ProductDisplay;
import com.example.aditya.retailerclient.Model.ProductParent;
import com.example.aditya.retailerclient.Model.UserCred;
import com.example.aditya.retailerclient.Model.UserProfile;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Aditya on 4/15/2018.
 */

public interface API {

    //////////////////////////////////////////////////////////////
    // public String BASE_URL = " http://10.0.2.2:/api/";
    //this is to get all the detail_product parent ID and name

    //this is to request a particular user. Use1:- Login
//  @GET("UserLogin/{id}")
//  Call<List<UserCred>> getUser (@Path("id") String id);
////

////////////////////////////////////////////////////////////////
    //public String BASE_URL = "/webapp/";
    public String BASE_URL = "/phpservice/";

    @GET("userCredentials.php")
    Call<List<UserCred>> getUser (@Query("name") String name);

    @GET("changeStatus.php")
    Call<Void> changeLoginStatus (@Query("name") String name,@Query("status") int status);
//
//    //this is to change that user has logged in once
//    @GET("loginOnce.php")
//    Call<List<UserCred>> updatevalue(@QueryMap Map<String,String> options);


    @GET("finalProducts.php")
    Call<List<ProductDisplay>> getProductsPrice(@Query("U_name_FK") String U_name_FK);

    @GET("parentWiseProducts.php")
    Call<List<ProductDisplay>> getParentWiseProducts(@Query("U_name_FK") String U_name_FK,
                                                     @Query("PP_id_FK") int PP_id_FK);


    @FormUrlEncoded
    @POST("addToCart.php")
    Call<Void> addtoCart(@Field("U_name") String U_name, @Field("P_id_FK") int P_id_FK,
                                         @Field("Quant") int quant, @Field("Price") double price);

    @GET("getCartContent.php")
    Call<List<CartDisplay>> getCartContent(@Query("U_name_FK") String U_name_FK);

    @FormUrlEncoded
    @POST("removeFromCart.php")
    Call<List<CartDisplay>> removeFromCart(@Field("U_name_FK") String U_name_FK,@Field("Product_id") int Product_id,
                                           @Field("Price") double Price);
//
    @FormUrlEncoded
    @POST("purchaseCartContent.php")
    Call<List<CartDisplay>> purchaseCart(@Field("U_name_FK") String U_name_FK);



    @GET("myorders.php")
    Call<List<MyOrder>> getMyOrders(@Query("U_name_FK") String U_name_FK);

    @GET("cancelMyOrder.php")
    Call<Void> cancelMyOrder(@Query("T_id") int T_id);

    @GET("profile.php")
    Call<List<UserProfile>> getprofiledata(@Query("U_name_FK") String U_name_FK);

    @FormUrlEncoded
    @POST("updateprofilePersonal.php")
    Call<List<UserProfile>> updateProfilePersonal(@Field("U_name_FK") String U_name_FK,@Field("U_email") String U_email,
                                           @Field("U_whatsapp") String U_whatsapp, @Field("U_address") String U_address);

    @FormUrlEncoded
    @POST("updateprofileOther.php")
    Call<List<UserProfile>> updateProfileOther(@Field("U_name_FK") String U_name_FK,@Field("U_GST") String U_email,
                                                  @Field("U_adhar") String U_whatsapp, @Field("U_Pan") String U_address);


    @GET("resetpassword.php")
    Call<List<UserCred>> getphonenumber(@Query("U_whatsapp") String U_whatsapp);


    @GET("getProductParent.php")
    Call<List<ProductParent>> getParents();


    @GET("removeAllFromCart.php")
    Call<List<Void>> emptyCart(@Query("U_name_FK") String U_name_FK);

    @GET("getAllOffers.php")
    Call<List<Offers>> getAllOffers(@Query("U_name_FK") String U_name_FK);


    @FormUrlEncoded
    @POST("addBillTableData.php")
    Call<List<Void>> addBillTableData(@Field("U_name_FK") String U_name_FK,@Field("Total_Amount") Double Total_Amount,
                                               @Field("Final_Amount") Double Final_Amount, @Field("Discount") Double Discount);

    @GET("changePassword.php")
    Call<List<Void>> changePassword(@Query("U_name_FK") String U_name_FK,@Query("new_Password") String newPassword);

    @GET("getBrochures.php")
    Call<List<BrochureDisplay>> getBrochures();

//    @GET("resetpassword.php")
//    Call<List<UserCred>> getphonenumber(@Query("phnnumber") String phonenumber);
//
//    @GET("updateprofile.php")
//    Call<List<userprofile>> updateprofiledata(@Query("Email") String Email, @Query("Mb") String Mb,
//                                              @Query("Adharcard") String Adharcard, @Query("Pancard") String Pancard,
//                                              @Query("Username") String Username);
//
//
//
//    @GET("profile.php")
//    Call<List<userprofile>> getprofiledata(@Query("username") String username);
//
//


}
