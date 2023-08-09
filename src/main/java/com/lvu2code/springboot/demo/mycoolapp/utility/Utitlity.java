package com.lvu2code.springboot.demo.mycoolapp.utility;

import jakarta.servlet.http.HttpServletRequest;

public class Utitlity {

    public static String getSiteURL(HttpServletRequest request){
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
