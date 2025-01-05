package com.blinker.atom.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class MainController {

    // 로그인 페이지를 반환
    @GetMapping("/main")
    public String main() {
        return "main"; // login.jsp를 렌더링
    }
    // 로그인 페이지를 반환
    @GetMapping("/auth/sign-in")
    public String loginPage() {
        return "login"; // login.jsp를 렌더링
    }

    // 회원가입 페이지를 반환
    @GetMapping("/auth/sign-up")
    public String signupPage() {
        return "signup"; // signup.jsp를 렌더링
    }
}
