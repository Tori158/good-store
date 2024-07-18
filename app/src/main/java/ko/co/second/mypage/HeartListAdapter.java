package ko.co.second.mypage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ko.co.second.R;

public class HeartListAdapter extends RecyclerView.Adapter<HeartListAdapter.ViewHolder> {

    private List<FavoriteItem> favoriteList;
    private HeartList heartListActivity;

    public HeartListAdapter(List<FavoriteItem> favoriteList, HeartList heartListActivity) {
        this.favoriteList = favoriteList;
        this.heartListActivity = heartListActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteItem item = favoriteList.get(position);
        holder.storeNameTextView.setText(item.getStoreName());
        holder.addressTextView.setText(item.getAddress());
        holder.phoneNumberTextView.setText(item.getPhoneNumber());

        holder.itemView.setOnClickListener(v -> heartListActivity.startMapInfoActivity(item));
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView storeNameTextView;
        TextView addressTextView;
        TextView phoneNumberTextView;

        ViewHolder(View itemView) {
            super(itemView);
            storeNameTextView = itemView.findViewById(R.id.storeNameTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            phoneNumberTextView = itemView.findViewById(R.id.phoneNumberTextView);
        }
    }
}
