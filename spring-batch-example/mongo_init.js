db = db.getSiblingDB('test');
db.createUser(
  {
    user: 'test',
    pwd: 'test',
    roles: [
      {
        role: 'readWrite',
        db: 'test'
      }
    ]
  }
);

db.createCollection('schedule');

var documents = [];
for (var i = 1; i <= 10000; i++) {
  documents.push({
    target: NumberLong(i),
    status: 'reservation',
    method: 'function1',
    begin: ISODate("2020-01-01T00:00:00.000Z"),
    end: ISODate("2021-01-01T00:00:00.000Z"),
    audit: 'user'
  });
  documents.push({
    target: NumberLong(i),
    status: 'reservation',
    method: 'function2',
    begin: ISODate("2020-01-01T00:00:00.000Z"),
    end: ISODate("2021-01-01T00:00:00.000Z"),
    audit: 'user'
  });
}

db.schedule.insertMany(documents, {
  ordered: false
});

db.schedule.createIndex({
  'target': 1
});

db.schedule.createIndex({
  'begin': 1,
  'end': 1,
  'method': 1,
  'status': 1
});
