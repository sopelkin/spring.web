INSERT INTO User (id, username, password) VALUES
  (2, 'user', 'user'),
  (1, 'admin', 'admin');

INSERT INTO Role (id, name) VALUES
  (2, 'ROLE_USER'),
  (1, 'ROLE_ADMIN');

INSERT INTO user_role (user_id, role_id) VALUES
  (1, 1),
  (2, 2);