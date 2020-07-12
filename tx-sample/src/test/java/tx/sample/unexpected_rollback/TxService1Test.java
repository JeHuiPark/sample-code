package tx.sample.unexpected_rollback;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.IllegalTransactionStateException;

@SpringBootTest
class TxService1Test {

  @Autowired
  TxService1 subject;

  @Test
  void test2() {
    try {
      subject.requireTransaction();
      fail();
    } catch (IllegalTransactionStateException expected) {
      System.out.println("비공개 메소드는 트랜잭션을 획득할 수 없습니다.");
    }
  }
}
