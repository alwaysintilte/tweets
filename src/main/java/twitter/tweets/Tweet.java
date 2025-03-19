package twitter.tweets;

public class Tweet {

    double latitude;

    double longitude;

    String text;

    String statePostalCode;

    Double sentimentNumber;

    public Tweet(double longitude, double latitude, String text, Double sentimentNumber){
        this.latitude=latitude;
        this.longitude=longitude;
        this.text=text;
        this.sentimentNumber=sentimentNumber;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public String getStatePostalCode() {
        return this.statePostalCode;
    }

    public Double getSentimentNumber() {
        return this.sentimentNumber;
    }

    public void setLatitude(double latitude) {
        this.latitude=latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude=longitude;
    }

    public void setStatePostalCode(String statePostalCode) {
        this.statePostalCode=statePostalCode;
    }

    public void setSentimentNumber(double sentimentNumber) {
        this.sentimentNumber=sentimentNumber;
    }
}
