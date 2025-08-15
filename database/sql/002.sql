-- create enums
create type user_role as enum ('admin', 'user');

-- create tables
create table if not exists public.users (
    id serial primary key,
    first_name text not null,
    last_name text not null,
    email text not null unique,
    created_at timestamp with time zone default now(),
    updated_at timestamp with time zone default now()
);

create table if not exists public.collections (
    id serial primary key,
    name text not null,
    description text,
    parent_id integer references public.collections(id),
    created_at timestamp with time zone default now(),
    created_by integer not null references public.users(id),
    updated_at timestamp with time zone default now(),
    updated_by integer not null references public.users(id)
);

create table if not exists public.user_groups (
    id serial primary key,
    name text not null,
    created_at timestamp with time zone default now(),
    created_by integer not null references public.users(id),
    updated_at timestamp with time zone default now(),
    updated_by integer not null references public.users(id)
);

create table if not exists public.items (
    id serial primary key,
    name text not null,
    description text,
    collection_id integer references public.collections(id),
    user_group_id integer not null references public.user_groups(id),
    created_at timestamp with time zone default now(),
    created_by integer not null references public.users(id),
    updated_at timestamp with time zone default now(),
    updated_by integer not null references public.users(id)
);

create table if not exists public.user_group_members (
    id serial primary key,
    user_id integer not null references public.users(id) on delete cascade,
    user_group_id integer not null references public.user_groups(id) on delete cascade,
    role user_role not null,
    created_at timestamp with time zone default now(),
    created_by integer not null references public.users(id),
    updated_at timestamp with time zone default now(),
    updated_by integer not null references public.users(id)
);

-- todo: create an index on expires_at and schedule a cron job to delete expired sessions
create table if not exists public.sessions (
    id uuid primary key default gen_random_uuid(),
    user_id integer not null references public.users(id) on delete cascade,
    expires_at timestamp not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);

create index if not exists idx_sessions_user_id on public.sessions(user_id);


-- Create a trigger function to update the updated_at column
create or replace function public.update_updated_at_column()
returns trigger as $$
begin
    new.updated_at = now();
    return new;
end;
$$ language plpgsql;

-- Create a trigger to call the function before each update
create trigger update_users_updated_at
before update on public.users
for each row
execute procedure public.update_updated_at_column();

create trigger update_user_groups_updated_at
before update on public.user_groups
for each row
execute procedure public.update_updated_at_column();

create trigger update_user_group_members_updated_at
before update on public.user_group_members
for each row
execute procedure public.update_updated_at_column();

create trigger update_item_updated_at
before update on public.items
for each row
execute procedure public.update_updated_at_column();

create trigger update_collections_updated_at
before update on public.collections
for each row
execute procedure public.update_updated_at_column();

create trigger update_sessions_updated_at
    before update on public.sessions
    for each row
execute procedure public.update_updated_at_column();
