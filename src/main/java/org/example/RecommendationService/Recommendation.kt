package org.example.RecommendationService

import org.example.RecommendationService.UserAndRatings.CourseInfo

class Recommendation {
    val userAndRatings = UserAndRatings()

    data class TotalByTopic(val score: Int, val topic: String)

    fun DefiningRatingsByTopic(user_id: String): List<TotalByTopic>?{
        val user_information = userAndRatings.getUserCourses(user_id) //UserCourses или null

        val courses_with_scores: MutableList<TotalByTopic> = mutableListOf()
        if(user_information != null){
            courses_with_scores.plus(user_information.courses.get(0))

            for (i: CourseInfo in user_information.courses) {
                val index = courses_with_scores.indexOfFirst { userCourse ->
                    userCourse.topic == i.topic
                }

                if (index != -1) {
                    val currentItem = courses_with_scores[index]
                    val updatedItem = currentItem.copy(score = currentItem.score + i.score)
                    courses_with_scores[index] = updatedItem
                } else {
                    courses_with_scores.plus(TotalByTopic(i.score,i.topic))
                }
            }
            return courses_with_scores
        }
        return null
    }

    fun adding_to_the_list(){

    }

    fun recommendations(user_id: String): MutableList<UserAndRatings.Course>{
        val definingRatingsByTopic = DefiningRatingsByTopic(user_id) //темы и их сумма баллов
        val topTopics = definingRatingsByTopic?.sortedByDescending { it.score } ?: listOf()

        val completed_сourses = userAndRatings.getAllUserAndCoursesByUserId(user_id)//получение всех пройденных курсов пользователя
        val completed_сourseIds = completed_сourses.map { it.id_course }.toSet()

        val recommended_сourses = mutableListOf<UserAndRatings.Course>()

        for (topicRating in topTopics) {
            val courses_by_topic = userAndRatings.getCoursesByTopic(topicRating.topic)//поолучение всех курсов по теме
            val not_passed_сourses = courses_by_topic.filter { it.course_id !in completed_сourseIds }//исключение уже пройденных
            recommended_сourses.addAll(not_passed_сourses)
        }

        return recommended_сourses
    }


}