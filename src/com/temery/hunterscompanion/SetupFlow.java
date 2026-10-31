package com.temery.hunterscompanion;

import android.app.*;import android.content.*;import android.content.pm.*;import android.net.Uri;import android.provider.Settings;import android.widget.*;import android.os.*;import java.io.*;import java.net.*;import java.util.concurrent.atomic.AtomicBoolean;

/** User-driven setup, no silent installation and no game-folder writes. */
final class SetupFlow {
 private final MainActivity activity;
 private boolean busy;
 private AtomicBoolean cancelled=new AtomicBoolean();
 SetupFlow(MainActivity a){activity=a;}
 private File apk(){return new File(activity.getFilesDir(),"runtime.apk");}
 private PackageInfo installed(){try{return activity.getPackageManager().getPackageInfo(RuntimeArtifact.PACKAGE,0);}catch(PackageManager.NameNotFoundException e){return null;}}
 void show(){
  PackageInfo installed=installed();String runtime=installed==null?"Not installed":"Installed: "+installed.versionName;
  String folder=activity.prefs.getBoolean("valid",false)?"Companion folder selected":"Choose your extracted game folder";
  String msg="1 · BLOODBORNE ANDROID\n"+runtime+"\nTested upstream release: 2.2.0 (experimental beta).\n\n2 · YOUR GAME FILES\n"+folder+"\n\n3 · GAME PREPARATION\nInside Bloodborne, select the same folder and complete its native-code setup. The companion cannot verify that step.\n\n4 · LAUNCH BOTH\nThe companion stays below; the game opens above. No game files are included.\n\nDownloads go directly to GitHub and its asset servers (about 63 MB). No files are uploaded. The upstream APK is downloaded unchanged, checked against a pinned SHA-256 and signing certificate, and installed only with your confirmation.";
  String[] options={"Download verified installer","Choose game folder","Open game setup on top","Launch both screens","Upstream instructions"};
  new AlertDialog.Builder(activity).setTitle("Bloodborne + companion setup").setMessage(msg).setPositiveButton("Choose step",(d,w)->new AlertDialog.Builder(activity).setTitle("Setup steps").setItems(options,(dialog,n)->{if(n==0)download();if(n==1)activity.chooseFolder();if(n==2)activity.openRuntime();if(n==3)activity.launchGame();if(n==4)activity.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://github.com/ducvd89/Bloodborne-Android#how-to-get-the-game-running")));}).show()).setNegativeButton("Close",null).show();
 }
 void cancel(){cancelled.set(true);}
 private void checkPackage(File f)throws Exception {
  PackageInfo info=activity.getPackageManager().getPackageArchiveInfo(f.getPath(),PackageManager.GET_SIGNING_CERTIFICATES);
  if(info==null||!RuntimeArtifact.PACKAGE.equals(info.packageName)||info.getLongVersionCode()!=RuntimeArtifact.VERSION_CODE||info.signingInfo==null)throw new IOException("Unexpected installer package or version.");
  Signature[] signatures=info.signingInfo.getApkContentsSigners();
  if(signatures.length!=1||!RuntimeArtifact.CERT_SHA256.equals(RuntimeArtifact.digest(signatures[0].toByteArray())))throw new IOException("Unexpected installer signing certificate.");
 }
 private HttpURLConnection connection(String address)throws Exception {
  for(int n=0;n<6;n++){
   HttpURLConnection c=(HttpURLConnection)RuntimeArtifact.allowedUrl(address).openConnection();c.setInstanceFollowRedirects(false);c.setConnectTimeout(15000);c.setReadTimeout(15000);c.setRequestProperty("User-Agent","HuntersCompanion/0.3.0");int code=c.getResponseCode();
   if(code==200)return c;
   if(code==301||code==302||code==303||code==307||code==308){String next=c.getHeaderField("Location");c.disconnect();address=new URL(new URL(address),next).toString();continue;}
   c.disconnect();throw new IOException("Download failed (HTTP "+code+"). Try again later.");
  }throw new IOException("Too many download redirects.");
 }
 private void download(){
  if(busy){Toast.makeText(activity,"Installer check already running",Toast.LENGTH_SHORT).show();return;}
  busy=true;cancelled=new AtomicBoolean();
  ProgressDialog dialog=new ProgressDialog(activity);dialog.setTitle("Verified upstream installer");dialog.setMessage("Checking download…");dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);dialog.setMax(100);dialog.setCancelable(false);dialog.setButton(ProgressDialog.BUTTON_NEGATIVE,"Cancel",(d,w)->cancel());dialog.show();
  new Thread(()->{File part=new File(activity.getFilesDir(),"runtime.apk.part");HttpURLConnection c=null;
   try{
    boolean cached=false;try{RuntimeArtifact.verify(apk(),RuntimeArtifact.SIZE,RuntimeArtifact.SHA256);checkPackage(apk());cached=true;}catch(Exception ignored){}
    if(!cached){
     if(activity.getFilesDir().getUsableSpace()<RuntimeArtifact.SIZE+16777216L)throw new IOException("Free at least 80 MB for the installer.");
     c=connection(RuntimeArtifact.URL);long size=c.getContentLengthLong();if(size!=-1&&size!=RuntimeArtifact.SIZE)throw new IOException("Unexpected installer download size.");
     long total=0,last=0;try(InputStream in=c.getInputStream();OutputStream out=new FileOutputStream(part)){byte[] buffer=new byte[65536];int n;while((n=in.read(buffer))!=-1){if(cancelled.get())throw new InterruptedIOException("Download cancelled. Nothing installed.");total+=n;if(total>RuntimeArtifact.SIZE)throw new IOException("Installer exceeds expected size.");out.write(buffer,0,n);if(System.currentTimeMillis()-last>250){final int p=(int)(total*100/RuntimeArtifact.SIZE);activity.runOnUiThread(()->{if(!activity.isDestroyed())dialog.setProgress(p);});last=System.currentTimeMillis();}}}
     RuntimeArtifact.verify(part,RuntimeArtifact.SIZE,RuntimeArtifact.SHA256);checkPackage(part);if(cancelled.get())throw new InterruptedIOException("Download cancelled. Nothing installed.");
     if(!part.renameTo(apk()))throw new IOException("Could not save verified installer.");
    }
    if(cancelled.get())throw new InterruptedIOException("Download cancelled. Nothing installed.");
    activity.runOnUiThread(()->{busy=false;if(activity.isDestroyed())return;dialog.dismiss();confirmInstall();});
   }catch(Exception e){String message=e.getMessage();activity.runOnUiThread(()->{busy=false;if(activity.isDestroyed())return;dialog.dismiss();new AlertDialog.Builder(activity).setTitle("Installer not installed").setMessage(message).setPositiveButton("OK",null).show();});}
   finally{if(c!=null)c.disconnect();if(part.exists())part.delete();}
  },"verified-runtime-download").start();
 }
 private void confirmInstall(){
  PackageInfo existing=installed();
  if(existing!=null&&existing.getLongVersionCode()>=RuntimeArtifact.VERSION_CODE){new AlertDialog.Builder(activity).setTitle("Installer verified · game already installed").setMessage("The upstream 2.2.0 download passed size, checksum, package and certificate checks. Your installed game is "+existing.versionName+". It will not be replaced or downgraded. Continue with your game folder and native-code setup.").setPositiveButton("Setup",(d,w)->show()).setNegativeButton("Close",null).show();return;}
  new AlertDialog.Builder(activity).setTitle("Install Bloodborne Android 2.2.0?").setMessage("The upstream installer passed verification. Android will ask you to confirm installation. Your game files are separate. This port is experimental and has its own license and behavior.").setPositiveButton("Continue",(d,w)->install()).setNegativeButton("Not now",null).show();
 }
 private void install(){try{
  if(!activity.getPackageManager().canRequestPackageInstalls()){
   new AlertDialog.Builder(activity).setTitle("Android installation permission").setMessage("Allow Hunter’s Companion to request an installation on the next Android screen, then return here and choose Download verified installer again. The checked download is cached; you can turn this permission off afterward.").setPositiveButton("Open Android settings",(d,w)->activity.startActivity(new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,Uri.parse("package:"+activity.getPackageName())))).setNegativeButton("Cancel",null).show();return;
  }
  Uri uri=Uri.parse("content://com.temery.hunterscompanion.installer/runtime.apk");Intent i=new Intent(Intent.ACTION_VIEW);i.setDataAndType(uri,"application/vnd.android.package-archive");i.setClipData(ClipData.newRawUri("Verified Bloodborne installer",uri));i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);activity.startActivity(i);
 }catch(Exception e){new AlertDialog.Builder(activity).setMessage("Could not open Android installer: "+e.getMessage()).setPositiveButton("OK",null).show();}}
}
