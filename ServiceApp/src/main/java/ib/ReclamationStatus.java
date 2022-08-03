package ib;

import javafx.beans.property.*;

public class ReclamationStatus {

    /**
     * ReclamationStatus class is used to define shown data in tableView in dbFXClient.
     */
    
    private IntegerProperty clientID;
    private StringProperty deviceName;
    private StringProperty serialNo;
    private DoubleProperty price;
    private StringProperty reclamationStatus;

    public ReclamationStatus() {
        this.clientID = new SimpleIntegerProperty();
        this.deviceName = new SimpleStringProperty();
        this.serialNo = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();
        this.reclamationStatus = new SimpleStringProperty();
    }

    public int getClientID() {
        return clientID.get();
    }

    public IntegerProperty clientIDProperty() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID.set(clientID);
    }

    public String getDeviceName() {
        return deviceName.get();
    }

    public StringProperty deviceNameProperty() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName.set(deviceName);
    }

    public String getSerialNo() {
        return serialNo.get();
    }

    public StringProperty serialNoProperty() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo.set(serialNo);
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

    public String getReclamationStatus() {
        return reclamationStatus.get();
    }

    public StringProperty reclamationStatusProperty() {
        return reclamationStatus;
    }

    public void setReclamationStatus(String reclamationStatus) {
        this.reclamationStatus.set(reclamationStatus);
    }
}
