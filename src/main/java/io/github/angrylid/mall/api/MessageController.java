package io.github.angrylid.mall.api;

import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.MsgDto;
import io.github.angrylid.mall.jwt.UserRole;
import io.github.angrylid.mall.jwt.annotation.TokenRequired;
import io.github.angrylid.mall.mbg.entity.Message;
import io.github.angrylid.mall.service.impl.MessageServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(tags = "批假条模块")
@RequestMapping(path = "/message")
public class MessageController {

    @Autowired
    MessageServiceImpl messageService;


    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @ApiOperation("提交申请")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataTypeClass = String.class)
    @TokenRequired(role = UserRole.STAFF)
    @PostMapping
    public CustomResponse<String> apply(@RequestBody
                                        @Validated MsgDto dto,
                                        BindingResult bindingResult,
                                        @RequestHeader("Authorization") String token,
                                        HttpServletRequest req) {
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                logger.warn("fieldError:{}", fieldError);
            }
            return CustomResponse.validException("输入的参数不正确");
        }

        logger.warn("identity: {}", req.getAttribute("identity"));
        Long id = 0L;
        try {
            id = (Long) req.getAttribute("identity");
        } catch (Exception e) {
            logger.warn("{}", e.toString());
        } finally {
            CustomResponse.validException("错误");
        }
        boolean result = this.messageService.addMessage(dto.getMessage(), id);
        if (result) {
            return CustomResponse.success("成功提交");
        }
        return CustomResponse.dbException("提交失败");

    }

    @ApiOperation("处理申请")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataTypeClass = String.class)
    @TokenRequired(role = UserRole.ADMIN)
    @PutMapping
    public CustomResponse<String> approval(@RequestParam("id") int id,
                                           @RequestParam("status") Boolean status) {
        boolean result = this.messageService.checkMessage(id, status);
        if (result) {
            return CustomResponse.success("成功提交");
        }

        return CustomResponse.dbException("提交失败");
    }

    @ApiOperation("检索所有待处理申请")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataTypeClass = String.class)
    @TokenRequired(role = UserRole.ADMIN)
    @GetMapping
    public CustomResponse<List<Message>> retrieve() {
        List<Message> messages = null;
        try {
            messages = this.messageService.retrieveAll();
        } catch (Exception e) {
            logger.warn(e.toString());
            CustomResponse.dbException("出现故障");
        }

        return CustomResponse.success(messages);
    }

    @ApiOperation("查看当前用户的申请")
    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header", dataTypeClass = String.class)
    @TokenRequired(role = UserRole.STAFF)
    @GetMapping(path = "/cur")
    public CustomResponse<List<Message>> getMessagesOfCurrentUser(HttpServletRequest req){
        Long id = 0L;
        try {
            id = (Long) req.getAttribute("identity");
        } catch (Exception e) {
            logger.warn("{}", e.toString());
        } finally {
            CustomResponse.validException("错误");
        }
        List<Message> messages = this.messageService.retrieveCurrentUser(id);
        return CustomResponse.success(messages);
    }
}
