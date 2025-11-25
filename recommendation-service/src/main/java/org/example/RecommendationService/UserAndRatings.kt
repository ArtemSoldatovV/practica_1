package org.example.RecommendationService;

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


class UserAndRatings {
    //тут я получаю пользователя и его все оценки по курсам и виды курсов

    object Users_db : Table() {
        val user_id = varchar("user_id", 50)
        override val primaryKey = PrimaryKey(user_id)
        val name = varchar("name", 100)
        val surname = varchar("surname", 100)
        val patronymic = varchar("patronymic", 100)
    }

    object Course_db : Table() {
        val course_id = varchar("course_id", 50)
        override val primaryKey = PrimaryKey(course_id)
        val name = varchar("name", 100)
        val topic = varchar("topic", 100)
    }

    object UserAndCourses_db : Table() {
        val id = varchar("id", 50)
        override val primaryKey = PrimaryKey(id)
        val id_user = varchar("id_user", 50).references(Users_db.user_id)
        val id_course = varchar("id_course", 50).references(Course_db.course_id)
        val score = integer("score")
    }

    data class CourseInfo(val courseName: String, val score: Int, val topic: String)
    data class UserCourses(val userName: String, val courses: List<CourseInfo>)

    fun getUserCourses(user_id: String): UserCourses? {
        return transaction {
            val userName = Users_db.select { Users_db.user_id eq user_id }
                .map {
                    (it[Users_db.name] + " " + it[Users_db.surname] + " " + it[Users_db.patronymic])?: (it[Users_db.name] + " " + it[Users_db.surname])
                }
                .firstOrNull() ?: return@transaction null

            val coursesData = (UserAndCourses_db innerJoin Course_db).select { UserAndCourses_db.id_user eq user_id }
                .map {
                    CourseInfo(
                        courseName = it[Course_db.name],
                        score = it[UserAndCourses_db.score],
                        topic = it[Course_db.topic]
                    )
                }

            UserCourses(userName = userName, courses = coursesData)
        }
    }

    data class Course(val course_id: String, val name: String, val topic: String)

    fun getAllCourses(): List<Course> {
        return transaction {
            Course_db.selectAll().map {
                Course(
                    course_id = it[Course_db.course_id],
                    name = it[Course_db.name],
                    topic = it[Course_db.topic]
                )
            }
        }
    }
    fun getCoursesByTopic(topic: String): List<Course> {
        return transaction {
            Course_db.select { Course_db.topic eq topic }
                .map {
                    Course(
                        course_id = it[Course_db.course_id],
                        name = it[Course_db.name],
                        topic = it[Course_db.topic]
                    )
                }
        }
    }
    fun getCoursesById(course_id: String): List<Course> {
        return transaction {
            Course_db.select { Course_db.course_id eq course_id }
                .map {
                    Course(
                        course_id = it[Course_db.course_id],
                        name = it[Course_db.name],
                        topic = it[Course_db.topic]
                    )
                }
        }
    }

    data class UserAndCourses(val Id_user_and_courses: String, val id_user: String, val id_course: String, val score: Int)

    fun getAllUserAndCourses(): List<UserAndCourses> {
        return transaction {
            UserAndCourses_db.selectAll().map {
                UserAndCourses(
                    Id_user_and_courses = it[UserAndCourses_db.id],
                    id_user = it[UserAndCourses_db.id_user],
                    id_course = it[UserAndCourses_db.id_course],
                    score = it[UserAndCourses_db.score]
                )
            }
        }
    }

    fun getAllUserAndCoursesByUserId(id_user: String): List<UserAndCourses> {
        return transaction {
            UserAndCourses_db.select { UserAndCourses_db.id_user eq id_user }
                .map {
                    UserAndCourses(
                        Id_user_and_courses = it[UserAndCourses_db.id],
                        id_user = it[UserAndCourses_db.id_user],
                        id_course = it[UserAndCourses_db.id_course],
                        score = it[UserAndCourses_db.score]
                    )
                }
        }
    }

}
