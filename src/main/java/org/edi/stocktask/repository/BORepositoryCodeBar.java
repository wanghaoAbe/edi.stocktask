package org.edi.stocktask.repository;

import org.apache.log4j.Logger;
import org.edi.freamwork.exception.BusinessException;
import org.edi.initialfantasy.data.ResultCode;
import org.edi.initialfantasy.data.ResultDescription;
import org.edi.stocktask.bo.codeBar.ICodeBar;
import org.edi.stocktask.mapper.CodeBarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @author Fancy
 * @date 2018/7/10
 */
@Component(value="boRepositoryCodeBar")
public class BORepositoryCodeBar implements IBORepositoryCodeBar{
    private static Logger log = Logger.getLogger(BORepositoryCodeBar.class);



    @Autowired
    private CodeBarMapper codeBarMapper;

    /**
     * 条码解析
     * @param codebar
     * @return
     */
    @Override
    public List<ICodeBar> parseCodeBar(String codebar,String baseType,int baseEntry,int baseLine) {
        if(codebar==null||codebar.isEmpty()){
            throw new BusinessException(ResultCode.CODEBAR_IS_NULL,ResultDescription.CODEBAR_IS_NULL);
        }
        if(baseType==null||baseType.isEmpty()){
            throw new BusinessException(ResultCode.STOCK_BASETYPE_IS_NULL,ResultDescription.STOCK_BASETYPE_IS_NULL);
        }
        if(baseEntry==0){
            throw new BusinessException(ResultCode.STOCK_BASEENTRY_IS_NULL,ResultDescription.STOCK_BASEENTRY_IS_NULL);
        }
        List<ICodeBar> listCodeBar = null;
        HashMap<String,Object> codeBarParam = new HashMap();
        codeBarParam.put("codebar",codebar);
        codeBarParam.put("baseType",baseType);
        codeBarParam.put("baseEntry",baseEntry);
        codeBarParam.put("baseLine",baseLine);
        try {
            listCodeBar = codeBarMapper.parseCodeBar(codeBarParam);
        }catch (Exception e){
            e.printStackTrace();
            log.warn(e);
            throw new BusinessException(ResultCode.BARCODE_ANALYSIS_IS_FAIL,ResultDescription.BARCODE_ANALYSIS_IS_FAIL);
        }

        return listCodeBar;
    }
}
