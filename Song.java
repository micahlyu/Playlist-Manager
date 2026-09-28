/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author micah
 */


/**
 * Stores the artist and title for one song.
 */
public class Song {

    private String artist;
    private String title;

    public Song(String artist, String title) {
        this.artist = artist == null ? "" : artist;
        this.title = title == null ? "" : title;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return artist + " - " + title;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Song)) {
            return false;
        }

        Song other = (Song) obj;

        return artist.equalsIgnoreCase(other.artist)
                && title.equalsIgnoreCase(other.title);
    }

    @Override
    public int hashCode() {
        return (artist.toLowerCase() + "\n"
                + title.toLowerCase()).hashCode();
    }
}
