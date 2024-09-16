package org.cancogenvirusseq.muse.repository.model;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Table("project")
public class Project {
  @Id private UUID projectId;
  @NonNull private String name;
  @NonNull private String pathogen;
  @NonNull private Integer noOfSamples;
  @NonNull private UUID userId;
  private OffsetDateTime createdAt;
}
