package com.edu.yellingapp;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class YellingAppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(YellingAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        MarketKafkaStreamsApp.maskingPipeline();
    }
}
