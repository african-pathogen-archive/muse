package org.cancogenvirusseq.muse.service;

import java.util.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.cancogenvirusseq.muse.repository.ProjectRepository;
import org.cancogenvirusseq.muse.repository.model.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectRepository projectRepository;

  /**
   * @param project
   * @param securityContext
   * @return Mono<Void>
   */
  public Mono<Void> create(Project project, @NonNull SecurityContext securityContext) {
    return projectRepository.save(project).then();
  }

  /**
   * @param projectId
   * @param securityContext
   * @return Mono<Project>
   */
  public Mono<Project> findById(@NonNull UUID projectId, @NonNull SecurityContext securityContext) {
    return projectRepository.findById(projectId);
  }

  /**
   * @param name
   * @param securityContext
   * @return Flux<Project>
   */
  public Flux<Project> findByName(String name, @NonNull SecurityContext securityContext) {
    return projectRepository.findByName(name);
  }

  /**
   * @param securityContext
   * @return Flux<Project>
   */
  public Flux<Project> findAllPaged(Pageable page, @NonNull SecurityContext securityContext) {
    // return projectRepository.findAllPaged(page); TODO: Need to figure out how to make pagination
    // work.
    return projectRepository.findAll();
  }

  /**
   * @param project
   * @param securityContext
   * @return Mono<Project>
   */
  public Mono<Project> update(Project project, @NonNull SecurityContext securityContext) {
    return projectRepository.save(project);
  }

  /**
   * @param projectId
   * @param securityContext
   * @return Mono<Void>
   */
  public Mono<Void> delete(@NonNull UUID projectId, @NonNull SecurityContext securityContext) {
    return projectRepository.deleteById(projectId);
  }
}
