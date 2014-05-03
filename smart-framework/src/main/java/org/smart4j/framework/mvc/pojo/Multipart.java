package org.smart4j.framework.mvc.pojo;

import java.io.InputStream;
import org.smart4j.framework.core.bean.BaseBean;

public class Multipart extends BaseBean {

    private String fieldName;
    private String fileName;
    private long fileSize;
    private String contentType;
    private InputStream inputStream;

    public Multipart(String fieldName, String fileName, String contentType, long fileSize, InputStream inputStream) {
        this.fieldName = fieldName;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.inputStream = inputStream;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}