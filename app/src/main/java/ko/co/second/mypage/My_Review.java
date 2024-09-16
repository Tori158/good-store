package ko.co.second.mypage;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ko.co.second.R;
import ko.co.second.Review.Review;
import ko.co.second.Review.Review_adapter;

public class My_Review extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private RecyclerView myReviewRecyclerView;
    private Review_adapter reviewAdapter;
    private List<Review> myReviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        myReviewRecyclerView = findViewById(R.id.my_review_list);
        myReviewList = new ArrayList<>();

        myReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewAdapter = new Review_adapter(myReviewList);
        myReviewRecyclerView.setAdapter(reviewAdapter);

        if (currentUser != null) {
            loadMyReviews(currentUser.getEmail()); // 이메일로 필터링
        } else {
            Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadMyReviews(String userEmail) {
        db.collection("reviews")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        myReviewList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String storeName = document.getId(); // 현재 문서 ID를 가게 이름으로 사용
                            List<Map<String, Object>> reviews = (List<Map<String, Object>>) document.get("reviews");
                            if (reviews != null) {
                                for (Map<String, Object> reviewData : reviews) {
                                    String email = (String) reviewData.get("userEmail");
                                    if (userEmail.equals(email)) {
                                        Review review = new Review();
                                        review.setUserEmail(email);
                                        review.setRating(Float.parseFloat(reviewData.get("rating").toString()));
                                        review.setReview((String) reviewData.get("review"));
                                        review.setTimestamp((String) reviewData.get("timestamp"));
                                        review.setStoreName(storeName); // 가게 이름 설정
                                        myReviewList.add(review);
                                    }
                                }
                            }
                        }
                        reviewAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("My_Review", "Error getting documents: ", task.getException());
                        Toast.makeText(My_Review.this, "리뷰 로드 실패.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
