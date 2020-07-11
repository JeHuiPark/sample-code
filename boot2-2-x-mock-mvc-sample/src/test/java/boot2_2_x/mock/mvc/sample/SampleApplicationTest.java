package boot2_2_x.mock.mvc.sample;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SampleConfig
@SpringBootTest
class SampleApplicationTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  void test2() throws Exception {
    var json = objectMapper.writeValueAsString(Map.of("k", "한글"));

    mockMvc.perform(
        get("/sample")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
    ).andDo(print());

  }
}
