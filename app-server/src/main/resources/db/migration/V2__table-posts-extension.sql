CREATE TABLE IF NOT EXISTS "post_extensions"
(
    "id"                    BIGSERIAL        NOT NULL PRIMARY KEY,
    "post_id"               BIGINT           NULL,
    "additional_content"    VARCHAR(1024)    NOT NULL,

    CONSTRAINT FK_post_extension_posts_post_id FOREIGN KEY ("post_id")
            REFERENCES "posts" ("id")
            ON UPDATE CASCADE
            ON DELETE SET NULL
) WITH (OIDS = FALSE);
