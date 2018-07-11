package org.edi.stocktask.mapper;

import org.edi.stocktask.bo.stockreport.StockReport;
import org.edi.stocktask.bo.stockreport.StockReportItem;

import java.util.List;
import java.util.Map;

public interface StockReportMapper {
    List<StockReport> fetchStockReport();
    StockReport fetchStockReportByEntry(Integer docEntry);
    List<StockReportItem> fetchStockReportItem(Integer docEntry);
    void saveStockReport(StockReport stockReports);
    void saveStockReportItem(StockReportItem stockReportItem);
    void deleteStockReport(Integer docEntry);
    void deleteStockReportItem(Integer docEntry);
    List<StockReport> fetchStockReportByCondition(Map<String,String> stockReportCondition);
    List<StockReport> fetchStockReportFuzzy(String value);
}