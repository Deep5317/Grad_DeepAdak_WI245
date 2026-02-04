CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    role VARCHAR(10) NOT NULL  -- ADMIN / OWNER
);
CREATE TABLE sites (
    site_id SERIAL PRIMARY KEY,
    site_number INT UNIQUE,

    property_type VARCHAR(30), 
    -- Villa / Apartment / Independent / OpenSite

    length INT,
    width INT,
	site_status VARCHAR(30) DEFAULT 'OPEN',
    

    owner_id INT NULL,
    FOREIGN KEY(owner_id) REFERENCES users(user_id)
);
INSERT INTO users(username,password,role)
VALUES('admin','admin123','ADMIN');
INSERT INTO sites(site_number,length,width)
SELECT i,40,60 FROM generate_series(1,10) i;
INSERT INTO sites(site_number,length,width)
SELECT i,30,50 FROM generate_series(11,20) i;
INSERT INTO sites(site_number,length,width)
SELECT i,30,40 FROM generate_series(21,35) i;
CREATE TABLE maintenance (
    maint_id SERIAL PRIMARY KEY,

    site_id INT,

    total_amount DECIMAL NOT NULL,
    paid_amount DECIMAL DEFAULT 0,

    pending_amount DECIMAL GENERATED ALWAYS AS 
        (total_amount - paid_amount) STORED,

    status VARCHAR(20) DEFAULT 'NOT PAID',
    -- NOT PAID / PARTIAL / FULLY PAID

    FOREIGN KEY(site_id) REFERENCES sites(site_id)
);
CREATE TABLE password_requests (
    req_id SERIAL PRIMARY KEY,
    user_id INT,

    new_password VARCHAR(50),

    status VARCHAR(20) DEFAULT 'PENDING',
    -- PENDING / APPROVED / REJECTED

    FOREIGN KEY(user_id) REFERENCES users(user_id)
);
CREATE TABLE site_update_requests (
    req_id SERIAL PRIMARY KEY,

    site_id INT,
    requested_status VARCHAR(30),

    status VARCHAR(20) DEFAULT 'PENDING',

    FOREIGN KEY(site_id) REFERENCES sites(site_id)
);
select * from users