package ua.goit.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "skills")
public class Skill {

    @Id @GeneratedValue(generator = "skills_id_seq")
    private Long id = 0L;
    private String branch;
    @Column(name = "skill_level")
    private String skillLevel;

    public Skill() {
    }

    public Skill(String branch, String skillLevel) {
        this.branch = branch;
        this.skillLevel = skillLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return getId().equals(skill.getId())
                && getBranch().equals(skill.getBranch())
                && getSkillLevel().equals(skill.getSkillLevel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBranch(), getSkillLevel());
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", branch='" + branch + '\'' +
                ", skillLevel='" + skillLevel + '\'' +
                '}';
    }
}
