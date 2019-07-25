package com.liyan.pojo;

public class Student {
    private Integer id;
    private String name;
    private Integer age;
    private Integer tid;
    private Teacher teacher;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", tid=" + tid +
                ", teacher=" + teacher +
                '}';
    }

    public Student() {
    }

    public Student(Integer id, String name, Integer age, Integer tid, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.tid = tid;
        this.teacher = teacher;
    }
}
