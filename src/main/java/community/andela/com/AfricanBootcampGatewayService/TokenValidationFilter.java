package community.andela.com.AfricanBootcampGatewayService;

import community.andela.com.AfricanBootcampGatewayService.accessibility.repository.UserRepositoryI;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class TokenValidationFilter implements Filter {

    @Autowired
    UserRepositoryI userRepository;

    /**
     * Called by the web container to indicate to a filter that it is being
     * placed into service. The servlet container calls the init method exactly
     * once after instantiating the filter. The init method must complete
     * successfully before the filter is asked to do any filtering work.
     * <p>
     * The web container cannot place the filter into service if the init method
     * either:
     * <ul>
     * <li>Throws a ServletException</li>
     * <li>Does not return within a time period defined by the web
     *     container</li>
     * </ul>
     * The default implementation is a NO-OP.
     *
     * @param filterConfig The configuration information associated with the
     *                     filter instance being initialised
     * @throws ServletException if the initialisation fails
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    /**
     * The <code>doFilter</code> method of the Filter is called by the container
     * each time a request/response pair is passed through the chain due to a
     * client request for a resource at the end of the chain. The FilterChain
     * passed in to this method allows the Filter to pass on the request and
     * response to the next entity in the chain.
     * <p>
     * A typical implementation of this method would follow the following
     * pattern:- <br>
     * 1. Examine the request<br>
     * 2. Optionally wrap the request object with a custom implementation to
     * filter content or headers for input filtering <br>
     * 3. Optionally wrap the response object with a custom implementation to
     * filter content or headers for output filtering <br>
     * 4. a) <strong>Either</strong> invoke the next entity in the chain using
     * the FilterChain object (<code>chain.doFilter()</code>), <br>
     * 4. b) <strong>or</strong> not pass on the request/response pair to the
     * next entity in the filter chain to block the request processing<br>
     * 5. Directly set headers on the response after invocation of the next
     * entity in the filter chain.
     *
     * @param request  The request to process
     * @param response The response associated with the request
     * @param chain    Provides access to the next filter in the chain for this
     *                 filter to pass the request and response to for further
     *                 processing
     * @throws IOException      if an I/O error occurs during this filter's
     *                          processing of the request
     * @throws ServletException if the processing fails for any other reason
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("-->Validating Token--->");
        var httpServletResponse = (HttpServletResponse) response;
        var httpServletRequest = (HttpServletRequest) request;

        var token = httpServletRequest.getHeader("Token");
        var authorization = httpServletRequest.getHeader("Authorization");

        if(authorization != null && authorization.startsWith("Basic")){ // The authorization header uses Basic and not Bearer
            var decodedCredentials = new String(Base64.getDecoder().decode(authorization.split(" ")[1]));
            var username = decodedCredentials.split(":")[0];
            System.out.println("-->Username: " + username + " --->");

            if(token == null){ //The request does not contain Token header
                System.out.println("-->Token is null --->");
                httpServletResponse.sendError(401,"Request fail to contain token header");
            }

            if(token.equals("")){ //The request contain empty token header
                System.out.println("-->Token is empty --->");
                httpServletResponse.sendError(401,"Token header contains no value");
            }

            if(validateToken(token,username)){ //The token header value passes validation
                chain.doFilter(request,response);
            }else {
                httpServletResponse.sendError(401,"Token validation fail");
            }

        }else{
            httpServletResponse.sendError(401,"Authentication header required, if present then you are getting this error because its value does "+
                    "not start with Basic");

        }
    }

    /**
     * Called by the web container to indicate to a filter that it is being
     * taken out of service. This method is only called once all threads within
     * the filter's doFilter method have exited or after a timeout period has
     * passed. After the web container calls this method, it will not call the
     * doFilter method again on this instance of the filter. <br>
     * <br>
     * <p>
     * This method gives the filter an opportunity to clean up any resources
     * that are being held (for example, memory, file handles, threads) and make
     * sure that any persistent state is synchronized with the filter's current
     * state in memory.
     * <p>
     * The default implementation is a NO-OP.
     */
    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private boolean validateToken(String token, String username){
        Key key = Global.generateKey("asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4");
        var user = userRepository.findUserByUserName(username);
        var claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        var claimed_username = claims.get("username", String.class);
        var claimed_firstname = claims.get("firstname", String.class);
        var claimed_lastname = claims.get("lastname", String.class);
        var claimed_password = claims.get("password", String.class);
        var claimed_role = claims.get("role", String.class);

        return claimed_firstname.equals(user.getFirstName())
                && claimed_role.equals("ROLE_" + user.getRole().name())
                && claimed_lastname.equals(user.getLastName())
                && claimed_username.equals(user.getUserName())
                && claimed_password.equals(user.getPassword())
                && claims.getExpiration().toInstant().compareTo(Date.from(Instant.now()).toInstant()) < 0;
    }
}
