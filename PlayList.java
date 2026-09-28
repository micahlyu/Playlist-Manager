/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author micah
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Menu-driven playlist manager.
 */
public class PlayList {

    private MyDoubleLinkedList<Song> playlist;
    private Random random;

    public PlayList() {
        playlist = new MyDoubleLinkedList<Song>();
        random = new Random();
    }

    public static void main(String[] args) {
        PlayList manager = new PlayList();
        manager.run();
    }

    private void run() {
        Scanner input = new Scanner(System.in);

        printMenu();

        boolean running = true;

        while (running) {
            System.out.print("\n: ");

            String command =
                    input.nextLine().trim().toLowerCase();

            switch (command) {

                case "add":
                    addSong(input);
                    break;

                case "remove":
                    removeSong(input);
                    break;

                case "count":
                    System.out.println(playlist.count());
                    break;

                case "play":
                    play();
                    break;

                case "shuffle":
                    shuffle();
                    break;

                case "reverse":
                    playlist.reverse();
                    break;

                case "save":
                    savePlaylist(input);
                    break;

                case "load":
                    loadPlaylist(input);
                    break;

                case "quit":
                    running = false;
                    break;

                case "":
                    break;

                default:
                    System.out.println("Invalid command.");
                    break;
            }
        }

        input.close();
    }

    private void printMenu() {
        System.out.println("*** Playlist Manager! ***");
        System.out.println("Commands:");
        System.out.println("add");
        System.out.println("remove");
        System.out.println("count");
        System.out.println("play");
        System.out.println("shuffle");
        System.out.println("reverse");
        System.out.println("save");
        System.out.println("load");
        System.out.println("quit");
    }

    private void addSong(Scanner input) {
        System.out.print("Enter artist: ");
        String artist = input.nextLine();

        System.out.print("Enter title: ");
        String title = input.nextLine();

        playlist.add(new Song(artist, title));
    }

    private void removeSong(Scanner input) {
        System.out.print("Enter artist: ");
        String artist = input.nextLine();

        System.out.print("Enter title: ");
        String title = input.nextLine();

        boolean removed =
                playlist.remove(new Song(artist, title));

        if (!removed) {
            System.out.println("Song not found.");
        }
    }

    private void play() {
        if (playlist.isEmpty()) {
            System.out.println("Playlist is empty.");
            return;
        }

        for (Song song : playlist) {
            System.out.println(song);
        }
    }

    /**
     * Rearranges the songs pseudo-randomly.
     */
    private void shuffle() {
        int size = playlist.size();

        if (size < 2) {
            return;
        }

        Song[] original = new Song[size];

        for (int i = 0; i < size; i++) {
            original[i] = playlist.get(i);
        }

        do {
            for (int i = size - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);

                Song temp = playlist.get(i);

                playlist.set(i, playlist.get(j));
                playlist.set(j, temp);
            }

        } while (sameOrder(original));
    }

    private boolean sameOrder(Song[] original) {
        for (int i = 0; i < original.length; i++) {
            if (!original[i].equals(playlist.get(i))) {
                return false;
            }
        }

        return true;
    }

    private void savePlaylist(Scanner input) {
        System.out.print("Enter file: ");
        String fileName = input.nextLine().trim();

        if (fileName.isEmpty()) {
            System.out.println(
                    "File name cannot be empty.");
            return;
        }

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(fileName))) {

            for (Song song : playlist) {
                writer.write(song.getArtist());
                writer.write("\t");
                writer.write(song.getTitle());
                writer.newLine();
            }

            System.out.println("Playlist saved.");

        } catch (IOException e) {
            System.out.println(
                    "Unable to save playlist.");
        }
    }

    private void loadPlaylist(Scanner input) {
        System.out.print("Enter file: ");
        String fileName = input.nextLine().trim();

        if (fileName.isEmpty()) {
            System.out.println(
                    "File name cannot be empty.");
            return;
        }

        MyDoubleLinkedList<Song> loaded =
                new MyDoubleLinkedList<Song>();

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts =
                        line.split("\t", 2);

                if (parts.length == 2) {
                    loaded.add(
                            new Song(parts[0], parts[1]));
                }
            }

            playlist = loaded;

            System.out.println("Playlist loaded.");

        } catch (IOException e) {
            System.out.println(
                    "Unable to load playlist.");
        }
    }
}