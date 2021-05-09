package com.karayadar.app;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ImageApi {

    //the base URL for our API
    //make sure you are not using localhost
    //find the ip usinc ipconfig command
  String BASE_URL = "http://appkarayadar.000webhostapp.com/ImagesUploadApi/";
    //String BASE_URL = "http://192.168.10.4/ImagesUploadApi/";

    //this is our multipart request
    //we have two parameters on is name and other one is description
    @Multipart
    @POST("Api.php?apicall=upload")
    Call<ImageResponse> uploadImage(
                                    @Part("image\"; filename=\"myfile.jpg\" ") RequestBody file,
                                    @Part("simage\"; filename=\"myfile.jpg\" ") RequestBody file2,
                                    @Part("status") RequestBody status,
                                    @Part("maker") RequestBody maker,
                                    @Part("model") RequestBody model,
                                    @Part("color") RequestBody color,
                                    @Part("rent") RequestBody rent,
                                    @Part("description") RequestBody description,
                                    @Part("userid") RequestBody userid);

    @Multipart
    @POST("Api.php?apicall=uploadapartment")
    Call<ImageResponse> uploadApartmentImage(
                                             @Part("image\"; filename=\"myfile.jpg\" ") RequestBody file,
                                             @Part("simage\"; filename=\"myfile.jpg\" ") RequestBody file2,
                                             @Part("status") RequestBody status,
                                             @Part("bedrooms") RequestBody bedrooms,
                                             @Part("floors") RequestBody floors,
                                             @Part("elevator") RequestBody elevator,
                                             @Part("rent") RequestBody rent,
                                             @Part("address") RequestBody address,
                                             @Part("description") RequestBody description,
                                             @Part("userid") RequestBody userid);

    @Multipart
    @POST("Api.php?apicall=uploadshop")
    Call<ImageResponse> uploadShopImage(@Part("image\"; filename=\"myfile.jpg\" ") RequestBody file,
                                        @Part("simage\"; filename=\"myfile.jpg\" ") RequestBody file2,
                                        @Part("shopstatus") RequestBody shopstatus,
                                        @Part("size") RequestBody size,
                                        @Part("address") RequestBody address,
                                        @Part("floors") RequestBody floors,
                                        @Part("rent") RequestBody rent,
                                        @Part("description") RequestBody description,
                                        @Part("user_id") RequestBody user_id);

    @Multipart
    @POST("Api.php?apicall=uploadhouse")
    Call<ImageResponse> uploadHouseImage(
                                         @Part("image\"; filename=\"myfile.jpg\" ") RequestBody file,
                                         @Part("simage\"; filename=\"myfile.jpg\" ") RequestBody file2,
                                         @Part("housestatus") RequestBody housestatus,
                                         @Part("furnished") RequestBody furnished,
                                         @Part("garage") RequestBody garage,
                                         @Part("size") RequestBody size,
                                         @Part("address") RequestBody address,
                                         @Part("bedroom") RequestBody bedroom,
                                         @Part("floors") RequestBody floors,
                                         @Part("rent") RequestBody rent,
                                         @Part("description") RequestBody description,
                                         @Part("user_id") RequestBody user_id);

}
