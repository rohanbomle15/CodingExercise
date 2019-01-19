package rohanbomle.codingexercise.view.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import rohanbomle.codingexercise.R;
import rohanbomle.codingexercise.model.Album;
import rohanbomle.codingexercise.view.MainActivity;

import java.util.List;
import java.util.Random;


/**
 * Album adapter
 * USE TO : define base adapter for RecyclerView
 * Data binding for album list
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.CustomViewHolder> {

    private List<Album> mAlbumList;
    private Context mContext;

    public AlbumAdapter(Context context, List<Album> mAlbums) {
        this.mContext = context;
        this.mAlbumList = mAlbums;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.album_list_item, viewGroup, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        Album details = mAlbumList.get(i);
        customViewHolder.txtId.setText(""+details.getId());
        customViewHolder.txtUserId.setText("userId: "+details.getUserId());
        customViewHolder.txtTitle.setText(details.getTitle());
        Random rnd = new Random();
        int color = Color.argb(120, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        customViewHolder.txtId.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtId;
        TextView txtTitle;
        TextView txtUserId;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtId = mView.findViewById(R.id.txt_id);
            txtTitle = mView.findViewById(R.id.txt_title);
            txtUserId = mView.findViewById(R.id.txt_user_id);
        }
    }
}
