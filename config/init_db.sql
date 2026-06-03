drop table if exists public.resume;
create table public.resume
(
    uuid      char(36) not null
              constraint uuid
              primary key,
    full_name text     not null
);

alter table public.resume
    owner to postgres;

drop table if exists public.contact;
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

drop table if exists public.section;
create table public.section
(
    id           serial,
    resume_uuid  char(36)
        constraint section_resume_uuid_fk
            references public.resume
            on delete cascade,
    section_type text not null,
    value        text not null
);

alter table public.section
    owner to postgres;

create unique index section_uuid_type_index
    on section (resume_uuid, section_type);
