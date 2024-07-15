package ko.co.second.Review;

public class Review_data {
    private String id;
    private float rating;
    private String review;

    public Review_data(String id, float rating, String review) {
        this.id = id;
        this.rating = rating;
        this.review = review;
    }

    public String getId() {
        return id;
    }

    public float getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }
}
