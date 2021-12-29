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
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(tags = Tags.INTERNAL_ORDER_API_TAG)
public interface InternalOrderApi {
    String GET_INTERNAL_ORDER_LIST = "/internal-order-list";

    String GET_INTERNAL_ORDER = "/internal-order/{id}";
    String ADD_INTERNAL_ORDER = "/internal-order";
//    String UPDATE_PERSONAL_COMPUTER = "/personal-computer";
//    String DELETE_PERSONAL_COMPUTER = "/personal-computer/{id}";


    @ApiOperation(value = "Get all internal orders", response = InternalOrderShortDTO.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All internal orders", response = InternalOrderShortDTO.class, responseContainer = "List")})
    @RequestMapping(value = GET_INTERNAL_ORDER_LIST,
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    ResponseEntity<List<InternalOrderShortDTO>> getAllInternalOrderList();

    @ApiOperation(value = "Get one specific internal order", response = InternalOrderDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InternalOrderDTO.class),
            @ApiResponse(code = 400, message = "Bad input parameter"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)
    })
    @RequestMapping(value = GET_INTERNAL_ORDER,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    ResponseEntity<InternalOrderDTO> getInternalOrder(@ApiParam(value = "internal order id", required = true) @PathVariable("id") String id);

    @ApiOperation(value = "Add a new personal computer", response = InternalOrderDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad input parameter"),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
    @RequestMapping(value = ADD_INTERNAL_ORDER,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST)
    ResponseEntity<InternalOrderDTO> addInternalOrder(@ApiParam(value = "Object to add") @RequestBody NewInternalOrderDTO internalOrderDTO);
}
