package com.next.kafkaproducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.kafkaproducer.model.SensorData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class KafkaProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(KafkaTemplate<String, String> template) {
//        double upLeftLat = 32.177807, upLeftLong = 34.852143;
        double upRightLat = 32.177534, upRightLong = 34.856631;
        double lowLeftLat = 32.172993, lowLeftLong = 34.852280;
//        double lowRightLat = 32.173844, lowRightLong = 34.856667;
        ObjectMapper mapper = new ObjectMapper();
        return args -> {
            while (true) {
                SensorData sd =
                        SensorDataGenerator.createRandomSensorData(lowLeftLat, lowLeftLong, upRightLat, upRightLong);
                template.send("sensor", mapper.writeValueAsString(sd));
                TimeUnit.SECONDS.sleep(1);
            }
        };
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    //    @KafkaListener(topics = "test")
    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
        System.out.println(cr.value().toString());
    }
}
