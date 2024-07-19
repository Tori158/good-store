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
import com.google.firebase.firestore.FieldValue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ko.co.second.R;

public class WriteReview extends AppCompatActivity {

    private FirebaseFirestore db; // Firestore 객체 선언
    private FirebaseAuth mAuth; // Firebase Authentication 객체 선언
    private String storeName; // storeName을 저장할 변수

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

        // Intent로부터 storeName 받기
        Intent intent = getIntent();
        storeName = intent.getStringExtra("storeName");

        // 등록 버튼 눌렀을 경우
        Button registerButton = findViewById(R.id.okButton);
        registerButton.setOnClickListener(v -> saveReviewToFirestore()); // 리뷰를 Firestore에 저장
    }

    private void saveReviewToFirestore() {
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        EditText editTextReview = findViewById(R.id.editTextText);

        float rating = ratingBar.getRating();
        String review = editTextReview.getText().toString();

        // Firebase Authentication에서 현재 사용자 가져오기
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            String userId = currentUser.getUid();
            // 현재 시간 얻기
            long currentTimeMillis = System.currentTimeMillis();
            // SimpleDateFormat을 사용하여 날짜 포맷 지정
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
            String formattedDate = sdf.format(new Date(currentTimeMillis));

            // Firestore에 데이터 저장
            Map<String, Object> reviewData = new HashMap<>();
            reviewData.put("userEmail", userEmail); // 사용자 이메일 추가
            reviewData.put("rating", rating);
            reviewData.put("review", review);
            reviewData.put("timestamp", formattedDate);

            // `storeName`에 해당하는 문서가 존재하지 않으면 생성 후 리뷰 추가
            db.collection("reviews").document(storeName).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (!documentSnapshot.exists()) {
                            // 문서가 존재하지 않으면 생성
                            db.collection("reviews").document(storeName)
                                    .set(new HashMap<>()) // 빈 문서로 초기화
                                    .addOnSuccessListener(aVoid -> {
                                        // 문서 생성 후 리뷰 추가
                                        addReviewToDocument(reviewData);
                                    })
                                    .addOnFailureListener(e -> {
                                        // 문서 생성 실패 처리
                                        Toast.makeText(WriteReview.this, "문서 생성에 실패하였습니다: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            // 문서가 존재하면 리뷰 추가
                            addReviewToDocument(reviewData);
                        }
                    })
                    .addOnFailureListener(e -> {
                        // 문서 읽기 실패 처리
                        Toast.makeText(WriteReview.this, "문서 읽기에 실패하였습니다: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            // 사용자가 로그인되어 있지 않은 경우 처리
            Toast.makeText(WriteReview.this, "사용자가 로그인되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
            // 로그인 화면으로 이동하도록 구현할 수 있음
        }
    }

    private void addReviewToDocument(Map<String, Object> reviewData) {
        db.collection("reviews")
                .document(storeName)
                .update("reviews", FieldValue.arrayUnion(reviewData))
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(WriteReview.this, "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(WriteReview.this, "리뷰 등록에 실패하였습니다: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
