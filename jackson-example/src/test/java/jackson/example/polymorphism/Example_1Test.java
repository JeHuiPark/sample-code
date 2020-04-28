package jackson.example.polymorphism;

import static jackson.example.polymorphism.Example_1.Customer;
import static jackson.example.polymorphism.Example_1.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jackson.example.polymorphism.Example_1.Admin;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("InnerClassMayBeStatic")
@DisplayName("Example_1")
class Example_1Test {

  private static final ObjectMapper om = new ObjectMapper();

  @Nested
  @DisplayName("json 다형성 테스트")
  class Describe_serialize {

    @Nested
    @DisplayName("general")
    class Context_general {

      Admin given_admin = new Admin("admin");
      Customer given_customer = new Customer("customer");

      @Test
      @DisplayName("직렬화 -> 역직렬화")
      void test1() throws IOException {
        String adminJson = om.writeValueAsString(given_admin);
        String customerJson = om.writeValueAsString(given_customer);

        System.out.println("adminJson = " + adminJson);
        System.out.println("customerJson = " + customerJson);

        User actual_admin = om.readValue(adminJson, User.class);
        User actual_customer = om.readValue(customerJson, User.class);

        assertEquals(Admin.class, actual_admin.getClass());
        assertEquals(Customer.class, actual_customer.getClass());
        assertEquals(given_admin, actual_admin);
        assertEquals(given_customer, actual_customer);
      }
    }

  }
}
