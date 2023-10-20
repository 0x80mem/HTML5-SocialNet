package com.html.nds.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice // 必须必须要被启动类扫描
public class GlobalExceptionHandler {


    /**
     * 处理系统异常
     * @return
     *
     * catch (Exception e) {
     *             e.printStackTrace();
     *             return AjaxResult.me().setSuccess(false).setMessage("系统繁忙，请重试！");
     *         }
     */
    @ExceptionHandler(Exception.class) //这句代码可以认为是trycatch中的catch
    public R exceptionHandler(Exception e){
        e.printStackTrace();
        return R.error(GlobalExceptionEnum.ERROR.getCode(),GlobalExceptionEnum.ERROR.getMessage());
    }

    /**
     * 处理自定义业务异常
     * @return
     *
     * catch (GlobalException e) {
     *             e.printStackTrace();
     *             return AjaxResult.me().setSuccess(false).setMessage("系统繁忙，请重试！");
     *         }
     */
    @ExceptionHandler(GlobalException.class) //这句代码可以认为是trycatch中的catch
    public R globalExceptionHandler(GlobalException e){
        e.printStackTrace();
        return R.error(e.getCode(), e.getGlobalMessage());
    }


}


