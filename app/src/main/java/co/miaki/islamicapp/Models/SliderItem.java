package co.miaki.islamicapp.Models;

public class SliderItem {

    private String description;
    private String imageUrl;

    public SliderItem(String url1, String abc) {
        this.description = abc;
        this.imageUrl = url1;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

