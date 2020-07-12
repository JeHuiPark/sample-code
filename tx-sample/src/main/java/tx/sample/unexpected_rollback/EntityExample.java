package tx.sample.unexpected_rollback;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
class EntityExample {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
}
