package jackson.example.copy;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.junit.jupiter.api.Test;

class Copy_Test {

  private static ObjectMapper prototype;
  private static ObjectMapper copy;

  static {
    prototype = new ObjectMapper();
    prototype.configure(WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false);

    copy = prototype.copy();
    copy.configure(WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, true);
  }

  @Test
  void test1() throws JsonProcessingException {
    SampleObject sampleObject = new SampleObject();

    String json_by_prototype_mapper = prototype.writeValueAsString(sampleObject);
    String json_by_copied_mapper = copy.writeValueAsString(sampleObject);

    System.out.println(json_by_prototype_mapper);
    System.out.println(json_by_copied_mapper);

    assertNotEquals(json_by_prototype_mapper, json_by_copied_mapper);
  }



  @Data
  private static class SampleObject {
    private String[] str = new String[]{"1"};
  }
}
