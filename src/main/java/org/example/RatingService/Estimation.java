package org.example.RatingService;

import jakarta.persistence.*;
import java.util.List;

import org.example.UserService.User;
import org.example.CourseService.Course;

public class Estimation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    int scores;

    @ManyToOne
    @JoinColumn(name = "id_user")
    User user;
    @ManyToOne
    @JoinColumn(name = "id_Coures")
    Course courses;

    public String EstimationToString(){
        //создание сообщения виде запроса JONS
        String exit ="";
        exit += "\"user\":" + "\"" + user.getFullName()+ "\"";
        exit += " \"courses\":" + "\"" + courses.getName() + "\"";;
        return exit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourses() {
        return courses;
    }

    public void setCourses(Course courses) {
        this.courses = courses;
    }
}
