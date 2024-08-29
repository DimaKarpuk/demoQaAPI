package models;

import lombok.Data;

@Data
public class LoginErrorResponseModel {
    String code, message;
}
