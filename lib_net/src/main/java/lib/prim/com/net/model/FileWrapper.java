package lib.prim.com.net.model;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import okhttp3.MediaType;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/13 0013
 * 描    述：文件的包装类
 * 修订历史：
 * ================================================
 */
public class FileWrapper implements Serializable {
    private static final long serialVersionUID = -2356139899636767776L;

    public File file;                      //文件
    public long size;                      //文件大小
    public String fileName;                //文件名
    public transient MediaType mediaType;  //文件的类型

    public FileWrapper(File file,String fileName, MediaType mediaType) {
        this.file = file;
        this.size = file.length();
        this.fileName = fileName;
        this.mediaType = mediaType;
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeObject(mediaType.toString());
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        mediaType = MediaType.parse(String.valueOf(inputStream.readObject()));
    }

}
