package com.karayadar.app;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karayadar.app.activities.GoogleMapShow;
import com.karayadar.app.activities.MainMenu;
import com.karayadar.app.api.APIService;
import com.karayadar.app.api.APIUrl;
import com.karayadar.app.fragment.House;
import com.karayadar.app.models.Result;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.karayadar.app.MyHouseListAdapter.currentPosition;

public class HouseItemDisplayActivity extends AppCompatActivity {
    TextView tname,trent,ttype,tduration, tsize,tdesc,tbedroom,tgarage, tfurnished,txtname,txtowneraddress,txtemail,txtphone;
    ImageView imageView,ImageViewDial;
    TextView sname, saddress, semail, sphone, owner;

    Button statusbtn,deleteHouse,MapButton,ratebtn;
    String choice;
    String ch;

   public static String[] sliderImageId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_item_display);

        tname=      findViewById(R.id.txtStat);
        trent=      findViewById(R.id.txtrent);
        ttype=      findViewById(R.id.txtaddress);
        tduration=  findViewById(R.id.txtfloor);
        imageView = findViewById(R.id.showimagehouse);
        tsize =     findViewById(R.id.txtsiz);
        tbedroom =  findViewById(R.id.txtbed);
        tgarage =   findViewById(R.id.txtGarage);
        tdesc =     findViewById(R.id.txtDescript);
        tfurnished = findViewById(R.id.txtfurnished);
        txtname =     findViewById(R.id.txtname);
        txtowneraddress =     findViewById(R.id.txtowneraddress);
        txtemail =     findViewById(R.id.txtemail);
        txtphone =     findViewById(R.id.txtphone);
        sname =     findViewById(R.id.sname);
        saddress =     findViewById(R.id.sadd);
        semail =     findViewById(R.id.semail);
        sphone =     findViewById(R.id.sphone);
        owner =     findViewById(R.id.owner);
        statusbtn = findViewById(R.id.status);
        ImageViewDial = findViewById(R.id.dial);
        deleteHouse = findViewById(R.id.deleteHouse);
        MapButton = findViewById(R.id.GoogleHouse);
        ratebtn = findViewById(R.id.ratebtn);


        Intent intobject=getIntent();
        final String itemid =      getIntent().getStringExtra("id");
        String status=      (String) intobject.getSerializableExtra("ShopStatus");
        String fName=       (String) intobject.getSerializableExtra("Address");
        String age=         (String) intobject.getSerializableExtra("Floors");
        String sem=         (String) intobject.getSerializableExtra("Size");
        String rent=        (String) intobject.getSerializableExtra("Rent");
        String desc=        (String) intobject.getSerializableExtra("Descrr");
        String garage=      (String) intobject.getSerializableExtra("Garage");
        String bedroom=     (String) intobject.getSerializableExtra("Bedroom");
        String furnished=   (String) intobject.getSerializableExtra("Furnished");
        String img=         (String) intobject.getSerializableExtra("image");
        String img2=         (String) intobject.getSerializableExtra("image2");
        final String userid=(String) intobject.getSerializableExtra("userid");

      // Toast.makeText(this, "asd:"+userid, Toast.LENGTH_SHORT).show();
        tname.setText(status);
        trent.setText(rent);
        ttype.setText(fName);
        tduration.setText(age);
        tsize.setText(sem);
        tdesc.setText(desc);
        tgarage.setText(garage);
        tbedroom.setText(bedroom);
        tfurnished.setText(furnished);




        if(currentPosition==1){
            txtphone.setVisibility(View.GONE);
            txtname.setVisibility(View.GONE);
            txtowneraddress.setVisibility(View.GONE);
            txtemail.setVisibility(View.GONE);
            sname.setVisibility(View.GONE);
            //semail.setVisibility(View.GONE);
           // saddress.setVisibility(View.GONE);
           // sphone.setVisibility(View.GONE);
            owner.setVisibility(View.GONE);
            ImageViewDial.setVisibility(View.GONE);
            statusbtn.setVisibility(View.VISIBLE);
            deleteHouse.setVisibility(View.VISIBLE);
            MapButton.setVisibility(View.GONE);
            ratebtn.setVisibility(View.GONE);
            currentPosition=0;
        }
        else
        {
            txtphone.setVisibility(View.VISIBLE);
            txtname.setVisibility(View.VISIBLE);
            txtowneraddress.setVisibility(View.VISIBLE);
            txtemail.setVisibility(View.VISIBLE);
            sname.setVisibility(View.VISIBLE);
            //semail.setVisibility(View.VISIBLE);
            //saddress.setVisibility(View.VISIBLE);
            //sphone.setVisibility(View.VISIBLE);
            owner.setVisibility(View.VISIBLE);
            ImageViewDial.setVisibility(View.VISIBLE);
            statusbtn.setVisibility(View.GONE);
            deleteHouse.setVisibility(View.GONE);
            MapButton.setVisibility(View.VISIBLE);
            ratebtn.setVisibility(View.VISIBLE);
        }

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });

        MapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HouseItemDisplayActivity.this, GoogleMapShow.class);

                intent.putExtra("Activity","House");
                intent.putExtra("ShopStatus",status);
                intent.putExtra("Address",fName);
                intent.putExtra("Rent",rent);
                intent.putExtra("Descrr",desc);

                startActivity(intent);
            }
        });

        ratebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HouseItemDisplayActivity.this);
                final String[] statusitems={"0","1","2","3"};
                alertDialogBuilder.setTitle("Choose Stars");


                alertDialogBuilder.setSingleChoiceItems(statusitems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ch=statusitems[which];




                    }
                });
                alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        // Toast.makeText(MyItemDetailsActivity.this, "Item Status Update"+choice, Toast.LENGTH_LONG).show();
                        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().rateHouseItem(
                                ch, itemid
                        );


                        call.enqueue(new Callback<DefaultResponse>() {
                            @Override
                            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                                Toast.makeText(HouseItemDisplayActivity.this, "Item Rated", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                Toast.makeText(HouseItemDisplayActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.show();
            }
        });
        deleteHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .deleteHouse(itemid);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String s = null;

                        try {
                            if (response.code() == 201) {
                                s = response.body().string();
                            } else {
                                s = response.body().string();
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (s != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(HouseItemDisplayActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HouseItemDisplayActivity.this);
                final String[] statusitems={"Rented","UnAvailable","Other"};
                alertDialogBuilder.setTitle("Choose Status");


                alertDialogBuilder.setSingleChoiceItems(statusitems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choice=statusitems[which];

                    }
                });
                alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(HouseItemDisplayActivity.this, "Deleted Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(HouseItemDisplayActivity.this, MainMenu.class);

                        startActivity(intent);
                        // Toast.makeText(MyItemDetailsActivity.this, "Item Status Update"+choice, Toast.LENGTH_LONG).show();
                        /*Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().updateCarItem(
                                choice, itemid
                        );


                        call.enqueue(new Callback<DefaultResponse>() {
                            @Override
                            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {


                                Toast.makeText(ItemDisplayActivity.this, "Item Status Update", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                Toast.makeText(ItemDisplayActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

                            }
                        });*/

                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.show();
            }
        });

        statusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        //        Toast.makeText(HouseItemDisplayActivity.this, "Item Status Update"+ itemid, Toast.LENGTH_LONG).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HouseItemDisplayActivity.this);
                final String[] statusitems={"Available","UnAvailable"};
                alertDialogBuilder.setTitle("Choose Status");


                alertDialogBuilder.setSingleChoiceItems(statusitems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choice=statusitems[which];

                    }
                });
                alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        // Toast.makeText(MyItemDetailsActivity.this, "Item Status Update"+choice, Toast.LENGTH_LONG).show();
                        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().updateHouseItem(
                                choice, itemid
                        );


                        call.enqueue(new Callback<DefaultResponse>() {
                            @Override
                            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {


                                Toast.makeText(HouseItemDisplayActivity.this, "Item Status Update", Toast.LENGTH_LONG).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                Toast.makeText(HouseItemDisplayActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.show();
            }
        });

        sliderImageId = new String[]{img,img2};


        Picasso.with(getApplicationContext()).load(RetrofitClient.Image_url + img).into(imageView);


        findViewById(R.id.prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(HouseItemDisplayActivity.this, "P"+RetrofitClient.Image_url + img, Toast.LENGTH_SHORT).show();
                Picasso.with(getApplicationContext()).load(RetrofitClient.Image_url + img).into(imageView);
            }
        });

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   Toast.makeText(HouseItemDisplayActivity.this, "P"+RetrofitClient.Image_url + img2, Toast.LENGTH_SHORT).show();


                if (img2 !=null){

                Picasso.with(getApplicationContext()).load(RetrofitClient.Image_url + img2).into(imageView);}
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);


        Call<Result> call = service.owner(userid);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //progressDialog.dismiss();
                if (!response.body().getError()) {
                    // Toast.makeText(ApartmentItemActivity.this, "Owner::"+ response.body().getUser().getName(), Toast.LENGTH_SHORT).show();

                    txtname.setText(response.body().getUser().getName());
                    txtowneraddress.setText(response.body().getUser().getAddress());
                    txtemail.setText(response.body().getUser().getEmail());
                    txtphone.setText(response.body().getUser().getPhoneno());

                } else {
                 //   Toast.makeText(getApplicationContext(), "Error :"+response.body().getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                //    progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        ImageViewDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + txtphone.getText().toString()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {


                        // TODO: Consider calling
                        //    Activity#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for Activity#requestPermissions for more details.


                        Toast.makeText(HouseItemDisplayActivity.this, "Please give permissions", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                startActivity(intent);
            }
        });
       /* final EditText addrText = findViewById(R.id.location);
        final Button button = findViewById(R.id.mapButton);

        final Button btn = findViewById(R.id.contact);

        // Link UI elements to actions in code
        button.setOnClickListener(new View.OnClickListener() {

            // Called when user clicks the Show Map button
            public void onClick(View v) {
                try {

                    // Process text for network transmission
                    String address = addrText.getText().toString();
                    address = address.replace(' ', '+');

                    // Create Intent object for starting Google Maps application
                    Intent geoIntent = new Intent(
                            android.content.Intent.ACTION_VIEW, Uri
                            .parse("geo:0,0?q=" + address));

                    // Use the Intent to start Google Maps application using Activity.startActivity()
                    startActivity(geoIntent);

                } catch (Exception e) {
                    // Log any error messages to LogCat using Log.e()
//                    Log.e(TAG, e.toString());
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {

            // Called when user clicks the Show Map button
            public void onClick(View v) {

                Intent intent = new Intent(ItemDisplayActivity.this, ItemDisplayActivity.class);
                intent.putExtra("rid",userid);
                startActivity(intent);

            }
        });*/
    }
}
