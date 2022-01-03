package ua.goit.dao;

import ua.goit.model.Company;
import ua.goit.model.Developer;


public class CompanyDao extends AbstractDao<Company> {

   private static CompanyDao instance;

    private CompanyDao() {
        super(Company.class);
    }

    public static CompanyDao getInstance() {
        return instance == null ? instance = new CompanyDao() : instance;
    }

    public void addDeveloper(Company company, Developer developer) {
        company.getDevelopers().add(developer);
        DeveloperDao.getInstance().addCompany(developer, company);
    }

    public void removeDeveloper(Company company, Developer developer) {
        company.getDevelopers().remove(developer);
        DeveloperDao.getInstance().removeCompany(developer, company);
    }
}
