package com.mal.service.api;

import com.mal.service.service.MallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mall")
public class MallApi {
    @Autowired
    private MallService mallService;

    @GetMapping("/type/{type}")
    public List getMallsByType(@PathVariable String type) {
        if (type.equals("shoe")) {
            return mallService.getShoes();
        }
        return null;
    }

    @GetMapping("/{type}/{id}")
    public Object getMallDetail(@PathVariable String type, @PathVariable Integer id) {
        if (type.equals("shoe")) {
            return mallService.getShoeDetail(id);
        }
        return null;
    }
}
