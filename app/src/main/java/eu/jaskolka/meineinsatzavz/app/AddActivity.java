package eu.jaskolka.meineinsatzavz.app;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.tfb.fbtoast.FBToast;
import eu.jaskolka.meineinsatzavz.R;

import java.util.Calendar;
import java.util.UUID;

import eu.jaskolka.meineinsatzavz.entity.Einsatz;
import io.realm.Realm;
import io.realm.RealmAsyncTask;

public class AddActivity extends AppCompatActivity {

    private EditText einsatzName;
    private EditText einsatzBeginn;
    private EditText einsatzEnde;
    private EditText avzStufe, einsatzStatus;
    DatePickerDialog datePickerDialog;

    private Realm myRealm;
    private RealmAsyncTask realmAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        einsatzName = findViewById(R.id.einsatz_bezeichnung);
        einsatzBeginn = findViewById(R.id.einsatz_beginn);
        einsatzBeginn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int jahr,
                                                  int monat, int tag) {
                                // set day of month , month and year value in the edit text
                                einsatzBeginn.setText(tag + "."
                                        + (monat + 1) + "." + jahr);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        einsatzEnde = findViewById(R.id.einsatz_ende);
        einsatzEnde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int jahr,
                                                  int monat, int tag) {
                                // set day of month , month and year value in the edit text
                                einsatzEnde.setText(tag + "."
                                        + (monat + 1) + "." + jahr);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        avzStufe = findViewById(R.id.avz_stufe);
        einsatzStatus = findViewById(R.id.einsatz_status);
        myRealm = Realm.getDefaultInstance();

    }




    private void insertRecords() {

        final String id = UUID.randomUUID().toString();

        final String einsatz_name = einsatzName.getText().toString().trim();
        final String einsatz_beginn = einsatzBeginn.getText().toString().trim();
        final String einsatz_ende = einsatzEnde.getText().toString().trim();
        final String avz_stufe = avzStufe.getText().toString().trim();
        final String einsatz_status = einsatzStatus.getText().toString().trim();

        if (einsatz_name.isEmpty()) {
            FBToast.errorToast(AddActivity.this,
                    "Einsatzbezeichnung einfügen ..."
                    , FBToast.LENGTH_SHORT);
            return;
        }

        if (einsatz_beginn.isEmpty()) {
            FBToast.errorToast(AddActivity.this,
                    "Einsatzbeginn erforderlich ..."
                    , FBToast.LENGTH_SHORT);
            return;
        }

        if (einsatz_ende.isEmpty()) {
            FBToast.errorToast(AddActivity.this,
                    "Einsatzende erforderlich ..."
                    , FBToast.LENGTH_SHORT);
            return;
        }

        if (avz_stufe.isEmpty()) {
            FBToast.errorToast(AddActivity.this,
                    "AVZ-Stufe erforderlich ..."
                    , FBToast.LENGTH_SHORT);
            return;
        }

        if (einsatz_status.isEmpty()) {
            FBToast.errorToast(AddActivity.this,
                    "Status erforderlich ..."
                    , FBToast.LENGTH_SHORT);
            return;
        }

        realmAsyncTask = myRealm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        Einsatz einsatz = realm.createObject(Einsatz.class, id);
                        einsatz.setEinsatzName(einsatz_name);
                        einsatz.setEinsatzBeginn(einsatz_beginn);
                        einsatz.setEinsatzEnde(einsatz_ende);
                        einsatz.setAvzSatz(Double.parseDouble(avz_stufe));
                        einsatz.setEinsatzStatus(einsatz_status);

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
                        einsatzStatus.setText("");

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
