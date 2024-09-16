package ko.co.second.Review;

public class Review {
    private String userEmail;
    private String review;
    private String timestamp;
    private float rating;
    private String storeName;

    public Review() {
        // Default constructor required for calls to DataSnapshot.getValue(Review.class)
    }

    public Review(String userEmail, String review, String timestamp, float rating, String storeName) {
        this.userEmail = userEmail;
        this.review = review;
        this.timestamp = timestamp;
        this.rating = rating;
        this.storeName = storeName;  // storeName 초기화
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
