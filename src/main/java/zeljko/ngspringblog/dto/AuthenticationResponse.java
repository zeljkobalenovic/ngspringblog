package zeljko.ngspringblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AuthenticationResponse
 */

 @Data
 @AllArgsConstructor
 public class AuthenticationResponse {

    private String authenticationToken;
    private String username;
}