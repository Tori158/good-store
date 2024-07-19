package ko.co.second.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import ko.co.second.Heart.FavoriteItem;
import ko.co.second.Heart.HeartListAdapter;
import ko.co.second.R;
import ko.co.second.map.MapInfoActivity;

public class HeartList extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private RecyclerView recyclerView;
    private HeartListAdapter adapter;
    private List<FavoriteItem> favoriteList;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_list);

        // Firebase 초기화
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        favoriteList = new ArrayList<>();
        adapter = new HeartListAdapter(favoriteList, this);
        recyclerView.setAdapter(adapter);

        // 앱 타이틀 설정
        TextView toolbarTitle = findViewById(R.id.toolbar_HeartList);
        toolbarTitle.setText("찜 목록");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadFavorites();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavorites(); // Activity가 다시 화면에 나타날 때 목록을 새로고침
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            loadFavorites(); // 변경된 결과가 있을 때 목록을 새로고침
        }
    }

    private void loadFavorites() {
        if (currentUser != null) {
            db.collection("Favorites")
                    .whereEqualTo("userId", currentUser.getUid())
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        favoriteList.clear();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            FavoriteItem item = document.toObject(FavoriteItem.class);
                            favoriteList.add(item);
                        }
                        adapter.notifyDataSetChanged();
                    })
                    .addOnFailureListener(e -> {
                        // 실패 시 처리
                    });
        }
    }

    public void startMapInfoActivity(FavoriteItem item) {
        Intent intent = new Intent(this, MapInfoActivity.class);
        intent.putExtra("STORE_NAME", item.getStoreName());
        intent.putExtra("ADDRESS", item.getAddress());
        intent.putExtra("PHONE_NUMBER", item.getPhoneNumber());
        intent.putExtra("YOUTUBE_LINK", item.getYoutubeLink() != null ? item.getYoutubeLink() : ""); // 유튜브 링크 전달
        startActivityForResult(intent, REQUEST_CODE);
    }
}
