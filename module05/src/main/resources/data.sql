INSERT INTO my_user (id, username, password, email) VALUES
  (2, 'user', 'user', 'user@email.me'),
  (1, 'admin', 'admin', 'admin@localhost');

INSERT INTO role (id, name) VALUES
  (2, 'ROLE_USER'),
  (1, 'ROLE_ADMIN');

INSERT INTO user_role (user_id, role_id) VALUES
  (1, 1),
  (2, 2);