package org.cancogenvirusseq.muse.api;

import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cancogenvirusseq.muse.api.model.*;
import org.cancogenvirusseq.muse.exceptions.MuseBaseException;
import org.cancogenvirusseq.muse.repository.model.Project;
import org.cancogenvirusseq.muse.service.ProjectService;
import org.cancogenvirusseq.muse.utils.SecurityContextWrapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProjectController implements ProjectDefinition {
  private final ProjectService projectService;

  public Mono<Void> create(Project project) {
    return SecurityContextWrapper.forMono(projectService::create).apply(project);
  }

  public Mono<ProjectDTO> findById(@NonNull UUID projectId) {
    return SecurityContextWrapper.forMono(projectService::findById)
        .apply(projectId)
        .map(ProjectDTO::fromDAO);
  }

  public Flux<ProjectDTO> findByName(String name) {
    return SecurityContextWrapper.forFlux(projectService::findByName)
        .apply(name)
        .map(ProjectDTO::fromDAO);
  }

  public Mono<EntityListResponse<ProjectDTO>> getAllProjects(
      Integer page, Integer size, Sort.Direction sortDirection, ProjectSortField sortField) {
    return SecurityContextWrapper.forFlux(projectService::findAllPaged)
        .apply(PageRequest.of(page, size, Sort.by(sortDirection, sortField.toString())))
        .map(ProjectDTO::fromDAO)
        .collectList()
        .transform(this::listResponseTransform);
  }

  public Mono<ProjectDTO> update(Project project) {
    return SecurityContextWrapper.forMono(projectService::update)
        .apply(project)
        .map(ProjectDTO::fromDAO);
  }

  public Mono<Void> delete(UUID projectId) {
    return SecurityContextWrapper.forMono(projectService::delete).apply(projectId);
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handle(Throwable ex) {
    log.error("ProjectController exception handler", ex);
    if (ex instanceof MuseBaseException) {
      return ErrorResponse.errorResponseEntity((MuseBaseException) ex);
    } else if (ex instanceof AccessDeniedException) {
      return ErrorResponse.errorResponseEntity(HttpStatus.FORBIDDEN, ex.getLocalizedMessage());
    } else {
      return ErrorResponse.errorResponseEntity(
          HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
    }
  }

  private <T> Mono<EntityListResponse<T>> listResponseTransform(Mono<List<T>> entities) {
    return entities.map(entityList -> EntityListResponse.<T>builder().data(entityList).build());
  }
}
