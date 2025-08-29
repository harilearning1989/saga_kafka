package com.web.saga;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "test-topic" })
class KafkaIntegrationTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private String receivedMessage;
    private final CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void listen(ConsumerRecord<String, String> record) {
        this.receivedMessage = record.value();
        latch.countDown();
    }

    @Test
    void testKafkaMessaging() throws Exception {
        // send message
        kafkaTemplate.send("test-topic", "hello from test");

        // wait until listener consumes
        boolean messageConsumed = latch.await(5, TimeUnit.SECONDS);

        assertThat(messageConsumed).isTrue();
        assertThat(receivedMessage).isEqualTo("hello from test");
    }
}

