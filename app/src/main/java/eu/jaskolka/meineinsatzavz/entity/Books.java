package eu.jaskolka.meineinsatzavz.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Books extends RealmObject {

    @PrimaryKey
    @Required
    private String bookId;
    @Required
    private String einsatzName;
    @Required
    private String authorName;
    @Required
    private Double bookPrice;
    @Required
    private String bookDescription;

    public Books() {
    }

    public Books(String einsatzName, String authorName, Double bookPrice, String bookDescription) {
        this.einsatzName = einsatzName;
        this.authorName = authorName;
        this.bookPrice = bookPrice;
        this.bookDescription = bookDescription;
    }

    public String getBookId() {
        return bookId;
    }

    public String getEinsatzName() {
        return einsatzName;
    }

    public void setEinsatzName(String einsatzName) {
        this.einsatzName = einsatzName;
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
