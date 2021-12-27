package de.niko.pcstore.controller.api;


import de.niko.pcstore.configuration.Tags;
import de.niko.pcstore.dto.ErrorDTO;
import de.niko.pcstore.dto.GlobalVariableDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = Tags.GLOBAL_VARIABLE_API_TAG)
public interface GlobalVariableApi {
    String ADD_GLOBAL_VARIABLE = "/global-variable";
    String GET_CONSTANT = "/global-variable/{id}";
    String GET_GLOBAL_VARIABLES = "/global-variables";
    String UPDATE_GLOBAL_VARIABLE = "/global-variable";
    String DELETE_GLOBAL_VARIABLE = "/global-variable/{id}";

    @ApiOperation(value = "Add a new constant")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "item created"),
            @ApiResponse(code = 400, message = "invalid input, object invalid"),
            @ApiResponse(code = 409, message = "an existing item already exists")})
    @RequestMapping(value = ADD_GLOBAL_VARIABLE,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST)
    ResponseEntity<GlobalVariableDTO> addGlobalVariable(@ApiParam(value = "Source item to add") @RequestBody GlobalVariableDTO globalVariableDTO);

    @ApiOperation(value = "Get all values for dropdown-fields", response = GlobalVariableDTO.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "search results matching criteria", response = GlobalVariableDTO.class, responseContainer = "List")})
    @RequestMapping(value = GET_GLOBAL_VARIABLES,
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    ResponseEntity<List<GlobalVariableDTO>> getGlobalVariables(@ApiParam(value = "Type") @RequestParam(value = "type", required = false) List<String> types);

    //    @ApiOperation(value = "Get a one specific constant", response = ConstantDTO.class, responseContainer = "List")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "search results matching criteria", response = ConstantDTO.class, responseContainer = "List"),
//            @ApiResponse(code = 400, message = "bad input parameter")})
//    @RequestMapping(value = GET_CONSTANT,
//            produces = {MediaType.APPLICATION_JSON_VALUE},
//            method = RequestMethod.GET)
//    ResponseEntity<ConstantDTO> getConstant(@ApiParam(value = "pass the id of the constant you want to have", required = true) @PathVariable("id") String id);
//
    @ApiOperation(value = "Delete a constant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "item deleted"),
            @ApiResponse(code = 404, message = "an item is already deleted"),
            @ApiResponse(code = 409, message = "an item is not deletable")})
    @RequestMapping(value = DELETE_GLOBAL_VARIABLE,
            method = RequestMethod.DELETE)
    ResponseEntity<Object> deleteGlobalVariable(@ApiParam(value = "pass the id of the constant you want to delete", required = true) @PathVariable("id") String id);

    @ApiOperation(value = "Update a constant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "item updated"),
            @ApiResponse(code = 400, message = "invalid input, object invalid"),
            @ApiResponse(code = 500, message = "Error occurred while processing", response = ErrorDTO.class,
                    examples = @Example({
                            @ExampleProperty(mediaType = MediaType.APPLICATION_JSON_VALUE, value = "{\"code\": 1,\"message\": \"Optimistic locking failure\"}")
                    }))})
    @RequestMapping(value = UPDATE_GLOBAL_VARIABLE,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.PUT)
    ResponseEntity<Object> updateGlobalVariable(@ApiParam(value = "Source item to update") @RequestBody GlobalVariableDTO globalVariableDTO);
}
