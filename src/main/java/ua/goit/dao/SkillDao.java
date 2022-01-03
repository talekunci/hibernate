package ua.goit.dao;

import ua.goit.model.Skill;

import java.util.List;
import java.util.stream.Collectors;


public class SkillDao extends AbstractDao<Skill> {

    private static SkillDao instance;

    private SkillDao() {
        super(Skill.class);
    }

    public static SkillDao getInstance() {
        return instance == null ? instance = new SkillDao() : instance;
    }

    public List<Skill> getByLevel(String skillLevel) {
        return getAll()
                .stream()
                .filter(s -> s.getSkillLevel().equalsIgnoreCase(skillLevel))
                .collect(Collectors.toList());
    }

    public List<Skill> getByBranch(String branch) {
        return getAll()
                .stream()
                .filter(s -> s.getBranch().equalsIgnoreCase(branch))
                .collect(Collectors.toList());
    }
}
