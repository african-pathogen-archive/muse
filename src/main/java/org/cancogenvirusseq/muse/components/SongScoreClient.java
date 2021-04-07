package org.cancogenvirusseq.muse.components;

import static java.lang.String.format;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.cancogenvirusseq.muse.model.song_score.AnalysisFileResponse;
import org.cancogenvirusseq.muse.model.song_score.ScoreFileSpec;
import org.cancogenvirusseq.muse.model.song_score.SubmitResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class SongScoreClient {

  @Value("${songScoreClient.songRootUrl}")
  String songRootUrl;

  @Value("${songScoreClient.scoreRootUrl}")
  String scoreRootUrl;

  @Value("${songScoreClient.systemApiToken}")
  String systemApiToken;

  @PostConstruct
  public void init() {
    log.info("Initialized song score client.");
    log.info("songRootUrl - " + songRootUrl);
    log.info("scoreRootUrl - " + scoreRootUrl);
  }

  public Mono<SubmitResponse> submitPayload(String studyId, String payload) {
    val url = format(songRootUrl + "/submit/%s", studyId);
    return WebClient.create(url)
        .post()
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(payload))
        .header("Authorization", "Bearer " + systemApiToken)
        .retrieve()
        .bodyToMono(SubmitResponse.class)
        .onErrorMap(t -> new Error("Failed to submit payload"))
        .log();
  }

  public Mono<AnalysisFileResponse> getFileSpecFromSong(String studyId, UUID analysisId) {
    val url = format(songRootUrl + "/studies/%s/analysis/%s/files", studyId, analysisId.toString());
    return WebClient.create(url)
        .get()
        .retrieve()
        .bodyToFlux(AnalysisFileResponse.class)
        // we expect only one file to be uploaded in each analysis
        .next()
        .log();
  }

  public Mono<ScoreFileSpec> initScoreUpload(
      AnalysisFileResponse analysisFileResponse, String md5Sum) {
    val url =
        format(
            scoreRootUrl + "/upload/%s/uploads?fileSize=%s&md5=%s&overwrite=true",
            analysisFileResponse.getObjectId(),
            analysisFileResponse.getFileSize(),
            md5Sum);

    return WebClient.create(url)
        .post()
        .header("Authorization", "Bearer " + systemApiToken)
        .retrieve()
        .bodyToMono(ScoreFileSpec.class)
        .onErrorMap(t -> new Error("Failed to initialize upload"))
        .log();
  }

  public Mono<String> uploadAndFinalize(
      ScoreFileSpec scoreFileSpec, String fileContent, String md5) {
    // we expect only one file part
    val presignedUrl = decodeUrl(scoreFileSpec.getParts().get(0).getUrl());

    return WebClient.create(presignedUrl)
        .put()
        .contentType(MediaType.TEXT_PLAIN)
        .contentLength(fileContent.length())
        .body(BodyInserters.fromValue(fileContent))
        .retrieve()
        .toBodilessEntity()
        .map(res -> res.getHeaders().getETag().replace("\"", ""))
        .flatMap(eTag -> finalizeScoreUpload(scoreFileSpec, md5, eTag))
        .onErrorMap(t -> new Error("Failed to upload and finalize"))
        .log();
  }

  private Mono<String> finalizeScoreUpload(ScoreFileSpec scoreFileSpec, String md5, String etag) {
    val objectId = scoreFileSpec.getObjectId();
    val uploadId = scoreFileSpec.getUploadId();

    val finalizePartUrl =
        format(
            scoreRootUrl + "/upload/%s/parts?uploadId=%s&etag=%s&md5=%s&partNumber=1",
            objectId,
            uploadId,
            etag,
            md5);
    val finalizeUploadPart =
        WebClient.create(finalizePartUrl)
            .post()
            .header("Authorization", "Bearer " + systemApiToken)
            .retrieve()
            .toBodilessEntity();

    val finalizeUploadUrl = format(scoreRootUrl + "/upload/%s?uploadId=%s", objectId, uploadId);
    val finalizeUpload =
        WebClient.create(finalizeUploadUrl)
            .post()
            .header("Authorization", "Bearer " + systemApiToken)
            .retrieve()
            .toBodilessEntity();

    // The finalize step in score requires finalizing each file part and then the whole upload
    // we only have one file part, so we finalize the part and upload one after the other
    return finalizeUploadPart.then(finalizeUpload).map(Objects::toString).log();
  }

  public Mono<String> publishAnalysis(String studyId, UUID analysisId) {
    return publishAnalysis(studyId, analysisId.toString());
  }

  public Mono<String> publishAnalysis(String studyId, String analysisId) {
    val url =
        format(
            songRootUrl + "/studies/%s/analysis/publish/%s?ignoreUndefinedMd5=false",
            studyId,
            analysisId);
    return WebClient.create(url)
        .put()
        .header("Authorization", "Bearer " + systemApiToken)
        .retrieve()
        .toBodilessEntity()
        .map(Objects::toString)
        .onErrorMap(t -> new Error("Failed to publish analysis"))
        .log();
  }

  public Mono<String> downloadObject(String objectId) {
    return getFileLink(objectId).flatMap(this::downloadFromS3);
  }

  private Mono<String> getFileLink(String objectId) {
    val url = format(scoreRootUrl + "/download/%s?offset=0&length=-1&external=true", objectId);
    return WebClient.create(url)
        .get()
        .header("Authorization", "Bearer " + systemApiToken)
        .retrieve()
        .bodyToMono(ScoreFileSpec.class)
        // we request length = -1 which returns one file part
        .map(spec -> spec.getParts().get(0).getUrl());
  }

  private Mono<String> downloadFromS3(String presignedUrl) {
    return WebClient.create(decodeUrl(presignedUrl))
        .get()
        .retrieve()
        .bodyToMono(String.class)
        .log();
  }

  private static String decodeUrl(String str) {
    return URLDecoder.decode(str, StandardCharsets.UTF_8);
  }
}