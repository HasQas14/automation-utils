package qa.delete.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaResponseBuilder
{
    private static ObjectMapper mapper;
    
    public static KafkaResponse map(final Object input) {
        if (null == input) {
            return KafkaResponse.builder().build();
        }
        return KafkaResponseBuilder.mapper.convertValue(input, KafkaResponse.class);
    }

    public static List<KafkaResponse> map(final List<Object> objectList) {
        return objectList.stream()
                .map(KafkaResponseBuilder::map)
                .collect(Collectors.toList());
    }

    static {
        KafkaResponseBuilder.mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS);
    }
}
