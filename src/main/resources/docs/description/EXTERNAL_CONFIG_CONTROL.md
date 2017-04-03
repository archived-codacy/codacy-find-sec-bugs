 Allowing external control of system settings can disrupt service or cause an application to behave in unexpected, and potentially malicious ways. An attacker could cause an error by providing a nonexistent catalog name or connect to an unauthorized portion of the database.  

**Code at risk:**

```
conn.setCatalog(request.getParameter("catalog"));
```
  

**References**  
[CWE-15: External Control of System or Configuration Setting](http://cwe.mitre.org/data/definitions/15.html)

