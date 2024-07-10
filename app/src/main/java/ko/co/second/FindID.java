package ko.co.second;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class FindID extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        ActionBar actionBar = getSupportActionBar(); // ActionBar 객체 가져오기
        if (actionBar != null) {
            actionBar.hide(); // ActionBar 숨기기
        }
    }
}