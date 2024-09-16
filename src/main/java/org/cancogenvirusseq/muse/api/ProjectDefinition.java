package org.cancogenvirusseq.muse.api;

import io.swagger.annotations.*;
import java.util.UUID;
import javax.validation.Valid;
import org.cancogenvirusseq.muse.api.model.*;
import org.cancogenvirusseq.muse.components.security.HasReadWriteAccess;
import org.cancogenvirusseq.muse.repository.model.Project;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Api(value = "Project Service Endpoints.", tags = "Project")
public interface ProjectDefinition {
  String BAD_REQUEST = "The request is malformed.";
  String UNAUTHORIZED_MSG = "Request requires authorization.";
  String FORBIDDEN_MSG = "The requester is not authorized to perform this action.";
  String UNKNOWN_MSG = "An unexpected error occurred.";

  // Create
  @ApiOperation(value = "Create a Project", nickname = "Create a Project", tags = "Project")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "", response = Project.class),
        @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorResponse.class),
        @ApiResponse(code = 401, message = UNAUTHORIZED_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 403, message = FORBIDDEN_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 500, message = UNKNOWN_MSG, response = ErrorResponse.class)
      })
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @HasReadWriteAccess
  Mono<Void> create(@RequestBody Project project);

  // Find By Id
  @ApiOperation(
      value = "Get a Project by Id",
      nickname = "Get a Project by Id",
      // response = ProjectDTO.class,
      tags = "Project")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "", response = Project.class),
        @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorResponse.class),
        @ApiResponse(code = 401, message = UNAUTHORIZED_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 403, message = FORBIDDEN_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 500, message = UNKNOWN_MSG, response = ErrorResponse.class)
      })
  @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
  @HasReadWriteAccess
  Mono<ProjectDTO> findById(@PathVariable("projectId") UUID projectId);

  // Find By Name
  @ApiOperation(
      value = "Get a Project by name",
      nickname = "Get a Project by name",
      // response = ProjectDTO.class,
      tags = "Project")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "", response = Project.class),
        @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorResponse.class),
        @ApiResponse(code = 401, message = UNAUTHORIZED_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 403, message = FORBIDDEN_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 500, message = UNKNOWN_MSG, response = ErrorResponse.class)
      })
  @RequestMapping(value = "/projectName/{projectName}", method = RequestMethod.GET)
  @HasReadWriteAccess
  Flux<ProjectDTO> findByName(@PathVariable("projectName") String projectName);

  // Find All
  // @ApiOperation(
  // value = "Get all Active Projects",
  // nickname = "Get all Active Projects",
  // response = EntityListResponse.class,
  // tags = "Project")
  // @ApiResponses(
  // value = {
  // @ApiResponse(code = 200, message = "", response = ProjectDTO.class),
  // @ApiResponse(code = 400, message = BAD_REQUEST, response =
  // ErrorResponse.class),
  // @ApiResponse(code = 401, message = UNAUTHORIZED_MSG, response =
  // ErrorResponse.class),
  // @ApiResponse(code = 403, message = FORBIDDEN_MSG, response =
  // ErrorResponse.class),
  // @ApiResponse(code = 500, message = UNKNOWN_MSG, response =
  // ErrorResponse.class)
  // })
  // @RequestMapping(method = RequestMethod.GET, produces =
  // MediaType.TEXT_EVENT_STREAM_VALUE)
  // @HasReadWriteAccess
  // Mono<EntityListResponse<ProjectDTO>> findAll(
  // @ApiParam(
  // example = "0",
  // value =
  // "OPTIONAL: Given page size, what page of entities to return. Example: A page
  // value of 10 with size 5 results in entities 50 - 54 being returned.")
  // @Valid
  // @RequestParam(value = "page", defaultValue = "0", required = false)
  // Integer page,
  // @ApiParam(
  // example = "10",
  // value =
  // "OPTIONAL: The preferred number of entities to return for a page, may result
  // in fewer entities than requested but never more.")
  // @Valid
  // @RequestParam(value = "size", defaultValue = "10", required = false)
  // Integer size,
  // @ApiParam(example = "DESC", value = "OPTIONAL: Direction on which to apply
  // sort.")
  // @Valid
  // @RequestParam(value = "sortDirection", defaultValue = "DESC", required =
  // false)
  // Sort.Direction sortDirection,
  // @ApiParam(example = "createdAt", value = "OPTIONAL: Field on which to sort.")
  // @Valid
  // @RequestParam(value = "sortField", defaultValue = "createdAt", required =
  // false)
  // ProjectSortField sortField);

  @ApiOperation(
      value = "Get all Active Projects",
      nickname = "Get all Active Projects",
      response = EntityListResponse.class,
      tags = "Project")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "", response = EntityListResponse.class),
        @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorResponse.class),
        @ApiResponse(code = 401, message = UNAUTHORIZED_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 403, message = FORBIDDEN_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 500, message = UNKNOWN_MSG, response = ErrorResponse.class)
      })
  @RequestMapping(
      value = "/getAllProjects",
      produces = MediaType.APPLICATION_JSON_VALUE,
      method = RequestMethod.GET)
  @HasReadWriteAccess
  Mono<EntityListResponse<ProjectDTO>> getAllProjects(
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
      value = "Update a Project by Id",
      nickname = "Update a Project by Id",
      response = Project.class,
      tags = "Project")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "", response = Project.class),
        @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorResponse.class),
        @ApiResponse(code = 401, message = UNAUTHORIZED_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 403, message = FORBIDDEN_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 500, message = UNKNOWN_MSG, response = ErrorResponse.class)
      })
  @RequestMapping(value = "/update", method = RequestMethod.PUT)
  @HasReadWriteAccess
  Mono<ProjectDTO> update(@RequestBody Project project);

  // Delete
  @ApiOperation(
      value = "Delete a Project by Id",
      nickname = "Delete a Project by Id",
      // response = ProjectDTO.class,
      tags = "Project")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "", response = Project.class),
        @ApiResponse(code = 400, message = BAD_REQUEST, response = ErrorResponse.class),
        @ApiResponse(code = 401, message = UNAUTHORIZED_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 403, message = FORBIDDEN_MSG, response = ErrorResponse.class),
        @ApiResponse(code = 500, message = UNKNOWN_MSG, response = ErrorResponse.class)
      })
  @RequestMapping(value = "/delete/{projectId}", method = RequestMethod.DELETE)
  @HasReadWriteAccess
  Mono<Void> delete(@PathVariable("projectId") UUID projectId);
}
