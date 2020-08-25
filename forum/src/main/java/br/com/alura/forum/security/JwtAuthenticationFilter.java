package br.com.alura.forum.security;

import br.com.alura.forum.model.User;
import br.com.alura.forum.service.TokenService;
import br.com.alura.forum.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UserService userService;

    public JwtAuthenticationFilter(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1OTgwNjEyNTMsInN1YiI6IjQiLCJpYXQiOjE1OTgwNjAzNTN9.tNedknXV84g4AFxebuZENjKgE89EkuSCX9-qxzdHh1E

        String possibleToken = getTokenFromRequest(request);

        if (tokenService.validateToken(possibleToken)) {
            Long userIdFromToken = tokenService.getUserIdFromToken(possibleToken);
            User user = userService.loadById(userIdFromToken);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }


        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String tokenPrefix = "Bearer ";
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith(tokenPrefix)) {
            String jwtToken = header.substring(tokenPrefix.length(), header.length());
            return jwtToken;
        }
        return null;
    }
}
