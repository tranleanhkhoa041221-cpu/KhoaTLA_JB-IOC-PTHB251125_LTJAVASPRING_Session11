package ra.edu.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.UUID;

@Component
@Slf4j
public class RequestIdFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) {

        try {
            MDC.put("requestId", UUID.randomUUID().toString());

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("Filter error", e);
        } finally {
            log.info("END REQUEST");
            MDC.clear();
        }
    }
}
