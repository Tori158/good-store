package ko.co.second.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ko.co.second.R;
import ko.co.second.mypage.Announcement;
import ko.co.second.mypage.Changing_information;
import ko.co.second.mypage.HeartList;
import ko.co.second.mypage.My_Review;

public class MyInfoFragment extends Fragment {

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

        Button moveHeartButton = view.findViewById(R.id.button);
        moveHeartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HeartList.class);
                startActivity(intent);
            }
        });

        Button moveAnnouncementButton = view.findViewById(R.id.button2);
        moveAnnouncementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Announcement.class);
                startActivity(intent);
            }
        });

        Button moveChangingButton = view.findViewById(R.id.button3);
        moveChangingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Changing_information.class);
                startActivity(intent);
            }
        });

        Button moveMyReviewButton = view.findViewById(R.id.button6);
        moveMyReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), My_Review.class);
                startActivity(intent);
            }
        });

        return view; // 수정된 부분: 인플레이트한 view를 반환
    }

}
