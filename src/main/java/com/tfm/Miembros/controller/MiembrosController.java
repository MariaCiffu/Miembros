package com.tfm.Miembros.controller;


import com.tfm.Miembros.model.pojo.Miembro;
import com.tfm.Miembros.model.pojo.MiembroDto;
import com.tfm.Miembros.model.request.CreateMiembroRequest;
import com.tfm.Miembros.service.MiembrosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Miembros Controller", description = "Microservicio encargado de exponer operaciones CRUD sobre miembros alojados en una base de datos en memoria.")
public class MiembrosController {
    private final MiembrosService service;

    @GetMapping("/miembros")
    @Operation(
            operationId = "Obtener miembros",
            description = "Operación de lectura",
            summary = "Se devuelve una lista de todos los miembros almacenados en la base de datos.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Miembro.class)))
    public ResponseEntity<List<Miembro>> getMiembros(
            @RequestHeader Map<String, String> headers,
            @Parameter(name = "nombre", description = "Nombre del miembro", required = false)
            @RequestParam(required = false) String nombre,
            @Parameter(name = "apellido", description = "Apellido de miembro", required = false)
            @RequestParam(required = false) String apellido,
            @Parameter(name = "cargo", description = "Cargo del miembro", required = false)
            @RequestParam(required = false) String cargo,
            @Parameter(name = "tfno", description = "Teléfono del miembro", required = false)
            @RequestParam(required = false) String tfno,
            @Parameter(name = "mail", description = "Email del miembro", required = false)
            @RequestParam(required = false) String mail) {

        log.info("headers: {}", headers);
        List<Miembro> miembros = service.getMiembros(nombre, apellido, cargo, tfno, mail);

        if (miembros != null) {
            return ResponseEntity.ok(miembros);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/miembros/{miembroId}")
    @Operation(
            operationId = "Obtener un miembro",
            description = "Operación de lectura",
            summary = "Se devuelve un miembro a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Miembro.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el miembro con el identificador indicado.")
    public ResponseEntity<Miembro> getMiembro(@PathVariable String miembroId) {

        log.info("Request received for product {}", miembroId);
        Miembro miembro = service.getMiembro(miembroId);

        if (miembro != null) {
            return ResponseEntity.ok(miembro);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/miembros/{miembroId}")
    @Operation(
            operationId = "Eliminar un miembro",
            description = "Operación de escritura",
            summary = "Se elimina un miembro a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el miembro con el identificador indicado.")
    public ResponseEntity<Void> deleteMiembro(@PathVariable String miembroId) {

        Boolean removed = service.removeMiembro(miembroId);

        if (Boolean.TRUE.equals(removed)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/miembros")
    @Operation(
            operationId = "Insertar un miembro",
            description = "Operación de escritura",
            summary = "Se crea un miembro a partir de sus datos.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del miembro a crear.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateMiembroRequest.class))))
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Miembro.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Datos incorrectos introducidos.")
    public ResponseEntity<Miembro> addMiembro(@RequestBody CreateMiembroRequest request) {

        Miembro createdMiembro = service.createMiembro(request);

        if (createdMiembro != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMiembro);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @PatchMapping("/miembros/{miembroId}")
    @Operation(
            operationId = "Modificar parcialmente un miembro",
            description = "RFC 7386. Operación de escritura",
            summary = "RFC 7386. Se modifica parcialmente un miembro.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del miembro a crear.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = String.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Miembro.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Miembro inválido o datos incorrectos introducidos.")
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el miembro con el identificador indicado.")
    public ResponseEntity<Miembro> patchMiembro(@PathVariable String miembroId, @RequestBody String patchBody) {

        Miembro patched = service.updateMiembro(miembroId, patchBody);
        if (patched != null) {
            return ResponseEntity.ok(patched);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/miembros/{miembroId}")
    @Operation(
            operationId = "Modificar totalmente un miembro",
            description = "Operación de escritura",
            summary = "Se modifica totalmente un miembro.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del miembro a actualizar.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = MiembroDto.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Miembro.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Miembro no encontrado.")
    public ResponseEntity<Miembro> updateMiembro(@PathVariable String miembroId, @RequestBody MiembroDto body) {

        Miembro updated = service.updateMiembro(miembroId, body);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
