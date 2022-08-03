package ib;


import javafx.beans.property.*;

public class Reclamation {

    /**
     * This class is used to define shown data in tableView in dbFXServer.
     */

    private IntegerProperty id;
    private DoubleProperty price;
    private StringProperty date;
    private IntegerProperty clientID;
    private StringProperty deviceID;
    private StringProperty status;

    public Reclamation() {
        this.id = new SimpleIntegerProperty();
        this.price = new SimpleDoubleProperty();
        this.date = new SimpleStringProperty();
        this.clientID = new SimpleIntegerProperty();
        this.deviceID = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String country) {
        this.date.set(country);
    }

    public void setCountry(String country) {
        this.date.set(country);
    }

    public int getClientID() {
        return clientID.get();
    }

    public IntegerProperty ClientIDProperty() {
        return clientID;
    }

    public void setClientID(int id) {
        this.clientID.set(id);
    }

    public String getDeviceID() {
        return deviceID.get();
    }

    public StringProperty deviceIDProperty() {
        return deviceID;
    }

    public void setDeviceID(String country) {
        this.deviceID.set(country);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusIDProperty() {
        return status;
    }

    public void setStatus(String country) {
        this.status.set(country);
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", price=" + price +
                ", date=" + date +
                ", clientID=" + clientID +
                ", deviceID=" + deviceID +
                ", status=" + status +
                '}';
    }
}
