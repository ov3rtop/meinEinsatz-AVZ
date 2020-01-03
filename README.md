# Realm Android
Realm is a mobile database that runs directly inside phones, tablets or wearables. This repository holds the source code for the Java version of Realm, which currently runs only on Android.

###  dependencies

```gradle

repositories {
        maven { url 'https://jitpack.io' }
        }
 // add top of build.gradle:app         
apply plugin: 'realm-android'

```

### Realm Configuration 

```java

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmConfigs extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("books.realm").build();
        Realm.setDefaultConfiguration(configuration);

    }
}

```

### Realm Table Model 

```java

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Books extends RealmObject {

    @PrimaryKey
    @Required
    private String bookId;
    @Required
    private String bookName;
    @Required
    private String authorName;
    @Required
    private Double bookPrice;
    @Required
    private String bookDescription;

    public Books() {
    }

    public Books(String bookName, String authorName, Double bookPrice, String bookDescription) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookPrice = bookPrice;
        this.bookDescription = bookDescription;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }
}


```

### Insert Records 

```java

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

```

### Display Records 

```java

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

```

### Update Records 

```java

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

```
### Delete Records 

```java

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

```

## Realm Work Done!



