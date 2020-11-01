package com.hellothomas.assignment.controller;

import com.hellothomas.assignment.service.DecimalConvertService;
import com.hellothomas.assignment.service.UniqueSeqService;
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

    private final DecimalConvertService decimalConvertService;
    private final UniqueSeqService uniqueSeqService;

    public RedirectController(DecimalConvertService decimalConvertService, UniqueSeqService uniqueSeqService) {
        this.decimalConvertService = decimalConvertService;
        this.uniqueSeqService = uniqueSeqService;
    }

    @ApiIgnore
    @GetMapping("/{seqString}")
    public String getOriginUrl(@PathVariable("seqString") String seqString) {
        long seq = decimalConvertService.decimalConvertToNumber(seqString, 62);
        return "redirect:" + uniqueSeqService.SeqConvertToOriginUrl(seq);
    }
}
