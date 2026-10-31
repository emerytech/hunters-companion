package com.temery.hunterscompanion;

import java.io.*;
import java.net.*;
import java.security.*;

/** Pinned upstream artifact; updates require an explicit reviewed change. */
public final class RuntimeArtifact {
 public static final String PACKAGE = "com.ducvd89.bloodborne";
 public static final String VERSION = "2.2.0";
 public static final long VERSION_CODE = 40;
 public static final long SIZE = 62889966L;
 public static final String SHA256 = "e97bed80872e2552ae175d9e1189def61c9a9f086bf64fd7d065602340a6ebc0";
 public static final String CERT_SHA256 = "baec6fac22c73501e4d0c4cf965fb257eb6bcbfb3ae05ffa2f23918dc49d86b2";
 public static final String URL = "https://github.com/ducvd89/Bloodborne-Android/releases/download/android-2.2.0/Bloodborne-2.2.0.apk";
 public static String digest(byte[] bytes) throws Exception {
  byte[] digest=MessageDigest.getInstance("SHA-256").digest(bytes);
  StringBuilder s=new StringBuilder(); for(byte b:digest)s.append(String.format("%02x",b&255)); return s.toString();
 }
 public static void verify(File f,long size,String expected)throws Exception {
  if(f.length()!=size)throw new IOException("Installer size does not match the upstream release.");
  MessageDigest md=MessageDigest.getInstance("SHA-256");
  try(InputStream in=new FileInputStream(f)){byte[] buffer=new byte[65536];int n;while((n=in.read(buffer))!=-1)md.update(buffer,0,n);}
  StringBuilder hex=new StringBuilder();for(byte b:md.digest())hex.append(String.format("%02x",b&255));
  if(!hex.toString().equals(expected))throw new IOException("Installer checksum mismatch. Nothing was installed.");
 }
 public static URL allowedUrl(String address)throws Exception {
  URL u=new URL(address);String h=u.getHost();
  if(!"https".equals(u.getProtocol()) || (u.getPort()!=-1&&u.getPort()!=443) || u.getUserInfo()!=null || !(h.equals("github.com")||h.equals("release-assets.githubusercontent.com")||h.equals("objects.githubusercontent.com")))throw new IOException("Unexpected installer download destination.");
  return u;
 }
 private RuntimeArtifact(){}
}
