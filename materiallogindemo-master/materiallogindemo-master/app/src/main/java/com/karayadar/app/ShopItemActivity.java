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

import static com.karayadar.app.MyShopListAdapter.currentPosition;

public class ShopItemActivity extends AppCompatActivity {
    TextView tname, trent, ttype, tduration, tsize, tdesc, txtname, txtowneraddress, txtemail, txtphone;
    ImageView imageView, ImageViewDial;
    TextView sname, saddress, semail, sphone, owner;
    Button statusbtn, delshop, MapBtn, rateBtn;
    String choice;
    String ch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_item);

        tname = findViewById(R.id.txtStat);
        trent = findViewById(R.id.textRent);
        ttype = findViewById(R.id.txtAddress);
        tduration = findViewById(R.id.txtFloors);
        imageView = findViewById(R.id.showimage);
        tsize = findViewById(R.id.txtSize);
        tdesc = findViewById(R.id.txtDescrip);
        txtname = findViewById(R.id.txtname);
        txtowneraddress = findViewById(R.id.txtowneraddress);
        txtemail = findViewById(R.id.txtemail);
        txtphone = findViewById(R.id.txtphone);
        sname = findViewById(R.id.sname);
        saddress = findViewById(R.id.sadd);
        semail = findViewById(R.id.semail);
        sphone = findViewById(R.id.sphone);
        owner = findViewById(R.id.owner);
        statusbtn = findViewById(R.id.statusshop);
        ImageViewDial = findViewById(R.id.dial);
        delshop = findViewById(R.id.deleteShop);
        MapBtn = findViewById(R.id.GoogleShop);
        rateBtn = findViewById(R.id.ratebtn);


        Intent intobject = getIntent();
        //String iname = getIntent().getStringExtra("");
        final String itemid = (String) intobject.getSerializableExtra("Id");
        String status = (String) intobject.getSerializableExtra("ShopStatus");
        String fName = (String) intobject.getSerializableExtra("Address");
        String age = (String) intobject.getSerializableExtra("Floors");
        String sem = (String) intobject.getSerializableExtra("Size");
        String rent = (String) intobject.getSerializableExtra("Rent");
        String desc = (String) intobject.getSerializableExtra("Descrr");
        String img = (String) intobject.getSerializableExtra("image");
        String img2 = (String) intobject.getSerializableExtra("image2");
        final String userid = (String) intobject.getSerializableExtra("userid");

        tname.setText(status);
        trent.setText(rent);
        ttype.setText(fName);
        tduration.setText(age);
        tsize.setText(sem);
        tdesc.setText(desc);

        if (currentPosition == 1) {
            txtphone.setVisibility(View.GONE);
            txtname.setVisibility(View.GONE);
            txtowneraddress.setVisibility(View.GONE);
            txtemail.setVisibility(View.GONE);
            sname.setVisibility(View.GONE);
            // semail.setVisibility(View.GONE);
            // saddress.setVisibility(View.GONE);
            // sphone.setVisibility(View.GONE);
            owner.setVisibility(View.GONE);
            ImageViewDial.setVisibility(View.GONE);
            statusbtn.setVisibility(View.VISIBLE);
            delshop.setVisibility(View.VISIBLE);
            rateBtn.setVisibility(View.GONE);
            MapBtn.setVisibility(View.GONE);
            currentPosition = 0;
        } else {
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
            delshop.setVisibility(View.GONE);
            rateBtn.setVisibility(View.VISIBLE);
            MapBtn.setVisibility(View.VISIBLE);
        }

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        MapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopItemActivity.this, GoogleMapShow.class);

                intent.putExtra("Activity", "Shop");
                intent.putExtra("ShopStatus", status);
                intent.putExtra("Address", fName);
                intent.putExtra("Rent", rent);
                intent.putExtra("Descrr", desc);

                startActivity(intent);
            }
        });

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ShopItemActivity.this);
                final String[] statusitems = {"0", "1", "2", "3"};
                alertDialogBuilder.setTitle("Choose Stars");


                alertDialogBuilder.setSingleChoiceItems(statusitems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ch = statusitems[which];


                    }
                });
                alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        // Toast.makeText(MyItemDetailsActivity.this, "Item Status Update"+choice, Toast.LENGTH_LONG).show();
                        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().rateShopItem(
                                ch, itemid
                        );


                        call.enqueue(new Callback<DefaultResponse>() {
                            @Override
                            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                                Toast.makeText(ShopItemActivity.this, "Item Rated", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                Toast.makeText(ShopItemActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

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
        delshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .deleteShop(itemid);

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
                        Toast.makeText(ShopItemActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ShopItemActivity.this);
                final String[] statusitems = {"Rented", "UnAvailable", "Other"};
                alertDialogBuilder.setTitle("Choose Status");


                alertDialogBuilder.setSingleChoiceItems(statusitems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choice = statusitems[which];

                    }
                });
                alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(ShopItemActivity.this, "Deleted Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ShopItemActivity.this, MainMenu.class);

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
                //    Toast.makeText(ShopItemActivity.this, "Item Status Update"+ itemid, Toast.LENGTH_LONG).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ShopItemActivity.this);
                final String[] statusitems = {"Available", "UnAvailable"};
                alertDialogBuilder.setTitle("Choose Status");


                alertDialogBuilder.setSingleChoiceItems(statusitems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choice = statusitems[which];

                    }
                });
                alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                      //  Toast.makeText(ShopItemActivity.this, "Item Status Update"+itemid, Toast.LENGTH_LONG).show();
                        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().updateShopItem(
                                choice, itemid
                        );


                        call.enqueue(new Callback<DefaultResponse>() {
                            @Override
                            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {


                                Toast.makeText(ShopItemActivity.this, "Item Status Update", Toast.LENGTH_LONG).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                Toast.makeText(ShopItemActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

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


        //Toast.makeText(this, "path:"+RetrofitClient.Image_url + img, Toast.LENGTH_SHORT).show();
        Picasso.with(getApplicationContext()).load(RetrofitClient.Image_url + img).into(imageView);

        findViewById(R.id.prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picasso.with(getApplicationContext()).load(RetrofitClient.Image_url + img).into(imageView);
            }
        });

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (img2 != null) {

                    Picasso.with(getApplicationContext()).load(RetrofitClient.Image_url + img2).into(imageView);
                }
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

                }
                /*else {
                    //Toast.makeText(getApplicationContext(), "Invalid email or password"+response.body().getError(), Toast.LENGTH_LONG).show();
                }*/
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                //    progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        findViewById(R.id.dial).setOnClickListener(new View.OnClickListener() {
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


                        Toast.makeText(ShopItemActivity.this, "Please give permissions", Toast.LENGTH_SHORT).show();
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
