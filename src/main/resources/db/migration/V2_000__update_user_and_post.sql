alter table user_u ADD COLUMN email VARCHAR(100);
alter table user_u ADD COLUMN password VARCHAR(100);

alter table post ADD COLUMN like_post integer;