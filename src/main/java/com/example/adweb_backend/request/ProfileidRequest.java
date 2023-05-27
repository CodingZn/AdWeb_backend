package com.example.adweb_backend.request;

import lombok.*;
import org.hibernate.validator.constraints.Range;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileidRequest {
    @Range(min=1, max = 14)
    private int profileID;
}
