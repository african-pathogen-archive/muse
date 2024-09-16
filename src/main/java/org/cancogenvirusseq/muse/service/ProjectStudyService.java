package org.cancogenvirusseq.muse.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.cancogenvirusseq.muse.repository.ProjectStudyRepository;
import org.cancogenvirusseq.muse.repository.model.ProjectStudy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProjectStudyService {
  private final ProjectStudyRepository projectStudyRepository;

  /**
   * @param projectStudy
   * @param securityContext
   * @return Mono<Void>
   */
  public Mono<Void> create(ProjectStudy projectStudy, @NonNull SecurityContext securityContext) {
    return projectStudyRepository.save(projectStudy).then();
  }
}
