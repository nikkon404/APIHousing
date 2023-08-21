// package com.conestoga.APIHousing.configs;

// import com.conestoga.APIHousing.utils.JwtUtil;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.util.StringUtils;
// import org.springframework.web.filter.OncePerRequestFilter;

// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.util.Enumeration;

// public class JwtAuthorizationFilter extends OncePerRequestFilter {

//     private final JwtUtil jwtUtil;


//     @Autowired
//     public JwtAuthorizationFilter(JwtUtil jwtUtil) {
//         this.jwtUtil = jwtUtil;
//     }
// @Override
// protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    
//      String requestURI = request.getRequestURI();

//      //list of all the urls that do not need to be authenticated
//         String[] allowedUrls = {
//         "/accounts/login",
//          "/accounts/register", 
//          "/accounts/reset-password",
//          "/send-pin", 
//          "/validate", 
//          "/uploads",
//          "/chat"
//         };

//     // Allow access to the allowed urls
//     for (String url : allowedUrls) {
//         if (requestURI.contains(url)) {
//             //proceed to the next filter in the chain
//             chain.doFilter(request, response);
//             return;
//         }
//     }

//     String header = request.getHeader("Authorization");

//     if (StringUtils.isEmpty(header) || !header.startsWith("Bearer ")) {
     
        
//         // If there is no token or token is invalid, clear the context and return a 401 Unauthorized response
//         SecurityContextHolder.clearContext();
//         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//         return;
//     }

//     // Extract the token from the Authorization header
//     String token = header.replace("Bearer ", "");

//     // Validate and authenticate the token
//     if (!jwtUtil.validateToken(token)) {
//         // If the token is invalid, clear the context and return a 401 Unauthorized response
//         SecurityContextHolder.clearContext();
//         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//         return;
//     }

//     // If the token is valid, set the authenticated authentication object in the SecurityContextHolder
//     // (You might have your own authentication implementation based on the token)
//     // Authentication authentication = getAuthentication(token);
//     // SecurityContextHolder.getContext().setAuthentication(authentication);

//     chain.doFilter(request, response);
// }
   
// }
