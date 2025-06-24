-- create tables
create table if not exists public.items (
    id serial primary key,
    name text not null,
    description text,
    created_at timestamp with time zone default now(),
    updated_at timestamp with time zone default now()
);

create table if not exists public.collections (
    id serial primary key,
    name text not null,
    description text,
    created_at timestamp with time zone default now(),
    updated_at timestamp with time zone default now()
);

create table if not exists public.items_collections (
    item_id integer not null references public.items(id) on delete cascade,
    collection_id integer not null references public.collections(id) on delete cascade,
    created_at timestamp with time zone default now(),
    updated_at timestamp with time zone default now(),
    primary key (item_id, collection_id)
);

create table if not exists public.collections_collections (
    parent_collection_id integer not null references public.collections(id) on delete cascade,
    child_collection_id integer not null references public.collections(id) on delete cascade,
    created_at timestamp with time zone default now(),
    updated_at timestamp with time zone default now(),
    primary key (parent_collection_id, child_collection_id)
);

-- Create a trigger function to update the updated_at column
create or replace function public.update_updated_at_column()
returns trigger as $$
begin
    new.updated_at = now();
    return new;
end;
$$ language plpgsql;

-- Create a trigger to call the function before each update
create trigger update_item_updated_at
before update on public.items
for each row
execute procedure public.update_updated_at_column();

create trigger update_collections_updated_at
before update on public.collections
for each row
execute procedure public.update_updated_at_column();

create trigger update_items_collections_updated_at
before update on public.items_collections
for each row
execute procedure public.update_updated_at_column();

create trigger update_collections_collections_updated_at
before update on public.collections_collections
for each row
execute procedure public.update_updated_at_column();
