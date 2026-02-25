package com.manpibrook.backend_api.dto.request;

import lombok.Data;
import lombok.Getter;

@Data
public class LoginRequest {
	public String userName;
	public String password;
}
