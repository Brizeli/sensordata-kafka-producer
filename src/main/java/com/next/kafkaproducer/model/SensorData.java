package com.next.kafkaproducer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class SensorData implements Serializable {
    Long id;
    Double lat, lng;
    boolean free;
    long timestamp;

    @Override
    public String toString() {
        return id + "," + lat + "," + lng + "," + free + "," + timestamp;
    }

}
