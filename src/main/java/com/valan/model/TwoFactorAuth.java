package com.valan.model;

import com.valan.domain.VerificationType;

import lombok.Data;


@Data
public class TwoFactorAuth {
   private boolean isEnable=false;
   private VerificationType sendTo;
}
