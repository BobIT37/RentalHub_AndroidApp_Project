package com.karayadar.app.api;

import com.karayadar.app.ApartmentItemResponse;
import com.karayadar.app.DefaultResponse;
import com.karayadar.app.HouseItemResponse;
import com.karayadar.app.ItemResponse;
import com.karayadar.app.OwnerResponse;
import com.karayadar.app.ShopItemResponse;
import com.karayadar.app.activities.Shop;
import com.karayadar.app.models.Result;
import com.karayadar.app.models.Users;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;



public interface APIService {

    @FormUrlEncoded
    @POST("registerUser")
    Call<Result> createUser(
            @Field("name") String name,
            @Field("address") String address,
            @Field("email") String email,
            @Field("phoneno") String phoneno,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("login")
    Call<Result> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("owner")
    Call<Result> owner(
            @Field("id") String id

    );


    @GET("users")
    Call<Users> getUsers();

    @POST("allitems")
    Call<ItemResponse> getItems(

    );

    @POST("allapartmentitems")
    Call<ApartmentItemResponse> getApartmentItems(

    );

    @POST("allhouseitems")
    Call<HouseItemResponse> gethouseItems(

    );

    @POST("allshopitems")
    Call<ShopItemResponse> getshopItems(

    );


    @FormUrlEncoded
    @POST("useritems")
    Call<ApartmentItemResponse> getMyItems(
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("userShopitems")
    Call<ShopItemResponse> getMyShopsItems(
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("userHouseitems")
    Call<HouseItemResponse> getMyHouseItems(
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("userCaritems")
    Call<ItemResponse> getMyCarItems(
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("useritems")
    Call<OwnerResponse> getowner(
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("rateapartmentitem")
    Call<DefaultResponse> rateItem(
            @Field("status") String rate,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("ratehouseitem")
    Call<DefaultResponse> rateHouseItem(
            @Field("rate") String rate,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("rateshopitem")
    Call<DefaultResponse> rateShopItem(
            @Field("rate") String rate,
            @Field("id") String id
    );
    @FormUrlEncoded
    @POST("ratecaritem")
    Call<DefaultResponse> rateCarItem(
            @Field("rate") String rate,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("updateapartmentitem")
    Call<DefaultResponse> updateItem(
            @Field("rate") String status,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("updatehouseitem")
    Call<DefaultResponse> updateHouseItem(
            @Field("status") String status,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("updateshopitem")
    Call<DefaultResponse> updateShopItem(
            @Field("status") String status,
            @Field("id") String id
    );
    @FormUrlEncoded
    @POST("updatecaritem")
    Call<DefaultResponse> updateCarItem(
            @Field("status") String status,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("carDetails")
    Call<Result> saveCar(
            @Field("status") String status,
            @Field("maker") String maker,
            @Field("model") String model,
            @Field("color") String color,
            @Field("rent") String rent,
            @Field("description") String description,
            @Field("userid") int userid);

    @FormUrlEncoded
    @POST("carname")
    Call<ItemResponse> getItemsbyname(
            @Field("itemname") String itemname
    );

    @FormUrlEncoded
    @POST("carname")
    Call<ApartmentItemResponse> getApartbyname(
            @Field("itemname") String itemname
    );

    @FormUrlEncoded
    @POST("housename")
    Call<HouseItemResponse> gethousebyname(
            @Field("itemname") String itemname
    );

    @FormUrlEncoded
    @POST("shopname")
    Call<ShopItemResponse> getshopbyname(
            @Field("itemname") String itemname
    );

    @FormUrlEncoded
    @POST("deletecar")
    Call<ResponseBody> deleteCar(
            @Field("id") String id
    );
    @FormUrlEncoded
    @POST("deletehouse")
    Call<ResponseBody> deleteHouse(
            @Field("id") String id
    );
    @FormUrlEncoded
    @POST("deleteapart")
    Call<ResponseBody> deleteApart(
            @Field("id") String id
    );
    @FormUrlEncoded
    @POST("deleteshop")
    Call<ResponseBody> deleteShop(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("itemsrent")
    Call<ItemResponse> getItemsbyRent(
            @Field("range1") String range1,
            @Field("range2") String range2
    );

    @FormUrlEncoded
    @POST("apartrent")
    Call<ApartmentItemResponse> getApartbyRent(
            @Field("range1") String range1,
            @Field("range2") String range2
    );
    @FormUrlEncoded
    @POST("houserent")
    Call<HouseItemResponse> getHousebyRent(
            @Field("range1") String range1,
            @Field("range2") String range2
    );
    @FormUrlEncoded
    @POST("shoprent")
    Call<ShopItemResponse> getShopbyRent(
            @Field("range1") String range1,
            @Field("range2") String range2
    );
    /*@FormUrlEncoded
    @POST("sendmessage")
    Call<MessageResponse> sendMessage(
            @Field("from") int from,
            @Field("to") int to,
            @Field("title") String title,
            @Field("message") String message);

    @FormUrlEncoded
    @POST("update/{id}")
    Call<Result> updateUser(
            @Path("id") int id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("gender") String gender
    );

    //getting messages
    @GET("messages/{id}")
    Call<Messages> getMessages(@Path("id") int id);*/
}
