package com.bishe.db;

import com.bishe.db.db.StandAloneDB;

public class Application {

    public static void main(String[] args) {
        spinTest();
    }

    public static void spinTest() {
        StandAloneDB db = new StandAloneDB(1000, 500);
        try {
            db.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
