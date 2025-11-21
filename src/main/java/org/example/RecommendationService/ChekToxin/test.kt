package org.example.RecommendationService.ChekToxin

class test {
    fun test() {
        val secret = "ваш_секретный_ключ" // хранить в защищенном месте

        // при обработке входящего запроса:
        val authHeader = call.request.headers["Authorization"] ?: return call.respond(HttpStatusCode.Unauthorized)

        val token = authHeader.removePrefix("Bearer ").trim()

        try {
            val verifier = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("yourIssuer")
                .build()

            val decodedJWT = verifier.verify(token)
            // Тут можно проверить дополнительные данные
        } catch (e: JWTVerificationException) {
            return call.respond(HttpStatusCode.Unauthorized)
        }
    }
}