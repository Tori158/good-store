package ko.co.second;

import com.naver.maps.geometry.LatLng;

public class Store {
    private String youtubeName;
    private String storeName;
    private LatLng location;
    private String phoneNumber;

    // Constructor
    public Store(String youtubeName, String storeName, LatLng location, String phoneNumber) {
        this.youtubeName = youtubeName;
        this.storeName = storeName;
        this.location = location;
        this.phoneNumber = phoneNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
