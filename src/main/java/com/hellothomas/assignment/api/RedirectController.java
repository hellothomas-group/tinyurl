package com.hellothomas.assignment.api;

import com.hellothomas.assignment.applicaton.UniqueSeqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import static com.hellothomas.assignment.constants.Constants.PROXY_PATH;

/**
 * @classname RedirectController
 * @author Thomas
 * @date 2020/11/1 19:56
 * @description
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping(PROXY_PATH)
public class RedirectController {

    private final UniqueSeqService uniqueSeqService;

    public RedirectController(UniqueSeqService uniqueSeqService) {
        this.uniqueSeqService = uniqueSeqService;
    }

    @ApiIgnore
    @GetMapping("/{seqString}")
    public String getOriginUrl(@PathVariable("seqString") String seqEncode) {
        return "redirect:" + uniqueSeqService.seqEncodeConvertToOriginUrl(seqEncode);
    }
}
