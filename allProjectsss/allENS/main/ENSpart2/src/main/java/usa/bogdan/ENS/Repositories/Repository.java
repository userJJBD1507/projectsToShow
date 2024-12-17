package usa.bogdan.ENS.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import usa.bogdan.ENS.Entity.ENSentity;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<ENSentity, Integer> {
    @Query("delete from ENSentity ")
    @Transactional
    @Modifying
    public int deleteClearDB();
}