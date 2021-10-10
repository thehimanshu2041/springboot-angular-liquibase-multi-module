package com.trisul.controller.universal;

import com.trisul.core.constant.WebResource;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = WebResource.CTX_PATH + "/swagger")
@Hidden
public class SwaggerController {

  @GetMapping()
  public String swagger() {
    return "swagger-docs";
  }
}
