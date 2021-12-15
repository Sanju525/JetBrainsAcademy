package platform.codeshare;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CodeShareRepository extends CrudRepository<Code, String> {
    Optional<Code> findById(String uuid);
    List<Code> findAll();
}
