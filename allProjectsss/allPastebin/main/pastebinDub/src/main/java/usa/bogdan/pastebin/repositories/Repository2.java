package usa.bogdan.pastebin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import usa.bogdan.pastebin.entities.HashEntity;

@Repository
public interface Repository2 extends JpaRepository<HashEntity, Integer> {
    @Query("delete HashEntity has where has.metadata_id=:metadata")
    @Modifying
    @Transactional
    public int delete(@Param("metadata") int metadata);
    @Query("select has from HashEntity has where has.hash=?1")
    public HashEntity getHashEntity(@Param("hash") String hash);
    @Query("select has from HashEntity has where has.metadata_id=:id")
    public HashEntity getByMainEntityId(@Param("id") int id);
    @Query("select max(has.metadata_id) from HashEntity has")
    public Integer getMaximumId();
}