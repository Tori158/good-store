package ko.co.second.Review;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import ko.co.second.R;
import ko.co.second.map.MapInfoActivity;

public class WriteReview extends AppCompatActivity {

    private FirebaseFirestore db; // Firestore 객체 선언
    private FirebaseAuth mAuth; // Firebase Authentication 객체 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_write_review);

        // Firestore 객체 초기화
        db = FirebaseFirestore.getInstance();

        // Firebase Authentication 객체 초기화
        mAuth = FirebaseAuth.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActionBar actionBar = getSupportActionBar(); // ActionBar 객체 가져오기
        if (actionBar != null) {
            actionBar.hide(); // ActionBar 숨기기
        }

        //취소버튼 눌렀을 경우
        Button moveRemoveButton = findViewById(R.id.removeButton);
        moveRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이전의 WriteReview 액티비티로 돌아가도록 함
                Intent intent = new Intent(WriteReview.this, MapInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 이전 액티비티를 재사용하도록 설정
                startActivity(intent);
            }
        });

        //등록 버튼 눌렀을 경우
        Button registerButton = findViewById(R.id.okButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReviewToFirestore();
            }
        });
    }

    private void saveReviewToFirestore() {
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        EditText editTextReview = findViewById(R.id.editTextText);

        float rating = ratingBar.getRating();
        String review = editTextReview.getText().toString();

        // Firebase Authentication에서 현재 사용자 가져오기
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Firestore에 데이터 저장
            Map<String, Object> reviewData = new HashMap<>();
            reviewData.put("userId", userId); // 사용자 ID 추가
            reviewData.put("rating", rating);
            reviewData.put("review", review);

            db.collection("reviews")
                    .add(reviewData)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(WriteReview.this, "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                        // 리뷰 등록 후 원하는 동작을 수행할 수 있음
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(WriteReview.this, "리뷰 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        // 실패 시 처리할 로직 추가 가능
                    });
        } else {
            // 사용자가 로그인되어 있지 않은 경우 처리
            Toast.makeText(WriteReview.this, "사용자가 로그인되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
            // 로그인 화면으로 이동하도록 구현할 수 있음
        }
    }
}
