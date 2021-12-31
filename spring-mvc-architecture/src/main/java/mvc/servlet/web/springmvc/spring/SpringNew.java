package mvc.servlet.web.springmvc.spring;

import lombok.RequiredArgsConstructor;
import mvc.servlet.domain.member.Member;
import mvc.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/springmvc/v3/members")
public class SpringNew {


    private final MemberRepository memberRepository = MemberRepository.getInstance();


    @GetMapping("")
    public String members(Model model) {
        List<Member> members = memberRepository.findAll();

        model.addAttribute("members", members);

        return "members";
    }

    @PostMapping("/save")
    public String save(@RequestParam String username, @RequestParam int age, Model model) {

        Member member = new Member(username, age);
        memberRepository.save(member);

        model.addAttribute("member", member);

        return "save-result";
    }

    @GetMapping("/new-form")
    public String form() {
        return "new-form";
    }
}
