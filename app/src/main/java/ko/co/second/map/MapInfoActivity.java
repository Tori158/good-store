package ko.co.second.map;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import ko.co.second.R;

public class MapInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_info);

        // 레이아웃에서 YouTubePlayerView를 찾습니다.
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_view);

        // 생명주기와 함께 YouTubePlayerView를 관리
        getLifecycle().addObserver(youTubePlayerView);

        // Intent로부터 유튜브 링크를 가져옵니다.
        String youtubeLink = getIntent().getStringExtra("YOUTUBE_LINK");

        // YouTubePlayerView 초기화
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(youtubeLink, 0); //0초부터 시작한다는 뜻
            }
        });

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
    }
}
