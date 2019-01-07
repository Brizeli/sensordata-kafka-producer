package com.next.kafkaproducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.kafkaproducer.model.SensorData;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class SensorDataGenerator {
    private static final Random random = new Random();
    private static final ObjectMapper mapper = new ObjectMapper();
    static double upLeftLat = 32.177807, upLeftLong = 34.852143;
    static double upRightLat = 32.177534, upRightLong = 34.856631;
    static double lowLeftLat = 32.172993, lowLeftLong = 34.852280;
    static double lowRightLat = 32.173844, lowRightLong = 34.856667;
    static long id = 0;

    static SensorData createRandomSensorData(double lowLeftLat, double lowLeftLong, double upRightLat, double upRightLong) {
        double lat = lowLeftLat + Math.random() * (upRightLat - lowLeftLat);
        double lng = lowLeftLong + Math.random() * (upRightLong - lowLeftLong);
        return new SensorData(id++, lat, lng, random.nextBoolean(), System.currentTimeMillis());
    }

    public static void main(String[] args) throws IOException {
//        try (PrintWriter fos = new PrintWriter(new FileOutputStream("d:/sensordata/sensordata.txt"))) {
//            for (int i = 0; i < 1000; i++) {
//                SensorData sd = createRandomSensorData();
//                fos.println(sd);
//            }
//        }
        try (PrintWriter w = new PrintWriter(new FileOutputStream("d:/sensordata/sensordata.json", true))) {
            for (int i = 0; i < 1000; i++) {
                SensorData sd = createRandomSensorData(lowLeftLat, lowLeftLong, upRightLat, upRightLong);
                w.println(mapper.writeValueAsString(sd));
            }
        }
    }
}
