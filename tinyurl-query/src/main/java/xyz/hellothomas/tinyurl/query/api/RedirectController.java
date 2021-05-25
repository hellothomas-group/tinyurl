package xyz.hellothomas.tinyurl.query.api;

import xyz.hellothomas.tinyurl.query.applicaton.UniqueSeqService;
import xyz.hellothomas.tinyurl.query.common.enums.ErrorCodeEnum;
import xyz.hellothomas.tinyurl.query.infrastructure.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.regex.Pattern;

/**
 * @classname RedirectController
 * @author Thomas
 * @date 2020/11/1 19:56
 * @description
 * @version 1.0
 */
@Slf4j
@Controller
public class RedirectController {
    private static final String SEQ_ENCODE_PATTERN = "^[[A-Za-z0-9]+]{6}";

    private final UniqueSeqService uniqueSeqService;

    public RedirectController(UniqueSeqService uniqueSeqService) {
        this.uniqueSeqService = uniqueSeqService;
    }

    @GetMapping("/{seqEncode}")
    public String getOriginUrl(@PathVariable("seqEncode") String seqEncode) {
        checkInput(seqEncode);
        String originUrl = uniqueSeqService.seqEncodeConvertToOriginUrl(seqEncode);
        if (originUrl == null) {
            throw new MyException(ErrorCodeEnum.URL_NOT_EXIST);
        }
        return "redirect:" + originUrl;
    }

    private void checkInput(String seqEncode) {
        if (!Pattern.matches(SEQ_ENCODE_PATTERN, seqEncode)) {
            throw new MyException(ErrorCodeEnum.URL_PATH_INVALID);
        }
    }
}
