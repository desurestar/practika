package ru.inversion.countriescatalog;

import java.util.Collections;
import java.util.Map;
import ru.inversion.annotation.StartMethod;
import ru.inversion.countriescatalog.countries.ViewCountriesController;
import ru.inversion.fx.app.BaseApp;
import ru.inversion.fx.form.FXFormLauncher;
import ru.inversion.fx.form.ViewContext;
import ru.inversion.tc.TaskContext;

public class App extends BaseApp
{
    @Override
    protected void showMainWindow() {
        showViewAuditEntities(getPrimaryViewContext(), null, Collections.emptyMap());
    }

    @Override
    public String getAppID() {
        return "CountriesCatalog";
    }

    public static void main(String[] args) {
        launch(args);
    }

    @StartMethod(description = "Справочник стран")
    public static void showViewAuditEntities(ViewContext vc, TaskContext tc, Map<String, Object> map) {
        new FXFormLauncher<>(tc, vc, ViewCountriesController.class)
                .initProperties(map)
                .show();
    }
    
}
