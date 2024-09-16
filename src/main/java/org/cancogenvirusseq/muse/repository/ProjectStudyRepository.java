package org.cancogenvirusseq.muse.repository;

import java.util.UUID;
import org.cancogenvirusseq.muse.repository.model.ProjectStudy;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProjectStudyRepository extends ReactiveCrudRepository<ProjectStudy, UUID> {}
