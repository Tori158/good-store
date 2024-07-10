package ko.co.second.login;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ko.co.second.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActionBar actionBar = getSupportActionBar(); // ActionBar 객체 가져오기
        if (actionBar != null) {
            actionBar.hide(); // ActionBar 숨기기
        }
    }
}