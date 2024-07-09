package com.green.fefu.admin.test;

import com.green.fefu.admin.model.req.adminUserReq;
import org.springframework.http.ResponseEntity;

public interface AdminController {
    ResponseEntity findUnAcceptList(int p);
    ResponseEntity deleteUser(adminUserReq p);
    ResponseEntity acceptUser(adminUserReq p);
}
