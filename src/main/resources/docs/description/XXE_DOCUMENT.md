### Attack

XML External Entity (XXE) attacks can occur when an XML parser supports XML entities while processing XML received from an untrusted source.

**Risk 1: Expose local file content (XXE: XML eXternal Entity)**

```
<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE foo [
   <!ENTITY xxe SYSTEM "file:///etc/passwd" > ]>
<foo>&xxe;</foo>
```
**Risk 2: Denial of service (XEE: Xml Entity Expansion)**

```
<?xml version="1.0"?>
<!DOCTYPE lolz [
 <!ENTITY lol "lol">
 <!ELEMENT lolz (#PCDATA)>
 <!ENTITY lol1 "&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;">
 <!ENTITY lol2 "&lol1;&lol1;&lol1;&lol1;&lol1;&lol1;&lol1;&lol1;&lol1;&lol1;">
 <!ENTITY lol3 "&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;">
[...]
 <!ENTITY lol9 "&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;">
]>
<lolz>&lol9;</lolz>
```

### Solution

In order to avoid exposing dangerous feature of the XML parser, you can do the following change to the code.

**Vulnerable Code:**

```
DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

Document doc = db.parse(input);
```
  

The following snippets show two available solutions. You can set one feature or both.

**Solution using "Secure processing" mode:**

This setting will protect you against Denial of Service attack and remote file access.

```
DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
DocumentBuilder db = dbf.newDocumentBuilder();

Document doc = db.parse(input);
```

**Solution disabling DTD:**

By disabling DTD, almost all XXE attacks will be prevented.

```
DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
DocumentBuilder db = dbf.newDocumentBuilder();

Document doc = db.parse(input);
```
  

**References**  
[CWE-611: Improper Restriction of XML External Entity Reference ('XXE')](http://cwe.mitre.org/data/definitions/611.html)  
[CERT: IDS10-J. Prevent XML external entity attacks](https://www.securecoding.cert.org/confluence/pages/viewpage.action?pageId=61702260)  
[OWASP.org: XML External Entity (XXE) Processing](https://www.owasp.org/index.php/XML_External_Entity_%28XXE%29_Processing)  
[WS-Attacks.org: XML Entity Expansion](http://www.ws-attacks.org/index.php/XML_Entity_Expansion)  
[WS-Attacks.org: XML External Entity DOS](http://www.ws-attacks.org/index.php/XML_External_Entity_DOS)  
[WS-Attacks.org: XML Entity Reference Attack](http://www.ws-attacks.org/index.php/XML_Entity_Reference_Attack)  
[Identifying Xml eXternal Entity vulnerability (XXE)](http://blog.h3xstream.com/2014/06/identifying-xml-external-entity.html)  
[Xerces2 complete features list](http://xerces.apache.org/xerces2-j/features.html)

