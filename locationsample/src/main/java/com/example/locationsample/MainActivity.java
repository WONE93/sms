//package com.example.locationsample;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.pedro.library.AutoPermissions;
//import com.pedro.library.AutoPermissionsListener;
//
//
//public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
//
//    TextView textView;
//    Button button;
//    private MapView mapView;
//    GoogleMap map;
//    private static final String MAP_BUNDLE_KEY = "MapBundleKey"; // 상태정보를 키값 으로 읽기위해
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        //권한체크
//        AutoPermissions.Companion.loadAllPermissions(this, 101);
//
//        textView = findViewById(R.id.textView);
//        button = findViewById(R.id.button);
//
//        Bundle mapViewBundle = null;
//        if (savedInstanceState != null) {
//            mapViewBundle = savedInstanceState.getBundle(MAP_BUNDLE_KEY);
//        }
//        mapView = findViewById(R.id.mapView);
//        mapView.onCreate(mapViewBundle);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startLocationService();
//            }
//        });
//
//    }// E of oncreate
//
//    @SuppressLint("MissingPermission")
//    public void startLocationService() { // 제일 마지막 좌표를 가져와 텍스트뷰에 뿌림
//        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        try {
//            //마지막 좌표 출력
//            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (location != null) {
//                double latitude = location.getLatitude();
//                double longitude = location.getLongitude();
//                String message = "최근 위치: " + latitude + ", " + longitude;
//                textView.setText(message);
//            }
//
//            //10초 간격으로 좌표 요청
//            if (location != null) {
//                GPSListener gpsListner = new GPSListener();
//                long minTime = 10000; //10초
//                float minDistance = 0;                                      //10초가 지나면 gpsListner을 호출
//                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListner);
//                Toast.makeText(getApplicationContext(), "내 위치 확인 요청함", Toast.LENGTH_LONG).show();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onDenied(int i, String[] strings) {
//
//    }
//
//    @Override
//    public void onGranted(int i, String[] strings) {
//
//    }
//
//    @Override // 수락하고나면 이거뒤에 Denied Granted 진행됨
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
//    }
//
//    //필드만들기
//    static String TAG = "location";
//
//    // 알트 + 인서트 누르면 오버라이드매소드 뜸
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mapView.onStart();
//        Log.d(TAG, "onStart");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mapView.onStop();
//        Log.d(TAG, "onStop");
//    }
//
//    @Override
//    protected void onDestroy() {
//        mapView.onDestroy();
//        super.onDestroy();
//        Log.d(TAG, "onDestroy");
//    }
//
//    @Override
//    protected void onPause() {
//        mapView.onPause();
//        super.onPause();
//        Log.d(TAG, "onPause");
//    }
//
//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        Bundle mapBundle = outState.getBundle(MAP_BUNDLE_KEY);
//        if (mapBundle == null) {
//            mapBundle = new Bundle();
//            outState.putBundle(MAP_BUNDLE_KEY, mapBundle);
//        } //outState에 저장해두고 key값으로 읽어드릴것임
//        mapView.onSaveInstanceState(mapBundle);
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView.onLowMemory();
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mapView.onResume();
//        Log.d(TAG, "onResume");
//    }
//
//    //내부 클래스
//    class GPSListener implements LocationListener {
//        @Override
//        public void onLocationChanged(Location location) {
//            double latitude = location.getLatitude();
//            double longitude = location.getLongitude();
//            String message = "최근 위치: " + latitude + ", " + longitude;
//            textView.setText( message);
//
//            //맵에 마크를 더하고 지도를 옯겨주고 줌인 가능
//            LatLng curPoint = new LatLng(latitude, longitude);
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.position(curPoint);
//            markerOptions.title("현재위치");
//            map.addMarker(markerOptions);
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(curPoint,15));
//
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//        }
//    }
//
//
//}
package com.example.locationsample;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    TextView textView;
    Button button;
    GoogleMap map;
    /*
        SupportMapFragment mapFragment;*/
    private static final String MAP_BUNDLE_KEY = "MapBundleKey";
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoPermissions.Companion.loadAllPermissions(this, 101);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationService();
            }
        });




        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_BUNDLE_KEY);
        }
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                LatLng seoul = new LatLng(37.56641923090, 126.9778741551);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(seoul);
                markerOptions.title("서울");
                map.addMarker(markerOptions);
                map.moveCamera(CameraUpdateFactory.newLatLng(seoul));
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            }
        });

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationService();
            }
        });
    }


    public void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            //바로 최근 위치 정보 확인
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String message = "최근 위치: " + latitude + ", " + longitude;
                textView.setText(message);
            }

            //일정한 시간 간격으로 위치 정보 확인
            GPSListener gpsListner = new GPSListener();
            long minTime = 10000;
            float minDistance = 0;
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListner);
            Toast.makeText(getApplicationContext(), "내 위치 확인 요청함", Toast.LENGTH_LONG).show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapBundle = outState.getBundle(MAP_BUNDLE_KEY);
        if(mapBundle == null) {
            mapBundle = new Bundle();
            outState.putBundle(MAP_BUNDLE_KEY, mapBundle);
        }
        mapView.onSaveInstanceState(mapBundle);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory");
        mapView.onLowMemory();
    }

    static String TAG ="location";
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDenied(int i, String[] strings) {

    }

    @Override
    public void onGranted(int i, String[] strings) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }


    class GPSListener implements LocationListener {

        @Override
        //10초간격으로 좌표가 변경되면 호출되는 이벤트 핸들러
        public void onLocationChanged(Location location) {
            //텍스트뷰에 표시
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            String message = "최근 위치: " + latitude + ", " + longitude;
            textView.setText( message) ;

            //맵에 마커 표시
            LatLng curPoint = new LatLng(latitude, longitude);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(curPoint);
            markerOptions.title("현재위치");
            map.addMarker(markerOptions);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(curPoint,15));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }


}


