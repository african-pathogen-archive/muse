package org.cancogenvirusseq.muse.api;

import io.swagger.annotations.*;
import java.util.UUID;
import javax.validation.Valid;
import org.cancogenvirusseq.muse.api.model.*;
import org.cancogenvirusseq.muse.components.security.HasReadWriteAccess;
import org.cancogenvirusseq.muse.repository.model.ProjectStudy;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Api(value = "ProjectStudy-Submission Service Endpoints.", tags = "ProjectStudy")
public interface ProjectStudyDefinition {
  String BAD_REQUEST = "The request is malformed.";
  String UNAUTHORIZED_MSG = "Request requires authorization.";
  String FORBIDDEN_MSG = "The requester is not authorized to perform this action.";
  String UNKNOWN_MSG = "An unexpected error occurred.";

  // Create
  @ApiOperation(
      value = "Create a ProjectStudy",
      nickname = "Create a ProjectStudy",
      tags = "ProjectStudy")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "", response = ProjectStudy.class),
        @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorResponse.class),
        @ApiResponse(code = 401, message = UNAUTHORIZED_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 403, message = FORBIDDEN_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 500, message = UNKNOWN_MSG, response = ErrorResponse.class)
      })
  @RequestMapping(value = "/ps/create", method = RequestMethod.POST)
  @HasReadWriteAccess
  Mono<Void> create(@RequestBody ProjectStudy projectStudy);

  // Find By Id
  @ApiOperation(
      value = "Get a ProjectStudy by Id",
      nickname = "Get a ProjectStudy by Id",
      // response = ProjectSubmissionDTO.class,
      tags = "ProjectStudy")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "", response = ProjectStudy.class),
        @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorResponse.class),
        @ApiResponse(code = 401, message = UNAUTHORIZED_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 403, message = FORBIDDEN_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 500, message = UNKNOWN_MSG, response = ErrorResponse.class)
      })
  @RequestMapping(value = "/ps/{id}", method = RequestMethod.GET)
  @HasReadWriteAccess
  Mono<ProjectStudyDTO> findById(@PathVariable("id") UUID id);

  @ApiOperation(
      value = "Get all Active ProjectSubmissions",
      nickname = "Get all Active ProjectSubmissions",
      response = EntityListResponse.class,
      tags = "ProjectStudy")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "", response = EntityListResponse.class),
        @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorResponse.class),
        @ApiResponse(code = 401, message = UNAUTHORIZED_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 403, message = FORBIDDEN_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 500, message = UNKNOWN_MSG, response = ErrorResponse.class)
      })
  @RequestMapping(
      value = "/ps/getAllProjectSubmissions",
      produces = MediaType.APPLICATION_JSON_VALUE,
      method = RequestMethod.GET)
  @HasReadWriteAccess
  Mono<EntityListResponse<ProjectStudyDTO>> getAllProjectSubmissions(
      @ApiParam(
              example = "0",
              value =
                  "OPTIONAL: Given page size, what page of entities to return. Example: A page value of 10 with size 5 results in entities 50 - 54 being returned.")
          @Valid
          @RequestParam(value = "page", defaultValue = "0", required = false)
          Integer page,
      @ApiParam(
              example = "10",
              value =
                  "OPTIONAL: The preferred number of entities to return for a page, may result in fewer entities than requested but never more.")
          @Valid
          @RequestParam(value = "size", defaultValue = "10", required = false)
          Integer size,
      @ApiParam(example = "DESC", value = "OPTIONAL: Direction on which to apply sort.")
          @Valid
          @RequestParam(value = "sortDirection", defaultValue = "DESC", required = false)
          Sort.Direction sortDirection,
      @ApiParam(example = "createdAt", value = "OPTIONAL: Field on which to sort.")
          @Valid
          @RequestParam(value = "sortField", defaultValue = "createdAt", required = false)
          ProjectSortField sortField);

  // Update
  @ApiOperation(
      value = "Update a ProjectStudy by Id",
      nickname = "Update a ProjectStudy by Id",
      response = ProjectStudy.class,
      tags = "ProjectStudy")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "", response = ProjectStudy.class),
        @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorResponse.class),
        @ApiResponse(code = 401, message = UNAUTHORIZED_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 403, message = FORBIDDEN_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 500, message = UNKNOWN_MSG, response = ErrorResponse.class)
      })
  @RequestMapping(value = "/ps/update", method = RequestMethod.PUT)
  @HasReadWriteAccess
  Mono<ProjectStudyDTO> update(@RequestBody ProjectStudy projectStudy);

  // Delete
  @ApiOperation(
      value = "Delete a ProjectStudy by Id",
      nickname = "Delete a ProjectStudy by Id",
      // response = ProjectSubmissionDTO.class,
      tags = "ProjectStudy")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "", response = ProjectStudy.class),
        @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorResponse.class),
        @ApiResponse(code = 401, message = UNAUTHORIZED_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 403, message = FORBIDDEN_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 500, message = UNKNOWN_MSG, response = ErrorResponse.class)
      })
  @RequestMapping(value = "/ps/delete/{id}", method = RequestMethod.DELETE)
  @HasReadWriteAccess
  Mono<Void> delete(@PathVariable("id") UUID id);
}
