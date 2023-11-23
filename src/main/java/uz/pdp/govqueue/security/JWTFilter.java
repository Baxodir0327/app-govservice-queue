package uz.pdp.govqueue.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.govqueue.model.User;
import uz.pdp.govqueue.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {
            authorization = authorization.substring(7);
            String userId = jwtProvider.getSubjectFromToken(authorization);
            if (userId != null) {
                Optional<User> optionalUser = userRepository.findById(Integer.valueOf(userId));
                optionalUser.ifPresent(user -> {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    new ArrayList<>());
                    WebAuthenticationDetails details = new WebAuthenticationDetails(request);
                    authenticationToken.setDetails(details);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                });
            }
        }

        filterChain.doFilter(request, response);
    }
}
