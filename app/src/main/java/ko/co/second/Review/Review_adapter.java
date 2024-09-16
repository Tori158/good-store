package ko.co.second.Review;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ko.co.second.R;

public class Review_adapter extends RecyclerView.Adapter<Review_adapter.ReviewViewHolder> {

    private List<Review> reviewList;

    // ViewHolder 클래스에 storeNameID 추가
    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        public TextView userEmail, reviewText, timestamp, storeNameID;
        public RatingBar ratingBar;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            userEmail = itemView.findViewById(R.id.ReviewID);
            reviewText = itemView.findViewById(R.id.ReviewContent);
            timestamp = itemView.findViewById(R.id.time);
            ratingBar = itemView.findViewById(R.id.ratingBar2);
            storeNameID = itemView.findViewById(R.id.storeNameID);  // storeNameID 연결
        }
    }

    // 어댑터 생성자
    public Review_adapter(List<Review> reviews) {
        this.reviewList = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list, parent, false);
        return new ReviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review currentReview = reviewList.get(position);
        holder.userEmail.setText(currentReview.getUserEmail());
        holder.reviewText.setText(currentReview.getReview());
        holder.timestamp.setText(currentReview.getTimestamp());
        holder.ratingBar.setRating(currentReview.getRating());
        holder.storeNameID.setText(currentReview.getStoreName());  // storeName 설정
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    // 데이터 갱신 메서드
    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
        notifyDataSetChanged();  // 데이터 변경 사항을 어댑터에 알려 UI를 갱신하도록 합니다.
    }
}
