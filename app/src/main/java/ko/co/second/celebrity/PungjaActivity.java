package ko.co.second.celebrity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import java.util.ArrayList;
import java.util.List;
import ko.co.second.R;
import ko.co.second.map.MapInfoActivity;
import ko.co.second.map.Store;
import ko.co.second.map.StoreManager;

public class PungjaActivity extends AppCompatActivity {

    private List<Store> pungjaStores; // 유튜버 풍자의 스토어 리스트
    private ArrayAdapter<String> adapter; // 리스트뷰 어댑터
    private List<String> storeNames; // 스토어 이름 리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pungja);

        // 앱 타이틀 설정
        TextView toolbarTitle = findViewById(R.id.toolbar_title2);
        toolbarTitle.setText("풍자");

        // 뒤로가기 버튼 설정
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료
            }
        });

        // StoreManager 초기화 및 데이터 가져오기
        StoreManager storeManager = new StoreManager();
        pungjaStores = storeManager.getStoresByYoutubeName("풍자");

        // storeName만 추출하여 리스트에 담기
        storeNames = new ArrayList<>();
        for (Store store : pungjaStores) {
            storeNames.add(store.getStoreName());
        }

        // 리스트뷰 정보 설정
        ListView list = findViewById(R.id.ListView2);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, storeNames);
        list.setAdapter(adapter);

        // 리스트 항목 클릭 시 MapInfoActivity로 이동
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Store selectedStore = pungjaStores.get(position);
                Intent intent = new Intent(PungjaActivity.this, MapInfoActivity.class);
                // 선택한 스토어의 정보를 MapInfoActivity로 전달
                intent.putExtra("STORE_NAME", selectedStore.getStoreName());
                intent.putExtra("PHONE_NUMBER", selectedStore.getPhoneNumber());
                intent.putExtra("ADDRESS", selectedStore.getAddress());
                intent.putExtra("YOUTUBE_LINK", selectedStore.getYoutubeLink());
                startActivity(intent);
            }
        });

        // SearchView 설정
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setSubmitButtonEnabled(true); // 제출 버튼 활성화
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // 사용자가 검색어 제출 시 수행할 작업 없음
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText); // 검색어 변경 시 리스트 필터링
                return false;
            }
        });

        // 화면 이동 설정 (Edge-to-Edge UI)
        View mainView = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            WindowInsetsCompat systemInsets = insets;
            v.setPadding(systemInsets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    systemInsets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    systemInsets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    systemInsets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom);
            return insets;
        });

        // Edge-to-Edge UI
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat controller = ViewCompat.getWindowInsetsController(mainView);
        if (controller != null) {
            controller.show(WindowInsetsCompat.Type.systemBars());
            controller.setAppearanceLightStatusBars(true); // 상태 표시줄의 아이콘 색상을 어둡게 설정
        }
    }
}
