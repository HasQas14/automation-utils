// 

// 

package qa.mongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.JSONArray;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.FindIterable;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import io.restassured.path.json.JsonPath;
import org.bson.Document;
import java.util.LinkedList;

public class MongoResponse
{
    private LinkedList<Document> documentList;
    private JsonPath jsonPath;
    private Logger logger;
    
    public MongoResponse(final Document document) {
        this.documentList = new LinkedList<Document>();
        this.logger = LoggerFactory.getLogger((Class)MongoResponse.class);
        this.documentList.add(document);
    }
    
    public MongoResponse(final FindIterable<Document> documents) {
        this.documentList = new LinkedList<Document>();
        this.logger = LoggerFactory.getLogger((Class)MongoResponse.class);
        for (final Document document : documents) {
            this.documentList.add(document);
        }
    }
    
    public JsonPath jsonResp() {
        return this.jsonPath;
    }
    
    protected MongoResponse build() {
        final JSONArray jsonRespArray = new JSONArray();
        for (final Document document : this.documentList) {
            final String jsonString = document.toJson();
            final JSONObject temp = new JSONObject(jsonString);
            jsonRespArray.put((Object)temp);
        }
        this.jsonPath = new JsonPath(jsonRespArray.toString());
        return this;
    }
    
    @Override
    public String toString() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        try {
            return mapper.writeValueAsString(this.jsonResp().get());
        }
        catch (final JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
