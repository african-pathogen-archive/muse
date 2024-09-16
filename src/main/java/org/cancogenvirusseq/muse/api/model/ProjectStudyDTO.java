package org.cancogenvirusseq.muse.api.model;

import java.util.UUID;
import lombok.NonNull;
import lombok.Value;
import org.cancogenvirusseq.muse.repository.model.ProjectStudy;

@Value
public class ProjectStudyDTO {
  @NonNull UUID id;
  @NonNull UUID projectId;
  @NonNull String studyId;

  public static ProjectStudyDTO fromDAO(ProjectStudy projectStudy) {
    return new ProjectStudyDTO(
        projectStudy.getId(), projectStudy.getProjectId(), projectStudy.getStudyId());
  }
}
