package de.niko.pcstore.controller.api;


import de.niko.pcstore.configuration.Tags;
import de.niko.pcstore.dto.ErrorDTO;
import de.niko.pcstore.dto.InternalOrderDTO;
import de.niko.pcstore.dto.InternalOrderShortDTO;
import de.niko.pcstore.dto.NewInternalOrderDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = Tags.INTERNAL_ORDER_API_TAG)
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

    @ApiOperation(value = "Delete a internal order")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "item deleted"),
            @ApiResponse(code = 404, message = "an item is already deleted"),
            @ApiResponse(code = 409, message = "an item is not deletable")})
    @RequestMapping(value = DELETE_INTERNAL_ORDER,
            method = RequestMethod.DELETE)
    ResponseEntity<Object> deleteGlobalVariable(@ApiParam(value = "pass the id of the internal order you want to delete", required = true) @PathVariable("id") String id);

    @ApiOperation(value = "Get all internal orders", response = InternalOrderShortDTO.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All internal orders", response = InternalOrderShortDTO.class, responseContainer = "List")})
    @RequestMapping(value = GET_INTERNAL_ORDER_LIST,
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    ResponseEntity<List<InternalOrderShortDTO>> getInternalOrderList(@RequestParam(required = false) List<InternalOrderDTO.Status> statuses);

    @ApiOperation(value = "Get one specific internal order", response = InternalOrderDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InternalOrderDTO.class),
            @ApiResponse(code = 400, message = "Bad input parameter"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)
    })
    @RequestMapping(value = GET_INTERNAL_ORDER,
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    ResponseEntity<InternalOrderDTO> getInternalOrder(@ApiParam(value = "internal order id", required = true) @PathVariable("id") String id);

    @ApiOperation(value = "Add a new internal order", response = InternalOrderDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad input parameter"),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @RequestMapping(value = ADD_INTERNAL_ORDER,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST)
    ResponseEntity<InternalOrderDTO> addInternalOrder(@ApiParam(value = "Object to add") @RequestBody NewInternalOrderDTO internalOrderDTO);

    @ApiOperation(value = "Update status of internal order", response = Object.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad input parameter"),
            @ApiResponse(code = 304, message = "Status already set"),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @RequestMapping(value = UPDATE_INTERNAL_ORDER_STATUS,
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    ResponseEntity<Object> updateInternalOrderStatus(@RequestParam("id") String internalOrderId, @RequestParam InternalOrderDTO.Status status);
}
