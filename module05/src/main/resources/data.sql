CREATE TABLE IF NOT EXISTS persistent_logins (
  username  VARCHAR(100) NOT NULL,
  series    VARCHAR(64) PRIMARY KEY,
  token     VARCHAR(64)             NOT NULL,
  last_used TIMESTAMP               NOT NULL
);

INSERT INTO my_user (id, username, password, email) VALUES
  (2, 'user', 'user', 'user@email.me'),
  (1, 'admin', 'admin', 'admin@localhost');

INSERT INTO role (id, name) VALUES
  (2, 'USER'),
  (1, 'ADMIN');

INSERT INTO user_role (user_id, role_id) VALUES
  (1, 1),
  (2, 2);
  
INSERT INTO privilege (id, name) VALUES
  (2, 'AUTH2'),
  (1, 'AUTH1');
  
INSERT INTO role_privilege (role_id, privilege_id) VALUES
  (1, 1),
  (2, 2);
  