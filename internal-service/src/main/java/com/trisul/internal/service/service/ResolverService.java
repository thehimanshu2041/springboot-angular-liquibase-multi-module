package com.trisul.internal.service.service;

import com.trisul.internal.service.model.CodeDetail;
import com.trisul.internal.service.model.CscDetail;

public interface ResolverService {

  CodeDetail resolveCodeDetail(CodeDetail codeDetail);

  CscDetail resolveCscDetail(CscDetail cscDetail);
}
