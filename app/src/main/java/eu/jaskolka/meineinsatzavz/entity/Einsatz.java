package eu.jaskolka.meineinsatzavz.entity;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Einsatz extends RealmObject {

    @PrimaryKey
    @Required
    private String einsatzId;
    @Required
    private String einsatzName;
    @Required
    private String einsatzBeginn;
    @Required
    private String einsatzEnde;
    @Required
    private String einsatzStatus;
    @Required
    private Double avzSatz;


    public Einsatz() {
    }

    public Einsatz(String einsatzName, String einsatzBeginn, String einsatzEnde, String einsatzStatus, Double avzSatz) {
        this.einsatzName = einsatzName;
        this.einsatzBeginn = einsatzBeginn;
        this.einsatzEnde = einsatzEnde;
        this.einsatzStatus = einsatzStatus;
        this.avzSatz = avzSatz;
    }

    public String getEinsatzId() { return einsatzId; }

    public String getEinsatzName() { return einsatzName; }

    public void setEinsatzName(String einsatzName) {
        this.einsatzName = einsatzName;
    }

    public String getEinsatzBeginn() {
        return einsatzBeginn;
    }

    public void setEinsatzBeginn(String einsatzBeginn) {
        this.einsatzBeginn = einsatzBeginn;
    }

    public String getEinsatzEnde() {
        return einsatzEnde;
    }

    public void setEinsatzEnde(String einsatzEnde) { this.einsatzEnde = einsatzEnde; }

    public String getEinsatzStatus() {
        return einsatzStatus;
    }

    public void setEinsatzStatus(String einsatzStatus) {
        this.einsatzStatus = einsatzStatus;
    }

    public Double getAvzSatz() {
        return avzSatz;
    }

    public void setAvzSatz(Double avzSatz) {
        this.avzSatz = avzSatz;
    }


}
