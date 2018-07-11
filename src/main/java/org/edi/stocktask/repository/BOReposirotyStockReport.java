package org.edi.stocktask.repository;

import org.edi.freamwork.exception.BusinessException;
import org.edi.initialfantasy.util.CharsetConvert;
import org.edi.stocktask.bo.stockreport.StockReport;
import org.edi.stocktask.bo.stockreport.StockReportItem;
import org.edi.stocktask.mapper.StockReportMapper;
import org.edi.stocktask.util.B1DocEntryCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashMap;
import java.util.List;

/**
 * Created by asus on 2018/6/29.
 */


@Component(value="boReposirotyStockReport")
public class BOReposirotyStockReport implements IBOReposirotyStockReport{

    @Autowired
    private StockReportMapper stockReportMapper;

    @Autowired
    private B1DocEntryCheck b1DocEntryCheck;

    @Autowired
    private PlatformTransactionManager ptm;


    /**
     * 查询任务汇报清单
     * @return
     */
    @Override
    public List<StockReport> fetchStockReport(){
        List<StockReport> StockReports = stockReportMapper.fetchStockReport();
        for(int i=0;i<StockReports.size();i++){
            StockReport stockReport = StockReports.get(i);
            List<StockReportItem> StockReportItems = stockReportMapper.fetchStockReportItem(stockReport.getDocEntry());
            stockReport.setStockReportItems(StockReportItems);
        }
        return StockReports;
    }



    /**
     * 根据DOCENTRY查询库存任务汇报
     * @param docEntry
     * @return
     */
    @Override
    public StockReport fetchStockReportByEntry(Integer docEntry){
        StockReport stockReport = stockReportMapper.fetchStockReportByEntry(docEntry);
        List<StockReportItem> stockReportItems = stockReportMapper.fetchStockReportItem(docEntry);
        stockReport.setStockReportItems(stockReportItems);
        return stockReport;
    }

    /**
     * 保存任务汇报
     * @param stockReports
     * @return
     */
    public void saveStockReports(List<StockReport> stockReports){
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = ptm.getTransaction(def);
        try {
            for (int i = 0; i < stockReports.size(); i++) {
                StockReport stockReport = stockReports.get(i);
                stockReportMapper.saveStockReport(stockReport);
                for (int j = 0; j < stockReports.get(i).getStockReportItems().size(); j++) {
                    StockReportItem stockReportItem = stockReports.get(i).getStockReportItems().get(j);
                    stockReportItem.setLineId(j + 1);
                    stockReportMapper.saveStockReportItem(stockReportItem);
                }
            }
            ptm.commit(status);
        }catch(Exception e){
            e.printStackTrace();
            ptm.rollback(status);
            throw e;
        }
    }



    /**
     * 删除任务汇报
     * @param docEntry
     */
    @Override
    public void deleteStockReport(Integer docEntry) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = ptm.getTransaction(def);
        if(b1DocEntryCheck.B1EntryCheck(docEntry)){
            try {
                stockReportMapper.deleteStockReport(docEntry);
                stockReportMapper.deleteStockReportItem(docEntry);
                ptm.commit(status);
            }catch(Exception e){
                e.printStackTrace();
                ptm.rollback(status);
                throw e;
            }
        }else{
            throw new BusinessException(CharsetConvert.convert("B1单据在系统已生成，无法修改！"));
        }

    }




    /**
     * 条件查询任务汇报
     * @param companyName
     * @param baseDocumentType
     * @param baseDocumentEntry
     * @return
     */
    @Override
    public List<StockReport> fetchStockReportByCondition(String companyName, String baseDocumentType, String baseDocumentEntry) {
        HashMap<String,String> stockReportCondition = new HashMap<>();
        stockReportCondition.put("companyName",companyName);
        stockReportCondition.put("baseDocumentType",baseDocumentType);
        stockReportCondition.put("baseDocumentEntry",baseDocumentEntry);
        List<StockReport> StockReports = stockReportMapper.fetchStockReportByCondition(stockReportCondition);
        for(int i=0;i<StockReports.size();i++){
            StockReport stockReport = StockReports.get(i);
            List<StockReportItem> StockReportItems = stockReportMapper.fetchStockReportItem(stockReport.getDocEntry());
            stockReport.setStockReportItems(StockReportItems);
        }
        return StockReports;
    }




    /**
     * 模糊查询库存任务汇报
     * @param value
     * @return
     */
    @Override
    public List<StockReport> fetchStockReportFuzzy(String value){
        List<StockReport> StockReports = stockReportMapper.fetchStockReportFuzzy(value);
        for(int i=0;i<StockReports.size();i++){
            StockReport stockReport = StockReports.get(i);
            List<StockReportItem> StockReportItems = stockReportMapper.fetchStockReportItem(stockReport.getDocEntry());
            stockReport.setStockReportItems(StockReportItems);
        }
        return StockReports;
    }





    /**
     * 更新库存任务汇报
     * @param stockReport
     */
    @Override
    public void updateStockReport(StockReport stockReport) {
        //TODO 更新库存任务汇报
        //1、先查询库存任务汇报是否生成单据  （条件：B1DocEntry的值为null或者0）
        //2、如果任务汇报没有生成单据，先删除再保存
    }




}