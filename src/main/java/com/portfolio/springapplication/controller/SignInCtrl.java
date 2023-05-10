package com.portfolio.springapplication.controller;

import com.portfolio.springapplication.security.auth.UserPrincipalDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SignInCtrl {
    private boolean isAuthenticated(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())){
            return false;
        }
        return authentication.isAuthenticated();
    }

    @GetMapping("/user/signin/form")
    public String signIn(HttpServletRequest request, @AuthenticationPrincipal UserPrincipalDetail userPrincipalDetail){
        HttpSession session = request.getSession();
        String msg = (String) session.getAttribute("signinErrorMessage");
        session.setAttribute("signinErrorMessage", msg != null ? msg : "");

        if (isAuthenticated()){
            if (userPrincipalDetail == null){
                return "redirect:/user/signin/signout";
            }
            return "redirect:/user/main";
        }

        return "signin/signin";
    }

    @GetMapping("/user/main")
    public String main(){
        return "main/main";
    }

}
