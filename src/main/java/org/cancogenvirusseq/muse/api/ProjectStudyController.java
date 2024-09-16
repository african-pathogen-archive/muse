package org.cancogenvirusseq.muse.api;

import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.cancogenvirusseq.muse.api.model.*;
import org.cancogenvirusseq.muse.repository.model.ProjectStudy;
import org.cancogenvirusseq.muse.service.ProjectStudyService;
import org.cancogenvirusseq.muse.utils.SecurityContextWrapper;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ProjectStudyController implements ProjectStudyDefinition {
  private final ProjectStudyService projectStudyService;

  public Mono<Void> create(ProjectStudy projectStudy) {
    return SecurityContextWrapper.forMono(projectStudyService::create).apply(projectStudy);
  }

  @Override
  public Mono<ProjectStudyDTO> findById(UUID id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public Mono<EntityListResponse<ProjectStudyDTO>> getAllProjectSubmissions(
      @Valid Integer page,
      @Valid Integer size,
      Sort.Direction sortDirection,
      @Valid ProjectSortField sortField) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAllProjectSubmissions'");
  }

  @Override
  public Mono<ProjectStudyDTO> update(ProjectStudy projectStudy) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public Mono<Void> delete(UUID id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }
}
