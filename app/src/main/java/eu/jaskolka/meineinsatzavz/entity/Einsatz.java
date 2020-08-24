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
    private Date einsatzBeginn;
    @Required
    private Date einsatzEnde;
    @Required
    private Date heranBeginn;
    @Required
    private Date heranEnde;
    @Required
    private String einsatzStatus;
    @Required
    private Double avzSatz;
    @Required
    private Double avzSumme;

    public Einsatz() {
    }

    public String getEinsatzId() { return einsatzId; }

    public String getEinsatzName() {
        return einsatzName;
    }

    public void setEinsatzName(String einsatzName) {
        this.einsatzName = einsatzName;
    }

    public Date getEinsatzBeginn() {
        return einsatzBeginn;
    }

    public void setEinsatzBeginn(Date einsatzBeginn) {
        this.einsatzBeginn = einsatzBeginn;
    }

    public Date getEinsatzEnde() {
        return einsatzEnde;
    }

    public void setEinsatzEnde(Date einsatzEnde) {
        this.einsatzEnde = einsatzEnde;
    }

    public Date getHeranBeginn() {
        return heranBeginn;
    }

    public void setHeranBeginn(Date heranBeginn) {
        this.heranBeginn = heranBeginn;
    }

    public Date getHeranEnde() {
        return heranEnde;
    }

    public void setHeranEnde(Date heranEnde) {
        this.heranEnde = heranEnde;
    }

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

    public Double getAvzSumme() {
        return avzSumme;
    }

    public void setAvzSumme(Double avzSumme) {
        this.avzSumme = avzSumme;
    }

    public Einsatz(String einsatzName, Date einsatzBeginn, Date einsatzEnde, Date heranBeginn,
                   Date heranEnde, String einsatzStatus, Double avzSatz, Double avzSumme) {
        this.einsatzName = einsatzName;
        this.einsatzBeginn = einsatzBeginn;
        this.einsatzEnde = einsatzEnde;
        this.heranBeginn = heranBeginn;
        this.heranEnde = heranEnde;
        this.einsatzStatus = einsatzStatus;
        this.avzSatz = avzSatz;
        this.avzSumme = avzSumme;
    }
}
