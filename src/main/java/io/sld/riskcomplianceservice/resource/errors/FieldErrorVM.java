package io.sld.riskcomplianceservice.resource.errors;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@Builder(toBuilder = true)
@ToString
@RequiredArgsConstructor
public class FieldErrorVM implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String objectName;

    private final String field;

    private final String message;

}
