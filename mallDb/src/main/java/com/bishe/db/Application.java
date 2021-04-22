package com.bishe.db;

import com.bishe.db.db.StandAloneDB;
import com.bishe.db.queue.RequestLinkedQueue;
import com.bishe.db.queue.ResponseMapQueue;

public class Application {

    public static void main(String[] args) {
        spinTest();
    }

    public static void spinTest() {
        StandAloneDB db = new StandAloneDB(16, new RequestLinkedQueue(),
                new ResponseMapQueue());
        try {
            db.start();
        } catch (InterruptedException e) {
            db.stop();
            e.printStackTrace();
        }
    }
}
