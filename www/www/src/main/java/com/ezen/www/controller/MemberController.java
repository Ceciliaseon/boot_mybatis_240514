package com.ezen.www.controller;

import com.ezen.www.domain.MemberVO;
import com.ezen.www.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member/*")
public class MemberController {

    private final MemberService msv;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public void join(){}

    @PostMapping("/register")
    public String register(MemberVO mvo){
        log.info(">> mvo >> {}", mvo);
        mvo.setPwd(passwordEncoder.encode(mvo.getPwd()));
        int isOk = msv.insert(mvo);
        return "/index";
    }
    @GetMapping("/login")
    public void login(){}

    @PostMapping("/login")
    public String loginPost(HttpServletRequest request, RedirectAttributes re, String err){
        log.info(">> errMsg Controller >> {}", err);
        log.info(">> errMsg Controller >> {}", request.getAttribute("errMsg").toString());
//        re.addAttribute("email", request.getAttribute("email"));
//        re.addAttribute("errMsg", request.getAttribute("errMsg"));
        re.addAttribute("errMsg", err);
        return "redirect:/member/login";
    }

    @GetMapping("/list")
    public String userList(Model m){
        List<MemberVO> memberList = msv.getList();
        log.info("memberList >> {}", memberList);
        m.addAttribute("memberList", memberList);
        return "/member/list";
    }

    @GetMapping("/modify")
    public void modify(){}

    @PostMapping("/modify")
    public String modify(MemberVO mvo, HttpServletRequest request, HttpServletResponse response){
        if(mvo.getEmail().isEmpty() || mvo.getPwd().length() == 0){
            msv.nameUpdate(mvo);
        } else{
            mvo.setPwd(passwordEncoder.encode(mvo.getPwd()));
            msv.updateAll(mvo);
        }
        return "redirect:/member/logout";
    }

    @GetMapping("/remove")
    public String remove(Principal principal){
        msv.remove(principal.getName());
        return "redirect:/member/logout";
    }
}
