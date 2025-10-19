package ac.sw.mvcTest.service;

import ac.sw.mvcTest.model.Member;
import ac.sw.mvcTest.repository.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // DI위해서 ...
public class MemberService {
    @Autowired
    MemberDAO memberDAO;

    public void addMember(String name, String email, String pwd, String age) {
        int intAge = Integer.parseInt(age);
        memberDAO.addMember(name,email,pwd, intAge);
    }

    public Member findMember(String email, String pwd) {
        Member member = memberDAO.findMember(email, pwd);
        return member;
    }

    public Member findMemberByID(int id) {
//        Member member = memberDAO.findMemberById(id);
//        return member;
        return memberDAO.findMemberById(id);
    }

    public void updateMember(Member member) {
        memberDAO.updateMember(member);
    }

    public List<Member> getAllMember() {
//        List<Member> list = memberDAO.getAllMember();
//        return list;
        return memberDAO.getAllMember();
    }

    public void deleteMember(int id) {
        memberDAO.deleteMember(id);
    }
}
