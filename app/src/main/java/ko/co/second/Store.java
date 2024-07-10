package ko.co.second;

import com.naver.maps.geometry.LatLng;

public class Store {
    private String youtubeName; //유튜브 이름
    private String storeName; //가게 이름
    private LatLng location; //위도,경도
    private String address; // 주소
    private String phoneNumber; //전화번호
    private String youtubeLink; //유튜브 링크
    private String naverMapLink; // 네이버 링크

    // Constructor
    public Store(String youtubeName, String storeName, LatLng location, String address, String phoneNumber, String youtubeLink, String naverMapLink) {
        this.youtubeName = youtubeName;
        this.storeName = storeName;
        this.location = location;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.youtubeLink = youtubeLink;
        this.naverMapLink = naverMapLink;
    }

    // Getters
    public String getYoutubeName() {
        return youtubeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public String getNaverMapLink() {
        return naverMapLink;
    }
}
