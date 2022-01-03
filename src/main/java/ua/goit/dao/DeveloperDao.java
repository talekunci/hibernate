package ua.goit.dao;

import ua.goit.model.Company;
import ua.goit.model.Developer;
import ua.goit.model.Skill;

import java.util.List;
import java.util.stream.Collectors;

public class DeveloperDao extends AbstractDao<Developer> {

    private static DeveloperDao instance;

    private DeveloperDao() {
        super(Developer.class);
    }

    public static DeveloperDao getInstance() {
        return instance == null ? instance = new DeveloperDao() : instance;
    }

    public List<Developer> getBySkill(Skill skill) {
        return getAll()
                .stream()
                .filter(developer -> developer.getSkills().contains(skill))
                .collect(Collectors.toList());
    }

    public List<Developer> getBySkillBranch(String branch) {
        return getAll()
                .stream()
                .filter(d -> d.getSkills().stream().anyMatch(s -> s.getBranch().equalsIgnoreCase(branch)))
                .collect(Collectors.toList());
    }

    public List<Developer> getBySkillLevel(String skillLevel) {
        return getAll()
                .stream()
                .filter(dev -> dev.getSkills().stream().anyMatch(s -> s.getSkillLevel().equalsIgnoreCase(skillLevel)))
                .collect(Collectors.toList());
    }

    public void addCompany(Developer developer, Company company) {
        em.getTransaction().begin();
        developer.getCompanies().add(company);
        em.merge(developer);
        em.getTransaction().commit();
    }

    public void removeCompany(Developer developer, Company company) {
        em.getTransaction().begin();
        developer.getCompanies().remove(company);
        em.merge(developer);
        em.getTransaction().commit();
    }

    public void addSkill(Developer developer, Skill skill) {
        em.getTransaction().begin();
        developer.getSkills().add(skill);
        em.merge(developer);
        em.getTransaction().commit();
    }

    public void removeSkill(Developer developer, Skill skill) {
        em.getTransaction().begin();
        developer.getSkills().remove(skill);
        em.merge(developer);
        em.getTransaction().commit();
    }
}
