package org.example.RecommendationService

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration
import java.util.*

class RecommendationService {

    private val recommendation = mutableMapOf<String, List<String>>()
    private val kafkaConsumer: KafkaConsumer<String, String>
    private val scope = CoroutineScope(Dispatchers.Default)

    init {
        val props = Properties().apply {
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
            put(ConsumerConfig.GROUP_ID_CONFIG, "recommendation-group")
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
        }
        kafkaConsumer = KafkaConsumer(props)
        kafkaConsumer.subscribe(listOf("recommendations"))

        scope.launch {
            while (isActive) {
                val records = kafkaConsumer.poll(Duration.ofMillis(100))
                for (record in records) {
                    val userId = record.key()
                    val recs = record.value().split(",")
                    recommendation[userId] = recs
                }
            }
        }
    }

    fun Application.module() {
        routing {
            get("/recommendations/{userId}") {
                val userId = call.parameters["userId"]
                if (userId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Missing or invalid userId")
                    return@get
                }
                val recs = recommendation[userId] ?: emptyList()
                call.respond(recs)
            }
        }
    }
    
}