// 

// 

package qa.mongo;

import org.slf4j.LoggerFactory;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Callable;
import com.mongodb.client.result.DeleteResult;
import java.util.Arrays;
import java.util.List;
import qa.generic.PollingPredicate;
import java.time.Duration;
import java.util.function.Predicate;
import org.bson.conversions.Bson;
import com.mongodb.client.FindIterable;
import org.slf4j.Logger;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Map;

public class QueryMongo
{
    static final Map<String, MongoDatabase> db_map;
    static final Map<String, MongoCollection<Document>> table_map;
    private String db;
    private String table;
    private static Logger logger;
    
    private QueryMongo() {
    }
    
    public static QueryMongo getInstance(final String db, final String table) {
        final QueryMongo queryMongo = new QueryMongo();
        queryMongo.db = db;
        queryMongo.table = table;
        return queryMongo;
    }
    
    public MongoResponse findAll() {
        this.createColl();
        QueryMongo.logger.info(String.format("Initiating query to mongo: \n db: %s \n table: %s", this.db, this.table));
        final MongoResponse response = new MongoResponse((FindIterable<Document>)QueryMongo.table_map.get(this.table).find()).build();
        QueryMongo.logger.info(String.format("Response for query to mongo: \n db: %s \n table: %s \n response: %s", this.db, this.table, response.toString()));
        return new MongoResponse((FindIterable<Document>)QueryMongo.table_map.get(this.table).find()).build();
    }
    
    public MongoResponse find(final Bson queryParam) {
        this.createColl();
        QueryMongo.logger.info(String.format("Initiating query to mongo: \n db: %s \n table: %s \n queryString: %s", this.db, this.table, queryParam));
        final MongoResponse response = new MongoResponse((FindIterable<Document>)QueryMongo.table_map.get(this.table).find(queryParam)).build();
        QueryMongo.logger.info(String.format("Response for query to mongo: \n db: %s \n table: %s \n queryString: %s \n response: %s", this.db, this.table, queryParam, response.toString()));
        return response;
    }
    
    public MongoResponse find(final Bson queryParam, final Predicate<MongoResponse> predicate, final Duration MAX_TIME, final Duration POLL_TIME) {
        this.createColl();
        final MongoResponse response = new PollingPredicate<MongoResponse>(this.checkStatus(queryParam), predicate, MAX_TIME, POLL_TIME).evaluate();
        return response;
    }
    
    public MongoResponse find(final Bson queryParam, final Predicate<MongoResponse> predicate) {
        return this.find(queryParam, predicate, Duration.ofMinutes(2L), Duration.ofMillis(500L));
    }
    
    public void createSingleDocument(final Document document) {
        this.createColl();
        QueryMongo.logger.info(String.format("Initiating Insert document to mongo: \n db: %s \n table: %s \n inserted data: %s", this.db, this.table, document.toJson()));
        QueryMongo.table_map.get(this.table).insertOne(document);
        QueryMongo.logger.info(String.format("Document is inserted successfully to mongo: \n db: %s \n table: %s \n inserted data: %s ", this.db, this.table, document.toJson()));
    }
    
    public void createMultipleDocument(final List<Document> documents) {
        this.createColl();
        QueryMongo.logger.info(String.format("Initiating Insert multiple documents to mongo: \n db: %s \n table: %s \n inserted data: %s", this.db, this.table, Arrays.toString(documents.toArray())));
        QueryMongo.table_map.get(this.table).insertMany((List)documents);
        QueryMongo.logger.info(String.format("Documents are inserted successfully to mongo: \n db: %s \n table: %s \n inserted data: %s ", this.db, this.table, Arrays.toString(documents.toArray())));
    }
    
    public DeleteResult deleteSingleDocument(final Bson document) {
        this.createColl();
        QueryMongo.logger.info(String.format("Initiating Delete document to mongo: \n db: %s \n table: %s \n deleted data: %s", this.db, this.table, document));
        final DeleteResult deleteResult = QueryMongo.table_map.get(this.table).deleteOne(document);
        QueryMongo.logger.info(String.format("Document is deleted successfully to mongo: \n db: %s \n table: %s \n deleted data: %s ", this.db, this.table, document));
        return deleteResult;
    }
    
    public DeleteResult deleteMultipleDocument(final Bson documents) {
        this.createColl();
        QueryMongo.logger.info(String.format("Initiating Delete multiple documents to mongo: \n db: %s \n table: %s \n deleted data: %s", this.db, this.table, documents));
        final DeleteResult deleteResult = QueryMongo.table_map.get(this.table).deleteMany(documents);
        QueryMongo.logger.info(String.format("Documents are deleted successfully to mongo: \n db: %s \n table: %s \n deleted data: %s ", this.db, this.table, documents));
        return deleteResult;
    }
    
    public DeleteResult deleteSingleDocument(final Bson document, final Predicate<DeleteResult> predicate, final Duration MAX_TIME, final Duration POLL_TIME) {
        this.createColl();
        final DeleteResult response = new PollingPredicate<DeleteResult>(this.checkSingleDeleteStatus(document), predicate, MAX_TIME, POLL_TIME).evaluate();
        return response;
    }
    
    public DeleteResult deleteMultipleDocument(final Bson document, final Predicate<DeleteResult> predicate, final Duration MAX_TIME, final Duration POLL_TIME) {
        this.createColl();
        final DeleteResult response = new PollingPredicate<DeleteResult>(this.checkMultipleDeleteStatus(document), predicate, MAX_TIME, POLL_TIME).evaluate();
        return response;
    }
    
    public DeleteResult deleteSingleDocument(final Bson document, final Predicate<DeleteResult> predicate) {
        return this.deleteSingleDocument(document, predicate, Duration.ofMinutes(2L), Duration.ofMillis(500L));
    }
    
    public DeleteResult deleteMultipleDocuments(final Bson documents, final Predicate<DeleteResult> predicate) {
        return this.deleteMultipleDocument(documents, predicate, Duration.ofMinutes(2L), Duration.ofMillis(500L));
    }
    
    private Callable<MongoResponse> checkStatus(final Bson queryParam) {
        return new Callable<MongoResponse>() {
            @Override
            public MongoResponse call() throws Exception {
                return QueryMongo.this.find(queryParam);
            }
        };
    }
    
    private Callable<DeleteResult> checkSingleDeleteStatus(final Bson queryParam) {
        return new Callable<DeleteResult>() {
            @Override
            public DeleteResult call() throws Exception {
                return QueryMongo.this.deleteSingleDocument(queryParam);
            }
        };
    }
    
    private Callable<DeleteResult> checkMultipleDeleteStatus(final Bson queryParam) {
        return new Callable<DeleteResult>() {
            @Override
            public DeleteResult call() throws Exception {
                return QueryMongo.this.deleteMultipleDocument(queryParam);
            }
        };
    }
    
    private void createColl() {
        if (!QueryMongo.db_map.containsKey(this.db)) {
            QueryMongo.logger.info(String.format("creating mongodb instance for db: %s", this.db));
            final MongoDatabase database = MongoClientConfiguration.getInstance().getClient().getDatabase(this.db);
            QueryMongo.db_map.put(this.db, database);
        }
        if (!QueryMongo.table_map.containsKey(this.table)) {
            final MongoDatabase database = QueryMongo.db_map.get(this.db);
            QueryMongo.logger.info(String.format("creating mongo collection instance for collection: %s", this.table));
            final MongoCollection<Document> collection = (MongoCollection<Document>)database.getCollection(this.table);
            QueryMongo.table_map.put(this.table, collection);
        }
    }
    
    static {
        db_map = new ConcurrentHashMap<String, MongoDatabase>();
        table_map = new ConcurrentHashMap<String, MongoCollection<Document>>();
        QueryMongo.logger = LoggerFactory.getLogger((Class)QueryMongo.class);
    }
}
