 The application write data to external storage (potentially SD card). There are multiple security implication to this action. First, file store on SD card will be accessible to the application having the [`READ_EXTERNAL_STORAGE`](http://developer.android.com/reference/android/Manifest.permission.html#READ_EXTERNAL_STORAGE) permission. Also, if the data persisted contains confidential information about the user, encryption would be needed.

**Code at risk:**

```
file file = new File(getExternalFilesDir(TARGET_TYPE), filename);
fos = new FileOutputStream(file);
fos.write(confidentialData.getBytes());
fos.flush();
```

**Better alternative:**

```
fos = openFileOutput(filename, Context.MODE_PRIVATE);
fos.write(string.getBytes());
```
  

**References**  
[Android Official Doc: Security Tips](http://developer.android.com/training/articles/security-tips.html#ExternalStorage)  
[CERT: DRD00-J: Do not store sensitive information on external storage [...]](https://www.securecoding.cert.org/confluence/display/java/DRD00-J.+Do+not+store+sensitive+information+on+external+storage+%28SD+card%29+unless+encrypted+first)  
[Android Official Doc: Using the External Storage](http://developer.android.com/guide/topics/data/data-storage.html#filesExternal)  
[OWASP Mobile Top 10 2014-M2: Insecure Data Storage](https://www.owasp.org/index.php/Mobile_Top_10_2014-M2)  
[CWE-312: Cleartext Storage of Sensitive Information](https://cwe.mitre.org/data/definitions/312.html)

