package com.green.fefu.admin.test;

import com.green.fefu.admin.model.req.adminUserReq;

import java.util.Map;

public interface AdminService {
    Map findUnAcceptList(int p, Map map)throws Exception;
    void deleteUser(adminUserReq p) throws Exception;
    void acceptUser(adminUserReq p) throws Exception;


}
