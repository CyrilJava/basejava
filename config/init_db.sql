--drop table public.resume;
create table public.resume
(
    uuid      char(36) not null
        constraint uuid
            primary key,
    full_name text     not null
);

alter table public.resume
    owner to postgres;

--drop table public.contact;
create table public.contact
(
    id          serial,
    resume_uuid char(36)
        constraint contact_resume_uuid_fk
            references public.resume
            on delete cascade,
    type        text not null,
    value       text not null
);

alter table public.contact
    owner to postgres;

create unique index contact_uuid_type_index
    on contact (resume_uuid, type);
