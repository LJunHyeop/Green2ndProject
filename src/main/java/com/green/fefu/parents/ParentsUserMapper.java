package com.green.fefu.parents;

import com.green.fefu.parents.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ParentsUserMapper {
    // 회원가입
    int postParentsUser(PostParentsUserReq p);
    // 아이디, 이메일 중복 체크
    CheckEmailOrUidRes checkEmailOrUid(CheckEmailOrUidReq c);
    // 회원정보 확인
    ParentsUserEntity getParentsUser(GetParentsUserReq parentsId);
    // 회원 정보 수정
    int patchParentsUser(PatchParentsUserReq p);
    // 아이디 찾기
    GetFindIdRes getFindId(GetFindIdReq req);
    // 비밀번호 수정
    int patchPassword(PatchPasswordReq req);
    // 로그인 전 비밀번호 수정 조회
    List<ParentsUserEntity> selPasswordBeforeLogin(String uid);
    // 로그인
    ParentsUser signInPost(String uid);
    // 회원 찾기 ( 비밀번호 찾기 )
    List<ParentsUserEntity> getParentUserList(GetFindPasswordReq req);
    // 전자서명
    int signature(SignatureReq req) ;
    // 자기 자녀 정보조회
    List<GetStudentParentsRes> getStudentParents(long parentsPk) ;
    // 전자서명 조회
    GetSignatureRes getSignature(GetSignatureReq req) ;
    // 전자서명 삭제
    int delSignature(GetSignatureReq req) ;


    // TDD select
    List<ParentsUser> selTest(long signedId);
    // 학생정보 수정 ( 학부모 pk )
    int updStudent(UpdateStudentParentsIdReq p);
}
