package com.example.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.YammerAppConfig;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("yammer/group")
@Api(value = "yammer/group")
public class GroupController {
	
    @ApiOperation(value = "Get Group Messages")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved host general settings"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, dataType = "String", paramType = "header")
    @GetMapping("/message/{groupId}")
    public String getGroupMsg(HttpServletRequest request,
    		@PathVariable("groupId") String groupId) throws Exception {
    	
    	String accessToken = request.getHeader("Authorization");

    	HttpResponse<JsonNode> response = null;
    	try{
			response = Unirest
					.get(YammerAppConfig.yammerBaseUrl + "messages/in_group/" + groupId + ".json")
					.header("accept", "application/json").header("Authorization", "Bearer " + accessToken).asJson();

		} catch (UnirestException e) {
			throw new Exception("Exception occurred while processing request" + e.getMessage());
		}

    	if(response.getStatus() == 200){
    		return response.getBody().toString();
    	}
    	
    	throw new Exception("Exception while processing the request " + response.getStatus());
    }


    @ApiOperation(value = "Get All Group")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved host general settings"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, dataType = "String", paramType = "header")
    @GetMapping()
    public String getAllGroup(HttpServletRequest request) throws Exception{
    	
    	String accessToken = request.getHeader("Authorization");
    	
		HttpResponse<JsonNode> response;
		try {
			response = Unirest
					.get(YammerAppConfig.yammerBaseUrl + "groups.json")
					.header("accept", "application/json").header("Authorization", "Bearer " + accessToken).asJson();
		} catch (UnirestException e) {
			throw new Exception("Exception occurred while processing request" + e.getMessage());
		}

    	if(response.getStatus() == 200){
    		return response.getBody().toString();
    	}
    	
    	throw new Exception("Exception while processing the request " + response.getStatus());
    }
    
}