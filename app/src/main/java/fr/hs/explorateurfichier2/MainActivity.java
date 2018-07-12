package fr.hs.explorateurfichier2;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {


    private File file;
    private ListView liste;
    private File[] files;
    private String path, pathSelected;
    private ImageButton imageHomeBtn;

    private void remplirlist(String path, ListView liste) { // Fonction pour l'initialisation du hashmap qui servira de liste de rep/fichier
        File f = new File( path );
        files = f.listFiles(); // files contient la liste des fichiers et repertoires du dossier
        String[] tImages = new String[ 14 ];

        // creation d'un tableau pour nos images
        tImages[ 0 ] = String.valueOf( R.drawable.dossiers );
        tImages[ 1 ] = String.valueOf( R.drawable.notreadable );
        tImages[ 2 ] = String.valueOf( R.drawable.pdf );
        tImages[ 3 ] = String.valueOf( R.drawable.video );
        tImages[ 4 ] = String.valueOf( R.drawable.photo );
        tImages[ 5 ] = String.valueOf( R.drawable.txt );
        tImages[ 6 ] = String.valueOf( R.drawable.csv );
        tImages[ 7 ] = String.valueOf( R.drawable.doc );
        tImages[ 8 ] = String.valueOf( R.drawable.rar );
        tImages[ 9 ] = String.valueOf( R.drawable.rar );
        tImages[ 10 ] = String.valueOf( R.drawable.xls );
        tImages[ 11 ] = String.valueOf( R.drawable.xml );
        tImages[ 12 ] = String.valueOf( R.drawable.pptx );
        tImages[ 13 ] = String.valueOf( R.drawable.apk );



        ////////////////////////


        if (files.length <= 0) { // si aucun fichier et ou dossier message "dossier vide"
            Toast toast = Toast.makeText( getBaseContext(), "Dossier vide", Toast.LENGTH_SHORT );
            toast.show();
        }

        try {

            List<Map<String, String>> listeFichier = new ArrayList<>();
            Map<String, String> hm;
            int i = 0;
            for (File inFile : files) { //
                hm = new HashMap<>();
                if (inFile.isDirectory()) { // Si c'est un dossier mettre icone dossier
                    hm.put( "image", tImages[ 0 ] );
                    hm.put( "nom", files[ i ].getName() );
                    i++;

                } else if (inFile.isFile()) { // Sinon si c'est un fichier mettre icone approprié
                    String extensionFile = URLConnection.guessContentTypeFromName( inFile.getName() );
                    switch ( extensionFile ) { // Switch case pour determiner l'icone qu'il faudra en fonction de l'extension
                        case "image/jpeg": // jpg
                            hm.put( "image", tImages[ 4 ] );
                            hm.put( "nom", files[ i ].getName() );
                            break;
                        case "image/png": // png
                            hm.put( "image", tImages[ 4 ] );
                            hm.put( "nom", files[ i ].getName() );
                            break;
                        case "video/mp4": // video
                            hm.put( "image", tImages[ 3 ] );
                            hm.put( "nom", files[ i ].getName() );
                            break;
                        case "application/pdf": // pdf
                            hm.put( "image", tImages[ 2 ] );
                            hm.put( "nom", files[ i ].getName() );
                            break;
                        case "text/xml": // xml
                            hm.put( "image", tImages[ 11 ] );
                            hm.put( "nom", files[ i ].getName() );
                            break;
                        case "text/plain": // txt
                            hm.put( "image", tImages[ 5 ] );
                            hm.put( "nom", files[ i ].getName() );
                            break;
                        case "text/comma-separated-values": // CSV
                            hm.put( "image", tImages[ 6 ] );
                            hm.put( "nom", files[ i ].getName() );
                            break;
                        case "application/msword": // word
                            hm.put( "image", tImages[ 7 ] );
                            hm.put( "nom", files[ i ].getName() );
                            break;
                        case "application/vnd.oasis.opendocument.text": // word
                            hm.put( "image", tImages[ 7 ] );
                            hm.put( "nom", files[ i ].getName() );
                            break;
                        case "application/vnd.openxmlformats-officedocument.wordprocessingml.document": // word
                            hm.put( "image", tImages[ 7 ] );
                            hm.put( "nom", files[ i ].getName() );
                            break;
                        case "application/rar": // rar
                            hm.put( "image", tImages[ 8 ] );
                            hm.put( "nom", files[ i ].getName() );
                            break;
                        case "application/zip": // zip
                            hm.put( "image", tImages[ 9 ] );
                            hm.put( "nom", files[ i ].getName() );
                            break;
                        case "application/vnd.openxmlformats-officedocument.presentationml.presentation": // pptx
                            hm.put( "image", tImages[ 12 ] );
                            hm.put( "nom", files[ i ].getName() );
                            break;
                        case "application/vnd.android.package-archive": // apk
                            hm.put( "image", tImages[ 13 ] );
                            hm.put( "nom", files[ i ].getName() );
                            break;
                        default:
                            hm.put( "image", tImages[ 1 ] );
                            hm.put( "nom", files[ i ].getName() );
                            System.out.println( files[ i ].getName() + " : " + extensionFile );
                            break;

                    } // Fin switch case
                    i++;

                }else {
                    hm.put( "image", tImages[ 1 ] );
                    hm.put( "nom", files[ i ].getName() );
                }


                listeFichier.add( hm );
            } // fin for


            // ajout d'un "SimpleAdapter" pour créer un modèle de liste avec le contenu de listefichier
            SimpleAdapter sa = new SimpleAdapter( this.getBaseContext(), listeFichier, R.layout.explorateurlist, new String[]{"image", "nom"}, new int[]{R.id.ImageViewLogo, R.id.Fichier} );


            liste.setAdapter( sa ); // Application de notre SimpleAdapter à "liste"


        } catch (Exception e) {
            System.out.println("Erreur ? " +e.toString());
        } // exception


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        // Permet d'enlever la barre de notifications pour afficher l'application en plein écran
      //  this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );


        path = Environment.getExternalStorageDirectory().getAbsolutePath();


        liste = findViewById( R.id.liste );
        imageHomeBtn = findViewById( R.id.imageHomeBtn );
        liste.setOnItemClickListener( this );
        imageHomeBtn.setOnClickListener( this );
        remplirlist( path, liste );

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(files[ position ].toString());
        pathSelected = files[ position ].toString();


        file = new File( pathSelected );
        if (file.isDirectory()) { // Si c'est un dossier afficher arborescence
            remplirlist( pathSelected, liste );
        } else {
            remplirlist( path, liste );
        }
    }


    @Override
    public void onClick(View v) {
        if (v==imageHomeBtn){
            remplirlist( path,liste );
        }
    }
}
