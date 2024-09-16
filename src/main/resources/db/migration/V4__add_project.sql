CREATE TABLE if not exists project
(
    project_id          uuid        DEFAULT uuid_generate_v4(),
    pathogen            text        not null,
    name                text        not null,
    no_of_samples       int         not null,
    user_id             uuid        not null,
    created_at          timestamptz DEFAULT current_timestamp,
    PRIMARY KEY (project_id)
);

-- BEGIN
--     IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'client_contact_contact_id_fkey') THEN
--         ALTER TABLE common.client_contact
--             ADD CONSTRAINT client_contact_contact_id_fkey
--             FOREIGN KEY (contact_id) REFERENCES common.contact_item(id);
--     END IF;
-- END;