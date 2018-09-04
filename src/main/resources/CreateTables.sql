DROP TABLE IF EXISTS normalizedUrl;
DROP TABLE IF EXISTS orginalUrl;
CREATE TABLE normalizedUrl(
    id INT NOT NULL AUTO_INCREMENT,
    url TEXT NOT NULL,
    hash VARCHAR(256) NOT NULL UNIQUE,
    count INT NOT NULL,
    PRIMARY KEY (ID)
);
CREATE TABLE orginalUrl(
    id INT NOT NULL AUTO_INCREMENT,
    url TEXT NOT NULL,
    hash VARCHAR(256) NOT NULL UNIQUE,
    normalizedUrl VARCHAR(256) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (normalizedUrl) REFERENCES normalizedUrl(hash)
);