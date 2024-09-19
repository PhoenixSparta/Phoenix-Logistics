package com.phoenix.logistics.core.delivery.api.controller.v1;

import com.phoenix.logistics.core.delivery.api.controller.v1.request.ExampleRequest;
import com.phoenix.logistics.core.delivery.api.controller.v1.response.ExampleResponse;
import com.phoenix.logistics.core.delivery.api.support.response.ApiResponse;
import com.phoenix.logistics.core.domain.ExampleData;
import com.phoenix.logistics.core.domain.ExampleResult;
import com.phoenix.logistics.core.domain.ExampleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    private final ExampleService exampleExampleService;

    public ExampleController(ExampleService exampleExampleService) {
        this.exampleExampleService = exampleExampleService;
    }

    @GetMapping("/get/{exampleValue}")
    public ApiResponse<ExampleResponse> exampleGet(@PathVariable String exampleValue,
            @RequestParam String exampleParam) {
        ExampleResult result = exampleExampleService.processExample(new ExampleData(exampleValue, exampleParam));
        return ApiResponse.success(new ExampleResponse(result.data()));
    }

    @PostMapping("/post")
    public ApiResponse<ExampleResponse> examplePost(@RequestBody ExampleRequest request) {
        ExampleResult result = exampleExampleService.processExample(request.toExampleData());
        return ApiResponse.success(new ExampleResponse(result.data()));
    }

}
