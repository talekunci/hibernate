package ua.goit.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "companies")
public class Company {

    @Id @GeneratedValue(generator = "companies_id_seq")
    private Long id = 0L;
    private String name;
    private String description;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "developerCompanies")
    private Set<Developer> companyDevelopers = new HashSet<>();

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    public Company(String name, String description) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Developer> getDevelopers() {
        return companyDevelopers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.companyDevelopers = developers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return getId().equals(company.getId())
                && getName().equals(company.getName())
                && Objects.equals(getDescription(), company.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription());
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
