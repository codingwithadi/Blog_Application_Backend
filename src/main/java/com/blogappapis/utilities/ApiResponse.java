package com.blogappapis.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
	
	//In this Class we add responseMessage and responseResult.
	
	private String responseMessage;
	private Boolean responseResult;
	
	

}
