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
@DisplayName("json 다형성 예제 1")
class Example_1Test {

  private static final ObjectMapper om = new ObjectMapper();

  @Nested
  @DisplayName("직렬화/역직렬화")
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

    @Nested
    @DisplayName("collection")
    class Context_in_collection {

      List<User> given_list = Arrays.asList(
          new Admin("admin"),
          new Customer("customer"));

      Map<Integer, User> given_map = Map.of(
          1, new Admin("admin"),
          2, new Customer("customer"));

      @Test
      @DisplayName("bad case")
      void test2() throws IOException  {
        String listJson = om.writeValueAsString(given_list);
        String mapJson = om.writeValueAsString(given_map);

        System.out.println("listJson = " + listJson);
        System.out.println("mapJson = " + mapJson);

        var typeReference = new TypeReference<User>() {};

        assertThrows(
            MismatchedInputException.class,
            () -> om.readValue(listJson, typeReference)
        );

        assertThrows(
            MismatchedInputException.class,
            () -> om.readValue(mapJson, typeReference)
        );
      }

      @Test
      @DisplayName("good case")
      void test3() throws IOException  {
        var listReference = new TypeReference<List<User>>() {};
        var mapReference = new TypeReference<Map<Integer, User>>() {};

        String listJson = om.writerFor(listReference).writeValueAsString(given_list);
        String mapJson = om.writerFor(mapReference).writeValueAsString(given_map);

        System.out.println("listJson = " + listJson);
        System.out.println("mapJson = " + mapJson);

        assertEquals(given_list, om.readValue(listJson, listReference));
        assertEquals(given_map, om.readValue(mapJson, mapReference));
      }
    }
  }
}
