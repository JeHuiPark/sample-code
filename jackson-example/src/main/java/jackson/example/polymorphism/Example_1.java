package jackson.example.polymorphism;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;

class Example_1 {

  @EqualsAndHashCode(of = "name")
  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
  @JsonSubTypes({
      @JsonSubTypes.Type(Admin.class),
      @JsonSubTypes.Type(Customer.class),
  })
  static abstract class User {

    @Getter
    String name;

    User(String name) {
      this.name = name;
    }
  }

  @EqualsAndHashCode(callSuper = true)
  static class Admin extends User {
    Admin(@JsonProperty("name") String name) {
      super(name);
    }
  }

  @EqualsAndHashCode(callSuper = true)
  static class Customer extends User {
    Customer(@JsonProperty("name") String name) {
      super(name);
    }
  }
}
