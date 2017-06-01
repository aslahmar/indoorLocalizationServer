CREATE TABLE userMoves (id TimeUUID PRIMARY KEY, mobileId text, floor text, region text, category text, date timestamp, duration int);
CREATE INDEX userMovesMobileIdindex ON userMoves (mobileId);
