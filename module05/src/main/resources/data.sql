CREATE TABLE IF NOT EXISTS persistent_logins (
  username  VARCHAR_IGNORECASE(100) NOT NULL,
  series    VARCHAR(64) PRIMARY KEY,
  token     VARCHAR(64)             NOT NULL,
  last_used TIMESTAMP               NOT NULL
);

INSERT INTO role (id, name) VALUES
  (2, 'ROLE_USER'),
  (1, 'ROLE_ADMIN');

INSERT INTO privilege (id, name) VALUES
  (2, 'PRIVELEGE_USER'),
  (1, 'PRIVELEGE_ADMIN');

INSERT INTO role_privilege (role_id, privilege_id) VALUES
  (1, 1),
  (2, 2);