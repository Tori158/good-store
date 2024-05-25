package ko.co.second;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
        return LayoutInflater.from(inflater.getContext()).inflate(R.layout.frg_info, container, false);
    }


}
