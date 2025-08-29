package com.web.saga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SagaApplication {

    /*
        @EnableKafka → Enables Kafka listener annotated endpoints.
        @KafkaListener → Marks a method to consume messages from a Kafka topic.
        @KafkaHandler → Handles multiple message types in the same listener class.
        @SendTo → Sends the result of a listener method to another Kafka topic.
        @EmbeddedKafka → Used in testing to spin up an in-memory Kafka broker.
     */
	public static void main(String[] args) {
		SpringApplication.run(SagaApplication.class, args);
	}


    /*// This method consumes messages from "input-topic"
    // and automatically sends the return value to "output-topic"
    @KafkaListener(topics = "input-topic", groupId = "my-group")
    @SendTo("output-topic")  this will have now Processed message
    public String processMessage(String message) {
        System.out.println("Received: " + message);
        return "Processed " + message;
    }*/

}
