package com.ezen.www.handler;

import com.ezen.www.domain.FileVO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileHandler {

    private final String UP_DIR = "C:\\Users\\mangs\\Desktop\\_myProject\\_java\\_fileUpload\\";

    public List<FileVO> uploadFiles(MultipartFile[] files){
        List<FileVO> flist = new ArrayList<>();

        LocalDate date = LocalDate.now();
        String today = date.toString();
        today = today.replace("-", File.separator);

        File folders = new File(UP_DIR, today);

        if(!folders.exists()){
            folders.mkdirs();
        }

        // FileVO 생성
        for(MultipartFile file : files) {
            FileVO fvo = new FileVO();
            fvo.setSaveDir(today);
            fvo.setFileSize(file.getSize());

            String originalFileName = file.getOriginalFilename();
            String fileName = originalFileName.substring(
                    originalFileName.lastIndexOf(File.separator)+1);
            fvo.setFileName(fileName);

            UUID uuid = UUID.randomUUID();
            String uuidStr = uuid.toString();
            fvo.setUuid(uuidStr);

            // 디스크에 저장
            String fullFileName = uuidStr + "_" + fileName;

            File storeFile = new File(folders, fullFileName);
            try {
                file.transferTo(storeFile);
                if (isImageFile(storeFile)) {
                    fvo.setFileType(1);

                    File thumbNail = new File(folders, uuidStr + "_th_" + fileName);
                    Thumbnails.of(storeFile).size(100, 100).toFile(thumbNail);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            flist.add(fvo);
        }
        return flist;
    }

    public boolean isImageFile(File file) throws IOException {
        String mimeType = new Tika().detect(file);
        return mimeType.startsWith("image");
    }
}
