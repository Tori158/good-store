package ko.co.second.celebrity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import java.util.ArrayList;
import java.util.List;

import ko.co.second.R;
import ko.co.second.map.Store;
import ko.co.second.map.StoreManager;

public class SungsiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sungsi);

        //앱 타이틀 설정
        TextView toolbarTitle = findViewById(R.id.toolbar_title4);
        toolbarTitle.setText("성시경");

        // StoreManager 초기화 및 데이터 가져오기
        StoreManager storeManager = new StoreManager();
        List<Store> sungSiKyungStores = storeManager.getStoresByYoutubeName("성시경");

        // storeName만 추출하여 리스트에 담기
        List<String> storeNames = new ArrayList<>();
        for (Store store : sungSiKyungStores) {
            storeNames.add(store.getStoreName());
        }

        // 리스트뷰 정보 설정
        ListView list = findViewById(R.id.ListView4); // ListView4로 변경
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, storeNames);
        list.setAdapter(adapter);

        // 화면 이동 설정
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
            controller.setAppearanceLightStatusBars(true);
        }


    }
}