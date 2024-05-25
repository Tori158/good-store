package ko.co.second;

import android.os.Bundle;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import java.util.HashMap;
import java.util.Map;
import ko.co.second.databinding.ActivityPagerBinding;

public class PagerActivity extends AppCompatActivity {

    ActivityPagerBinding binding;
    PagerAdapter mAdapter;
    Map<Integer, Fragment> mPageReferenceMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = ActivityPagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAdapter = new PagerAdapter(this);
        binding.pager.setAdapter(mAdapter);

        binding.pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setLayoutTab(position);
            }
        });

        binding.rlayoutTabFirst.setOnClickListener(v -> binding.pager.setCurrentItem(0));
        binding.rlayoutTabSecond.setOnClickListener(v -> binding.pager.setCurrentItem(1));
        binding.rlayoutTabThird.setOnClickListener(v -> binding.pager.setCurrentItem(2));
    }

    private void setLayoutTab(int position) {
        binding.rlayoutTabFirst.setSelected(position == 0);
        binding.rlayoutTabSecond.setSelected(position == 1);
        binding.rlayoutTabThird.setSelected(position == 2);
    }

    private class PagerAdapter extends FragmentStateAdapter {

        public PagerAdapter(AppCompatActivity activity) {
            super(activity);
        }

        @Override
        public Fragment createFragment(int position) {
            Fragment result = null;
            switch (position) {
                case 0:
                    result = MapFragment.newInstance();
                    break;
                case 1:
                    result = HomeFragment.newInstance();
                    break;
                case 2:
                    result = MyInfoFragment.newInstance();
                    break;
            }
            mPageReferenceMap.put(position, result);
            return result;
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}
