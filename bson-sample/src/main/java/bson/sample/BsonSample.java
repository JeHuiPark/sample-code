package bson.sample;

import org.bson.BasicBSONDecoder;
import org.bson.Document;

class BsonSample {
  public static void main(String[] args) {
    /**
     * expected.
     *
     * {"key":"value"}
     */
    final var expected = "{\"key\": \"value\"}";
    byte [] INPUT_BSON = new byte[]{
        0x14, 0x00, 0x00, 0x00, // 도큐먼트 사이즈 20
        0x02, // 문자열 필드
        0x6b, 0x65, 0x79, // 필드명: key
        0x00, // 필드명의 끝 (null)
        0x06, 0x00, 0x00, 0x00, // 필드 길이: 6 (null 포함)
        0x76, 0x61, 0x6c, 0x75, 0x65, // 필드: value
        0x00, // 필드의 끝 (null)
        0x00 // 도큐먼트의 끝 (null)
    };

    var bsonObject = new BasicBSONDecoder().readObject(INPUT_BSON);
    var parseDocument = new Document(bsonObject.toMap());

    if (!expected.equals(parseDocument.toJson().trim())) {
      throw new RuntimeException(parseDocument.toJson().trim());
    }
  }
}
