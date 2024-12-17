package usa.bogdan.ENS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import usa.bogdan.ENS.Entity.ENSentity;

import java.util.List;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<ENSentity, Integer> {
    @Query("select ens from ENSentity ens where ens.status='FAILED'")
    public List<ENSentity> getFailedUsers();
    @Query("update ENSentity ens set ens.status=:status where ens.id=:id")
    @Transactional
    @Modifying
    public int updateStatus(@Param("id") int id, @Param("status") String status);
}