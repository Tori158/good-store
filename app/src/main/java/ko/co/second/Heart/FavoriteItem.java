package ko.co.second.Heart;

public class FavoriteItem {
    private String storeName;
    private String address;
    private String phoneNumber;
    private String youtubeLink;
    private String naverMapLink; // 네이버 맵 링크 추가
    public FavoriteItem() {
        // Firestore에서 데이터를 읽어오려면 빈 생성자가 필요합니다.
    }

    public FavoriteItem(String storeName, String address, String phoneNumber, String youtubeLink,String naverMapLink) {
        this.storeName = storeName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.youtubeLink = youtubeLink;
        this.naverMapLink= naverMapLink;
    }

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
    public String getNaverMapLink() {return naverMapLink;}

}
