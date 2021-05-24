package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/{fileId}")
    public ResponseEntity getFile(Authentication authentication, @PathVariable Integer fileId) {
        Integer userId = null;
        try {
            File file = fileService.getFile(fileId);
            userId = userService.getUser(authentication.getName()).getUserId();
            if (file == null) {
                log.warn("User {} unsuccessfully downloaded file {}: not found", userId, fileId);
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else if (!file.getUserId().equals(userId)) {
                log.warn("User {} unsuccessfully downloaded file {}: forbidden", userId, fileId);
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            } else {
                log.info("User {} downloaded file {}", userId, fileId);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(file.getContentType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                        .body(file.getFileData());
            }
        } catch (Exception e) {
            log.error("User {} unsuccessfully downloaded file {}: server", userId, fileId);
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PostMapping
    public String postFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile file, Model model) {
        String filename = file.getOriginalFilename();
        boolean result = false;
        Integer userId = null;
        try {
            userId = userService.getUser(authentication.getName()).getUserId();
            if (file.isEmpty()) {
                log.warn("User {} unsuccessfully uploaded file: empty", userId);
            } else if (!fileService.isFilenameAvailable(filename, userId)) {
                log.warn("User {} unsuccessfully uploaded file: already exists", userId);
            } else {
                Integer fileId = fileService.createFile(file, userId);
                log.info("User {} uploaded file {}", userId, fileId);
                result = true;
            }
        } catch (Exception e) {
            log.error("User {} unsuccessfully uploaded file: server", userId);
        }
        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(Authentication authentication, @PathVariable Integer fileId, Model model) {
        boolean result = false;
        Integer userId = null;
        try {
            File file = fileService.getFile(fileId);
            userId = userService.getUser(authentication.getName()).getUserId();
            if (file == null) {
                log.warn("User {} unsuccessfully deleted file {}: not found", userId, fileId);
            } else if (!file.getUserId().equals(userId)) {
                log.warn("User {} unsuccessfully deleted file {}: forbidden", userId, fileId);
            } else {
                fileService.deleteFile(fileId);
                log.info("User {} deleted file {}", userId, fileId);
                result = true;
            }
        } catch (Exception e) {
            log.warn("User {} unsuccessfully deleted file {}: server", userId, fileId);
        }
        model.addAttribute("result", result);
        return "result";
    }
}
