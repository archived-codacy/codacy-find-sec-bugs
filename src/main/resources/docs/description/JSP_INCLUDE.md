 The inclusion of JSP file allow the entry of dynamic value. It may allow an attacker to control the JSP page included. If this is the case, an attacker will try to include a file on disk that he controls. By including arbitrary files, the attacker gets the ability to execute any code.

**Vulnerable Code:**

```
<jsp:include page="${param.secret_param}" />
```

**Solution:**

```
<c:if test="${param.secret_param == 'page1'}">
    <jsp:include page="page1.jsp" />
</c:if>
```
  

**References**  
[InfosecInstitute: File Inclusion Attacks](http://resources.infosecinstitute.com/file-inclusion-attacks/)  
[WASC-05: Remote File Inclusion](http://projects.webappsec.org/w/page/13246955/Remote%20File%20Inclusion)

 