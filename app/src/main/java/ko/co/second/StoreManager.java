package ko.co.second;

import com.naver.maps.geometry.LatLng;
import java.util.ArrayList;
import java.util.List;

public class StoreManager {
    private List<Store> stores; // 가게 목록을 저장하는 리스트

    public StoreManager() {
        stores = new ArrayList<>();
        initializeStores(); // 초기 데이터로 가게 정보를 설정
    }

    // 가게 데이터를 초기화하는 메서드
    private void initializeStores() {
        stores.add(new Store("YouTube Channel 1", "서울 시청", new LatLng(37.5670135, 126.9783740), "02-120"));
        stores.add(new Store("YouTube Channel 2", "덕수궁", new LatLng(37.565789, 126.976881), "02-123"));
        stores.add(new Store("YouTube Channel 3", "경복궁", new LatLng(37.576622, 126.976835), "02-321"));
    }

    // 가게 목록을 외부에 제공하는 메서드
    public List<Store> getStores() {
        return new ArrayList<>(stores); // 외부에 리스트의 복사본을 제공하여 캡슐화 유지
    }
}

