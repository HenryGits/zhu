package vrbaidu.top.util;

import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */
public class ExcelUtils {
    /**
     * excel读取
     * */
    //总行数
    private int totalRows = 0;
    //总列数
    private int totalCells = 0;
    //错误信息
    private String errorInfo;

    //构造方法
    public ExcelUtils() {
    }

    /**
     * 得到总行数
     */
    public int getTotalRows() {
        return totalRows;
    }

    /**
     * 得到总列数
     */
    public int getTotalCells() {
        return totalCells;
    }

    /**
     * 得到错误信息
     */
    public String getErrorInfo() {
        return errorInfo;
    }

    /**
     * 验证excel文件
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath) {
        /** 检查文件名是否为空或者是否是Excel格式的文件 */
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            errorInfo = "文件名不是excel格式";
            return false;
        }

        /** 检查文件是否存在 */
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            errorInfo = "文件不存在";
            return false;
        }
        return true;
    }

    /**
     * 根据文件路径读取excel文件
     * @param filePath
     * @return
     * @throws IOException
     */
    public List<List<String>> read(String filePath) throws IOException {
        List<List<String>> dataLst = new ArrayList<List<String>>();
        InputStream is = null;
        try {
            /** 验证文件是否合法 */
            if (!validateExcel(filePath)) {
                System.out.println(errorInfo);
                return null;
            }

            /** 判断文件的类型，是2003还是2007 */
            boolean isExcel2003 = true;
            if (isExcel2007(filePath)) {
                isExcel2003 = false;
            }

            /** 调用本类提供的根据流读取的方法 */
            File file = new File(filePath);
            is = new FileInputStream(file);
            dataLst = read(is, isExcel2003);
            is.close();
            is = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        return dataLst;
    }

    /**
     * 根据流读取Excel文件
     *
     * @param inputStream 文件输入流
     * @param isExcel2003 标识是否2003的excel。
     *                        true：是2003的excel，false：是2007的excel
     * @return
     *
     * @扩展说明
     *          如果使用springmvc的MultipartFile接收前端上传的excel文件的话，可以使用MultipartFile的对象，获取上传的文件名称，
     *          然后，可以通过 CheckExcelUtil 类的方法，接收文件名称参数，来判断excel所属的版本。最后再调用此方法来读取excel数据。
     *
     */
    public List<List<String>> read(InputStream inputStream, boolean isExcel2003) {
        List<List<String>> dataLst = null;
        try {
            /** 根据版本选择创建Workbook的方式 */
            Workbook wb = null;
            if (isExcel2003) {
                wb = new HSSFWorkbook(inputStream);
            } else {
                wb = new XSSFWorkbook(inputStream);
            }
            dataLst = read(wb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataLst;
    }

    /**
     * 读取Excel数据
     * @param wb
     * @return
     */
    private List<List<String>> read(Workbook wb) {
        List<List<String>> dataLst = new ArrayList<List<String>>();
        //得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        //得到Excel的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();
        //得到Excel的列数
        if (this.totalRows >= 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        //循环Excel的行
        for (int r = 0; r < this.totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            List<String> rowLst = new ArrayList<String>();
            //循环Excel的列
            for (int c = 0; c < this.getTotalCells(); c++) {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (null != cell) {
                    // 以下是判断数据的类型
                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                            cellValue = cell.getNumericCellValue() + "";
                            break;

                        case HSSFCell.CELL_TYPE_STRING: // 字符串
                            cellValue = cell.getStringCellValue();
                            break;

                        case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                            cellValue = cell.getBooleanCellValue() + "";
                            break;

                        case HSSFCell.CELL_TYPE_FORMULA: // 公式
                            cellValue = cell.getCellFormula() + "";
                            break;

                        case HSSFCell.CELL_TYPE_BLANK: // 空值
                            cellValue = "";
                            break;

                        case HSSFCell.CELL_TYPE_ERROR: // 故障
                            cellValue = "非法字符";
                            break;

                        default:
                            cellValue = "未知类型";
                            break;
                    }
                }
                rowLst.add(cellValue);
            }
            //保存第r行的第c列
            dataLst.add(rowLst);
        }
        return dataLst;
    }


    /**
     * 检查是否是2003的excel
     * @param filePath
     * @return true||false
     */
    private static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 检查是否是2007的excel
     * @param filePath
     * @return true||false
     */
    private static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }


    /**
     * word读取全部内容
     * @param is
     * @return
     * @throws IOException
     */
    public static String readDocAll(InputStream is) throws IOException {
        WordExtractor extractor = new WordExtractor(is);
        return extractor.getText();
    }

    /**
     * 分章节Section、段落Paragraph、字符串CharacterRun抽取
     * @param is
     * @throws IOException
     */
    public static void readDoc(InputStream is) throws IOException {
        HWPFDocument doc = new HWPFDocument(is);
        Range r = doc.getRange();
        for (int x = 0; x < r.numSections(); x++) {
            org.apache.poi.hwpf.usermodel.Section s = r.getSection(x);
            for (int y = 0; y < s.numParagraphs(); y++) {
                Paragraph p = s.getParagraph(y);
                for (int z = 0; z < p.numCharacterRuns(); z++) {
                    CharacterRun run = p.getCharacterRun(z);
                    String text = run.text();
                    System.out.print(text);
                }
            }
        }
    }

    /**
     * 直接抽取幻灯片的全部内容
     * @param is
     * @return
     * @throws IOException
     */
    public static String readAllPPT(InputStream is) throws IOException{
        PowerPointExtractor extractor=new PowerPointExtractor(is);
        return extractor.getText();
    }

    /**
     * 一张幻灯片一张幻灯片地读取
     * @param is
     * @return
     * @throws IOException
     */
    public static List<String> readPPT(InputStream is) throws IOException{
        List<String> list = new ArrayList<String>();
        SlideShow ss=new SlideShow(new HSLFSlideShow(is));
        Slide[] slides=ss.getSlides();
        if (slides.length ==0){
            return null;
        }
        for(int i=0;i<slides.length;i++){
            //读取一张幻灯片的标题
            String title=slides[i].getTitle();
            System.out.println("标题:"+title);
            //读取一张幻灯片的内容(包括标题)
            org.apache.poi.hslf.model.TextRun[] runs=slides[i].getTextRuns();
            for(int j=0;j<runs.length;j++){
                list.add(runs[j].getText());
            }
        }
        return  list;
    }


    public static void main(String[] args) throws Exception {
        ExcelUtils poi = new ExcelUtils();
        List<List<String>> list = poi.read("E:/批量导入客户模板.xlsx");
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                List<String> cellList = list.get(i);
                for (int j = 0; j < cellList.size(); j++) {
                    System.out.print("    " + cellList.get(j));
                }
                System.out.println();
            }
        }
    }
}
