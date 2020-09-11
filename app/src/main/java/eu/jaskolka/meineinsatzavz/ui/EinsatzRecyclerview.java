package eu.jaskolka.meineinsatzavz.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import eu.jaskolka.meineinsatzavz.R;
import eu.jaskolka.meineinsatzavz.app.EditActivity;
import eu.jaskolka.meineinsatzavz.entity.Einsatz;
import com.tfb.fbtoast.FBToast;
import com.uncopt.android.widget.text.justify.JustifiedTextView;
import io.realm.Realm;
import io.realm.RealmResults;

public class EinsatzRecyclerview extends RecyclerView.Adapter<EinsatzRecyclerview.Holders> {

    private Context context;
    private Realm realm;
    private RealmResults<Einsatz> realmResults;
    private LayoutInflater inflater;


    public EinsatzRecyclerview(Context context, Realm realm, RealmResults<Einsatz> realmResults) {
        this.context = context;
        this.realm = realm;
        this.realmResults = realmResults;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.einsatz_list_layout, parent, false);
        Holders holders = new Holders(view);
        return holders;
    }

    @Override
    public void onBindViewHolder(@NonNull Holders holder, int position) {

        final Einsatz einsatz = realmResults.get(position);
        holder.setEinsatz(einsatz, position);
        holder.setListener();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Angeklickt " + einsatz.getEinsatzId(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    public class Holders extends RecyclerView.ViewHolder {

        private int position;
        private TextView einsatz_bezeichnung, einsatz_beginn, einsatz_ende, avz_stufe;
        private JustifiedTextView einsatz_status;
        private ImageView editImage, deleteImage;

        public Holders(@NonNull View itemView) {
            super(itemView);

            einsatz_bezeichnung = itemView.findViewById(R.id.einsatz_bezeichnung);
            einsatz_beginn = itemView.findViewById(R.id.einsatz_beginn);
            einsatz_ende = itemView.findViewById(R.id.einsatz_ende);
            avz_stufe = itemView.findViewById(R.id.avz_stufe);
            einsatz_status = itemView.findViewById(R.id.einsatz_status);
            editImage = itemView.findViewById(R.id.edit_image_view);
            deleteImage = itemView.findViewById(R.id.delete_image_view);

        }

        public void setEinsatz(Einsatz einsatz, int position) {

            this.position = position;
            String einsatzName = einsatz.getEinsatzName();
            String einsatzBeginn = einsatz.getEinsatzBeginn();
            String einsatzEnde = einsatz.getEinsatzEnde();
            Double avzStufe = einsatz.getAvzSatz();
            String einsatzStatus = einsatz.getEinsatzStatus();

            einsatz_bezeichnung.setText(einsatzName);
            einsatz_beginn.setText(einsatzBeginn);
            einsatz_ende.setText(einsatzEnde);
            avz_stufe.setText(String.valueOf(avzStufe));
            einsatz_status.setText(einsatzStatus);


        }

        public void setListener() {

            editImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, EditActivity.class);
                    intent.putExtra("position", position);
                    context.startActivity(intent);

                }
            });

            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {

                            realmResults.deleteFromRealm(position);

                            FBToast.errorToast(context,
                                    "Delete Record Successfully"
                                    , FBToast.LENGTH_SHORT);

                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, realmResults.size());
                        }
                    });

                }
            });

        }

    }
}
