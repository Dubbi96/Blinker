-- CREATE TABLE statements
CREATE TABLE public.app_user (
    user_id BIGINT NOT NULL,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    password_hash VARCHAR(255) NOT NULL,
    salt VARCHAR(50) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER' NOT NULL,
    is_active BOOLEAN DEFAULT TRUE NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT app_user_pkey PRIMARY KEY (user_id)
);

CREATE TABLE public.sensor_group (
    group_id BIGINT NOT NULL,
    group_key VARCHAR(50) NOT NULL,
    group_name VARCHAR(100),
    description VARCHAR(255),
    sensor_count INT,
    fault_count INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT sensor_group_pkey PRIMARY KEY (group_id)
);

CREATE TABLE public.dummy_sensor (
    dummy_sensor_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    address VARCHAR(255),
    created_at TIMESTAMP(6),
    label VARCHAR(100),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    sensor_key VARCHAR(50) NOT NULL,
    updated_at TIMESTAMP(6),
    group_id BIGINT NOT NULL,
    CONSTRAINT dummy_sensor_pkey PRIMARY KEY (dummy_sensor_id)
);

CREATE TABLE public.sensor (
    sensor_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    address VARCHAR(255),
    created_at TIMESTAMP(6),
    is_fault BOOLEAN,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    sensor_key VARCHAR(50) NOT NULL,
    status VARCHAR(20),
    updated_at TIMESTAMP(6),
    group_id BIGINT NOT NULL,
    CONSTRAINT sensor_pkey PRIMARY KEY (sensor_id)
);

CREATE TABLE public.sensor_log (
    log_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_at TIMESTAMP(6),
    event_details JSONB,
    event_type VARCHAR(50),
    sensor_id BIGINT,
    CONSTRAINT sensor_log_pkey PRIMARY KEY (log_id)
);

CREATE TABLE public.sensor_setting (
    setting_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    bird_volume INT,
    created_at TIMESTAMP(6),
    location_guide_signal_strength REAL,
    location_guide_signal_strength_standard REAL,
    signal_guide_signal_strength REAL,
    signal_guide_signal_strength_standard REAL,
    updated_at TIMESTAMP(6),
    sensor_id BIGINT,
    CONSTRAINT sensor_setting_pkey PRIMARY KEY (setting_id),
    CONSTRAINT ukhcpn2kf985ohu5mjug0tvijbi UNIQUE (sensor_id)
);

CREATE TABLE public.user_group (
    user_group_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_at TIMESTAMP(6),
    role VARCHAR(20),
    group_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT user_group_pkey PRIMARY KEY (user_group_id)
);

-- ALTER TABLE statements to add constraints
ALTER TABLE public.dummy_sensor
    ADD CONSTRAINT FK_group_TO_dummy_sensor_1 FOREIGN KEY (group_id)
    REFERENCES public.sensor_group (group_id);

ALTER TABLE public.sensor
    ADD CONSTRAINT FK_group_TO_sensor_1 FOREIGN KEY (group_id)
    REFERENCES public.sensor_group (group_id);

ALTER TABLE public.sensor_log
    ADD CONSTRAINT FK_sensor_TO_sensor_log_1 FOREIGN KEY (sensor_id)
    REFERENCES public.sensor (sensor_id);

ALTER TABLE public.sensor_setting
    ADD CONSTRAINT FK_sensor_TO_sensor_setting_1 FOREIGN KEY (sensor_id)
    REFERENCES public.sensor (sensor_id);

ALTER TABLE public.user_group
    ADD CONSTRAINT FK_group_TO_user_group_1 FOREIGN KEY (group_id)
    REFERENCES public.sensor_group (group_id);

ALTER TABLE public.user_group
    ADD CONSTRAINT FK_user_TO_user_group_1 FOREIGN KEY (user_id)
    REFERENCES public.app_user (user_id);