 JNDI API support the binding of serialize object in LDAP directories. If certain attributes are presented, the deserialization of object will be made in the application querying the directory (See Black Hat USA 2016 white paper for details). Object deserialization should be threated as risky operation that can lead to remote code execution.

The exploitation of the vulnerability will be possible if the attacker has an entry point in an LDAP base query, by adding attributes to an existing LDAP entry or by configuring the application to use a malicious LDAP server.

**Vulnerable Code:**

```
DirContext ctx = new InitialDirContext();
//[...]

ctx.search(query, filter,
        new SearchControls(scope, countLimit, timeLimit, attributes,
            true, //Enable object deserialization if bound in directory
            deref));
```

**Solution:**

```
DirContext ctx = new InitialDirContext();
//[...]

ctx.search(query, filter,
        new SearchControls(scope, countLimit, timeLimit, attributes,
            false, //Disable
            deref));
```
  

**References**  
[Black Hat USA 2016: A Journey From JNDI/LDAP Manipulation to Remote Code Execution Dream Land](https://www.blackhat.com/docs/us-16/materials/us-16-Munoz-A-Journey-From-JNDI-LDAP-Manipulation-To-RCE-wp.pdf)([slides](https://www.blackhat.com/docs/us-16/materials/us-16-Munoz-A-Journey-From-JNDI-LDAP-Manipulation-To-RCE.pdf) & [video](https://www.youtube.com/watch?v=Y8a5nB-vy78)) by Alvaro Muñoz and Oleksandr Mirosh  
[HP Enterprise: Introducing JNDI Injection and LDAP Entry Poisoning](https://community.hpe.com/t5/Security-Research/Introducing-JNDI-Injection-and-LDAP-Entry-Poisoning/ba-p/6885118) by Alvaro Muñoz  
[TrendMicro: How The Pawn Storm Zero-Day Evaded Java's Click-to-Play Protection](http://blog.trendmicro.com/trendlabs-security-intelligence/new-headaches-how-the-pawn-storm-zero-day-evaded-javas-click-to-play-protection/) by Jack Tang

 