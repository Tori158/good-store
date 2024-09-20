package ko.co.second.Heart;

public class FavoriteItem {
    private String storeName;
    private String address;
    private String phoneNumber;
    private String youtubeLink;
    private String naverMapLink; // 필드 추가
    private String userId;       // 필드 추가

    public FavoriteItem() {
        // Firestore에서 데이터를 읽어오려면 빈 생성자가 필요합니다.
    }

    public FavoriteItem(String storeName, String address, String phoneNumber, String youtubeLink, String naverMapLink, String userId) {
        this.storeName = storeName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.youtubeLink = youtubeLink;
        this.naverMapLink = naverMapLink;
        this.userId = userId;
    }

    // Getter 메서드들
    public String getStoreName() {
        return storeName;
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

    public String getUserId() {
        return userId;
    }

    // Setter 메서드들
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public void setNaverMapLink(String naverMapLink) {
        this.naverMapLink = naverMapLink;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
