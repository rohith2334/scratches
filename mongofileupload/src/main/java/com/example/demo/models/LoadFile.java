package com.example.demo.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoadFile {

    private String filename;
    private String fileType;
    private String fileSize;
    private byte[] file;

    //toString
    @Override
    public String toString() {
        return "LoadFile [file=" + file + ", filename=" + filename + ", fileSize=" + fileSize + ", fileType=" + fileType
                + "]";
    }
}