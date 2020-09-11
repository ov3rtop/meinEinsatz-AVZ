package eu.jaskolka.meineinsatzavz.app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import eu.jaskolka.meineinsatzavz.R;

import com.tfb.fbtoast.FBToast;

import eu.jaskolka.meineinsatzavz.entity.Einsatz;
import io.realm.Realm;
import io.realm.RealmResults;

public class EditActivity extends AppCompatActivity {

    private EditText einsatzName, einsatzBeginn, einsatzEnde, einsatzStatus, avzStufe;
    private Realm myRealm;
    Bundle bundle;
    int position;
    private Einsatz einsatz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        bundle = getIntent().getExtras();

        if (bundle != null)
            position = bundle.getInt("position");

        einsatzName = findViewById(R.id.einsatz_bezeichnung);
        einsatzBeginn = findViewById(R.id.einsatz_beginn);
        einsatzEnde = findViewById(R.id.einsatz_ende);
        avzStufe = findViewById(R.id.avz_stufe);
        einsatzStatus = findViewById(R.id.einsatz_status);

        myRealm = Realm.getDefaultInstance();

        RealmResults<Einsatz> realmResults = myRealm.where(Einsatz.class).findAll();
        einsatz = realmResults.get(position);
        setupViews(einsatz);

    }

    private void setupViews(Einsatz einsatz) {

        einsatzName.setText(einsatz.getEinsatzName());
        einsatzBeginn.setText(einsatz.getEinsatzBeginn());
        einsatzEnde.setText(einsatz.getEinsatzEnde());
        avzStufe.setText("" + einsatz.getAvzSatz());
        einsatzStatus.setText(einsatz.getEinsatzStatus());

    }

    private void updateEinsatz() {

        myRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                einsatz.setEinsatzName(einsatzName.getText().toString().trim());
                einsatz.setEinsatzBeginn(einsatzBeginn.getText().toString().trim());
                einsatz.setEinsatzEnde(einsatzEnde.getText().toString().trim());
                einsatz.setAvzSatz(Double.parseDouble(avzStufe.getText().toString().trim()));
                einsatz.setEinsatzStatus(einsatzStatus.getText().toString().trim());

                FBToast.successToast(EditActivity.this,
                        "Edit Record Successfully ..."
                        , FBToast.LENGTH_SHORT);

            }
        });

    }

    public void editEinsatz(View view) {
        updateEinsatz();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myRealm.close();
    }
}
