-- Create an application user
create user app_user with
    password 'app_password'
    login
    nocreatedb
    nosuperuser
    nocreaterole
    noinherit
    noreplication
    nobypassrls;
-- Grant the user access to the database
grant connect on database find_it_db to app_user;
-- Grant the user access to the public schema
grant usage on schema public to app_user;
-- Grant the user access to all tables in the public schema
grant select, insert, update, delete on all tables in schema public to app_user;
-- Grant the user access to all sequences in the public schema
grant usage, select on all sequences in schema public to app_user;
-- Grant the user access to all functions in the public schema
grant execute on all functions in schema public to app_user;
-- Grant the user permissions on future tables, sequences, and functions in the public schema
alter default privileges in schema public
    grant select, insert, update, delete on tables to app_user;
alter default privileges in schema public
    grant usage, select on sequences to app_user;
alter default privileges in schema public
    grant execute on functions to app_user;
