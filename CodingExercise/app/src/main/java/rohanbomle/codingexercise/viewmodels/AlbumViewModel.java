package rohanbomle.codingexercise.viewmodels;

import android.database.sqlite.SQLiteDatabase;
import android.databinding.BaseObservable;
import rohanbomle.codingexercise.database.DatabaseHelper;
import rohanbomle.codingexercise.model.Album;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AlbumViewModel extends BaseObservable {

    private Album mAlbum;
    private List<Album> list;

    public void initializeAlbumViewModel(List<Album> items){
        this.list = items;
    }


    public void onToggleClicked(){
        Collections.sort(list, new Comparator<Album>() {
            @Override
            public int compare(Album album1, Album album2) {
                int res = String.CASE_INSENSITIVE_ORDER.compare(album1.getTitle(), album2.getTitle());
                if (res == 0) {
                    res = album1.getTitle().compareTo(album2.getTitle());
                }
                return res;
            }
        });
    }

    public void setTitle(String param){
        this.mAlbum.setTitle(param);
    }

    public void setId(int param){
        this.mAlbum.setId(param);
    }

    public void setUserId(int param){
        this.mAlbum.setUserId(param);
    }


}
