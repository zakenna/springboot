package ac.sw.mvcTest.controller;

import ac.sw.mvcTest.model.Member;
import ac.sw.mvcTest.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller //
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/") // (기본주소) http://localhost:8080 + 내App 주소("/")
    public String index() {
        return "index"; //index.html
    }

    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String pwd,
                           @RequestParam String age,
                           Model model // view 한테 data 넘겨주기 위한 객체
                           ) {
        System.out.println("name:"+name);
        System.out.println("email:"+email);
        System.out.println("pwd:"+pwd);
        System.out.println("age:"+age);

        //MemberService memberService = new MemberService();
        memberService.addMember(name,email,pwd,age);
        model.addAttribute("message", "사용자 등록 완료");

        return "index"; // index.html
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String pwd, Model model, HttpSession session) {
        Member member = memberService.findMember(email, pwd);
        if(member != null) { // 회원맞음
            session.setAttribute("login", member); // 로그인 성공한 member객체를 session에 저장
            model.addAttribute("user", member.getName());
        } else { // 회원아님

        }
        return "index";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
       return "loginForm";
    }

    @GetMapping("/registerForm")
    public String registerForm() {
        return "registerForm";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @GetMapping("/updateProfile")
    public String updateProfile(HttpSession session, Model model) {
        // 기존정보(DB)를 찾는다
        Member loginedMember = (Member) session.getAttribute("login");
        int id = loginedMember.getId();
        Member member = memberService.findMemberByID(id);

        // 기존정보를(member) Model를 사용하여 넘겨준다
        model.addAttribute("member", member);
        return "updateProfileForm"; // updateProfileForm.html
    }

    @PostMapping("/updateProfile")
    public String updateProfile(Member member) {
        memberService.updateMember(member);
        return "index";
    }

    @GetMapping("/memberList")
    public String memberList(Model model) {
        // DB로 부터 모든 회원정보를 가져온다.
       List<Member> list = memberService.getAllMember();

        // 이것을 memberList.html에게 Model을 사용하여 넘겨준다.
        model.addAttribute("list", list);
        return "memberList";
    }

    @GetMapping("/del/{id}")
    public String deleteMember(@PathVariable("id") int id, Model model) {
        memberService.deleteMember(id); // 회원삭제.

//        List<Member> list = memberService.getAllMember();
//        model.addAttribute("list", list);
//        return "memberList";
        return "redirect:/memberList";
    }

}
