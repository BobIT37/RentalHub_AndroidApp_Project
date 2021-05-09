package com.karayadar.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karayadar.app.ImageApi;
import com.karayadar.app.ImageResponse;
import com.karayadar.app.R;
import com.karayadar.app.SharedPrefManager;
import com.karayadar.app.models.User;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HouseActivity extends AppCompatActivity {

    EditText ed_size, ed_address, ed_bedroom, ed_floors, ed_rent, ed_description;

    Uri selectedImage, selectedImage2;

    String result;

    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;

    private RadioGroup radioSexGroupFur;
    private RadioButton radioSexButtonFur;

    private RadioGroup radioGroupGarage;
    private RadioButton radioButtonGarage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);

        ed_size = findViewById(R.id.size_value);
        ed_rent = findViewById(R.id.ren_value);
        ed_address = findViewById(R.id.address_value);
        ed_bedroom = findViewById(R.id.hbedroom_value);
        ed_floors = findViewById(R.id.floors_value);
        ed_description = findViewById(R.id.descript_value);


        radioSexGroup = findViewById(R.id.radioSex);
        radioSexGroupFur = findViewById(R.id.radiofur);
        radioGroupGarage = findViewById(R.id.radiogarage);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }

        findViewById(R.id.carimggg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    Toast.makeText(HouseActivity.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                    return;
                }
               /* if (selectedImage != null) {
                    selectedImage2 = selectedImage;
                    selectedImage=null;
                }*/
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });

        findViewById(R.id.carimggg2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    Toast.makeText(HouseActivity.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*if (selectedImage != null) {
                    selectedImage2 = selectedImage;
                    selectedImage=null;

                }*/
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 200);
            }
        });
        findViewById(R.id.savehouse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                file();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            //the image URI
            selectedImage=null;
            selectedImage = data.getData();
            // final String name = ed_status.getText().toString();

        }
        else if (requestCode == 200 && resultCode == RESULT_OK && data != null) {
            //the image URI
            selectedImage2=null;
            selectedImage2 = data.getData();
            // final String name = ed_status.getText().toString();

        }
    }

    private void file() {
        final String size = ed_size.getText().toString();
        final String rent = ed_rent.getText().toString();
        final String address = ed_address.getText().toString();
        final String bedroom = ed_bedroom.getText().toString();
        final String floor = ed_floors.getText().toString();
        final String description = ed_description.getText().toString();
        User user = SharedPrefManager.getInstance(HouseActivity.this).getUser();
        int ui = user.getId();
        final String userid = String.valueOf(ui);

        int selectedId = radioSexGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioSexButton = findViewById(selectedId);

        String status = String.valueOf(radioSexButton.getText());

        int selectedIdEle = radioSexGroupFur.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioSexButtonFur = findViewById(selectedIdEle);

        final String furn = String.valueOf(radioSexButtonFur.getText());


        int selectedIdgarage = radioGroupGarage.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButtonGarage = findViewById(selectedIdgarage);

        final String garage = String.valueOf(radioButtonGarage.getText());
        //calling the upload file method after choosing the file
        uploadFile(selectedImage, selectedImage2, status, furn, garage, size, rent, address, bedroom, floor, description, userid);
    }


    private void uploadFile(Uri fileUri, Uri fileUri2, String desc, String furni, String gara, String size, String rent, String address, String bed, String floor, String descr, String userid) {

        //creating a file
        File file = new File(getRealPathFromURI(fileUri));
        File file2 = new File(getRealPathFromURI(fileUri2));

        //creating request body for file
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
        RequestBody requestFile2 = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri2)), file2);
        RequestBody Status = RequestBody.create(MediaType.parse("text/plain"), desc);
        RequestBody furnish = RequestBody.create(MediaType.parse("text/plain"), furni);
        RequestBody garage = RequestBody.create(MediaType.parse("text/plain"), gara);
        RequestBody Size = RequestBody.create(MediaType.parse("text/plain"), size);
        RequestBody Rent = RequestBody.create(MediaType.parse("text/plain"), rent);
        RequestBody Address = RequestBody.create(MediaType.parse("text/plain"), address);
        RequestBody Bed = RequestBody.create(MediaType.parse("text/plain"), bed);
        RequestBody Floor = RequestBody.create(MediaType.parse("text/plain"), floor);
        RequestBody Description = RequestBody.create(MediaType.parse("text/plain"), descr);
        RequestBody Userid = RequestBody.create(MediaType.parse("text/plain"), userid);

        //The gson builder
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        //creating retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ImageApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //creating our api
        ImageApi api = retrofit.create(ImageApi.class);

        //creating a call and calling the upload image method
        Call<ImageResponse> call = api.uploadHouseImage(requestFile, requestFile2, Status, furnish, garage, Size, Address, Floor, Bed, Rent, Description, Userid);

        //finally performing the call
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                ImageResponse model = response.body();
//                System.out.println("error" + model.isError());
                if (model.isError().equals("false")) {

                    Toast.makeText(getApplicationContext(), "Ad Uploaded Successfully...", Toast.LENGTH_LONG).show();
                    finish();

                    /*Intent intent = new Intent(AddActivity.this, OutputAddActivity.class);
                    String itemname = editTextItName.getText().toString().trim();
                    String rent = editTextRent.getText().toString().trim();
//                  String type = editTextType.getText().toString().trim();
                    String duration = editTextDuration.getText().toString().trim();
                    intent.putExtra("Item Nanme",itemname);
                    intent.putExtra("Rent",rent);
                    intent.putExtra("Type",record);
                    intent.putExtra("Duration",duration);
                    intent.putExtra("imageuri",selectedImage);
                    startActivity(intent);*/

                } else {
                    Toast.makeText(getApplicationContext(), "Some error occurred...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    /*public void signup() {

        if (!validate()) {
            Toast.makeText(this, "Failed to upload", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Upload Add");
        progressDialog.show();

        String name = ed_status.getText().toString();
        String address = ed_maker.getText().toString();
        String email = ed_model.getText().toString();
        String mobile = ed_color.getText().toString();
        String password = ed_rent.getText().toString();
        String reEnterPassword = ed_desc.getText().toString();

        // TODO: Implement your own signup logic here.

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);


        User user = SharedPrefManager.getInstance(CarActivity.this).getUser();

        //Defining the user object as we need to pass it with the call

        Car car = new Car(name, address, email,mobile, password,reEnterPassword,1);

        Toast.makeText(this, "a"+user.getId(), Toast.LENGTH_SHORT).show();

        //defining the call
        Call<Result> call = service.saveCar(

                car.getStatus(),
                car.getMaker(),
                car.getModel(),
                car.getColor(),
                car.getRent(),
                car.getDescription(),
                user.getId()



        );

        //calling the api
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //hiding progress dialog
                progressDialog.dismiss();

                //displaying the message from the response as toast
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                //if there is no error
                if (!response.body().getError()) {
                    //starting profile activity
                    //  finish();
                    Toast.makeText(CarActivity.this, "Car details saved", Toast.LENGTH_SHORT).show();
//                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getUser());
                    startActivity(new Intent(getApplicationContext(), MainMenu.class));
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }*/

    public boolean validate() {
        boolean valid = true;


        String size = ed_size.getText().toString();
        String rent = ed_rent.getText().toString();
        String address = ed_address.getText().toString();
        String bedroom = ed_bedroom.getText().toString();
        String floor = ed_floors.getText().toString();
        String description = ed_description.getText().toString();


        if (address.isEmpty()) {
            ed_address.setError("Must Filled");
            valid = false;
        } else {
            ed_address.setError(null);
        }

        if (size.isEmpty()) {
            ed_size.setError("Must Filled");
            valid = false;
        } else {
            ed_size.setError(null);
        }

        if (rent.isEmpty()) {
            ed_rent.setError("Must Filled");
            valid = false;
        } else {
            ed_rent.setError(null);
        }

        if (bedroom.isEmpty()) {
            ed_bedroom.setError("Must Filled");
            valid = false;
        } else {
            ed_bedroom.setError(null);
        }

        if (floor.isEmpty()) {
            ed_floors.setError("Must Filled");
            valid = false;
        } else {
            ed_floors.setError(null);
        }
        if (description.isEmpty()) {
            ed_description.setError("Must Filled");
            valid = false;
        } else {
            ed_description.setError(null);
        }

        return valid;
    }


}
