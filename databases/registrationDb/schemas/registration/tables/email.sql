create table if not exists registration.email(
    email_id bigserial not null,
    constraint pc_registration_email_email_id primary key (email_id)
);

alter table registration.email

add column if not exists email_address text collate pg_catalog."default",
add column if not exists created_on timestamp with time zone default now(),
add column if not exists created_by text,
add column if not exists modified_on timestamp with time zone,
add column if not exists modified_by text,
add column if not exists deleted_on timestamp with time zone,
add column if not exists deleted_by text,
add column if not exists deleted boolean default false;