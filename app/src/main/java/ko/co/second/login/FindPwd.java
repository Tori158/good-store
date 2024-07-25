package ko.co.second.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import ko.co.second.R;

public class FindPwd extends AppCompatActivity {

    private EditText etEmail, etNewPassword, etConfirmPassword;
    private Button btnResetPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);

        ActionBar actionBar = getSupportActionBar(); // ActionBar 객체 가져오기
        if (actionBar != null) {
            actionBar.hide(); // ActionBar 숨기기
        }

        etEmail = findViewById(R.id.FindPwdEmailAddress);
        etNewPassword = findViewById(R.id.FindPWDPassword1);
        etConfirmPassword = findViewById(R.id.FindPWDCheakPassword);
        btnResetPassword = findViewById(R.id.Find_btn2);

        mAuth = FirebaseAuth.getInstance();

        btnResetPassword.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String newPassword = etNewPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(FindPwd.this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(FindPwd.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            resetPassword(email, newPassword);
        });
    }

    private void resetPassword(String email, String newPassword) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(FindPwd.this, "비밀번호 재설정 이메일을 보냈습니다.", Toast.LENGTH_SHORT).show();
            } else {
                if (task.getException() != null) {
                    Toast.makeText(FindPwd.this, "비밀번호 재설정에 실패했습니다: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}