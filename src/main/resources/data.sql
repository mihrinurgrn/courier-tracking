
CREATE TABLE IF NOT EXISTS store (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(255) UNIQUE NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL
    );

INSERT INTO store (name, latitude, longitude)
VALUES ('Ataşehir MMM Migros', 40.9923307, 29.1244229)
    ON CONFLICT (name) DO NOTHING;

INSERT INTO store (name, latitude, longitude)
VALUES ('Novada MMM Migros', 40.986106, 29.1161293)
    ON CONFLICT (name) DO NOTHING;

INSERT INTO store (name, latitude, longitude)
VALUES ('Beylikdüzü 5M Migros', 41.0066851, 28.6552262)
    ON CONFLICT (name) DO NOTHING;

INSERT INTO store (name, latitude, longitude)
VALUES ('Ortaköy MMM Migros', 41.055783, 29.0210292)
    ON CONFLICT (name) DO NOTHING;

INSERT INTO store (name, latitude, longitude)
VALUES ('Caddebostan MMM Migros', 40.9632463, 29.0630908)
    ON CONFLICT (name) DO NOTHING;
