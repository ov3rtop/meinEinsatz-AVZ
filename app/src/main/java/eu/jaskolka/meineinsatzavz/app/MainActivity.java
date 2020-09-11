package eu.jaskolka.meineinsatzavz.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import eu.jaskolka.meineinsatzavz.R;
import eu.jaskolka.meineinsatzavz.entity.Einsatz;
import eu.jaskolka.meineinsatzavz.ui.EinsatzRecyclerview;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm myRealm;
    private RecyclerView recyclerView;
    private EinsatzRecyclerview einsatzRecyclerview;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRealm = Realm.getDefaultInstance();
        einsatzListe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (einsatzRecyclerview != null)
            einsatzRecyclerview.notifyDataSetChanged();
    }

    private void einsatzListe() {

        final RealmResults<Einsatz> realmResults = myRealm.where(Einsatz.class).findAll();

        recyclerView = findViewById(R.id.meine_einsatz_liste);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);

        einsatzRecyclerview = new EinsatzRecyclerview(this, myRealm, realmResults);
        recyclerView.setAdapter(einsatzRecyclerview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

/*            case R.id.setting_menu:

                FBToast.infoToast(MainActivity.this,
                        "Setting Menu Is Clicked"
                        , FBToast.LENGTH_SHORT);

                break;*/

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
