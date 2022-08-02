CREATE TABLE IF NOT EXISTS "users"
(
    "id"       BIGSERIAL     NOT NULL PRIMARY KEY,
    "username" VARCHAR(1024) NOT NULL UNIQUE,
    "settings" BIGINT        NULL
) WITH (OIDS = FALSE);

CREATE TABLE IF NOT EXISTS "posts"
(
    "id"      BIGSERIAL     NOT NULL PRIMARY KEY,
    "user_id" BIGINT        NULL,
    "content" VARCHAR(1024) NOT NULL,

    CONSTRAINT FK_posts_users_user_id FOREIGN KEY ("user_id")
            REFERENCES "users" ("id")
            ON UPDATE CASCADE
            ON DELETE SET NULL
) WITH (OIDS = FALSE);
