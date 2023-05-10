CREATE TABLE IF NOT EXISTS "data_extensions"
(
    "id"                BIGSERIAL        NOT NULL PRIMARY KEY,
    "entity_id"         BIGINT           NOT NULL,
    "entity_type"       VARCHAR(1024)    NOT NULL,
    "property_name"     VARCHAR(1024)    NOT NULL,
    "property_value"    VARCHAR(1024)    NULL,
) WITH (OIDS = FALSE);
