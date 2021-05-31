package xyz.hellothomas.tinyurl.query.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.hellothomas.tinyurl.common.infrastructure.exception.MyException;
import xyz.hellothomas.tinyurl.query.applicaton.UniqueSeqService;
import xyz.hellothomas.tinyurl.query.common.enums.QueryErrorCodeEnum;

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

    private final UniqueSeqService uniqueSeqService;

    public RedirectController(UniqueSeqService uniqueSeqService) {
        this.uniqueSeqService = uniqueSeqService;
    }

    @GetMapping("/{seqEncode:^[[A-Za-z0-9]+]{6}}")
    public String getOriginUrl(@PathVariable("seqEncode") String seqEncode) {
        String originUrl = uniqueSeqService.seqEncodeConvertToOriginUrl(seqEncode);
        if (originUrl == null) {
            throw new MyException(QueryErrorCodeEnum.URL_NOT_EXIST);
        }
        return "redirect:" + originUrl;
    }
}
