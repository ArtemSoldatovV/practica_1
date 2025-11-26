import io.ktor.server.testing.*
import io.ktor.http.*
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class RecommendationServiceTest {

    @Test
    fun testRecommendationsEndpoint() {
        val mockConsumer = mock(KafkaConsumer::class.java) as KafkaConsumer<String, String>
        val record1 = ConsumerRecord("recommendation", "user1", "itemA,itemB")
        val records = listOf(record1)

        `when`(mockConsumer.poll(Duration.ofMillis(100))).thenReturn(records)

        val service = RecommendationService(kafkaConsumer = mockConsumer)

        withTestApplication({ service.module() }) {
            handleRequest(HttpMethod.Get,"/recommendations/user1").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assert(response.content == "[itemA, itemB]" || response.content.contains("itemA"))
            }
        }
    }
}