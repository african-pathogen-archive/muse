package org.cancogenvirusseq.muse.api.model;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.NonNull;
import lombok.Value;
import org.cancogenvirusseq.muse.repository.model.Project;

@Value
public class ProjectDTO {
  @NonNull UUID projectId;
  @NonNull String name;
  @NonNull String pathogen;
  @NonNull Integer noOfSamples;
  @NonNull OffsetDateTime createdAt;

  public static ProjectDTO fromDAO(Project project) {
    return new ProjectDTO(
        project.getProjectId(),
        project.getName(),
        project.getPathogen(),
        project.getNoOfSamples(),
        project.getCreatedAt());
  }
}
