 Without proper access control, executing an LDAP statement that contains a user-controlled value can allow an attacker to abuse poorly configured LDAP environment. All LDAP queries executed against ctx will be performed without authentication and access control. An attacker may be able to manipulate one of these queries in an unexpected way to gain access to records that would otherwise be protected by the directory's access control mechanism.

**Vulnerable Code:**

```
...
env.put(Context.SECURITY_AUTHENTICATION, "none");
DirContext ctx = new InitialDirContext(env);
...
```

**Solution:**  
Consider other modes of authentication to LDAP and ensure proper access control mechanism.

  

**References**  
[Ldap Authentication Mechanisms](https://docs.oracle.com/javase/tutorial/jndi/ldap/auth_mechs.html)

 