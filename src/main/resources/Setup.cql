CREATE KEYSPACE mykeyspace WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
CREATE TABLE userMoves (id TimeUUID PRIMARY KEY, sessionId text, mobileId text, floor int, region text, category text, date timestamp, duration int);
CREATE INDEX userMovesMobileIdindex ON userMoves (mobileId);

CREATE MATERIALIZED VIEW regionsMoves AS
    SELECT sessionId, region FROM userMoves
        WHERE sessionId IS NOT NULL
    PRIMARY KEY ((sessionId))
    WITH CLUSTERING ORDER BY (name DESC);


CREATE TABLE place (name text PRIMARY KEY, floor int, category text, entryPointX float, entryPointY float, importantVisit int);

