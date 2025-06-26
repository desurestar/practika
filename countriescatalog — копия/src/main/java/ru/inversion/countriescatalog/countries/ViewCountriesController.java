package ru.inversion.countriescatalog.countries;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.inversion.dataset.XXIDataSet;
import ru.inversion.dataset.fx.DSFXAdapter;
import ru.inversion.fx.form.*;
import ru.inversion.fx.form.controls.*;
import ru.inversion.bicomp.util.ParamMap;
import ru.inversion.countriescatalog.audit.ViewAuditEntitiesController;
import ru.inversion.db.expr.SQLExpressionException;
import ru.inversion.icons.enums.FontAwesome;
import ru.inversion.utils.S;
import ru.inversion.utils.U;

/**
 *
 * @author orlov
 * @since Tue Jun 07 16:44:15 MSK 2022
 */
public class ViewCountriesController extends JInvFXBrowserController {
//
// Поля для парсера
//

    enum CsvColumnEnum {
        SHORTNAME, GLOBAL_ID, FULLNAME, CODE, ALFA2, ALFA3
    }

    @FXML
    private JInvTable<POkSm> OK_SM;
    @FXML
    private JInvToolBar toolBar;

    private final XXIDataSet<POkSm> dsOK_SM = new XXIDataSet<>();
//
// initDataSet
//    

    private void initDataSet() throws Exception {
        dsOK_SM.setTaskContext(getTaskContext());
        dsOK_SM.setRowClass(POkSm.class);
        dsOK_SM.setOrderBy("cdigital");
        dsOK_SM.executeQuery(true);
    }
//
// Initializes the controller class.
//

    @Override
    protected void init() throws Exception {
//        if (!getAccess()) {
//            Alerts.info(this, getBundleString("NO.ACCESS", getTaskContext().getUserName()));
//            close();
//        }
        setTitle(getBundleString("VIEW.TITLE"));

        initDataSet();

        DSFXAdapter<POkSm> dsfx = DSFXAdapter.bind(dsOK_SM, OK_SM, null, false);

        dsfx.setEnableFilter(true);

        initToolBar();

        OK_SM.setToolBar(toolBar);
        OK_SM.setAction(ActionFactory.ActionTypeEnum.CREATE, (a) -> doOperation(FormModeEnum.VM_INS));
        OK_SM.setAction(ActionFactory.ActionTypeEnum.UPDATE, (a) -> doOperation(FormModeEnum.VM_EDIT));
        OK_SM.setAction(ActionFactory.ActionTypeEnum.DELETE, (a) -> doOperation(FormModeEnum.VM_DEL));
        OK_SM.setAction(ActionFactory.ActionTypeEnum.REFRESH, (a) -> doRefresh());

        doRefresh();
    }
//
// doRefresh
//    

    private void doRefresh() {
        OK_SM.executeQuery();
    }
//
// initToolBar
//    

    private void initToolBar() {
        toolBar.getItems().add(ActionFactory.createButton(FontAwesome.fa_download, null,
                new ViewCountriesController.CsvLoader(), getBundleString("TP.DOWNLOAD_CAT")));

        toolBar.setStandartActions(ActionFactory.ActionTypeEnum.CREATE,
                ActionFactory.ActionTypeEnum.UPDATE,
                ActionFactory.ActionTypeEnum.DELETE);
        toolBar.getItems().add(ActionFactory.createButton(FontAwesome.fa_user_secret, null, new ViewCountriesController.AuditLoader(), getBundleString("TB.AUDIT")));

    }
//
// doOperation
//    

    private void doOperation(JInvFXFormController.FormModeEnum mode) {
        POkSm p;
        switch (mode) {
            case VM_INS:
                p = new POkSm();
                new FXFormLauncher<>(this, CreateCountriesController.class)
                        .dataObject(p)
                        .dialogMode(mode)
                        .initProperties(getInitProperties())
                        .callback(this::doFormResult)
                        .doModal();
                break;
            case VM_EDIT:
                p = dsOK_SM.getCurrentRow();
                new FXFormLauncher<>(this, EditCountriesController.class)
                        .dataObject(p)
                        .dialogMode(mode)
                        .initProperties(getInitProperties())
                        .callback(this::doFormResult)
                        .doModal();
                break;
            case VM_DEL:
                p = dsOK_SM.getCurrentRow();
                new FXFormLauncher<>(this, DeleteCountriesController.class)
                        .dataObject(p)
                        .dialogMode(mode)
                        .initProperties(getInitProperties())
                        .callback(this::doFormResult)
                        .doModal();
                break;
        }
    }
//
// doFormResult 
//

    private void doFormResult(JInvFXFormController.FormReturnEnum ok, JInvFXFormController<POkSm> dctl) {
        if (JInvFXFormController.FormReturnEnum.RET_OK == ok) {
            OK_SM.executeQuery();
        }

    }
//
// Обновление таблицы после действий
//

    private void onOkResult(ParamMap pm) {
        OK_SM.executeQuery();
    }
//    private boolean getAccess() {
//        ParamMap pm = new ParamMap();
//
//        try {
//            pm.exec(this, "Access_Countries_Catalog");
//        } catch (SQLExpressionException ex) {
//            handleException(ex);
//        }
//
//        return "1".equals(pm.getString("b"));
//    }
//
// Открытие аудита
//  

    private class AuditLoader implements EventHandler<ActionEvent> {
//
// Обработчик диалогового окна
// 

        @Override
        public void handle(ActionEvent event) {
            POkSm p=dsOK_SM.getCurrentRow();
            new FXFormLauncher<>(getTaskContext(), getViewContext(), ViewAuditEntitiesController.class)
                    .dataObject(p)
                    .dialogMode(AbstractBaseController.FormModeEnum.VM_NONE)
                    .initProperties(Collections.emptyMap())
                    .show();
        }

    }
//
// Загрузка данных из CSV файла
//    

    private class CsvLoader implements EventHandler<ActionEvent> {
//
// Обработчик диалогового окна с прасером CSV
// 

        @Override
        public void handle(ActionEvent event) {
            File file = JInvFileChooser.showOpenDialog(getViewContext().getStage(),
                    new File(System.getProperty("user.home")),
                    new FileChooser.ExtensionFilter("CSV files", "*.csv"));

            if (file != null && file.isFile()) {
                CSVParser parser = null;

                try {
                    parser = CSVParser.parse(file, Charset.forName("Cp1251"), CSVFormat.DEFAULT
                            .withHeader(ViewCountriesController.CsvColumnEnum.class)
                            .withSkipHeaderRecord()
                            .withIgnoreHeaderCase()
                            .withTrailingDelimiter()
                            .withDelimiter(';'));
                    List<POkSm> listCountries = toTabOksm(parser);
                    listCountries.forEach((POkSm item) -> {
                        try {
                            new ParamMap()
                                    .add("cnum", item.getCDIGITAL())
                                    .add("calfa2", item.getCALFA_2())
                                    .add("calfa3", item.getCALFA_3())
                                    .add("cshname", item.getCSHORTNAME())
                                    .add("clnname", item.getCLONGNAME())
                                    .exec(ViewCountriesController.this, "addCSVOksm");
                        } catch (SQLExpressionException ex) {
                            handleException(ex);
                        }
                    });
                    onAddOksm(listCountries.size());
                } catch (IOException ex) {
                    handleException(ex);
                } finally {
                    if (parser != null) {
                        try {
                            parser.close();
                        } catch (IOException ex) {
                            handleException(ex);
                        }
                    }
                }
            }
        }
//
// Сообщение о добавлении записей
// 

        private void onAddOksm(Integer row) {
            Alerts.info(getViewContext(), getBundleString("MSG.LOADING_DONE",
                    row));
            OK_SM.executeQuery();
        }
//
// Формирование списка Pojo класса
// 

        private List<POkSm> toTabOksm(CSVParser parser) {
            List<POkSm> list = new ArrayList<>();

            for (CSVRecord record : parser) {
                if (record.get(ViewCountriesController.CsvColumnEnum.ALFA2).length() == 2) {
                    POkSm entity = new POkSm();
                    entity.setCSHORTNAME(record.get(ViewCountriesController.CsvColumnEnum.SHORTNAME).toUpperCase());
                    entity.setCLONGNAME(record.get(ViewCountriesController.CsvColumnEnum.FULLNAME).toUpperCase());
                    entity.setCDIGITAL(record.get(ViewCountriesController.CsvColumnEnum.CODE));
                    entity.setCALFA_2(record.get(ViewCountriesController.CsvColumnEnum.ALFA2));
                    entity.setCALFA_3(record.get(ViewCountriesController.CsvColumnEnum.ALFA3));

                    U.callIf(() -> S.isNullOrEmpty(entity.getCLONGNAME()), entity::setCLONGNAME,
                            entity.getCSHORTNAME());

                    list.add(entity);
                }
            }

            return list;
        }
    }
}
