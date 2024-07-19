package ko.co.second.map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.HashMap;
import java.util.Map;

import ko.co.second.R;
import ko.co.second.Review.WriteReview;

public class MapInfoActivity extends AppCompatActivity {

    private static final String TAG = "MapInfoActivity";
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private ToggleButton favoriteButton;
    private boolean isFavorite; // 가게별 상태를 저장할 변수

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

        Log.d(TAG, "youtubeLink: " + youtubeLink);
        Log.d(TAG, "storeName: " + storeName);
        Log.d(TAG, "phoneNumber: " + phoneNumber);
        Log.d(TAG, "address: " + address);

        if (youtubeLink == null || storeName == null || phoneNumber == null || address == null) {
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

        TextView storeNameTextView = findViewById(R.id.celebrityStoreName);
        TextView phoneNumberTextView = findViewById(R.id.celebrityStoreNumber);
        TextView storeAddressTextView = findViewById(R.id.celebrityStoreAddress);

        storeNameTextView.setText(storeName);
        phoneNumberTextView.setText(phoneNumber);
        storeAddressTextView.setText(address);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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

        favoriteButton.setOnClickListener(v -> handleFavoriteButtonClick(storeName, address, phoneNumber, youtubeLink));
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

    private void handleFavoriteButtonClick(String storeName, String address, String phoneNumber, String youtubeLink) {
        if (favoriteButton.isChecked()) {
            addToFavorites(storeName, address, phoneNumber, youtubeLink);
        } else {
            removeFromFavorites(storeName);
        }
    }

    private void addToFavorites(String storeName, String address, String phoneNumber, String youtubeLink) {
        if (currentUser != null) {
            Map<String, Object> favorite = new HashMap<>();
            favorite.put("userId", currentUser.getUid());
            favorite.put("storeName", storeName);
            favorite.put("address", address);
            favorite.put("phoneNumber", phoneNumber);
            favorite.put("youtubeLink", youtubeLink); // 유튜브 링크 저장

            db.collection("Favorites").document(currentUser.getUid() + "_" + storeName).set(favorite)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(MapInfoActivity.this, "찜목록 추가", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK); // 변경이 성공적으로 완료되었음을 알림
                    })
                    .addOnFailureListener(e -> {
                        favoriteButton.setChecked(false); // 실패 시 상태 초기화
                        Toast.makeText(MapInfoActivity.this, "Failed to add to favorites", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void removeFromFavorites(String storeName) {
        if (currentUser != null) {
            db.collection("Favorites").document(currentUser.getUid() + "_" + storeName).delete()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(MapInfoActivity.this, "찜목록 해제", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK); // 변경이 성공적으로 완료되었음을 알림
                    })
                    .addOnFailureListener(e -> {
                        favoriteButton.setChecked(true); // 실패 시 상태 초기화
                        Toast.makeText(MapInfoActivity.this, "Failed to remove from favorites", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
