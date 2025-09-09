package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginBodyModel {
    private String userName;
    private String password;
}
