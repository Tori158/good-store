package ko.co.second.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ko.co.second.R;
import ko.co.second.mypage.Announcement;
import ko.co.second.mypage.Changing_information;
import ko.co.second.mypage.HeartList;
import ko.co.second.mypage.My_Review;

public class MyInfoFragment extends Fragment {

    private FirebaseAuth mAuth;
    private TextView usernameTextView;

    public static MyInfoFragment newInstance() {
        MyInfoFragment fp = new MyInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("number", 2);
        fp.setArguments(bundle);
        return fp;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_info, container, false);


        mAuth = FirebaseAuth.getInstance();


        usernameTextView = view.findViewById(R.id.tvName);
        Button moveHeartButton = view.findViewById(R.id.button);
        Button moveAnnouncementButton = view.findViewById(R.id.button2);
        Button moveMyReviewButton = view.findViewById(R.id.button6);


        moveHeartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HeartList.class);
                startActivity(intent);
            }
        });

        moveAnnouncementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Announcement.class);
                startActivity(intent);
            }
        });

        moveMyReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), My_Review.class);
                startActivity(intent);
            }
        });


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null && currentUser.getEmail() != null) {

            String username = currentUser.getEmail().split("@")[0];

            usernameTextView.setText(username + "님");
        } else {

            usernameTextView.setText("사용자 정보를 불러올 수 없습니다.");
        }

        return view;
    }
}