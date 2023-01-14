package de.niko.pcstore.configuration;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class HttpErrorController implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<Object> error(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        log.error("HttpErrorController.error: " + status);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

//            if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
//                return new ResponseEntity<>("<b>401 UNAUTHORIZED ... Sowwy :(</b>", HttpStatus.FORBIDDEN);
//            }
//            else if (statusCode == HttpStatus.FORBIDDEN.value()) {
//                return new ResponseEntity<>("<b>403 FORBIDDEN ... Sowwy :(</b>", HttpStatus.FORBIDDEN);
//            }
//            else if (statusCode == HttpStatus.NOT_FOUND.value()) {
//                return new ResponseEntity<>("<b>404 NOT FOUND ... Sowwy :(</b>", HttpStatus.NOT_FOUND);
//            }
//            else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//                return new ResponseEntity<>("<b>500 INTERNAL SERVER ERROR ... Sowwy :(((</b>", HttpStatus.INTERNAL_SERVER_ERROR);
//            }
        }

        return ResponseEntity.internalServerError().build();
    }
}