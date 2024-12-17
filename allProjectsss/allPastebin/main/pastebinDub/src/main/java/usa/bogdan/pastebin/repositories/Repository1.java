package usa.bogdan.pastebin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import usa.bogdan.pastebin.entities.MetadataEntity;

@org.springframework.stereotype.Repository
public interface Repository1 extends JpaRepository<MetadataEntity, Integer> {
    @Query("select met from MetadataEntity met where met.metadata_link=:metadata")
    public MetadataEntity functionGetting(@Param("metadata") String metadata);
//    @Query("select met.metadata_link from MetadataEntity met where met.hashcode=:hashcode")
//    public String functionName(@Param("hashcode") int hashcode);
//    @Query("update MetadataEntity met set met.hashcode=:hashcode where met.metadata_link=:metadata")
//    @Modifying
//    @Transactional
//    public int update(@Param("hashcode") int hashcode,
//                      @Param("metadata") String metadata);
    @Query("delete MetadataEntity met where met.metadata_link=?1")
    @Modifying
    @Transactional
    public int delete(@Param("key") String key);
    @Query("select max(met.id) from MetadataEntity met")
    public Integer getMaximumId();
}