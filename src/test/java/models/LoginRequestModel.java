package models;

import lombok.Data;
@Data
public class LoginRequestModel {

   private String password,
            userName;
}
