package usa.bogdan.WebCrawler.Repositories;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import usa.bogdan.WebCrawler.Models.ContentEntity;

import java.util.List;

@Repository
public interface MongoDBRepository extends MongoRepository<ContentEntity, Integer> {
    @Query("{ 'content': { $regex: ?0, $options: 'i' } }")
    List<ContentEntity> findByContentContainingIgnoreCase(String content);
}