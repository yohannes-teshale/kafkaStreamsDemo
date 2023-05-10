package com.edu.yellingapp;

import com.edu.yellingapp.models.JobPosting;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Arrays;
import java.util.Properties;

public class MarketKafkaStreamsApp {
    public static void maskingPipeline() {


        StreamsConfig streamsConfig = new StreamsConfig(getProperties());

//        JsonSerializer<JobPosting> purchaseJsonSerializer = new JsonSerializer<>();
////        JsonDeserializer<JobPosting> purchaseJsonDeserializer = new JsonDeserializer<>(JobPosting.class);
////
////        Serde<JobPosting> jobPostingSerde = Serdes.serdeFrom(purchaseJsonSerializer);

        Serde<String> stringSerde = Serdes.String();
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<String, String > appleJobStreams = streamsBuilder.stream("job-posting",
                         Consumed.with(stringSerde, stringSerde))
                        .mapValues(x->x.toUpperCase())
                        .filter((y,x)->x.contains("APPLE"));
//        appleJobStreams.print(Printed.toFile("/Users/yohannes/schoolProjects/job-postings/JobPostingsProducer/YellingApp/stream.txt"));

        appleJobStreams.to("output",Produced.with(Serdes.String(),Serdes.String()));
        KafkaStreams kafkaStreams= new KafkaStreams(streamsBuilder.build(),streamsConfig);
        kafkaStreams.start();


    }

    private static Properties getProperties() {
        Properties props = new Properties();

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "job-posting");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");
        props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,Serdes.String().getClass());
        return props;
    }
}
