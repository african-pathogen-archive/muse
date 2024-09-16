CREATE TABLE if not exists project_study
(
    id                  uuid        DEFAULT uuid_generate_v4(),
    project_id          uuid        not null,
    study_id            text        not null,
    PRIMARY KEY (id)
);