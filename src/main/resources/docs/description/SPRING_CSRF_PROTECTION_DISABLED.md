 Disabling Spring Security's CSRF protection is unsafe for standard web applications.

A valid use case for disabling this protection would be a service exposing state-changing operations that is guaranteed to be used only by non-browser clients.

**Insecure configuration:**

```
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }
}
```

**References**  
[Spring Security Official Documentation: When to use CSRF protection](https://docs.spring.io/spring-security/site/docs/current/reference/html/csrf.html#when-to-use-csrf-protection)  
[OWASP: Cross-Site Request Forgery](https://www.owasp.org/index.php/Cross-Site_Request_Forgery_%28CSRF%29)  
[OWASP: CSRF Prevention Cheat Sheet](https://www.owasp.org/index.php/Cross-Site_Request_Forgery_%28CSRF%29_Prevention_Cheat_Sheet)  
[CWE-352: Cross-Site Request Forgery (CSRF)](https://cwe.mitre.org/data/definitions/352.html)

