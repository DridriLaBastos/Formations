package fr.adriencournand.formation.kafka.Producer;

public class RiderLocation {
    public String id;
    public double latitude;
    public double longitude;

    RiderLocation() {
    }

    RiderLocation(String _id, double _latitude, double _longitude) {
        this.id = _id;
        this.latitude = _latitude;
        this.longitude = _longitude;
    }
}
