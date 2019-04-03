package com.example.swim_zad_2_b;

public class Position {

    private String Author, Title, Date;
    private Boolean isFilm;
    private Float Rating;


    public Position(String Title, String Author, String Date, Float Rating, Boolean isFilm){
        this.Author = Author;
        this.Title = Title;
        this.Date = Date;
        this.Rating = Rating;
        this.isFilm = isFilm;
    }

    public String getAuthor() {
        return Author;
    }

    public String getTitle() {
        return Title;
    }

    public String getDate() {
        return Date;
    }

    public Float getRating() {
        return Rating;
    }

    public Boolean getFilm() {
        return isFilm;
    }

    @Override
    public String toString() {
        return Title + "  ~" + Author +" [" + Date + "]  ocena: " + Float.toString(Rating) + "( /4)" + ((isFilm) ? "F" : "K");
    }
}
