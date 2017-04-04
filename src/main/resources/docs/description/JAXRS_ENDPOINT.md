 This method is part of a REST Web Service (JSR311).

**The security of this web service should be analyzed. For example:**

- Authentication, if enforced, should be tested.
- Access control, if enforced, should be tested.
- The inputs should be tracked for potential vulnerabilities.
- The communication should ideally be over SSL.
- If the service supports writes (e.g., via POST), its vulnerability to CSRF should be investigated.[1]
  

**References**  
[OWASP: REST Assessment Cheat Sheet](https://www.owasp.org/index.php/REST_Assessment_Cheat_Sheet)  
[OWASP: REST Security Cheat Sheet](https://www.owasp.org/index.php/REST_Security_Cheat_Sheet)  
[OWASP: Web Service Security Cheat Sheet](https://www.owasp.org/index.php/Web_Service_Security_Cheat_Sheet)  
1. [OWASP: Cross-Site Request Forgery](https://www.owasp.org/index.php/Cross-Site_Request_Forgery_(CSRF))  
[OWASP: CSRF Prevention Cheat Sheet](https://www.owasp.org/index.php/Cross-Site_Request_Forgery_%28CSRF%29_Prevention_Cheat_Sheet)  
[CWE-20: Improper Input Validation](http://cwe.mitre.org/data/definitions/20.html)

