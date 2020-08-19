package com.nur.project.model;

public class File {
    
    private Long fileId;
    private String name;
    private String displayName;
    private String uniqueName;
    private String description;
    private Long size;
    private String mimeType;

    public File(Long fileId, String name, String displayName, String uniqueName, String description, Long size,
            String mimeType) {
        this.fileId = fileId;
        this.name = name;
        this.displayName = displayName;
        this.uniqueName = uniqueName;
        this.description = description;
        this.size = size;
        this.mimeType = mimeType;
    }

    public File() {
    } 

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    

    
    
}