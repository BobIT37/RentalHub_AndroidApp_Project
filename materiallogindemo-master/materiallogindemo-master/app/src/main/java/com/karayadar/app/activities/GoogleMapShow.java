package com.karayadar.app.activities;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karayadar.app.R;
import com.karayadar.app.models.HouseModel;
import com.karayadar.app.models.Items;
import com.karayadar.app.activities.ui.gallery.GalleryFragment;
import com.karayadar.app.models.MarkerData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class GoogleMapShow extends AppCompatActivity implements OnMapReadyCallback {

    private boolean mLocationPermissionGranted;
    public static final int PERMISSION_REQUEST_CODE = 9001;
    public static final int GPS_REQUEST_CODE = 9003;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 9001;
    private final int PLAY_SERVICES_ERROR_CODE = 9002;
    public static final String TAG = "GoogleMaps";
    private GoogleMap mGoogleMap;
    EditText locationname;
    ArrayList<MarkerData> markerData = new ArrayList<>();
    ArrayList<String> stock_list = new ArrayList<>();

    String Activity;
    String status;
    String fName;
    String rent;
    String desc;

    ImageButton search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map_fragment_container, supportMapFragment)
                .commit();
        supportMapFragment.getMapAsync(this);
      //  locationname = findViewById(R.id.et_location_name);
        //     mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
       // search = findViewById(R.id.bnt_location_search);
      //  search.setOnClickListener(this::geolocate);
        initGoogleMap();
        Intent intobject = getIntent();

        Bundle bundle = intobject.getExtras();

        if (bundle != null) {

            Activity = (String) intobject.getSerializableExtra("Activity");

        }


        if(Activity != null) {
            switch (Activity) {

                case "House": {

                    status = (String) intobject.getSerializableExtra("ShopStatus");
                    fName = (String) intobject.getSerializableExtra("Address");
                    rent = (String) intobject.getSerializableExtra("Rent");
                    desc = (String) intobject.getSerializableExtra("Descrr");
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocationName(fName, 2);

                        if (addressList.size() > 0) {
                            Address address = addressList.get(0);
                            markerData.add(new MarkerData(address.getLatitude(), address.getLongitude(), desc, rent, R.drawable.construction));
                            //Toast.makeText(this, "" + address.getLatitude(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "geolocate: " + address.getLocality());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                }

                case "Apartment": {

                    status = (String) intobject.getSerializableExtra("Status");
                    fName = (String) intobject.getSerializableExtra("Address");
                    rent = (String) intobject.getSerializableExtra("Rent");
                    desc = (String) intobject.getSerializableExtra("Descrr");
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocationName(fName, 2);

                        if (addressList.size() > 0) {
                            Address address = addressList.get(0);
                            markerData.add(new MarkerData(address.getLatitude(), address.getLongitude(), desc, rent, R.drawable.apartment));
                            //Toast.makeText(this, "" + address.getLatitude(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "geolocate: " + address.getLocality());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                }

                case "Shop": {

                    status = (String) intobject.getSerializableExtra("ShopStatus");
                    fName = (String) intobject.getSerializableExtra("Address");
                    rent = (String) intobject.getSerializableExtra("Rent");
                    desc = (String) intobject.getSerializableExtra("Descrr");
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocationName(fName, 2);

                        if (addressList.size() > 0) {
                            Address address = addressList.get(0);
                            markerData.add(new MarkerData(address.getLatitude(), address.getLongitude(), desc, rent, R.drawable.market));
                            //Toast.makeText(this, "" + address.getLatitude(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "geolocate: " + address.getLocality());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                }

            }

        }



        Intent i = new Intent(); //This should be getIntent();
        stock_list = new ArrayList<String>();

        stock_list =getIntent().getStringArrayListExtra("list");

       // Toast.makeText(this, "A:"+stock_list, Toast.LENGTH_SHORT).show();

        if (stock_list != null) {
            for (String s : stock_list) {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocationName(s, 2);

                    if (addressList.size() > 0) {
                        Address address = addressList.get(0);
                        markerData.add(new MarkerData(address.getLatitude(), address.getLongitude(), "", "", R.drawable.pin));
                        //Toast.makeText(this, "" + address.getLatitude(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "geolocate: " + address.getLocality());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private void geolocate(View view) {
        hideSoftkeyboard(view);
        String locationName = locationname.getText().toString();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocationName(locationName, 2);
            if (addressList.size() > 0) {
                Address address = addressList.get(0);
                gotLocation(address.getLatitude(), address.getLongitude());
                /*mGoogleMap.addMarker(
                        new MarkerOptions().position(new LatLng(address.getLatitude(), address.getLongitude()))

                );
*/

                markerData.add(new MarkerData(address.getLatitude(), address.getLongitude(), address.getCountryName(), address.getFeatureName(), R.drawable.common_google_signin_btn_icon_dark));

                Toast.makeText(this, "" + address.getLocality(), Toast.LENGTH_SHORT).show();

                Log.d(TAG, "geolocate: " + address.getLocality());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void hideSoftkeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void initGoogleMap() {
        if (isServicesOk()) {
            if (isGPSEnabled()) {
                if (checkLocationPermission()) {
                    Toast.makeText(this, "Ready to Map!", Toast.LENGTH_SHORT).show();

                } else {
                    requestLocationPermission();
                }
            }

        }
    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean providerenable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (providerenable) {

            return true;
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("GPS Permission").setMessage("Gps is required for this app to work.please enable ").
                    setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, GPS_REQUEST_CODE);
                        }
                    }).setCancelable(false)
                    .show();
        }

        return false;
    }

    private boolean checkLocationPermission() {

        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isServicesOk() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (result == ConnectionResult.SUCCESS) {
            return true;

        } else if (googleApiAvailability.isUserResolvableError(result)) {
            Dialog dialog = googleApiAvailability.getErrorDialog(this, result, PLAY_SERVICES_ERROR_CODE, new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface task) {
                            Toast.makeText(GoogleMapShow.this, "dialog is cancel", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            dialog.show();
        } else {
            Toast.makeText(this, "play service are required by function", Toast.LENGTH_SHORT).show();
        }

        return false;
//        if(mLocationPermissionGranted){
//            Toast.makeText(this, "Ready to Map!", Toast.LENGTH_SHORT).show();
//        }else{
//            requestLocationPermission();
//        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
      //  Toast.makeText(this, "Map is showing", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap = googleMap;
        // gotLocation(33.6844, 73.0479);
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().isMyLocationButtonEnabled();
        mGoogleMap.getUiSettings().setMapToolbarEnabled(true);

       /* for(int i = 0 ; i < markersArray.size() ; i++) {

          //  createMarker(markersArray.get(i).getLatitude(), markersArray.get(i).getLongitude(), markersArray.get(i).getTitle(), markersArray.get(i).getSnippet(), markersArray.get(i).getIconResID());
        }
*/


/*
        MarkerOptions markerOptions = new MarkerOptions().title("Title").position(new LatLng(33.6844, 73.0479));
        mGoogleMap.addMarker(markerOptions);
*/

        // mGoogleMap.getUiSettings().isCompassEnabled();


        //googleMap.setMyLocationEnabled(true);

        for (int i = 0; i < markerData.size(); i++) {

            createMarker(markerData.get(i).getLatitude(), markerData.get(i).getLongitude(), markerData.get(i).getTitle(), markerData.get(i).getSnippet(), markerData.get(i).getIconResID());
        }


    }

    protected Marker createMarker(double latitude, double longitude, String title, String snippet, int iconResID) {
        Drawable circleDrawable = getResources().getDrawable(iconResID);
        BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable);
        //     BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.common_google_signin_btn_icon_dark);
        return mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(markerIcon));
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_android_black_24dp);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private BitmapDescriptor vectorToBitmap(@DrawableRes int id, @ColorInt int color) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), id, null);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        DrawableCompat.setTint(vectorDrawable, color);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    //    private BitmapDescriptor getBitmapDescriptor(int id) {
//        Drawable vectorDrawable = getDrawable(id);
//        int h = ((int) Utils.convertDpToPixel(42, context));
//        int w = ((int) Utils.convertDpToPixel(25, context));
//        vectorDrawable.setBounds(0, 0, w, h);
//        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bm);
//        vectorDrawable.draw(canvas);
//        return BitmapDescriptorFactory.fromBitmap(bm);
//    }
    private void gotLocation(double lat, double lng) {

        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        mGoogleMap.animateCamera(cameraUpdate);

        //  mGoogleMap.getUiSettings().setZoomGesturesEnabled(false);
        //mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE) {

            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean providerenable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (providerenable) {

                Toast.makeText(this, "GPS is enabled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "GPS not enabled ", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
