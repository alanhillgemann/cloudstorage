package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public ArrayList<Credential> getCredentials(Integer userId) {
        return credentialMapper.getCredentials(userId);
    }

    public Credential getCredential(Integer credentialId) {
        return credentialMapper.getCredential(credentialId);
    }

    public void createCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encodedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encodedPassword);
        credentialMapper.insert(new Credential(null, credential.getUrl(), credential.getUsername(), credential.getKey(), credential.getPassword(), credential.getUserId()));
    }

    public void updateCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encodedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encodedPassword);
        credentialMapper.update(credential);
    }

    public void deleteCredential(Integer credentialId) { credentialMapper.delete(credentialId); }
}
