package com.sookmyung.concon.Photo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PhotoManager {
    private final PhotoService photoService;

    public String updatePhoto(String prefix, String oldFileName, LocalDateTime oldTime, String newFileName, LocalDateTime newTime) {
        String oldFilePath = null;
        if (oldFileName != null) oldFilePath = makeFilepath(prefix, oldFileName, oldTime);
        String newFilePath = makeFilepath(prefix, newFileName, newTime);
        return photoService.modifyPhoto(oldFilePath, newFilePath);
    }

    public String getPhoto(String prefix, String fileName, LocalDateTime time) {
        if (fileName == null) return null;
        return photoService.getPhoto(makeFilepath(prefix, fileName, time));
    }

    public String postPhoto(String prefix, String fileName, LocalDateTime time) {
        String filePath = makeFilepath(prefix, fileName, time);
        return photoService.createPhoto(filePath);
    }

    public void deletePhoto(String prefix, String fileName, LocalDateTime time) {
        photoService.deletePhoto(makeFilepath(prefix, fileName, time));
    }

    private String makeFilepath(String prefix, String fileName, LocalDateTime time) {
        return photoService.createPath(prefix, fileName, time);
    }
}
