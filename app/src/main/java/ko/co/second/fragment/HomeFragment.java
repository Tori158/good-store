package ko.co.second.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import ko.co.second.celebrity.JyangActivity;
import ko.co.second.celebrity.PungjaActivity;
import ko.co.second.R;
import ko.co.second.celebrity.SangmuActivity;
import ko.co.second.celebrity.SungsiActivity;
import ko.co.second.login.MainActivity;

public class HomeFragment extends Fragment {

    public static HomeFragment newInstance() {
        HomeFragment fp = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("number", 1);
        fp.setArguments(bundle);
        return fp;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_home, container, false);

        ImageButton moveJButton = view.findViewById(R.id.jyangButton);
        moveJButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), JyangActivity.class);
                startActivity(intent);
            }
        });

        ImageButton moveSangmuButton = view.findViewById(R.id.sangmuButton);
        moveSangmuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SangmuActivity.class);
                startActivity(intent);
            }
        });

        ImageButton moveSungsiButton = view.findViewById(R.id.sungsiButton);
        moveSungsiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SungsiActivity.class);
                startActivity(intent);
            }
        });

        ImageButton movePungjaButton = view.findViewById(R.id.pungjaButton);
        movePungjaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PungjaActivity.class);
                startActivity(intent);
            }
        });

        ImageButton moveLogoutButton = view.findViewById(R.id.logoutButton);
        moveLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Firebase 로그아웃
                FirebaseAuth.getInstance().signOut();
                // 로그아웃 완료 메시지 표시
                Toast.makeText(getActivity(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                // MainActivity로 이동
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                // 현재 Fragment의 Activity 종료 (옵션)
                getActivity().finish();
            }
        });

        return view;
    }
}
