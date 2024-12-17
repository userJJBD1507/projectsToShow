package usa.bogdan.WebCrawler.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import usa.bogdan.WebCrawler.Models.URLqueueEntity;

import java.util.List;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<URLqueueEntity, Integer> {
    //    @Query("select url from URLqueueEntity url where url.url=:url")
//    public URLqueueEntity getUrl(@Param("url") String url);
//    @Query("")
//    public List<URLqueueEntity> getUrls();
    @Query("select url from URLqueueEntity url where url.id>:fixedId")
    public List<URLqueueEntity> getAfterID(@Param("fixedId") int fixedId);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM URLqueueEntity u WHERE u.url = ?1")
    boolean existsByUrl(String url);
    @Query("update URLqueueEntity url set url.content_id = :contentId where url.url = :url")
    @Transactional
    @Modifying
    public int updateId(@Param("contentId") String contentId, @Param("url") String url);
    @Query("select url from URLqueueEntity url where url.content_id=null ")
    public List<URLqueueEntity> get();
    @Query("select url from URLqueueEntity url where url.content_id in :ids")
    public List<URLqueueEntity> getById(@Param("ids") List<String> ids);
    @Query("select url from URLqueueEntity url where url.content_id is null ")
    public List<URLqueueEntity> getList();
    @Query("UPDATE URLqueueEntity u SET u.content_id=:contentId WHERE u.url = :url")
    @Modifying
    @Transactional
    public int updateContentId(@Param("contentId") String contentId, @Param("url") String url);
}