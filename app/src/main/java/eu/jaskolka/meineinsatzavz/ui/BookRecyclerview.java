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
import eu.jaskolka.meineinsatzavz.entity.Books;
import com.tfb.fbtoast.FBToast;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import io.realm.Realm;
import io.realm.RealmResults;

public class BookRecyclerview extends RecyclerView.Adapter<BookRecyclerview.Holders> {

    private Context context;
    private Realm realm;
    private RealmResults<Books> realmResults;
    private LayoutInflater inflater;


    public BookRecyclerview(Context context, Realm realm, RealmResults<Books> realmResults) {
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

        Books books = realmResults.get(position);
        holder.setBooks(books, position);
        holder.setListener();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Angeklickt", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }

    public class Holders extends RecyclerView.ViewHolder {

        private int position;
        private TextView book_name, author_name, book_price;
        private JustifiedTextView book_description;
        private ImageView editImage, deleteImage;

        public Holders(@NonNull View itemView) {
            super(itemView);

            book_name = itemView.findViewById(R.id.book_name_text_view);
            author_name = itemView.findViewById(R.id.einsatz_beginn_text_view);
            book_price = itemView.findViewById(R.id.book_price_text_view);
            book_description = itemView.findViewById(R.id.book_description_text_view);
            editImage = itemView.findViewById(R.id.edit_image_view);
            deleteImage = itemView.findViewById(R.id.delete_image_view);

        }

        public void setBooks(Books books, int position) {

            this.position = position;
            String name = books.getEinsatzName();
            String author = books.getAuthorName();
            Double price = books.getBookPrice();
            String description = books.getBookDescription();

            book_name.setText(name);
            author_name.setText(author);
            book_price.setText("$ " + price);
            book_description.setText(description);


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
