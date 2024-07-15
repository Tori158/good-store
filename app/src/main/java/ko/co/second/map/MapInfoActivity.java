package ko.co.second.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import ko.co.second.R;
import ko.co.second.Review.WriteReview;
import ko.co.second.Review.Review_data; // Review_data 클래스 임포트

public class MapInfoActivity extends AppCompatActivity {

    private FirebaseFirestore db; // Firestore 객체 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_info);

        // Firestore 객체 초기화
        db = FirebaseFirestore.getInstance();

        // Intent에서 데이터를 받아옵니다.
        String youtubeLink = getIntent().getStringExtra("YOUTUBE_LINK");
        String storeName = getIntent().getStringExtra("STORE_NAME");
        String phoneNumber = getIntent().getStringExtra("PHONE_NUMBER");
        String address = getIntent().getStringExtra("ADDRESS");

        // YouTubePlayerView 초기화
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_view);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(youtubeLink, 0); // 유튜브 링크 재생
            }
        });

        // TextView에 가게 정보 설정
        TextView storeNameTextView = findViewById(R.id.celebrityStoreName);
        TextView phoneNumberTextView = findViewById(R.id.celebrityStoreNumber);
        TextView storeAddressTextView = findViewById(R.id.celebrityStoreAddress);

        storeNameTextView.setText(storeName);
        phoneNumberTextView.setText(phoneNumber);
        storeAddressTextView.setText(address);

        // 시스템 바의 패딩을 설정합니다.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ActionBar를 숨깁니다.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // 리뷰 작성 버튼 설정
        Button makeReviewButton = findViewById(R.id.makeReviewButton);
        makeReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapInfoActivity.this, WriteReview.class);
                startActivity(intent);
            }
        });
    }
}
