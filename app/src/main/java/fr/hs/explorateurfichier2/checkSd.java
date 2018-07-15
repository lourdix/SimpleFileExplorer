package fr.hs.explorateurfichier2;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;

import pl.droidsonroids.gif.GifImageView;

public class checkSd extends AppCompatActivity implements View.OnClickListener {

    boolean doubleBackPressed = false;
    private GifImageView GifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //Cette directive enlève la barre de titre
        this.requestWindowFeature( Window.FEATURE_NO_TITLE );
// Cette directive permet d'enlever la barre de notifications pour afficher l'application en plein écran
        this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView( R.layout.testsd );
        /////////////
        GifImageView = findViewById( R.id.GifImageView );
        GifImageView.setImageResource( R.drawable.logowait );
        GifImageView.setOnClickListener( this );
        GifImageView.setEnabled( false );
        checkSd();
    }

    @Override
    public void onBackPressed() {

        if (doubleBackPressed) {
//            super.onBackPressed();
        } else {
            doubleBackPressed = true;
            Toast.makeText( this, "Appuyez de nouveau pour quitter", Toast.LENGTH_SHORT ).show();
            new android.os.Handler().postDelayed( new Runnable() {
                @Override
                public void run() {
                    doubleBackPressed = false;
                }
            }, 2000 );

        }


    }

    public void checkSd() {

        String etatSD = Environment.getExternalStorageState();
        if (etatSD.equals( Environment.MEDIA_MOUNTED )) {
            File storageSD = Environment.getExternalStorageDirectory();
            if (storageSD.exists() && storageSD.canRead()) {
                Handler handler = new Handler();
                handler.postDelayed( new Runnable() {
                    public void run() {
//                        startActivity(new Intent(this.getBaseContext(), explorateur.class));
                        startActivity( new Intent( getBaseContext(), MainActivity.class ) );
                    }
                }, 3000 );


            } else {
                ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1 ); // Popup permission;
                Toast.makeText( getBaseContext(), "Carte sd non accessible en lecture", Toast.LENGTH_SHORT ).show();
                GifImageView.setEnabled( true );
            }


        } else {
            Toast.makeText( getBaseContext(), "Carte sd non montée", Toast.LENGTH_SHORT ).show();
            GifImageView.setEnabled( true );
        }


    }


    @Override
    public void onClick(View v) {
        if (v == GifImageView) {
            checkSd();
        }
    }
} // OnClick Fin



