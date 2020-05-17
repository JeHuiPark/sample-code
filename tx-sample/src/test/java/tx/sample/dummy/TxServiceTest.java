package tx.sample.dummy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TxServiceTest {

  @Autowired
  TxService subject;

  @Test
  void call_tx_method() {
    subject.tx_method();
  }

  @Test
  void call_none_tx_method() {
    try {
      subject.none_tx_method();
      fail();
    } catch (Exception expected) {
    }
  }

  @Test
  void call_proxy_tx_method() {
    subject.proxy_tx_method();
  }
}
