package com.green.fefu.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.green.fefu.chcommon.ResponsDataSet.*;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "관리자 승인", description = "관리자 관련 클래스")
public class AdminControllerImpl {
    private final AdminServiceImpl service;

    //    list Get
    @GetMapping("{p}")
    public ResponseEntity findUnAcceptList(@PathVariable int p) {
        Map map = new HashMap();
        log.info("{}",p);
        try {
            map = service.findUnAcceptList(p, map);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(map, OK);
    }


    //    반려
    @DeleteMapping
    public ResponseEntity deleteUser(@PathVariable int p) {
        Map map = new HashMap();
        try {
            map = service.deleteUser(p, map);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(map, OK);
    }


    //    승인
    @PutMapping
    public ResponseEntity acceptUser(@PathVariable int p) {
        Map map = new HashMap();
        try {
            map = service.acceptUser(p, map);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        return new ResponseEntity<>(map, OK);
    }

}
