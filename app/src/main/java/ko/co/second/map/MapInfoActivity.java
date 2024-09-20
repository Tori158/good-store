package ko.co.second.map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ko.co.second.R;
import ko.co.second.Review.Review;
import ko.co.second.Review.Review_adapter;
import android.util.Log;
import ko.co.second.Review.WriteReview;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MapInfoActivity extends AppCompatActivity {

    private static final String TAG = "MapInfoActivity";
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private ToggleButton favoriteButton;
    private boolean isFavorite;
    private Review_adapter reviewAdapter;
    private List<Review> reviewList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_info);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String youtubeLink = getIntent().getStringExtra("YOUTUBE_LINK");
        String storeName = getIntent().getStringExtra("STORE_NAME");
        String phoneNumber = getIntent().getStringExtra("PHONE_NUMBER");
        String address = getIntent().getStringExtra("ADDRESS");
        String naverMapLink = getIntent().getStringExtra("NAVER_MAP_LINK"); // 네이버 지도 링크 받기

        if (youtubeLink == null || storeName == null || phoneNumber == null || address == null || naverMapLink==null) {
            Toast.makeText(this, "필수 정보가 누락되었습니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // YouTubePlayerView 초기화
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_view);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(youtubeLink, 0);
            }
        });

        // UI 요소 설정
        TextView storeNameTextView = findViewById(R.id.celebrityStoreName);
        TextView phoneNumberTextView = findViewById(R.id.celebrityStoreNumber);
        TextView storeAddressTextView = findViewById(R.id.celebrityStoreAddress);
        TextView naverMapTextView = findViewById(R.id.open_naver_map_button); // 네이버 지도 링크 TextView 초기화

        storeNameTextView.setText(storeName);
        phoneNumberTextView.setText(phoneNumber);
        storeAddressTextView.setText(address);
        naverMapTextView.setText(naverMapLink); // 네이버 지도 링크 설정

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Button makeReviewButton = findViewById(R.id.makeReviewButton);
        makeReviewButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapInfoActivity.this, WriteReview.class);
            intent.putExtra("storeName", storeName); // storeName을 전달
            startActivity(intent);
        });


        favoriteButton = findViewById(R.id.toggle_button);
        checkFavoriteStatus(storeName);

        favoriteButton.setOnClickListener(v -> handleFavoriteButtonClick(storeName, address, phoneNumber, youtubeLink, naverMapLink));

        // 리뷰 RecyclerView 초기화
        recyclerView = findViewById(R.id.recycler_view); // XML에 추가된 RecyclerView 참조
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewAdapter = new Review_adapter(reviewList); // 어댑터 인스턴스 생성
        recyclerView.setAdapter(reviewAdapter);

        // 리뷰 로드
        loadReviews(storeName);
    }

    private void checkFavoriteStatus(String storeName) {
        if (currentUser != null) {
            DocumentReference docRef = db.collection("Favorites").document(currentUser.getUid() + "_" + storeName);
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    isFavorite = true;
                    favoriteButton.setChecked(true);
                } else {
                    isFavorite = false;
                    favoriteButton.setChecked(false);
                }
            }).addOnFailureListener(e -> {
                isFavorite = false;
                favoriteButton.setChecked(false);
                Toast.makeText(MapInfoActivity.this, "Failed to load favorite status.", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void handleFavoriteButtonClick(String storeName, String address, String phoneNumber, String youtubeLink, String naverMapLink) {
        if (favoriteButton.isChecked()) {
            addToFavorites(storeName, address, phoneNumber, youtubeLink, naverMapLink);
        } else {
            removeFromFavorites(storeName);
        }
    }

    private void addToFavorites(String storeName, String address, String phoneNumber, String youtubeLink, String naverMapLink) {
        if (currentUser != null) {
            Map<String, Object> favorite = new HashMap<>();
            favorite.put("userId", currentUser.getUid());
            favorite.put("storeName", storeName);
            favorite.put("address", address);
            favorite.put("phoneNumber", phoneNumber);
            favorite.put("youtubeLink", youtubeLink);
            favorite.put("naverMapLink", naverMapLink); // 네이버 맵 링크 추가

            db.collection("Favorites").document(currentUser.getUid() + "_" + storeName).set(favorite)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(MapInfoActivity.this, "찜목록 추가", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                    })
                    .addOnFailureListener(e -> {
                        favoriteButton.setChecked(false);
                        Toast.makeText(MapInfoActivity.this, "Failed to add to favorites", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void removeFromFavorites(String storeName) {
        if (currentUser != null) {
            db.collection("Favorites").document(currentUser.getUid() + "_" + storeName).delete()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(MapInfoActivity.this, "찜목록 해제", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                    })
                    .addOnFailureListener(e -> {
                        favoriteButton.setChecked(true);
                        Toast.makeText(MapInfoActivity.this, "Failed to remove from favorites", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void loadReviews(String storeName) {
        db.collection("reviews").document(storeName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            // 문서에서 `reviews` 배열을 가져옵니다.
                            List<Map<String, Object>> reviews = (List<Map<String, Object>>) documentSnapshot.get("reviews");
                            if (reviews != null) {
                                reviewList.clear(); // 기존 리스트 초기화
                                for (Map<String, Object> reviewData : reviews) {
                                    Review review = new Review();
                                    review.setUserEmail((String) reviewData.get("userEmail"));
                                    review.setRating(((Number) reviewData.get("rating")).floatValue());
                                    review.setReview((String) reviewData.get("review"));
                                    review.setTimestamp((String) reviewData.get("timestamp"));
                                    reviewList.add(review);
                                }
                                reviewAdapter.notifyDataSetChanged();
                                Log.d(TAG, "Reviews loaded: " + reviewList.size());
                            }
                        } else {
                            Toast.makeText(MapInfoActivity.this, "리뷰가 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MapInfoActivity.this, "리뷰 로드 실패.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
