CREATE TABLE developers
  (
     id          SERIAL PRIMARY KEY,
     NAME        VARCHAR(50) NOT NULL,
     age         INTEGER,
     gender      VARCHAR(6),
     description VARCHAR(250),
     salary      INTEGER DEFAULT 0
  );

CREATE TABLE companies
  (
     id          SERIAL PRIMARY KEY,
     NAME        VARCHAR(50) NOT NULL,
     description VARCHAR(250)
  );

CREATE TABLE projects
  (
     id            SERIAL PRIMARY KEY,
     company_id    INTEGER NOT NULL,
     NAME          VARCHAR(50) NOT NULL,
     description   VARCHAR(150),
     cost          INTEGER DEFAULT 0,
     creation_date DATE DEFAULT CURRENT_TIMESTAMP,
     CONSTRAINT company_id_fk FOREIGN KEY (company_id) REFERENCES companies(id)
  );

CREATE TABLE customers
  (
     id          SERIAL PRIMARY KEY,
     NAME        VARCHAR(50) NOT NULL,
     description VARCHAR(250)
  );

CREATE TABLE skills
  (
     id          SERIAL PRIMARY KEY,
     branch      VARCHAR(20) NOT NULL,
     skill_level VARCHAR(20) NOT NULL
  );

CREATE TABLE developer_skills
  (
     developer_id INTEGER NOT NULL,
     skill_id     INTEGER NOT NULL,
     CONSTRAINT developer_id_fk FOREIGN KEY (developer_id) REFERENCES developers
     (id),
     CONSTRAINT skill_id_fk FOREIGN KEY (skill_id) REFERENCES skills(id)
  );

CREATE TABLE project_developers
  (
     developer_id INTEGER NOT NULL,
     project_id   INTEGER NOT NULL,
     CONSTRAINT developer_id_fk FOREIGN KEY (developer_id) REFERENCES developers
     (id),
     CONSTRAINT project_id_fk FOREIGN KEY (project_id) REFERENCES projects(id)
  );

CREATE TABLE developer_companies
  (
     developer_id INTEGER NOT NULL,
     company_id   INTEGER NOT NULL,
     CONSTRAINT developer_id_fk FOREIGN KEY (developer_id) REFERENCES developers
     (id),
     CONSTRAINT company_id_fk FOREIGN KEY (company_id) REFERENCES companies(id)
  );

CREATE TABLE customers_projects
  (
     customer_id INTEGER NOT NULL,
     project_id  INTEGER NOT NULL,
     CONSTRAINT customer_id_fk FOREIGN KEY (customer_id) REFERENCES customers(id
     ),
     CONSTRAINT project_id_fk FOREIGN KEY (project_id) REFERENCES projects(id)
  );