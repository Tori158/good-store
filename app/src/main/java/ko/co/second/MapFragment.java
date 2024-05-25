package ko.co.second;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MapFragment extends Fragment {


    //원래
    public static MapFragment newInstance() {
        MapFragment fp = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("number", 0);
        fp.setArguments(bundle);
        return fp;
    }

    //ㅇ
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(inflater.getContext()).inflate(R.layout.frg_map, container, false);
    }


}
