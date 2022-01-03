package ua.goit.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {

    @Id @GeneratedValue(generator = "projects_id_seq")
    private Long id = 0L;
    @Column(name = "company_id")
    private Long companyId;
    private String name;
    private String description;
    private int cost;
    @Column(name = "creation_date")
    private Date creationDate;

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

    public Project(Long companyId, String name) {
        this.companyId = companyId;
        this.name = name;
        creationDate = new Date(new java.util.Date().getTime());
    }

    public Project(Long companyId, String name, String description, int cost, Date creationDate) {
        this.companyId = companyId;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Set<Developer> getDevelopers() {
        return projectDevelopers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.projectDevelopers = developers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return getId().equals(project.getId())
                && getCompanyId().equals(project.getCompanyId())
                && getName().equals(project.getName())
                && Objects.equals(getDescription(), project.getDescription())
                && getCreationDate().equals(project.getCreationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCompanyId(), getName(), getDescription(), getCreationDate());
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", creationDate=" + creationDate +
                '}';
    }
}
