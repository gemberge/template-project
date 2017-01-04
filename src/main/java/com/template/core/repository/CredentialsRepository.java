package com.template.core.repository;

import com.template.core.model.auth.AuthVendor;
import com.template.core.model.auth.Credential;
import org.springframework.data.repository.CrudRepository;

public interface CredentialsRepository extends CrudRepository<Credential, Long> {
    Credential findByVendorAndVendorIdAndPassword(AuthVendor vendor, String vendorId, String password);
    Credential findByVendorAndVendorId(AuthVendor vendor, String vendorId);
}