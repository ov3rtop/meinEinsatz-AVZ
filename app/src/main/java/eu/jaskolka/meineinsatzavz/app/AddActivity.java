package eu.jaskolka.meineinsatzavz.app;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.tfb.fbtoast.FBToast;

import eu.jaskolka.meineinsatzavz.R;
import eu.jaskolka.meineinsatzavz.entity.Books;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

public class AddActivity extends AppCompatActivity {

    private EditText einsatzName, einsatzBeginn, einsatzEnde, avzStufe;
    private Realm myRealm;
    private RealmAsyncTask realmAsyncTask;
    DatePickerDialog picker;
    EditText eText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        einsatzName = findViewById(R.id.book_name_edit_text);
        einsatzBeginn = findViewById(R.id.einsatz_beginn_edit_text);
        einsatzEnde = findViewById(R.id.book_price_edit_text);
        avzStufe = findViewById(R.id.book_description_edit_text);

        myRealm = Realm.getDefaultInstance();

    }

    private void insertRecords() {

        final String id = UUID.randomUUID().toString();

        final String einsatz_name = einsatzName.getText().toString().trim();
        final String einsatz_beginn = einsatzBeginn.getText().toString().trim();
        final String einsatz_ende = einsatzEnde.getText().toString().trim();
        final String avz_stufe = avzStufe.getText().toString().trim();


        if (einsatz_name.isEmpty()) {
            FBToast.successToast(AddActivity.this,
                    "Einsatzbezeichnung einfügen ..."
                    , FBToast.LENGTH_SHORT);
            return;
        }

        if (einsatz_beginn.isEmpty()) {
            FBToast.successToast(AddActivity.this,
                    "Einsatzbeginn erforderlich ..."
                    , FBToast.LENGTH_SHORT);
            return;
        }

        if (einsatz_ende.isEmpty()) {
            FBToast.successToast(AddActivity.this,
                    "Einsatzende erforderlich ..."
                    , FBToast.LENGTH_SHORT);
            return;
        }

        if (avz_stufe.isEmpty()) {
            FBToast.successToast(AddActivity.this,
                    "AVZ-Stufe erforderlich ..."
                    , FBToast.LENGTH_SHORT);
            return;
        }

        realmAsyncTask = myRealm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        Books books = realm.createObject(Books.class, id);
                        books.setEinsatzName(einsatz_name);
                        books.setAuthorName(einsatz_beginn);
                        books.setBookPrice(Double.parseDouble(einsatz_ende));
                        books.setBookDescription(avz_stufe);
                    }
                },
                new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {

                        FBToast.successToast(AddActivity.this,
                                "Einsatz hinzugefügt ..."
                                , FBToast.LENGTH_SHORT);

                        einsatzName.setText("");
                        einsatzBeginn.setText("");
                        einsatzEnde.setText("");
                        avzStufe.setText("");

                    }
                },

                new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {

                        FBToast.errorToast(AddActivity.this,
                                "FEHLER ..."
                                , FBToast.LENGTH_SHORT);
                    }
                });


    }

    public void addEinsatz(View view) {
        insertRecords();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (realmAsyncTask != null && realmAsyncTask.isCancelled()) {
            realmAsyncTask.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myRealm.close();
    }
}
