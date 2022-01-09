package ua.goit.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "developers")
public class Developer {

    @Id
    @GeneratedValue(generator = "developers_id_seq")
    private Long id = 0L;
    private String name;
    private int age;
    private String gender;
    private String description;
    private int salary;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "developer_skills",
            joinColumns = {@JoinColumn(name = "developer_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")}
    )
    private Set<Skill> developerSkills = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "developer_companies",
            joinColumns = {@JoinColumn(name = "developer_id")},
            inverseJoinColumns = {@JoinColumn(name = "company_id")}
    )
    private Set<Company> developerCompanies = new HashSet<>();

    @ManyToMany(mappedBy = "projectDevelopers")
    private Set<Project> developerProjects = new HashSet<>();

    public Developer() {
    }

    public Developer(String name) {
        this.name = name;
    }

    public Developer(String name, int age, String gender, String description, Integer salary) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.description = description;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Set<Skill> getSkills() {
        return developerSkills;
    }

    public void setSkills(Set<Skill> skills) {
        this.developerSkills = skills;
    }

    public Set<Company> getCompanies() {
        return developerCompanies;
    }

    public void setCompanies(Set<Company> companies) {
        this.developerCompanies = companies;
    }

    public Set<Project> getProjects() {
        return developerProjects;
    }

    public void setProjects(Set<Project> projects) {
        this.developerProjects = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return getAge() == developer.getAge()
                && getSalary() == developer.getSalary()
                && getId().equals(developer.getId())
                && getName().equals(developer.getName())
                && Objects.equals(getGender(), developer.getGender())
                && Objects.equals(getDescription(), developer.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAge(), getGender(), getDescription(), getSalary());
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", description='" + description + '\'' +
                ", salary=" + salary +
                '}';
    }
}
