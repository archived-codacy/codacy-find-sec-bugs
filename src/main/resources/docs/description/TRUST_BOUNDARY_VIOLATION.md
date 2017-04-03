 "A trust boundary can be thought of as line drawn through a program. On one side of the line, data is untrusted. On the other side of the line, data is assumed to be trustworthy. The purpose of validation logic is to allow data to safely cross the trust boundary - to move from untrusted to trusted. A trust boundary violation occurs when a program blurs the line between what is trusted and what is untrusted. By combining trusted and untrusted data in the same data structure, it becomes easier for programmers to mistakenly trust unvalidated data." [1]

**Code at risk:**

```
public void doSomething(HttpServletRequest req, String activateProperty) {
    //..

    req.getSession().setAttribute(activateProperty,"true");

}
```
  

```
public void loginEvent(HttpServletRequest req, String userSubmitted) {
    //..

    req.getSession().setAttribute("user",userSubmitted);
}
```

**Solution:**

The solution would be to add validation prior setting a new session attribute. When possible, prefer data from safe location rather than using direct user input.

  

**References**  
[1] [CWE-501: Trust Boundary Violation](https://cwe.mitre.org/data/definitions/501.html)  
[OWASP : Trust Boundary Violation](https://www.owasp.org/index.php/Trust_Boundary_Violation)

