package tx.sample.unexpected_rollback;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class TxService2 {

  private final TxService1 txService1;
  private final ExampleRepository exampleRepository;

  TxService2(TxService1 txService1, ExampleRepository exampleRepository) {
    this.txService1 = txService1;
    this.exampleRepository = exampleRepository;
  }

  @Transactional
  public void unexpectedRollbackExample() {
    try {
      txService1.saveAndThrow();
    } catch (Exception e) {
      System.out.println("이 트랜잭션은 예상하지 못한 롤백을 유발합니다.");
      this.exampleRepository.saveAndFlush(new EntityExample());
    }
  }

}
