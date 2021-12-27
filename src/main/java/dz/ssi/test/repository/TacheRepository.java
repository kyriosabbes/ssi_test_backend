package dz.ssi.test.repository;

import java.util.List;

import dz.ssi.test.models.Tache;
import dz.ssi.test.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacheRepository extends JpaRepository<Tache, Long> {
  List<Tache> findByTacheUser(User user);

  List<Tache> findByTitleContaining(String title);
}
