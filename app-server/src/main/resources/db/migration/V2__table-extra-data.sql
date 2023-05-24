CREATE TABLE IF NOT EXISTS "extra_data"
(
    "id"                VARCHAR(36)      NOT NULL PRIMARY KEY,
    "entity_id"         BIGINT           NOT NULL,
    "entity_type"       VARCHAR(1024)    NOT NULL,
    "property_name"     VARCHAR(1024)    NOT NULL,
    "property_value"    VARCHAR(1024)    NULL
) WITH (OIDS = FALSE);
