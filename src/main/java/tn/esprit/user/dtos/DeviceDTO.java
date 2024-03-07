package tn.esprit.user.dtos;

import lombok.Data;
import java.time.Instant;

@Data
public class DeviceDTO {
    String id;
    String deviceDetails;
    Instant lastLoggedIn;
}
