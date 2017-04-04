 Methods annotated with `RequestMapping` are by default mapped to all the HTTP request methods. However, Spring Security's CSRF protection is not enabled by default for the HTTP request methods `GET`, `HEAD`, `TRACE`, and `OPTIONS`(as this could cause the tokens to be leaked). Therefore, state-changing methods annotated with `RequestMapping` and not narrowing the mapping to the HTTP request methods `POST`, `PUT`, `DELETE`, or `PATCH`are vulnerable to CSRF attacks.

**Vulnerable Code:**

```
@Controller
public class UnsafeController {

    @RequestMapping("/path")
    public void writeData() {
        // State-changing operations performed within this method.
    }
}
```

**Solution (Spring Framework 4.3 and later):**

```
@Controller
public class SafeController {

    /**
     * For methods without side-effects use @GetMapping.
     */
    @GetMapping("/path")
    public String readData() {
        // No state-changing operations performed within this method.
        return "";
    }

    /**
     * For state-changing methods use either @PostMapping, @PutMapping, @DeleteMapping, or @PatchMapping.
     */
    @PostMapping("/path")
    public void writeData() {
        // State-changing operations performed within this method.
    }
}
```

**Solution (Before Spring Framework 4.3):**

```
@Controller
public class SafeController {

    /**
     * For methods without side-effects use either
     * RequestMethod.GET, RequestMethod.HEAD, RequestMethod.TRACE, or RequestMethod.OPTIONS.
     */
    @RequestMapping(value = "/path", method = RequestMethod.GET)
    public String readData() {
        // No state-changing operations performed within this method.
        return "";
    }

    /**
     * For state-changing methods use either
     * RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, or RequestMethod.PATCH.
     */
    @RequestMapping(value = "/path", method = RequestMethod.POST)
    public void writeData() {
        // State-changing operations performed within this method.
    }
}
```

**References**  
[Spring Security Official Documentation: Use proper HTTP verbs (CSRF protection)](https://docs.spring.io/spring-security/site/docs/current/reference/html/csrf.html#csrf-use-proper-verbs)  
[OWASP: Cross-Site Request Forgery](https://www.owasp.org/index.php/Cross-Site_Request_Forgery_%28CSRF%29)  
[OWASP: CSRF Prevention Cheat Sheet](https://www.owasp.org/index.php/Cross-Site_Request_Forgery_%28CSRF%29_Prevention_Cheat_Sheet)  
[CWE-352: Cross-Site Request Forgery (CSRF)](https://cwe.mitre.org/data/definitions/352.html)

