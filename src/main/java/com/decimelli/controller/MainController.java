package com.decimelli.controller;

import com.decimelli.model.Song;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {

    private static Session sf;

    static {
       Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    }

    public static void main(String[] args) {
        setup();
        up(sf);
//        addSong("Stairway to Heaven", "Led Zeppelin", "IV");
//        addSong("Black Dog", "Led Zeppelin", "IV");
//        addSong("Velvet Glove", "Red Hot Chili Peppers", "Californication");
//        addSong("Californication", "Red Hot Chili Peppers", "Californication");

        sf.createQuery("from Song").list().forEach(System.out::println);

        down(sf);
    }

    private static void addSong(String name, String artist, String album) {
        Song example = new Song(name, artist, album);
        sf.save(example);
    }

    private static void down(Session sf) {
        sf.getTransaction().commit();
    }

    private static void up(Session sf) {
        sf.beginTransaction();
    }

    private static void setup() {
        sf = new MetadataSources(new StandardServiceRegistryBuilder()
                .configure()
                .build())
                .buildMetadata()
                .buildSessionFactory()
                .openSession();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if(sf != null) sf.close();
    }
}
