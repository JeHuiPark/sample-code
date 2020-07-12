package tx.sample.unexpected_rollback;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

@SpringBootTest
class TxService2Test {

  @Autowired
  TxService2 subject;

  @Autowired
  ExampleRepository exampleRepository;

  @Test
  void test2() {
    try {
      subject.unexpectedRollbackExample();
      fail();
    } catch (UnexpectedRollbackException expected) {
      assertEquals(0, exampleRepository.findAll().size());
    }
  }
}
