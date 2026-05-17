CREATE TABLE IF NOT EXISTS reservations (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  copy_id BIGINT NOT NULL,
  status VARCHAR(20) NOT NULL,
  active BIT NOT NULL,
  reservation_date DATETIME NOT NULL,
  expiration_date DATETIME NOT NULL
);
