package uz.pdp.govqueue.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.govqueue.model.GovService;
import uz.pdp.govqueue.service.ModelMapperTest;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final ModelMapperTest modelMapperTest;

    @GetMapping
    public HttpEntity<GovService> mapGovService() {
        return ResponseEntity.ok(modelMapperTest.mapWithModelMapper());
    }
}
