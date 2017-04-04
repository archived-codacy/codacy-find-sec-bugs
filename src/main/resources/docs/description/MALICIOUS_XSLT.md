 "XSLT (Extensible Stylesheet Language Transformations) is a language for transforming XML documents into other XML documents".[1]  
 It is possible to attach malicious behavior to those stylesheets. Therefore, if an attacker can control the content or the source of the stylesheet, he might be able to trigger remote code execution.[2]

**Code at risk:**

```
Source xslt = new StreamSource(new FileInputStream(inputUserFile)); //Dangerous source to validate

Transformer transformer = TransformerFactory.newInstance().newTransformer(xslt);

Source text = new StreamSource(new FileInputStream("/data_2_process.xml"));
transformer.transform(text, new StreamResult(...));
```

**Solution:**

The solution would be to make sure the stylesheet is loaded from a safe sources and make sure that vulnerabilities such as Path traversal [3][4] are not possible.

**References**  
[1] [Wikipedia: XSLT (Extensible Stylesheet Language Transformations)](https://en.wikipedia.org/wiki/XSLT)  
[Offensive XSLT](https://prezi.com/y_fuybfudgnd/offensive-xslt/) by Nicolas Gregoire  
[2] [From XSLT code execution to Meterpreter shells](http://www.agarri.fr/kom/archives/2012/07/02/from_xslt_code_execution_to_meterpreter_shells/index.html) by Nicolas Gregoire  
[XSLT Hacking Encyclopedia](http://xhe.myxwiki.org/xwiki/bin/view/Main/) by Nicolas Gregoire  
[Acunetix.com : The hidden dangers of XSLTProcessor - Remote XSL injection](http://www.acunetix.com/blog/articles/the-hidden-dangers-of-xsltprocessor-remote-xsl-injection/)  
[w3.org XSL Transformations (XSLT) Version 1.0](https://www.w3.org/TR/xslt) : w3c specification  
[3] [WASC: Path Traversal](http://projects.webappsec.org/w/page/13246952/Path%20Traversal)  
[4] [OWASP: Path Traversal](https://www.owasp.org/index.php/Path_Traversal)

 