package ko.co.second.map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;

import java.util.List;

import ko.co.second.R;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "MapFragment";
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private NaverMap mNaverMap;
    private FusedLocationSource mLocationSource;
    private StoreManager storeManager = new StoreManager(); // 가게 데이터 관리자

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frg_map, container, false);

        // 기존 MapView를 네이버 지도 SDK의 MapFragment로 변경
        FragmentManager fm = getChildFragmentManager();
        com.naver.maps.map.MapFragment mapFragment = (com.naver.maps.map.MapFragment) fm.findFragmentById(R.id.map_fragment_container);

        if (mapFragment == null) {
            mapFragment = com.naver.maps.map.MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map_fragment_container, mapFragment).commit();
        }

        mLocationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);
        mapFragment.getMapAsync(this); // getMapAsync 호출 위치 수정

        return view;
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        mNaverMap = naverMap;
        mNaverMap.setLocationSource(mLocationSource);
        mNaverMap.getUiSettings().setLocationButtonEnabled(true);

        ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_REQUEST_CODE);

        setupMarkers();
    }

    private void setupMarkers() {
        List<Store> stores = storeManager.getStores();
        for (Store store : stores) {
            Marker marker = new Marker();
            // youtubeName에 따라 다른 마커 이미지 변경
            switch (store.getYoutubeName()) {
                case "성시경":
                    marker.setIcon(OverlayImage.fromResource(R.drawable.marker_sung_si_kyung));
                    break;
                case "풍자":
                    marker.setIcon(OverlayImage.fromResource(R.drawable.marker_pungja));
                    break;
                // 다른 youtubeName에 대한 설정 추가
                case "쯔양":
                    marker.setIcon(OverlayImage.fromResource(R.drawable.marker_tzuyang));
                    break;
                case "맛상무":
                    marker.setIcon(OverlayImage.fromResource(R.drawable.marker_matsangmu));
                    break;

            }
            marker.setWidth(150); // 마커의 너비 설정
            marker.setHeight(150); // 마커의 높이 설정
            marker.setPosition(store.getLocation());
            marker.setCaptionText(store.getStoreName()); // 가게 이름 표시
            marker.setMap(mNaverMap);

            marker.setOnClickListener(overlay -> {
                Intent intent = new Intent(getActivity(), MapInfoActivity.class);
                startActivity(intent);
                return true; // 클릭 이벤트 소비
            });
        }
    }

    //권한확인
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            } else {
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
        }
    }
}