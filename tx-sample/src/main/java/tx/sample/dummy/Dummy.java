package tx.sample.dummy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
class Dummy {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  String property;

  Dummy(String property) {
    this.property = property;
  }
}
