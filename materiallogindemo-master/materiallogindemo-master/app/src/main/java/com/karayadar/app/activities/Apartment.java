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

public class Apartment extends AppCompatActivity {

    EditText ed_maker,ed_model,ed_color,ed_rent,ed_desc;

    Uri selectedImage,selectedImage2;

    String result;

    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private RadioGroup radioEleGroup;
    private RadioButton radioEleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appartment);

        ed_maker = findViewById(R.id.bed_value);
        ed_model = findViewById(R.id.floor_value);
        ed_color = findViewById(R.id.addr_value);
        ed_rent = findViewById(R.id.rent_value);
        ed_desc = findViewById(R.id.desc_value);


        radioSexGroup =findViewById(R.id.radioSex);
        radioEleGroup =findViewById(R.id.radioelevator);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                files();
            }
        });


        findViewById(R.id.carimgg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    Toast.makeText(Apartment.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });

        findViewById(R.id.carimgg2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    Toast.makeText(Apartment.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 200);
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

    private void files(){
        final String address = ed_maker.getText().toString();
        final String email = ed_model.getText().toString();
        final String add = ed_color.getText().toString();
        final String password = ed_rent.getText().toString();
        final String reEnterPassword = ed_desc.getText().toString();
        User user = SharedPrefManager.getInstance(Apartment.this).getUser();
        int ui = user.getId();
        final String userid = String.valueOf(ui);

        int selectedId = radioSexGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioSexButton =findViewById(selectedId);

        String status = String.valueOf(radioSexButton.getText());



        int selectedIdEle = radioEleGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioEleButton =findViewById(selectedIdEle);

        String mobile = String.valueOf(radioEleButton.getText());


        //calling the upload file method after choosing the file
        uploadFile(selectedImage, selectedImage2, status, address, email, mobile, password, add, reEnterPassword, userid);
    }

    private void uploadFile(Uri fileUri,Uri fileUri2, String desc, String rent, String type, String duration, String color, String maker, String add, String userid) {

        //creating a file
        File file = new File(getRealPathFromURI(fileUri));
        File file2 = new File(getRealPathFromURI(fileUri2));

        //creating request body for file
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
        RequestBody requestFile2 = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri2)), file2);
        RequestBody Status = RequestBody.create(MediaType.parse("text/plain"), desc);
        RequestBody Model = RequestBody.create(MediaType.parse("text/plain"), rent);
        RequestBody Rent = RequestBody.create(MediaType.parse("text/plain"), type);
        RequestBody Description = RequestBody.create(MediaType.parse("text/plain"), duration);
        RequestBody Color = RequestBody.create(MediaType.parse("text/plain"), color);
        RequestBody Maker = RequestBody.create(MediaType.parse("text/plain"), maker);
        RequestBody addd = RequestBody.create(MediaType.parse("text/plain"), add);
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
        Call<ImageResponse> call = api.uploadApartmentImage(requestFile, requestFile2 , Status, Model,Rent, Description, Color, Maker, addd, Userid);

        //finally performing the call
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                ImageResponse model=response.body();
                System.out.println("error"+model.isError());
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


        String address = ed_maker.getText().toString();
        String email = ed_model.getText().toString();
//        String mobile = ed_color.getText().toString();
        String password = ed_rent.getText().toString();
        String reEnterPassword = ed_desc.getText().toString();


        if (address.isEmpty()) {
            ed_maker.setError("Must Filled");
            valid = false;
        } else {
            ed_maker.setError(null);
        }


        if (email.isEmpty() ) {
            ed_model.setError("Must Filled");
            valid = false;
        } else {
            ed_model.setError(null);
        }

        /*if (mobile.isEmpty()) {
            ed_color.setError("Must Filled");
            valid = false;
        } else {
            ed_color.setError(null);
        }*/

        if (password.isEmpty() ) {
            ed_rent.setError("Must Filled");
            valid = false;
        } else {
            ed_rent.setError(null);
        }

        if (reEnterPassword.isEmpty() ) {
            ed_desc.setError("Must Filled");
            valid = false;
        } else {
            ed_desc.setError(null);
        }

        return valid;
    }


}
