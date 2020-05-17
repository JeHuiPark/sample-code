package tx.sample.dummy;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class TxService {

  @PersistenceContext
  EntityManager em;

  @Autowired
  TxService proxyInstance;

  @Transactional
  public void tx_method() {
    em.persist(new Dummy("name1"));
    em.persist(new Dummy("name2"));
    em.flush();
  }

  public void none_tx_method() {
    tx_method(); // expected throw exception
  }

  public void proxy_tx_method() {
    proxyInstance.tx_method();
  }
}
