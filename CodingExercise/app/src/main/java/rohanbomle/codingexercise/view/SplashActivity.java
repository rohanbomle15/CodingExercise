package rohanbomle.codingexercise.view;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import rohanbomle.codingexercise.R;
import rohanbomle.codingexercise.common.Base;
import rohanbomle.codingexercise.database.DatabaseHelper;

import static android.text.Html.FROM_HTML_MODE_COMPACT;

public class SplashActivity extends AppCompatActivity {


    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDatabase;

    private Button btnOfflineMusic;
    private TextView txtWelcome;
    private TextView txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mDBHelper = new DatabaseHelper(this);
        mDatabase = mDBHelper.getReadableDatabase();

        btnOfflineMusic = (Button) findViewById(R.id.btnOfflineMusic);
        txtWelcome = (TextView) findViewById(R.id.text_welcome);
        txtInfo = (TextView) findViewById(R.id.txt_info);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            txtWelcome.setText(Html.fromHtml(getString(R.string.welcome_message), FROM_HTML_MODE_COMPACT));
        }else{
            txtWelcome.setText(Html.fromHtml(getString(R.string.welcome_message)));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mDBHelper.isAlbumDetailsAvailable(mDatabase)){
            btnOfflineMusic.setEnabled(true);
            btnOfflineMusic.setClickable(true);
            txtInfo.setVisibility(View.GONE);

        }else{
            btnOfflineMusic.setEnabled(false);
            btnOfflineMusic.setClickable(false);
            txtInfo.setVisibility(View.VISIBLE);
        }
    }

    public void OnOnlineMusic(View v) {
        Intent nextIntent = new Intent(SplashActivity.this, MainActivity.class);
        nextIntent.putExtra(Base.ONLINE_MUSIC, true);
        nextIntent.putExtra(Base.OFFLINE_MUSIC, false);
        startActivity(nextIntent);
    }

    public void OnOfflineMusic(View v) {
        Intent nextIntent = new Intent(SplashActivity.this, MainActivity.class);
        nextIntent.putExtra(Base.OFFLINE_MUSIC, true);
        nextIntent.putExtra(Base.ONLINE_MUSIC, false);
        startActivity(nextIntent);
    }
}
