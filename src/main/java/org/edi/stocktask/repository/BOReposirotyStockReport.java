package org.edi.stocktask.repository;

import org.edi.stocktask.bo.stockreport.StockReport;
import org.edi.stocktask.bo.stockreport.StockReportItem;
import org.edi.stocktask.mapper.StockReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by asus on 2018/6/29.
 */

@Transactional
@Component(value="boReposirotyStockReport")
public class BOReposirotyStockReport implements IBOReposirotyStockReport{

    @Autowired
    private StockReportMapper stockReportMapper;


    @Override
    //查询库存任务汇报
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
     * 查询任务汇报
     * @param companyName
     * @param baseDocumentType
     * @param baseDocumentDocEntry
     * @return
     */
    @Override
    public List<StockReport> fetchStockReport(String companyName, String baseDocumentType, String baseDocumentDocEntry) {
        return null;
    }

    /**
     * 根据DOCENTRY查询库存任务汇报
     * @param docEntry
     * @return
     */
    @Override
    public List<StockReport> fetchStockReportByEntry(Integer docEntry){
        List<StockReport> StockReports = stockReportMapper.fetchStockReportByEntry(docEntry);
        return StockReports;
    }

    @Override
    /**
     * 保存库存任务汇报
     */
    public void saveStockReport(StockReport stockReport){
            stockReportMapper.saveStockReport(stockReport);
    }


    /**
     * 保存库存任务汇报明细
     * @param stockReportItem
     */
    @Override
    public void saveStockReportItem(StockReportItem stockReportItem){
            stockReportMapper.saveStockReportItem(stockReportItem);
    }

    /**
     * 模糊查询库存任务汇报
     * @param value 查询值
     * @return
     */
    @Override
    public List<StockReport> fetchStockReportFuzzy(String value) {
        return null;
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

    /**
     * 删除任务汇报
     * @param docEntry
     */
    @Override
    public void deleteStockReport(Integer docEntry) {
        //TODO 将isDelete的值设为 ‘Y’
    }


}