package tx.sample.unexpected_rollback;

import static org.springframework.transaction.TransactionDefinition.PROPAGATION_MANDATORY;

import org.springframework.stereotype.Service;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
class TxService1 {

  private final ExampleRepository exampleRepository;
  private final PlatformTransactionManager transactionManager;

  TxService1(ExampleRepository exampleRepository, PlatformTransactionManager transactionManager) {
    this.exampleRepository = exampleRepository;
    this.transactionManager = transactionManager;
  }

  @Transactional
  public void saveAndThrow() {
    exampleRepository.saveAndFlush(new EntityExample());
    throw new RuntimeException();
  }

  @Transactional
  void requireTransaction() throws IllegalTransactionStateException {
    var def = new DefaultTransactionDefinition(PROPAGATION_MANDATORY);
    transactionManager.getTransaction(def);
  }
}
