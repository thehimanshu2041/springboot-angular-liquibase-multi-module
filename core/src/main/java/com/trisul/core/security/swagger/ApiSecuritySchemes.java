package com.trisul.core.security.swagger;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SecurityScheme(
    name = ApiSecuritySchemes.JWT_SECURITY_SCHEME,
    type = SecuritySchemeType.HTTP,
    bearerFormat = ApiSecuritySchemes.JWT_BEARER_FORMAT,
    scheme = ApiSecuritySchemes.EXPECTED_AUTH_SCHEME,
    description = ApiSecuritySchemes.EXPECTED_API_DESCRIPTION)
public @interface ApiSecuritySchemes {
  String JWT_SECURITY_SCHEME = "Authorization";
  String JWT_BEARER_FORMAT = "JWT";
  String EXPECTED_AUTH_SCHEME = "bearer";
  String EXPECTED_API_DESCRIPTION =
      "**The API operations may require HTTP Bearer authentication scheme with JWT format as defined in "
          + "Open API 3.0.**\nAn Authorization header must be present if the operation requires authorization, "
          + "\n\nfor example:\n```\nAuthorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkw"
          + "IiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c\n```\n";
}
