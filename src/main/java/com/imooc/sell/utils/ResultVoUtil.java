package com.imooc.sell.utils;

import com.imooc.sell.vo.ResultVo;

/**
 * @author XuMingyue
 * @create 2020-06-19 23:43
 * 5-3--a.5 封装result
 */
public class ResultVoUtil {
    /**
     * 返回成功数据
     * @param object
     * @return
     */
    public static ResultVo success(Object object){
        ResultVo resultVo = new ResultVo();
        resultVo.setData(object);
        resultVo.setCode(0);
        resultVo.setMsg("成功");
        return resultVo;
    }

    /**
     * 不传数据
     * @return
     */
    public static ResultVo success(){
        return success(null);
    }

    /**
     * 返回错误数据
     * @param code
     * @param msg
     * @return
     */
    public static ResultVo error(Integer code,String msg){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(code);
        resultVo.setMsg(msg);
        return  resultVo;
    }


}
