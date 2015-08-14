package com.hdu.newlife.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 类名:		ExcelColumnsInfoBean
 * 描述:		Excel导入列分析
 * @author newlife
 *
 */
public class ExcelColumnsInfoBean implements Serializable {

    private static final long     serialVersionUID = 1L;

    private List<ExcelColumnBean> excelColumnList;

    /**
     * @return 返回变量excelColumnList的值
     */
    public List<ExcelColumnBean> getExcelColumnList() {
        return excelColumnList;
    }

    /**
     * @param excelColumnList 设置excelColumnList的值
     */
    public void setExcelColumnList(List<ExcelColumnBean> excelColumnList) {
        this.excelColumnList = excelColumnList;
    }

}
