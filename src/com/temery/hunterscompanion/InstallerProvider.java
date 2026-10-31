package com.temery.hunterscompanion;
import android.content.*;import android.database.*;import android.net.Uri;import android.os.ParcelFileDescriptor;import android.provider.OpenableColumns;import java.io.*;

/** Grants the system installer read-only access to one verified APK, never a directory. */
public class InstallerProvider extends ContentProvider {
 public boolean onCreate(){return true;}
 private File artifact(Uri uri)throws FileNotFoundException {
  if(!"/runtime.apk".equals(uri.getPath())||!"com.temery.hunterscompanion.installer".equals(uri.getAuthority()))throw new FileNotFoundException("Unknown artifact");
  return new File(getContext().getFilesDir(),"runtime.apk");
 }
 @Override public ParcelFileDescriptor openFile(Uri uri,String mode)throws FileNotFoundException {
  if(!"r".equals(mode))throw new FileNotFoundException("Read-only installer");File f=artifact(uri);
  try{RuntimeArtifact.verify(f,RuntimeArtifact.SIZE,RuntimeArtifact.SHA256);}catch(Exception e){throw new FileNotFoundException("Installer verification failed");}
  return ParcelFileDescriptor.open(f,ParcelFileDescriptor.MODE_READ_ONLY);
 }
 public String getType(Uri u){return "application/vnd.android.package-archive";}
 public Cursor query(Uri u,String[] projection,String selection,String[] args,String sort){try{File f=artifact(u);String[] cols=projection==null?new String[]{OpenableColumns.DISPLAY_NAME,OpenableColumns.SIZE}:projection;MatrixCursor c=new MatrixCursor(cols);Object[] values=new Object[cols.length];for(int n=0;n<cols.length;n++)values[n]=OpenableColumns.DISPLAY_NAME.equals(cols[n])?"Bloodborne-2.2.0.apk":OpenableColumns.SIZE.equals(cols[n])?f.length():null;c.addRow(values);return c;}catch(Exception e){return null;}}
 public Uri insert(Uri u,ContentValues v){throw new UnsupportedOperationException();}
 public int update(Uri u,ContentValues v,String s,String[] a){throw new UnsupportedOperationException();}
 public int delete(Uri u,String s,String[] a){throw new UnsupportedOperationException();}
}
