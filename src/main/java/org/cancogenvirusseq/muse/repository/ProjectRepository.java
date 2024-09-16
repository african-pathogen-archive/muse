package org.cancogenvirusseq.muse.repository;

import java.util.UUID;
import org.cancogenvirusseq.muse.repository.model.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectRepository extends ReactiveCrudRepository<Project, UUID> {

  Mono<Project> findById(UUID projectId);

  Flux<Project> findByName(String projectName);

  @Query("SELECT * FROM Project ORDER BY id OFFSET 10 ROWS FETCH NEXT 10 ROWS ONLY;")
  Flux<Project> findAllPaged(Pageable page);
}
