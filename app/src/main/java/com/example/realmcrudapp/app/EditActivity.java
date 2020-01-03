package com.example.realmcrudapp.app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.realmcrudapp.R;
import com.example.realmcrudapp.entity.Books;
import com.tfb.fbtoast.FBToast;

import io.realm.Realm;
import io.realm.RealmResults;

public class EditActivity extends AppCompatActivity {

    private EditText book_name, author_name, book_price, book_description;
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

        book_name = findViewById(R.id.edit_book_name_edit_text);
        author_name = findViewById(R.id.edit_author_name_edit_text);
        book_price = findViewById(R.id.edit_book_price_edit_text);
        book_description = findViewById(R.id.edit_book_description_edit_text);

        myRealm = Realm.getDefaultInstance();

        RealmResults<Books> realmResults = myRealm.where(Books.class).findAll();
        books = realmResults.get(position);
        setupViews(books);

    }

    private void setupViews(Books books) {

        book_name.setText(books.getBookName());
        author_name.setText(books.getAuthorName());
        book_price.setText("" + books.getBookPrice());
        book_description.setText(books.getBookDescription());
    }

    private void updateBooks() {

        myRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                books.setBookName(book_name.getText().toString().trim());
                books.setAuthorName(author_name.getText().toString().trim());
                books.setBookPrice(Double.parseDouble(book_price.getText().toString().trim()));
                books.setBookDescription(book_description.getText().toString().trim());

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
