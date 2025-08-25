insert into public.users (first_name, last_name, email)
values
('System', 'Admin', 'sysadmin'),
('John', 'Doe', 'a@b.c'),
('Rohit', 'Pattanaik', 'rohit.pattanaik@gmail.com');

insert into public.user_groups (name, created_by, updated_by)
values
('test group', (select id from public.users where email = 'a@b.c'), (select id from public.users where email = 'a@b.c'));

insert into public.user_group_members (user_id, user_group_id, role, created_by, updated_by)
values
((select id from public.users where email = 'a@b.c'), (select id from public.user_groups where name = 'test group'), 'admin', (select id from public.users where email = 'a@b.c'), (select id from public.users where email = 'a@b.c'));

insert into public.items (name, description, user_group_id, created_by, updated_by)
values
('Sample Item', 'This is a sample item for testing purposes.', (select id from public.user_groups where name = 'test group'), (select id from public.users where email = 'a@b.c'), (select id from public.users where email = 'a@b.c'));

insert into public.admin_users (user_id, created_by, updated_by)
values
((select id from public.users where email = 'rohit.pattanaik@gmail.com'), (select id from public.users where email = 'sysadmin'), (select id from public.users where email = 'sysadmin'));