package ko.co.second;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button moveFindButton = findViewById(R.id.findId_button);
        Button moveFindPwdButton = findViewById(R.id.findpwd_button);
        Button moveMapButton = findViewById(R.id.start);
        moveFindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindID.class);
                startActivity(intent);
            }
        });

        moveFindPwdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindPwd.class);
                startActivity(intent);
            }
        });

        moveMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), MapActivity.class);
             //   Intent intent = new Intent(getApplicationContext(), PagerActivity.class);

                startActivity(intent);
            }
        });

    }
}
