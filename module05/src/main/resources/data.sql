CREATE TABLE IF NOT EXISTS persistent_logins (
  username  VARCHAR(100) NOT NULL,
  series    VARCHAR(64) PRIMARY KEY,
  token     VARCHAR(64)             NOT NULL,
  last_used TIMESTAMP               NOT NULL
);

INSERT INTO role (id, name) VALUES
  (2, 'USER'),
  (1, 'ADMIN');

  