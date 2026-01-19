package fr.adriencournand.formation.kafka.consumer;

public class RiderLocation {
    public String id;
    public double latitude;
    public double longitude;

    public RiderLocation() {
    }

    public RiderLocation(String _id, double _latitude, double _longitude) {
        this.id = _id;
        this.latitude = _latitude;
        this.longitude = _longitude;
    }
}
