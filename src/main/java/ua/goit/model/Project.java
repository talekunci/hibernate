package ua.goit.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(generator = "projects_id_seq")
    private Long id = 0L;
    private String name;
    private String description;
    private int cost;
    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company projectCompany;

    @ManyToMany(mappedBy = "customerProjects")
    private Set<Customer> customers = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "project_developers",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "developer_id")}
    )
    private Set<Developer> projectDevelopers = new HashSet<>();

    public Project() {
    }

    public Project(String name, Company projectCompany) {
        this.name = name;
        this.projectCompany = projectCompany;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Company getCompany() {
        return projectCompany;
    }

    public void setCompany(Company projectCompany) {
        this.projectCompany = projectCompany;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Set<Developer> getDevelopers() {
        return projectDevelopers;
    }

    public void setDevelopers(Set<Developer> projectDevelopers) {
        this.projectDevelopers = projectDevelopers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return getCost() == project.getCost()
                && Objects.equals(getId(), project.getId())
                && Objects.equals(getName(), project.getName())
                && Objects.equals(getDescription(), project.getDescription())
                && Objects.equals(getCreationDate(), project.getCreationDate())
                && Objects.equals(projectCompany, project.projectCompany)
                && Objects.equals(getCustomers(), project.getCustomers())
                && Objects.equals(projectDevelopers, project.projectDevelopers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getId(),
                getName(),
                getDescription(),
                getCost(),
                getCreationDate(),
                projectCompany,
                getCustomers(),
                projectDevelopers
        );
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", company='" + projectCompany + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", creationDate=" + creationDate +
                '}';
    }
}
