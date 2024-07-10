package ko.co.second;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

        return view;
    }
}
