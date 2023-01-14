package de.niko.pcstore.controller.api;


import de.niko.pcstore.configuration.Tags;
import de.niko.pcstore.dto.ErrorDTO;
import de.niko.pcstore.dto.InternalOrderDTO;
import de.niko.pcstore.dto.InternalOrderShortDTO;
import de.niko.pcstore.dto.NewInternalOrderDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = Tags.INTERNAL_ORDER_API_TAG)
@SecurityRequirement(name = "test-jwt")
public interface InternalOrderApi {
    String GET_INTERNAL_ORDER_LIST = "/internal-order-list";
    String GET_INTERNAL_ORDER = "/internal-order/{id}";
    // TODO:
    String GET_INTERNAL_ORDER_CLIENT_DATA = "/internal-order/{id}/client-data";
    // TODO:
    String GET_INTERNAL_ORDER_PERSONAL_COMPUTER = "/internal-order/{id}/personal-computer";
    String ADD_INTERNAL_ORDER = "/internal-order";
    String DELETE_INTERNAL_ORDER = "/internal-order/{id}";
    String UPDATE_INTERNAL_ORDER_STATUS = "/internal-order/update-status";

    @Operation(summary = "Delete a internal order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "item deleted", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "304", description = "an item is already deleted", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "401", description = "Wrong authentication token", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "403", description = "Lack of authority", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "404", description = "an item is not deletable or already deleted", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
    })
    @RequestMapping(value = DELETE_INTERNAL_ORDER,
            method = RequestMethod.DELETE)
    ResponseEntity<Object> deleteGlobalVariable(
            @Parameter(description = "Id of the internal order", required = true)
            @PathVariable("id")
            String id);

    @PreAuthorize("hasRole(\"READ\")")
    @Operation(summary = "Get all internal orders")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All internal orders", content = @Content(array = @ArraySchema(schema = @Schema(implementation = InternalOrderShortDTO.class)))),
            @ApiResponse(responseCode = "401", description = "Wrong authentication token", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "403", description = "Lack of authority", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "401", description = "Wrong authentication token", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
    })
    @RequestMapping(value = GET_INTERNAL_ORDER_LIST,
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    ResponseEntity<Object> getInternalOrderList(
            @RequestParam(required = false)
            @Parameter(description = "Statuses of the internal order")
            List<InternalOrderDTO.Status> statuses);

    @Operation(summary = "Get one specific internal order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = InternalOrderDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad input parameter", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "401", description = "Wrong authentication token", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "403", description = "Lack of authority", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
    })
    @RequestMapping(value = GET_INTERNAL_ORDER,
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    ResponseEntity<InternalOrderDTO> getInternalOrder(
            @Parameter(description = "Internal order id", required = true)
            @PathVariable("id")
            String id);

    @Operation(summary = "Add a new internal order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = InternalOrderDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad input parameter", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "401", description = "Wrong authentication token", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "403", description = "Lack of authority", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
    })
    @RequestMapping(value = ADD_INTERNAL_ORDER,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST)
    ResponseEntity<Object> addInternalOrder(
            @Parameter(description = "Object to add")
            @RequestBody
            NewInternalOrderDTO internalOrderDTO);


    @Operation(summary = "Update status of internal order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad input parameter"),
            @ApiResponse(responseCode = "304", description = "Status already set"),
            @ApiResponse(responseCode = "401", description = "Wrong authentication token", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "403", description = "Lack of authority", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
    })
    @RequestMapping(value = UPDATE_INTERNAL_ORDER_STATUS,
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    ResponseEntity<Object> updateInternalOrderStatus(
            @RequestParam("id")
            String internalOrderId,
            @RequestParam
            @Parameter(description = "New status", schema = @Schema(implementation = InternalOrderDTO.Status.class))
            InternalOrderDTO.Status status);

    @Operation(summary = "Get personal computer configuration of an internal order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad input parameter"),
            @ApiResponse(responseCode = "401", description = "Wrong authentication token", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "403", description = "Lack of authority", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
    })
    @RequestMapping(value = GET_INTERNAL_ORDER_PERSONAL_COMPUTER,
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    ResponseEntity<InternalOrderDTO.PersonalComputerDTO> getPersonalComputerConfigurationByOrderId(@Parameter(description = "Id of the internal order", required = true)
                                                                                                   @PathVariable("id")
                                                                                                   String id);
}
