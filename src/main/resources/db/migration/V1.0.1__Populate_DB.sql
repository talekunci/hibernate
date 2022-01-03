INSERT INTO developers (name, age, gender)
VALUES ('Misha', 16, 'male');


INSERT INTO developers (name, age, gender)
VALUES ('Vera', 35, 'female');


INSERT INTO developers (name, age, gender)
VALUES ('Kolya', 56, 'male');


INSERT INTO developers (name, age, gender)
VALUES ('Anna', 12, 'female');


INSERT INTO skills (branch, skill_level)
VALUES ('Java', 'Junior');


INSERT INTO skills (branch, skill_level)
VALUES ('Java', 'Middle');


INSERT INTO skills (branch, skill_level)
VALUES ('SQL', 'Junior');


INSERT INTO skills (branch, skill_level)
VALUES ('SQL', 'Middle');


INSERT INTO developer_skills (developer_id, skill_id)
VALUES (1, 2);


INSERT INTO developer_skills (developer_id, skill_id)
VALUES (2, 1);


INSERT INTO developer_skills (developer_id, skill_id)
VALUES (3, 1);


INSERT INTO developer_skills (developer_id, skill_id)
VALUES (4, 2);


INSERT INTO developer_skills (developer_id, skill_id)
VALUES (4, (SELECT id FROM skills WHERE branch = 'SQL' AND skill_level = 'Junior'));


INSERT INTO companies (name)
VALUES ('GOOGLE');


INSERT INTO companies (name)
VALUES ('AMAZON');


INSERT INTO developer_companies (developer_id, company_id)
VALUES ((SELECT id FROM developers WHERE name = 'Anna'), (SELECT id FROM companies WHERE name = 'GOOGLE'));


INSERT INTO developer_companies (developer_id, company_id)
VALUES ((SELECT id FROM developers WHERE name = 'Kolya'), (SELECT id FROM companies WHERE name = 'GOOGLE'));


INSERT INTO developer_companies (developer_id, company_id)
VALUES ((SELECT id FROM developers WHERE name = 'Misha'), (SELECT id FROM companies WHERE name = 'AMAZON'));


INSERT INTO developer_companies (developer_id, company_id)
VALUES ((SELECT id FROM developers WHERE name = 'Vera'), (SELECT id FROM companies WHERE name = 'AMAZON'));


INSERT INTO projects (company_id, name)
VALUES ((SELECT id FROM companies WHERE name = 'GOOGLE'), 'ARA');


INSERT INTO projects (company_id, name)
VALUES ((SELECT id FROM companies WHERE name = 'AMAZON'), 'Kindle');


INSERT INTO projects (company_id, name)
VALUES ((SELECT id FROM companies WHERE name = 'AMAZON'), 'Alexa');


INSERT INTO customers (name)
VALUES ('GOOGLE');


INSERT INTO customers (name)
VALUES ('People');


INSERT INTO customers_projects (customer_id, project_id)
VALUES ((SELECT id FROM customers WHERE name = 'GOOGLE'), (SELECT id FROM projects WHERE name = 'ARA'));


INSERT INTO customers_projects (customer_id, project_id)
VALUES ((SELECT id FROM customers WHERE name = 'People'), (SELECT id FROM projects WHERE name = 'Kindle'));


INSERT INTO customers_projects (customer_id, project_id)
VALUES ((SELECT id FROM customers WHERE name = 'People'), (SELECT id FROM projects WHERE name = 'Alexa'));


INSERT INTO project_developers (developer_id, project_id)
VALUES ((SELECT id FROM developers WHERE name = 'Anna'), (SELECT id FROM projects WHERE name = 'ARA'));


INSERT INTO project_developers (developer_id, project_id)
VALUES ((SELECT id FROM developers WHERE name = 'Kolya'), (SELECT id FROM projects WHERE name = 'ARA'));


INSERT INTO project_developers (developer_id, project_id)
VALUES ((SELECT id FROM developers WHERE name = 'Misha'), (SELECT id FROM projects WHERE name = 'Alexa'));


INSERT INTO project_developers (developer_id, project_id)
VALUES ((SELECT id FROM developers WHERE name = 'Misha'), (SELECT id FROM projects WHERE name = 'Kindle'));


INSERT INTO project_developers (developer_id, project_id)
VALUES ((SELECT id FROM developers WHERE name = 'Vera'), (SELECT id FROM projects WHERE name = 'Alexa'));


UPDATE projects
SET creation_date = '2013-10-29'
WHERE name = 'ARA';


UPDATE projects
SET creation_date = '2007-11-19'
WHERE name = 'Kindle';


UPDATE projects
SET creation_date = '2014-11-06'
WHERE name = 'Alexa';


UPDATE developers d
SET salary = 300
WHERE salary = 0
  AND
    (SELECT skill_id
     FROM developer_skills ds
     WHERE ds.developer_id = d.id
     LIMIT 1) <> 0;


UPDATE developers d
SET salary = salary + 200
WHERE d.id =
    (SELECT developer_id
     FROM developer_skills ds
     JOIN skills s ON d.id = ds.developer_id
     AND ds.skill_id = s.id
     AND s.branch = 'Java'
     AND s.skill_level = 'Middle');


UPDATE developers d
SET salary = salary + 250
WHERE d.id =
    (SELECT developer_id
     FROM developer_skills ds
     JOIN skills s ON ds.skill_id = s.id
     AND ds.developer_id = d.id
     AND s.branch = 'SQL');


UPDATE projects p
SET cost =
  (SELECT Sum(d.salary)
   FROM developers d
   JOIN project_developers pd ON d.id = pd.developer_id
   AND pd.project_id = p.id);