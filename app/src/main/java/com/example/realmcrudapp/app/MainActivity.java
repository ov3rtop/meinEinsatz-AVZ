package com.example.realmcrudapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realmcrudapp.R;
import com.example.realmcrudapp.entity.Books;
import com.example.realmcrudapp.ui.BookRecyclerview;
import com.tfb.fbtoast.FBToast;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm myRealm;
    private RecyclerView recyclerView;
    private BookRecyclerview bookRecyclerview;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRealm = Realm.getDefaultInstance();
        displayBooks();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bookRecyclerview != null)
            bookRecyclerview.notifyDataSetChanged();
    }

    private void displayBooks() {

        final RealmResults<Books> realmResults = myRealm.where(Books.class).findAll();

        recyclerView = findViewById(R.id.my_book_list);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);

        bookRecyclerview = new BookRecyclerview(this, myRealm, realmResults);
        recyclerView.setAdapter(bookRecyclerview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.setting_menu:

                FBToast.infoToast(MainActivity.this,
                        "Setting Menu Is Clicked"
                        , FBToast.LENGTH_SHORT);

                break;

            case R.id.insert_menu:
                startActivity(new Intent(MainActivity.this, AddActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myRealm.close();
    }
}
