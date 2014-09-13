drop table if exists entries;
create table entries (
  id integer primary key autoincrement,
  ident text not null,
  name text not null,
  profileurl text not null,
  headline text not null,
  picurl text not null
);
