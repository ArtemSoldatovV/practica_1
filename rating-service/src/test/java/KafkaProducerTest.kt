import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class KafkaProducerTest {

    @Test
    void testSendMessage() {
        KafkaTemplate<String, String> kafkaTemplate = mock(KafkaTemplate.class);
        KafkaProducer producer = new KafkaProducer(kafkaTemplate);

        String testMessage = "test message";

        producer.sendMessage(testMessage);

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(kafkaTemplate).send(topicCaptor.capture(), messageCaptor.capture());

        assertEquals("recommendation", topicCaptor.getValue());
        assertEquals(testMessage, messageCaptor.getValue());
    }
}