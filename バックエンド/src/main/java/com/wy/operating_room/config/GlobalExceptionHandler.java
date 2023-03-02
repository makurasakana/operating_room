package com.wy.operating_room.config;

import com.wy.operating_room.core.Result;
import com.wy.operating_room.repository.ExceptioRepository;
import com.wy.operating_room.entity.Exceptio;
import com.wy.operating_room.service.IdentityService;
import com.wy.operating_room.util.RequestUtil;
import com.wy.operating_room.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    IdentityService identityService;
    @Autowired
    ExceptioRepository exceptioRepository;


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest request, Exception exception) throws Exception {

        String personid = null;
        String token = RequestUtil.getToken(request);
        if (StringUtil.isNotBlank(token)) {
            if (identityService.getByToken(token) != null) {
                Map identityMap = identityService.getByToken(token);
                personid = String.valueOf(identityMap.get("id"));
            }
        }

        Exceptio e = Exceptio.of(personid, request);
        e.setDescription(getStackTrace(exception));

        exceptioRepository.save(e);

        return Result.oflost(1);
    }

    private String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
}
