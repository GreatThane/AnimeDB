package org.thane.AnimeDB.model;

public class SuggestionRequest {

    private String malusername;
    private String accuracy;
    private String minimumRating;

    public String getMalusername() {
        return malusername;
    }

    public void setMalusername(String malusername) {
        this.malusername = malusername;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getMinimumRating() {
        return minimumRating;
    }

    public void setMinimumRating(String minimumRating) {
        this.minimumRating = minimumRating;
    }

    public AnimeSuggestion toAnimeSuggestion() {
        return new AnimeSuggestion(this);
    }

}
