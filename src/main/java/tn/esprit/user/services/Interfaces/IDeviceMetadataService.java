package tn.esprit.user.services.Interfaces;

import org.springframework.http.HttpStatus;
import tn.esprit.user.dtos.DeviceListDTO;
import tn.esprit.user.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface IDeviceMetadataService {
    void saveDeviceDetails(String userAgent, User user);

    boolean isNewDevice(String userAgent, User user);

    ResponseEntity<HttpStatus> deleteDevice(String id);

    ResponseEntity<DeviceListDTO> getDevices(int page, int sizePerPage, Principal principal);
}