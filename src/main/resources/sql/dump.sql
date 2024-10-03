DROP TABLE IF EXISTS "channel";
DROP SEQUENCE IF EXISTS channel_id_seq;
CREATE SEQUENCE channel_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "public"."channel"
(
    "author_id"     bigint,
    "creation_date" timestamp(6),
    "id"            bigint DEFAULT nextval('channel_id_seq') NOT NULL,
    "avatar"        character varying(255),
    "category"      character varying(255),
    "description"   character varying(255),
    "main_language" character varying(255)                   NOT NULL,
    "name"          character varying(255)                   NOT NULL,
    CONSTRAINT "channel_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

TRUNCATE "channel";
INSERT INTO "channel" ("author_id", "creation_date", "id", "avatar", "category", "description", "main_language", "name")
VALUES (3, '2024-09-29 14:50:37.711583', 14, 'b3505649-24f9-4592-91a0-84cef8a1d46d', 'MUSIC', 'channel_ONE', 'ENGLISH',
        'channel_ONE');

DROP TABLE IF EXISTS "user_channel_subscriptions";
CREATE TABLE "public"."user_channel_subscriptions"
(
    "channel_id" bigint NOT NULL,
    "user_id"    bigint NOT NULL
) WITH (oids = false);

TRUNCATE "user_channel_subscriptions";
INSERT INTO "user_channel_subscriptions" ("channel_id", "user_id")
VALUES (14, 3);

DROP TABLE IF EXISTS "users";
DROP SEQUENCE IF EXISTS users_id_seq;
CREATE SEQUENCE users_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "public"."users"
(
    "id"       bigint DEFAULT nextval('users_id_seq') NOT NULL,
    "email"    character varying(255)                 NOT NULL,
    "name"     character varying(255)                 NOT NULL,
    "nickname" character varying(255)                 NOT NULL,
    CONSTRAINT "users_email_key" UNIQUE ("email"),
    CONSTRAINT "users_nickname_key" UNIQUE ("nickname"),
    CONSTRAINT "users_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

TRUNCATE "users";
INSERT INTO "users" ("id", "email", "name", "nickname")
VALUES (3, 'user2@mail.com', 'user2@mail.com', 'user2@mail.com');

ALTER TABLE ONLY "public"."channel" ADD CONSTRAINT "fk_author_id_to_id" FOREIGN KEY (author_id) REFERENCES users(id) NOT DEFERRABLE;

ALTER TABLE ONLY "public"."user_channel_subscriptions" ADD CONSTRAINT "fk_channel_user_id_to_id" FOREIGN KEY (channel_id) REFERENCES channel(id) NOT DEFERRABLE;
ALTER TABLE ONLY "public"."user_channel_subscriptions" ADD CONSTRAINT "fk_user_channel_id_to_id" FOREIGN KEY (user_id) REFERENCES users(id) NOT DEFERRABLE;