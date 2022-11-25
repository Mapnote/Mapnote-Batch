create table schedules (
    id bigint not null,
    created_at timestamp,
    updated_at timestamp,
    alarm_status varchar(255),
    category varchar(255),
    content varchar(255),
    is_deleted boolean,
    address varchar(255),
    road_name_address varchar(255),
    latitude double,
    longitude double,
    name varchar(255),
    schedule_status varchar(255),
    user_id BINARY(16),
    primary key (id)
);