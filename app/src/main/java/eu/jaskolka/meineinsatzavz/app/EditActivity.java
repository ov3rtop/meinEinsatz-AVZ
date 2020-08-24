package eu.jaskolka.meineinsatzavz.app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import eu.jaskolka.meineinsatzavz.R;
import eu.jaskolka.meineinsatzavz.entity.Books;
import com.tfb.fbtoast.FBToast;

import io.realm.Realm;
import io.realm.RealmResults;

public class EditActivity extends AppCompatActivity {

    private EditText einsatz_name, einsatzBeginn, einsatzEnde, avzStufe;
    private Realm myRealm;
    Bundle bundle;
    int position;
    private Books books;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        bundle = getIntent().getExtras();

        if (bundle != null)
            position = bundle.getInt("position");

        einsatz_name = findViewById(R.id.edit_book_name_edit_text);
        einsatzBeginn = findViewById(R.id.edit_author_name_edit_text);
        einsatzEnde = findViewById(R.id.edit_book_price_edit_text);
        avzStufe = findViewById(R.id.edit_book_description_edit_text);

        myRealm = Realm.getDefaultInstance();

        RealmResults<Books> realmResults = myRealm.where(Books.class).findAll();
        books = realmResults.get(position);
        setupViews(books);

    }

    private void setupViews(Books books) {

        einsatz_name.setText(books.getEinsatzName());
        einsatzBeginn.setText(books.getAuthorName());
        einsatzEnde.setText("" + books.getBookPrice());
        avzStufe.setText(books.getBookDescription());
    }

    private void updateBooks() {

        myRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                books.setEinsatzName(einsatz_name.getText().toString().trim());
                books.setAuthorName(einsatzBeginn.getText().toString().trim());
                books.setBookPrice(Double.parseDouble(einsatzEnde.getText().toString().trim()));
                books.setBookDescription(avzStufe.getText().toString().trim());

                FBToast.successToast(EditActivity.this,
                        "Edit Record Successfully ..."
                        , FBToast.LENGTH_SHORT);

            }
        });

    }

    public void editBooks(View view) {
        updateBooks();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myRealm.close();
    }
}
