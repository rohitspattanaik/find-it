insert into public.users (first_name, last_name, email, is_admin)
values
('System', 'Admin', 'sysadmin', false),
('John', 'Doe', 'a@b.c', false),
('Rohit', 'Pattanaik', 'rohit.pattanaik@gmail.com', true),
('Jane', 'Smith', 'jane.smith@email.com', false),
('Alice', 'Johnson', 'alice.j@email.com', false),
('Bob', 'Brown', 'bob.b@gmail.com', false),
('Charlie', 'Davis', 'charlie.d@email.com', false),
('Eve', 'Miller', 'eve.m@gmail.com', false),
('Frank', 'Wilson', 'frank.w@email.com', false),
('Grace', 'Lee', 'grace.l@gmail.com', false),
('Hank', 'Taylor', 'hank.t@email.com', false),
('Ivy', 'Anderson', 'ivy.a@email.com', false),
('Jack', 'Thomas', 'jack.t@email.com', false),
('Kathy', 'Moore', 'kathy.m@gmail.com', false),
('Leo', 'Martin', 'leo.m@email.com', false);

insert into public.user_groups (name, created_by, updated_by)
values
('test group', (select id from public.users where email = 'a@b.c'), (select id from public.users where email = 'a@b.c'));

insert into public.user_group_members (user_id, user_group_id, role, created_by, updated_by)
values
((select id from public.users where email = 'a@b.c'), (select id from public.user_groups where name = 'test group'), 'admin', (select id from public.users where email = 'a@b.c'), (select id from public.users where email = 'a@b.c'));

insert into public.user_group_members (user_id, user_group_id, role, created_by, updated_by)
values
((select id from public.users where email = 'rohit.pattanaik@gmail.com'), (select id from public.user_groups where name = 'test group'), 'admin', (select id from public.users where email = 'rohit.pattanaik@gmail.com'), (select id from public.users where email = 'rohit.pattanaik@gmail.com'));

insert into public.items (name, description, user_group_id, created_by, updated_by)
values
('Sample Item', 'This is a sample item for testing purposes.', (select id from public.user_groups where name = 'test group'), (select id from public.users where email = 'a@b.c'), (select id from public.users where email = 'a@b.c'));
