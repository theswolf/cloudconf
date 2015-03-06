package core.september.cloudconf.authserver.filter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.september.cloudconf.authserver.conf.AppConfig;
import core.september.cloudconf.authserver.service.JWTService;
import org.springframework.http.HttpStatus;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 * Created by christian on 06/03/15.
 */
public class JWTFilter implements Filter {

    private final JWTService service;

    public JWTFilter(JWTService service) {
        this.service = service;
    }

    private final GsonBuilder builder = new GsonBuilder();
    private final Gson gson = builder.create();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String token = ((HttpServletRequest)request).getHeader(AppConfig.AUTH_HEADER);
            Map<String,Object> claim = service.claim(token);
            ((HttpServletResponse)response).setHeader(AppConfig.CLAIM,gson.toJson(claim));
            chain.doFilter(request,response);
        }
        catch (Exception e) {
            ((HttpServletResponse)response).setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @Override
    public void destroy() {

    }


}
