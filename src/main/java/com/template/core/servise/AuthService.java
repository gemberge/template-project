package com.template.core.servise;

import com.template.core.model.Person;
import com.template.core.model.WebException;
import com.template.core.model.auth.AuthToken;
import com.template.core.model.auth.AuthVendor;
import com.template.core.model.auth.Credential;
import com.template.core.model.auth.DoubleAuthToken;
import com.template.core.repository.CredentialsRepository;
import com.template.core.repository.PersonsRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final CredentialsRepository credentialsRepository;
    private final PersonsRepository personsRepository;
    private final String appSessionSecret = "Mm37udTs6TPWhpTbhCbz";
    private final String appSessionSalt = "p5MrMjKkpqPrJWKEv25w";

    @Autowired
    AuthService(CredentialsRepository credentialsRepository, PersonsRepository personsRepository) {
        this.credentialsRepository = credentialsRepository;
        this.personsRepository = personsRepository;
    }

    public long authViaHeader(HttpServletRequest request) throws WebException {
        String header = request.getHeader("X-ACCESS-TOKEN");
        String userIp = request.getRemoteAddr();

        if(header == null || header.isEmpty()) {
            throw new WebException(HttpStatus.UNAUTHORIZED, "Use header \"X-ACCESS-TOKEN\" for authentication");
        }

        String[] tokenParts = header.split(":");
        if(tokenParts.length != 2) throw new WebException(HttpStatus.UNAUTHORIZED, "Invalid \"X-ACCESS-TOKEN\"");

        long userId = Long.valueOf(tokenParts[0]);
        String newToken = generateAccessToken(userId, userIp);
        if (newToken.equals(header)) {
            return userId;
        } else {
            throw new WebException(HttpStatus.UNAUTHORIZED, "Invalid \"X-ACCESS-TOKEN\"");
        }
    }

    public DoubleAuthToken authViaCredential(String userIp, Credential credential) throws WebException {
        System.out.println("   ---> user ip: " + userIp);
        credential.setVendorId(credential.getVendorId().toLowerCase());
        Credential credentialFromDB = this.credentialsRepository.findByVendorAndVendorIdAndPassword(credential.getVendor(), credential.getVendorId(), credential.getPassword());
        if (credentialFromDB == null) {
            if(credential.getPerson() != null) {
                credentialFromDB = this.attachCredential(credential);
            } else {
                throw new WebException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
            }
        }
        if (credentialFromDB != null) {
            return generateDoubleToken(credentialFromDB.getPerson(), userIp);
        }
        throw new WebException(HttpStatus.INTERNAL_SERVER_ERROR, "Some problem occurs during saving credentials");
    }

    private Credential attachCredential(Credential credential) throws WebException {
        if (credential.getPerson().getId() <= 0) throw new WebException(HttpStatus.BAD_REQUEST, "Person's id should be specified");

        Person person = personsRepository.findOne(credential.getPerson().getId());
        if (person == null) throw new WebException(HttpStatus.BAD_REQUEST, "There isn't person with such id");

        Credential credentialFromDB = credentialsRepository.findByVendorAndVendorId(credential.getVendor(), person.getEmail());
        if (credentialFromDB != null) throw new WebException(HttpStatus.BAD_REQUEST, "Credentials for this person already exist");

        if (credential.getVendorId().equals(person.getEmail())) {
            credential.setSalt(UUID.randomUUID().toString());
            credential.setCreatedAt(new Date());
            credential.setUpdatedAt(new Date());
            return credentialsRepository.save(credential);
        }
        throw new WebException(HttpStatus.BAD_REQUEST, "Person's email doesn't match");
    }

    public DoubleAuthToken generateDoubleToken(Person person, String userIp) throws WebException {
        return new DoubleAuthToken().accessToken(generateAccessToken(person.getId(), userIp))
                                    .authToken("" + person.getId() + ":authToken");
    }

    public AuthToken generateAccessToken(AuthToken authToken, String userIp) {
        return null;
    }

    public String generateAccessToken(Long userId, String userIp) throws WebException {
        String token = doMD5hash(userId + appSessionSalt + userIp);
        token = doMD5hash(appSessionSecret + token);
        return userId + ":" + token;
    }

    private String doMD5hash(String string) throws WebException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            byte[] input = md.digest();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < input.length; ++i) {
                builder.append(Integer.toString((input[i] & 255) + 256, 16).substring(1));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new WebException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during encrypting auth token");
        }
    }

    private String generateAuthToken(Credential credential) {
        return null;
    }

    private String generateAccessToken(String authToken) {
        return null;
    }
}