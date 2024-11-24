package com.github.apoioSolidario.controller;

import com.github.apoioSolidario.dto.request.FeedbackRequest;
import com.github.apoioSolidario.dto.response.FeedbackResponse;
import com.github.apoioSolidario.service.FeedbackService;
import com.github.apoioSolidario.exception.config.ErrorMessage;
import com.github.apoioSolidario.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Tag(name = "Feedback", description = "Contém todos os métodos relacionados ao feedback.")
@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {
    private final FeedbackService service;
    private final ResponseUtils responseUtils;

    public FeedbackController(FeedbackService service, ResponseUtils responseUtils) {
        this.service = service;
        this.responseUtils = responseUtils;
    }

    @Operation(summary = "Recuperar todos os feedbacks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todos os feedbacks recuperados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<FeedbackResponse>> getAllFeedbacks(Pageable pageable) {
        Page<FeedbackResponse> feedbacks = service.findAll(pageable);
        List<FeedbackResponse> feedbackResponses = feedbacks.getContent();
        HttpHeaders headers = responseUtils.getHeaders(feedbacks);
        return ResponseEntity.ok().headers(headers).body(feedbackResponses);
    }

    @Operation(summary = "Recuperar um feedback específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Feedback não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponse> getFeedback(@Valid @PathVariable UUID id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Operation(summary = "Atualizar um feedback específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Feedback não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<FeedbackResponse> updateFeedback(@Valid @PathVariable UUID id,
                                                           @RequestBody @Valid FeedbackRequest request) {
        var response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Criar um novo feedback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Feedback criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Entidade não processável",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<FeedbackResponse> saveFeedback(@Valid @RequestBody FeedbackRequest request) {
        var response = service.save(request);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getFeedbackId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @Operation(summary = "Deletar um feedback específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Feedback não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@Valid @PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
