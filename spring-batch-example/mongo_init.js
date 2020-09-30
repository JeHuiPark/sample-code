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
db.schedule.createIndex({'method': 1});
var documents = [];
for (var i = 1; i <= 10000; i++) {
  documents.push({_id:i, method: ['function1', 'function2']});
}
db.schedule.insertMany(documents, {
  ordered: false
});
