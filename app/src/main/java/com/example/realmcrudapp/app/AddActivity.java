package com.example.realmcrudapp.app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.realmcrudapp.R;
import com.example.realmcrudapp.entity.Books;
import com.tfb.fbtoast.FBToast;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

public class AddActivity extends AppCompatActivity {

    private EditText bookName, authorName, bookPrice, bookDescription;
    private Realm myRealm;
    private RealmAsyncTask realmAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        bookName = findViewById(R.id.book_name_edit_text);
        authorName = findViewById(R.id.author_name_edit_text);
        bookPrice = findViewById(R.id.book_price_edit_text);
        bookDescription = findViewById(R.id.book_description_edit_text);

        myRealm = Realm.getDefaultInstance();

    }

    private void insertRecords() {

        final String id = UUID.randomUUID().toString();

        final String book_name = bookName.getText().toString().trim();
        final String author_name = authorName.getText().toString().trim();
        final String book_price = bookPrice.getText().toString().trim();
        final String book_description = bookDescription.getText().toString().trim();


        if (book_name.isEmpty()) {
            FBToast.successToast(AddActivity.this,
                    "Enter Book Name Here ..."
                    , FBToast.LENGTH_SHORT);
            return;
        }

        if (author_name.isEmpty()) {
            FBToast.successToast(AddActivity.this,
                    "Enter Author Name Here ..."
                    , FBToast.LENGTH_SHORT);
            return;
        }

        if (book_price.isEmpty()) {
            FBToast.successToast(AddActivity.this,
                    "Enter Book Price Here ..."
                    , FBToast.LENGTH_SHORT);
            return;
        }

        if (book_description.isEmpty()) {
            FBToast.successToast(AddActivity.this,
                    "Enter Book Description Here ..."
                    , FBToast.LENGTH_SHORT);
            return;
        }

        realmAsyncTask = myRealm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        Books books = realm.createObject(Books.class, id);
                        books.setBookName(book_name);
                        books.setAuthorName(author_name);
                        books.setBookPrice(Double.parseDouble(book_price));
                        books.setBookDescription(book_description);
                    }
                },
                new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {

                        FBToast.successToast(AddActivity.this,
                                "Insert Record Successfully ..."
                                , FBToast.LENGTH_SHORT);

                        bookName.setText("");
                        authorName.setText("");
                        bookPrice.setText("");
                        bookDescription.setText("");

                    }
                },

                new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {

                        FBToast.errorToast(AddActivity.this,
                                "Error Is Insert Record ..."
                                , FBToast.LENGTH_SHORT);
                    }
                });


    }

    public void addBooks(View view) {
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
