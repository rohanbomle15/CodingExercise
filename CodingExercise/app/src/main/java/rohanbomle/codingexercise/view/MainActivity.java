package rohanbomle.codingexercise.view;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rohanbomle.codingexercise.R;
import rohanbomle.codingexercise.api.RetrofitApi;
import rohanbomle.codingexercise.common.Base;
import rohanbomle.codingexercise.common.RetrofitApiInstance;
import rohanbomle.codingexercise.database.DatabaseHelper;
import rohanbomle.codingexercise.model.Album;
import rohanbomle.codingexercise.view.Adapter.AlbumAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Activity extended from AppCompactActivity
 * Display list for albums
 * Provide button to sort albums in ASC/DESC way based on title
 *
 */
public class MainActivity extends AppCompatActivity {

    private ConstraintLayout mMainContent;
    private Snackbar mSnackbar;
    private RecyclerView mRecyclerView;
    private AlbumAdapter albumAdapter;
    private List<Album> mAlbums;

    private ProgressBar mProgressBar;

    //Database objects
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDatabase;

    private FloatingActionButton btnToggleSort;
    private static int mSortToggle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainContent = (ConstraintLayout) findViewById(R.id.main_content);
        mRecyclerView = (RecyclerView) findViewById(R.id.albumRecyclerView);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_circular) ;
        btnToggleSort = (FloatingActionButton) findViewById(R.id.btnToggleSort);


        mDBHelper = new DatabaseHelper(this);

        mAlbums = new ArrayList<Album>();
        albumAdapter = new AlbumAdapter(this, mAlbums);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(albumAdapter);


        //Get bundle parameters from intent
        //Disable offline button if there is no data available in Database
        //Hide/Show info text based on the offline music visibility
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            if (bundle.getBoolean(Base.ONLINE_MUSIC)) {
                getAlbumsData();
                setTitle(R.string.text_online_music);
            } else {
                hideLoadingDialog();
                populateAlbumData(Base.DATABASE_SORT_ASC);
                setTitle(R.string.text_offline_music);
            }
        }else{
            //If no intent extras received
            populateAlbumData(Base.DATABASE_SORT_ASC);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAlbums.clear();
    }

    //METHOD : Used to call restful api service and fetch data from the server
    private void getAlbumsData(){
        if(Base.isConnectedToInternet(MainActivity.this)) {
            showLoadingDialog();
            RetrofitApi api = RetrofitApiInstance.getRetrofitInstance().create(RetrofitApi.class);
            Call<List<Album>> call = api.getAlbums();
            call.enqueue(new Callback<List<Album>>() {
                @Override
                public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                    if (response.isSuccessful()) {
                        mDatabase = mDBHelper.getWritableDatabase();
                        mDBHelper.insertAlbumDetails(mDatabase, response.body());
                        populateAlbumData(Base.DATABASE_SORT_ASC);
                        hideLoadingDialog();
                    }
                }

                @Override
                public void onFailure(Call<List<Album>> call, Throwable t) {
                    showSnackBar(getString(R.string.api_error));
                    hideLoadingDialog();
                }
            });
        }else{
            populateAlbumData(Base.DATABASE_SORT_ASC);
        }
    }

    //METHOD : Used to fetch data from the database and populate that data in recylerview
    private void populateAlbumData(String sortOrder){
        mDatabase = mDBHelper.getReadableDatabase();
        List<Album> list = mDBHelper.selectAlbumDetailsSortByTitle(mDatabase, sortOrder);
        if(list != null && list.size() > 0){
            mAlbums.clear();
            mAlbums.addAll(list);
            albumAdapter.notifyDataSetChanged();
        }else{
            if(Base.isConnectedToInternet(MainActivity.this)) {
                getAlbumsData();
            }else{
                showSnackBar(getString(R.string.no_internet_error));
            }
        }

    }

    //METHOD : Display snackbar with given message
    public void showSnackBar(String message){
        mSnackbar = Snackbar.make(mMainContent, message, Snackbar.LENGTH_SHORT);
        mSnackbar.show();
    }

    //METHOD : Display progress dialog
    private void showLoadingDialog(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    //METHOD : Hide progress dialog
    private void hideLoadingDialog(){
        mProgressBar.setVisibility(View.GONE);
    }

    //METHOD : Used to toggle sort button for ascending and descending order
    public void ToggleSort(View v){
        if(mSortToggle == 0){
            mSortToggle = 1;
            btnToggleSort.setImageResource(R.drawable.ic_sort_ascending);
            populateAlbumData(Base.DATABASE_SORT_DESC);
        }else{
            mSortToggle = 0;
            btnToggleSort.setImageResource(R.drawable.ic_sort_descending);
            populateAlbumData(Base.DATABASE_SORT_ASC);
        }

    }
}
