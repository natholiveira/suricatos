CREATE TABLE IF NOT EXISTS user_u(
id BIGINT NOT NULL,
name VARCHAR(255) NOT NULL,
birthday TIMESTAMP NOT NULL,
type VARCHAR(150) NOT NULL,
biography VARCHAR(355),
created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
updated_at TIMESTAMP WITH TIME ZONE default NOW() NOT null,
CONSTRAINT user_pk PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS address(
id BIGINT NOT NULL,
zip_code VARCHAR(9) NOT NULL,
street VARCHAR(255) NOT NULL,
state VARCHAR(50) NOT NULL,
complement VARCHAR(255) NOT NULL,
number VARCHAR(10) NOT NULL,
city VARCHAR(255) NOT NULL,
neighborhood VARCHAR(255) NOT null,
created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
updated_at TIMESTAMP WITH TIME ZONE default NOW() NOT null,
CONSTRAINT address_pk PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS post(
id BIGINT NOT NULL,
slug VARCHAR(20) NOT NULL,
type VARCHAR(9) NOT NULL,
title VARCHAR(255) NOT NULL,
description VARCHAR(50) NOT NULL,
status VARCHAR(150) NOT NULL,
user_id BIGINT NOT null,
address_id BIGINT NOT NULL,
created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
updated_at TIMESTAMP WITH TIME ZONE default NOW() NOT null,
CONSTRAINT post_pk PRIMARY KEY (id),
constraint user_post_fk FOREIGN key (user_id) references user_u(id),
constraint address_fk FOREIGN key (address_id) references address(id)
);
CREATE TABLE IF NOT EXISTS post_reply(
id BIGINT NOT NULL,
external_link VARCHAR(255),
external_protocol VARCHAR(255),
description VARCHAR(255),
user_id BIGINT NOT null,
post_id BIGINT NOT NULL,
created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
updated_at TIMESTAMP WITH TIME ZONE default NOW() NOT null,
CONSTRAINT post_reply_pk PRIMARY KEY (id),
constraint user_fk FOREIGN key (user_id) references user_u (id),
constraint post_post_reply_fk FOREIGN key (post_id) references post(id)
);
CREATE TABLE IF NOT EXISTS comment(
id BIGINT NOT NULL,
comment VARCHAR(300) NOT NULL,
user_id BIGINT NOT NULL,
post_id BIGINT NOT NULL,
created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
updated_at TIMESTAMP WITH TIME ZONE default NOW() NOT null,
CONSTRAINT comment_pk PRIMARY KEY (id),
constraint user_comment_fk FOREIGN key (user_id) references user_u(id),
constraint post_fk FOREIGN key (post_id) references post(id)
);
CREATE TABLE IF NOT EXISTS rating(
id BIGINT NOT NULL,
star INTEGER NOT NULL,
react VARCHAR(255) NOT NULL,
comment VARCHAR(300) NOT NULL,
created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
updated_at TIMESTAMP WITH TIME ZONE default NOW() NOT null,
CONSTRAINT rating_pk PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS tag(
id BIGINT NOT NULL,
title VARCHAR(500) NOT NULL,
color VARCHAR(10) NOT null,
created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
updated_at TIMESTAMP WITH TIME ZONE default NOW() NOT null,
CONSTRAINT tag_pk PRIMARY KEY(id)
);
CREATE TABLE IF NOT EXISTS user_phone(
id BIGINT NOT NULL,
user_id BIGINT NOT NULL,
ddd VARCHAR(5) NOT NULL,
type VARCHAR(255) NOT NULL,
number VARCHAR(10) NOT NULL,
created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
updated_at TIMESTAMP WITH TIME ZONE default NOW() NOT null,
CONSTRAINT user_phone_pk PRIMARY KEY(id),
constraint user_user_phone_fk FOREIGN key (user_id) references user_u(id)
);
CREATE TABLE IF NOT EXISTS user_address(
id BIGINT NOT NULL,
user_id BIGINT NOT NULL,
address_id BIGINT NOT NULL,
created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
updated_at TIMESTAMP WITH TIME ZONE default NOW() NOT null,
CONSTRAINT user_address_pk PRIMARY KEY(id),
constraint user_user_address_fk FOREIGN key (user_id) references user_u(id),
constraint user_address_address_fk FOREIGN key (address_id) references address(id)
);
CREATE TABLE IF NOT EXISTS user_photo(
id BIGINT NOT NULL,
user_id BIGINT NOT NULL,
image VARCHAR(500) NOT NULL,
created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
updated_at TIMESTAMP WITH TIME ZONE default NOW() NOT null,
CONSTRAINT user_photo_pk PRIMARY KEY(id),
constraint user_user_photo_fk FOREIGN key (user_id) references user_u(id)
);
CREATE TABLE IF NOT EXISTS post_tag(
post_id BIGINT NOT NULL,
tag_id BIGINT NOT NULL,
created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
constraint post_fk FOREIGN key (post_id) references post (id),
constraint tag_fk FOREIGN key (tag_id) references tag (id)
);
CREATE TABLE IF NOT EXISTS post_photo(
id BIGINT NOT NULL,
post_id BIGINT NOT NULL,
image VARCHAR(500) NOT NULL,
CONSTRAINT post_photo_pk PRIMARY KEY(id),
constraint post_post_photofk FOREIGN key (post_id) references post(id)
);
CREATE TABLE IF NOT EXISTS post_pomment(
post_id BIGINT NOT NULL,
comment_id BIGINT NOT NULL,
created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
constraint post_fk FOREIGN key (post_id) references post (id),
constraint comment_fk FOREIGN key (comment_id) references comment (id)
);
CREATE TABLE IF NOT EXISTS post_rating(
post_id BIGINT NOT NULL,
rating_id BIGINT NOT NULL,
created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
constraint post_fk FOREIGN key (post_id) references post (id),
constraint rating_fk FOREIGN key (rating_id) references rating (id)
);
CREATE TABLE IF NOT EXISTS comment_rating(
comment_id BIGINT NOT NULL,
rating_id BIGINT NOT NULL,
created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
constraint comment_fk FOREIGN key (comment_id) references comment (id),
constraint rating_fk FOREIGN key (rating_id) references rating (id)
);
CREATE TABLE IF NOT EXISTS comment_metion(
comment_id BIGINT NOT NULL,
user_id BIGINT NOT NULL,
created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
constraint comment_fk FOREIGN key (comment_id) references comment (id),
constraint user_fk FOREIGN key (user_id) references user_u (id)
);