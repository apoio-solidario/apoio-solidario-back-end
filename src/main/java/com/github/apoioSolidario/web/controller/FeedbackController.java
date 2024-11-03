package com.github.apoioSolidario.web.controller;

import com.github.apoioSolidario.domain.dto.request.FeedbackRequest;
import com.github.apoioSolidario.domain.dto.response.FeedbackResponse;
import com.github.apoioSolidario.services.FeedbackService;
import com.github.apoioSolidario.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@Tag(name = "Feedback", description = "Contém todos os métodos relacionados ao feedback.")
@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {
    private final FeedbackService service;

    public FeedbackController(FeedbackService service) {
        this.service = service;
    }

    @Operation(summary = "Recuperar todos os feedbacks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todos os feedbacks recuperados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<FeedbackResponse>> getAllFeedbacks() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @Operation(summary = "Recuperar um feedback específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Feedback não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponse> getFeedback(@Valid @PathVariable Long id) {
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
    public ResponseEntity<FeedbackResponse> updateFeedback(@Valid @PathVariable Long id,
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
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @Operation(summary = "Deletar um feedback específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Feedback não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@Valid @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
