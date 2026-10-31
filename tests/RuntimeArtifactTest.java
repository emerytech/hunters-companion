package com.temery.hunterscompanion;
import java.nio.file.*;
public final class RuntimeArtifactTest {
 interface Check {void run()throws Exception;}
 static void reject(Check check)throws Exception{try{check.run();}catch(java.io.IOException expected){return;}throw new AssertionError("Unsafe artifact/URL accepted");}
 public static void main(String[] args)throws Exception{
  Path f=Files.createTempFile("runtime-fixture",".apk");try{
   Files.write(f,"abc".getBytes("UTF-8"));String hash="ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad";
   RuntimeArtifact.verify(f.toFile(),3,hash);
   reject(()->RuntimeArtifact.verify(f.toFile(),4,hash));
   reject(()->RuntimeArtifact.verify(f.toFile(),2,hash));
   reject(()->RuntimeArtifact.verify(f.toFile(),3,RuntimeArtifact.SHA256));
   Files.write(f,"abd".getBytes("UTF-8"));reject(()->RuntimeArtifact.verify(f.toFile(),3,hash));
   RuntimeArtifact.allowedUrl(RuntimeArtifact.URL);RuntimeArtifact.allowedUrl("https://release-assets.githubusercontent.com/file");
   reject(()->RuntimeArtifact.allowedUrl("http://github.com/file"));
   reject(()->RuntimeArtifact.allowedUrl("https://github.com.attacker.invalid/file"));
   reject(()->RuntimeArtifact.allowedUrl("https://github.com@attacker.invalid/file"));
   reject(()->RuntimeArtifact.allowedUrl("https://github.com:444/file"));
   reject(()->RuntimeArtifact.allowedUrl("https://user:pass@github.com/file"));
   System.out.println("Artifact integrity and redirect boundary tests passed.");
  }finally{Files.deleteIfExists(f);}
 }
}
